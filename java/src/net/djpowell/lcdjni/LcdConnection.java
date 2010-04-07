package net.djpowell.lcdjni;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Primary interface to the API.
 *
 * Create an instance of this class to get started.
 *
 * The init() method is called automatically, but the deInit() method should be called to release the API.
 * The init() method only needs to be called explicitly, if you need to re-init() the API after calling
 * deInit().
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class LcdConnection implements Closeable {

    private static final AtomicBoolean inited = new AtomicBoolean(false);

    /**
     * Initialise the API, if it isn't already initialised.
     */
    public static void init() {
        if (!inited.get()) {
            LcdException.checkResult(Native.lgLcdInit());
            inited.set(true);
        }
    }

    /**
     * De-Initialise the API, if it isn't already de-initialised.
     */
    public static void deInit() {
        if (inited.get()) {
            LcdException.checkResult(Native.lgLcdDeInit());
            inited.set(false);
        }
    }

    static {
        init();
    }

    private static final ConcurrentHashMap<Integer, ConfigCallback> configCallbacks = new ConcurrentHashMap<Integer, ConfigCallback>();
    static void dispatchConfigCallback(int context) {
        ConfigCallback callback = configCallbacks.get(context);
        if (callback != null) {
            callback.onConfig();
        } else {
//            System.err.println("DEBUG: Config Callback not found: " + context);
        }
    }

    private static final ConcurrentHashMap<Integer, NotificationCallback> notificationCallbacks = new ConcurrentHashMap<Integer, NotificationCallback>();
    static void dispatchNotificationCallback(int context, int code, int p1, int p2, int p3, int p4) {
        NotificationCallback callback = notificationCallbacks.get(context);
        if (callback != null) {
            if (code == Native.cLGLCD_NOTIFICATION_APPLET_DISABLED) callback.onAppletDisable();
            else if (code == Native.cLGLCD_NOTIFICATION_APPLET_ENABLED) callback.onAppletEnable();
            else if (code == Native.cLGLCD_NOTIFICATION_CLOSE_CONNECTION) callback.onConnectionClosure();
            else if (code == Native.cLGLCD_NOTIFICATION_DEVICE_ARRIVAL) callback.onDeviceArrival(DeviceType.fromCode(p1));
            else if (code == Native.cLGLCD_NOTIFICATION_DEVICE_REMOVAL) callback.onDeviceRemoval(DeviceType.fromCode(p1));
            else {
                System.err.println("DEBUG: Unknown notification code: " + code);
            }
        } else {
//            System.err.println("DEBUG: Notification Callback not found: " + context);
        }
    }
    
    private static final AtomicInteger contextCounter = new AtomicInteger(1);

    private final int context = contextCounter.getAndIncrement();
    private int conn;

    /**
     * Create a connection to the API.
     *
     * @param appFriendlyName the title of your applet to be shown in the user interface
     * @param isAutoStartable whether the applet can be restarted from LCD Manager.
     * Note that unless you have created a custom Java starter .exe, this should be set to 'false'
     * as the LCD Manager won't pass the necessary parameters to java when it tries to restart your
     * applet. 
     * @param appletCapabilities the set of applet capabilities supported by the applet.
     * @param configCallback a callback to be called when the 'Configure' button is pressed on the LCD Manager UI.  Can be null.
     * @param notificationCallback a callback to be called to notify of life-cycle events.  Can be null.
     */
    public LcdConnection(String appFriendlyName, boolean isAutoStartable, EnumSet<AppletCapability> appletCapabilities, ConfigCallback configCallback, NotificationCallback notificationCallback) {
        int caps = 0;
        for (AppletCapability cap : appletCapabilities) {
            caps |= cap.getCode();
        }
        if (configCallback != null) configCallbacks.put(context, configCallback);
        if (notificationCallback != null) notificationCallbacks.put(context, notificationCallback);
        ByteBuffer bb = Native.makelgLcdConnectContextEx(appFriendlyName, isAutoStartable, caps, context, configCallback != null);
        try {
            LcdException.checkResult(Native.lgLcdConnectEx(bb));
            bb.position(Native.cConnectionOffset);
            this.conn = bb.order(ByteOrder.nativeOrder()).asIntBuffer().get(0);
        } finally {
            Native.freelgLcdConnectContextEx(bb);
        }
    }

    /**
     * Close the current connection.
     * Note that any LcdDevice references will not be automatically released.
     */
    public void close() {
        int c = this.conn;
        configCallbacks.remove(context);
        notificationCallbacks.remove(context);
        this.conn = -1;
        LcdException.checkResult(Native.lgLcdDisconnect(c));
    }

    /**
     * Create a new connection to a device.
     *
     * @param deviceType the type of devices to connect to
     * @param keyCallback keyCallback
     * @return a callback to be called when the soft-keys are pressed.  Can be null.
     */
    public LcdDevice openDevice(DeviceType deviceType, KeyCallback keyCallback) {
        return new LcdDevice(this, deviceType, keyCallback);
    }

    /**
     * Get the native connection code.
     * @return native connection code.
     */
    public int getConnection() {
        return conn;
    }

    /**
     * Get the native context id.
     * @return native context id.
     */
    public int getContext() {
        return context;
    }

}
