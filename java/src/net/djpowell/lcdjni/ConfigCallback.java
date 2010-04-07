package net.djpowell.lcdjni;

/**
 * Callback to support the 'Configure' button on the LCD Manager 'Programs' tab.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public interface ConfigCallback {

    /**
     * Called when the 'Configure' button is pressed on the LCD Manager 'Programs' tab.
     */
    void onConfig();

}
