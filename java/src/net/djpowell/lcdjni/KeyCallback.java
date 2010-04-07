package net.djpowell.lcdjni;

/**
 * Callback to support the keyboard softkeys.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public interface KeyCallback {

    public final static int LEFT = Native.cLGLCDBUTTON_LEFT;
    public final static int RIGHT = Native.cLGLCDBUTTON_RIGHT;
    public final static int OK = Native.cLGLCDBUTTON_OK;
    public final static int CANCEL = Native.cLGLCDBUTTON_CANCEL;
    public final static int UP = Native.cLGLCDBUTTON_UP;
    public final static int DOWN = Native.cLGLCDBUTTON_DOWN;
    public final static int MENU = Native.cLGLCDBUTTON_MENU;

    public final static int BUTTON0 = Native.cLGLCDBUTTON_BUTTON0;
    public final static int BUTTON1 = Native.cLGLCDBUTTON_BUTTON1;
    public final static int BUTTON2 = Native.cLGLCDBUTTON_BUTTON2;
    public final static int BUTTON3 = Native.cLGLCDBUTTON_BUTTON3;
    public final static int BUTTON4 = Native.cLGLCDBUTTON_BUTTON4;
    public final static int BUTTON5 = Native.cLGLCDBUTTON_BUTTON5;
    public final static int BUTTON6 = Native.cLGLCDBUTTON_BUTTON6;
    public final static int BUTTON7 = Native.cLGLCDBUTTON_BUTTON7;

    /**
     * Called whenever the key state changes.
     *
     * @param buttons the current button state, consisting of zero or more of the constants of this class or'd together
     */
    void onKey(int buttons);

    /**
     * Called whenever a key is pressed.
     * When multiple buttons are pressed together, multiple calls will be made to these callbacks,
     * describing a single key per call.
     *
     * @param button a single button
     */
    void onKeyDown(int button);

    /**
    * Called whenever a key is released.
    * When multiple buttons are pressed together, multiple calls will be made to these callbacks,
    * describing a single key per call.
     * 
    * @param button a single button
    */
    void onKeyUp(int button);

}