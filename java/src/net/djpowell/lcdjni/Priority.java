package net.djpowell.lcdjni;

/**
 * Priority to show the applet at.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public enum Priority {

    /**
     * High priority
     */
    ALERT(Native.cLGLCD_PRIORITY_ALERT),

    /**
     * Low priority
     */
    BACKGROUND(Native.cLGLCD_PRIORITY_BACKGROUND),

    /**
     * Disable display
     */
    IDLE_NO_SHOW(Native.cLGLCD_PRIORITY_IDLE_NO_SHOW),

    /**
     * Normal priority
     */
    NORMAL(Native.cLGLCD_PRIORITY_NORMAL);

    private final int code;

    private Priority(int code) {
        this.code = code;
    }

    /**
     * Return the native code
     * @return native code
     */
    public int getCode() {
        return code;
    }

}