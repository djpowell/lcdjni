package net.djpowell.lcdjni;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Connection to the device.
 * 
 * @author David Powell
 * @since 05-Apr-2010
 */
public class LcdDevice implements Closeable {

    private static final ConcurrentHashMap<Integer, KeyHandler> keyHandlers = new ConcurrentHashMap<Integer, KeyHandler>();
    static void dispatchKeyCallback(int context, int buttons) {
        KeyHandler handler = keyHandlers.get(context);
        if (handler != null) {
            handler.onKey(buttons);
        } else {
//            System.err.println("DEBUG: Key Callback not found: " + device);
        }
    }
     
    private final LcdConnection conn;
    private int device;

    /**
     * Construct a device connection.
     * 
     * @param conn connection
     * @param deviceType deviceType
     * @param keyCallback soft-key callback
     */
    LcdDevice(LcdConnection conn, DeviceType deviceType, KeyCallback keyCallback) {
        this.conn = conn;
        if (keyCallback != null) keyHandlers.put(conn.getContext(), new KeyHandler(keyCallback));
        ByteBuffer bb = Native.makelgLcdOpenByTypeContext(conn.getConnection(), deviceType.getCode(), conn.getContext());
        try {
            LcdException.checkResult(Native.lgLcdOpenByType(bb));
            bb.position(Native.cDeviceOffset);
            this.device = bb.order(ByteOrder.nativeOrder()).asIntBuffer().get(0);
        } finally {
            Native.freelgLcdOpenByTypeContext(bb);
        }
    }

    /**
     * Close the current device connection.
     */
    public void close() {
        int d = this.device;
        keyHandlers.remove(conn.getContext());
        this.device = -1;
        LcdException.checkResult(Native.lgLcdClose(d));
    }

    /**
     * Get the native device code.
     *
     * @return native device code.
     */
    public int getDevice() {
        return this.device;
    }

    /**
     * Create a color bitmap that can be drawn on to.
     * @return color bitmap.
     */
    public LcdRGBABitmap createRGBABitmap() {
        return new LcdRGBABitmap(this);
    }

    /**
     * Create a monochrome bitmap that can be drawn on to.
     * @return mono bitmap.
     */             
    public LcdMonoBitmap createMonoBitmap() {
        return new LcdMonoBitmap(this);
    }

    /**
     * Asynchronously read the status of the soft-buttons.
     *
     * @see KeyCallback#onKey(int) 
     * @return the current button state, consisting of zero or more of the constants of this class or'd together.
     */
    public int readSoftButtons() {
        ByteBuffer dword = Native.makeDword();
        try {
            LcdException.checkResult(Native.lgLcdReadSoftButtons(this.device, dword));
            return dword.order(ByteOrder.nativeOrder()).asIntBuffer().get(0);
        } finally {
            Native.freeDword(dword);
        }
    }

    /**
     * Attempt to raise the applet to the foreground, or to cancel the request.
     *
     * @param foreground true to raise, false to cancel.
     */
    public void setForeground(boolean foreground) {
        LcdException.checkResult(Native.lgLcdSetAsLCDForegroundApp(this.device, foreground ? Native.cLGLCD_LCD_FOREGROUND_APP_YES : Native.cLGLCD_LCD_FOREGROUND_APP_NO));
    }

}
