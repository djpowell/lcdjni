package net.djpowell.lcdjni;

/**
 * Device Type to open for the applet.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public enum DeviceType {

    /**
     * Black and white screen.
     */
    BW(Native.cLGLCD_DEVICE_BW),

    /**
     * Color screen.
     */
    QVGA(Native.cLGLCD_DEVICE_QVGA);
    
    private final int code;

    private DeviceType(int code) {
        this.code = code;
    }

    /**
     * Get the native code.
     * 
     * @return native code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Create an enum from the native code.
     *
     * @param code native code
     * @return BW or QVGA
     */
    public static DeviceType fromCode(int code) {
        if (code == Native.cLGLCD_DEVICE_BW) return BW;
        else if (code == Native.cLGLCD_DEVICE_QVGA) return QVGA;
        else return null;
    }
    
}
