package net.djpowell.lcdjni;

/**
 * Callback to support life-cycle events. 
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public interface NotificationCallback {

    /**
     * Called when device of the given type has arrived.
     *
     * @param deviceType deviceType
     */
    void onDeviceArrival(DeviceType deviceType);

    /**
     * Called when all devices of the given type have been removed.
     *
     * @param deviceType deviceType
     */
    void onDeviceRemoval(DeviceType deviceType);

    /**
     * Called when the applet is enabled via LCD Manager
     */
    void onAppletEnable();

    /**
     * Called when the applet is disabled via LCD Manager 
     */
    void onAppletDisable();

    /**
     * Called when the connection is closed. 
     */
    void onConnectionClosure();

}