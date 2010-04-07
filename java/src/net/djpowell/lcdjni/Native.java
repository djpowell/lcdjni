package net.djpowell.lcdjni;

import java.nio.ByteBuffer;

/**
 * Low-level interface to JNI functions and constants.
 * Typically, you will want to use {@link net.djpowell.lcdjni.LcdConnection} rather than this class.
 *
 * @author David Powell
 * @since 03-Apr-2010
 */
public class Native {

    static {
        System.loadLibrary("lcdjni-c");
    }

    public native static int lgLcdInit();
    public native static int lgLcdDeInit();

    public native static int lgLcdConnectEx(ByteBuffer ctx);
    public native static ByteBuffer makelgLcdConnectContextEx(String appFriendlyName, boolean isAutoStartable, int appletCapabilitiesSupported, int context, boolean hasConfig);
    public native static void freelgLcdConnectContextEx(ByteBuffer bb);

    public native static int lgLcdDisconnect(int connection);

    public native static int lgLcdOpenByType(ByteBuffer ctx);
    public native static ByteBuffer makelgLcdOpenByTypeContext(int connection, int deviceType, int context);
    public native static void freelgLcdOpenByTypeContext(ByteBuffer bb);

    public native static int lgLcdClose(int device);

    public native static int lgLcdReadSoftButtons(int device, ByteBuffer buttons);
    public native static ByteBuffer makeDword();
    public native static void freeDword(ByteBuffer bb);

    public native static int lgLcdUpdateBitmap(int device, ByteBuffer bmp, int priority);
    public native static ByteBuffer makelgLcdBitmapHeader(int format);
    public native static void freelgLcdBitmapHeader(ByteBuffer bb);
    
    public native static int lgLcdSetAsLCDForegroundApp(int device, int foreground);

    private native static int[] getConstants();

    private static int[] constants = getConstants();
    private static int cc = 0;
    public final static int cDeviceOffset = constants[cc++];
    public final static int cConnectionOffset = constants[cc++];
    public final static int cPixelsOffset = constants[cc++];
    public final static int cLGLCD_BMP_FORMAT_160x43x1 = constants[cc++];
    public final static int cLGLCD_BMP_FORMAT_QVGAx32 = constants[cc++];
    public final static int cLGLCD_NOTIFICATION_DEVICE_ARRIVAL = constants[cc++];
    public final static int cLGLCD_NOTIFICATION_DEVICE_REMOVAL = constants[cc++];
    public final static int cLGLCD_NOTIFICATION_APPLET_ENABLED = constants[cc++];
    public final static int cLGLCD_NOTIFICATION_APPLET_DISABLED = constants[cc++];
    public final static int cLGLCD_NOTIFICATION_CLOSE_CONNECTION = constants[cc++];
    public final static int cLGLCD_APPLET_CAP_BASIC = constants[cc++];
    public final static int cLGLCD_APPLET_CAP_BW = constants[cc++];
    public final static int cLGLCD_APPLET_CAP_QVGA = constants[cc++];
    public final static int cLGLCD_DEVICE_BW = constants[cc++];
    public final static int cLGLCD_DEVICE_QVGA = constants[cc++];
    public final static int cRPC_S_SERVER_UNAVAILABLE = constants[cc++];
    public final static int cERROR_OLD_WIN_VERSION  = constants[cc++];
    public final static int cERROR_NO_SYSTEM_RESOURCES  = constants[cc++];
    public final static int cERROR_ALREADY_INITIALIZED  = constants[cc++];
    public final static int cERROR_SUCCESS  = constants[cc++];
    public final static int cERROR_SERVICE_NOT_ACTIVE = constants[cc++];
    public final static int cERROR_INVALID_PARAMETER = constants[cc++];
    public final static int cERROR_FILE_NOT_FOUND = constants[cc++];
    public final static int cERROR_ALREADY_EXISTS = constants[cc++];
    public final static int cRPC_X_WRONG_PIPE_VERSION = constants[cc++];
    public final static int cERROR_DEVICE_NOT_CONNECTED = constants[cc++];
    public final static int cLGLCD_PRIORITY_IDLE_NO_SHOW = constants[cc++];
    public final static int cLGLCD_PRIORITY_BACKGROUND = constants[cc++];
    public final static int cLGLCD_PRIORITY_NORMAL = constants[cc++];
    public final static int cLGLCD_PRIORITY_ALERT = constants[cc++];
    public final static int cLGLCD_SYNC_UPDATE = constants[cc++];
    public final static int cLGLCD_ASYNC_UPDATE = constants[cc++];
    public final static int cLGLCD_SYNC_COMPLETE_WITHIN_FRAME = constants[cc++];
    public final static int cLGLCD_LCD_FOREGROUND_APP_NO = constants[cc++];
    public final static int cLGLCD_LCD_FOREGROUND_APP_YES = constants[cc++];
    public final static int cERROR_LOCK_FAILED = constants[cc++];
    public final static int cLGLCDBUTTON_LEFT = constants[cc++];
    public final static int cLGLCDBUTTON_RIGHT = constants[cc++];
    public final static int cLGLCDBUTTON_OK = constants[cc++];
    public final static int cLGLCDBUTTON_CANCEL = constants[cc++];
    public final static int cLGLCDBUTTON_UP = constants[cc++];
    public final static int cLGLCDBUTTON_DOWN = constants[cc++];
    public final static int cLGLCDBUTTON_MENU = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON0 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON1 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON2 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON3 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON4 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON5 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON6 = constants[cc++];
    public final static int cLGLCDBUTTON_BUTTON7 = constants[cc++];
    static {
        // clear ref to constants array now that we have used it
        constants = null;
    }

    /*
     * Called from jni
     */
    private static void masterOnKeyChange(int device, int buttons, int context) {
        LcdDevice.dispatchKeyCallback(context, buttons);
    }

    /*
     * Called from jni
     */
    private static void masterOnConfigure(int connection, int context) {
        LcdConnection.dispatchConfigCallback(context);
    }

    /*
     * Called from jni
     */
    private static void masterOnNotification(int connection, int context, int code, int p1, int p2, int p3, int p4) {
        LcdConnection.dispatchNotificationCallback(context, code, p1, p2, p3, p4);
    }

}
