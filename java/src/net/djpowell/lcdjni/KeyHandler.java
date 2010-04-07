package net.djpowell.lcdjni;

/**
 * Helper class for calculating key-state changes
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
class KeyHandler {

    private final KeyCallback keyCallback;
    private int lastK = 0;

    KeyHandler(KeyCallback keyCallback) {
        this.keyCallback = keyCallback;
    }

    private static final int[] keys = {
            KeyCallback.CANCEL,
            KeyCallback.DOWN,
            KeyCallback.LEFT,
            KeyCallback.MENU,
            KeyCallback.OK,
            KeyCallback.RIGHT,
            KeyCallback.UP,
            KeyCallback.BUTTON0,
            KeyCallback.BUTTON1,
            KeyCallback.BUTTON2,
            KeyCallback.BUTTON3,
            KeyCallback.BUTTON4,
            KeyCallback.BUTTON5,
            KeyCallback.BUTTON6,
            KeyCallback.BUTTON7,
    };

    void onKey(int buttons) {
        keyCallback.onKey(buttons);
        int newBits = buttons & (~lastK);
        int oldBits = (~buttons) & lastK;
        for (int n : keys) {
            if ((newBits & n) != 0) {
                keyCallback.onKeyDown(n);
            }
            if ((oldBits & n) != 0) {
                keyCallback.onKeyUp(n);
            }
        }
        lastK = buttons;
    }

}