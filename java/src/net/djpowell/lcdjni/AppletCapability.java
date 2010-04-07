package net.djpowell.lcdjni;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Applet capabilities supported by the connection.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public enum AppletCapability {

    /**
     * Support legacy connection.  (Probably doesn't work!)
     */
    BASIC(Native.cLGLCD_APPLET_CAP_BASIC),

    /**
     * Support a black and white connection.
     */
    BW(Native.cLGLCD_APPLET_CAP_BW),

    /**
     * Support a color connection.
     */
    QVGA(Native.cLGLCD_APPLET_CAP_QVGA);

    private final int code;

    private AppletCapability(int code) {
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
     * Create a set of capabilities.
     * 
     * @param caps var-arg list of BASIC, BW, and QVGA
     * @return set of capabilities
     */
    public static EnumSet<AppletCapability> getCaps(AppletCapability... caps) {
        EnumSet<AppletCapability> ret = EnumSet.noneOf(AppletCapability.class);
        ret.addAll(Arrays.asList(caps));
        return ret;
    }

}
