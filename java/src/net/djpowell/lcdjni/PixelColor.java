package net.djpowell.lcdjni;

/**
 * Color of set pixels on monochrome screen
 *          
 * @author David Powell
 * @since 20-Jun-2010
 */
public enum PixelColor {
    /** Generic setting equivalent to G15_REV_2 and G19 */
    SET_IS_LIGHT(false),

    /** Generic setting equivalent to G15_REV_1 */
    SET_IS_DARK(true),

    G15_REV_1(true),
    G15_REV_2(false),
    G19(false);

    private final boolean setIsDark;

    private PixelColor(boolean setIsDark) {
        this.setIsDark = setIsDark;
    }

    public boolean isSetDark() {
        return setIsDark;
    }

}
