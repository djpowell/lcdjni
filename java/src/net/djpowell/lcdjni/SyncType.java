package net.djpowell.lcdjni;

/**
 * Whether to block until the screen update is complete.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public enum SyncType {

    /**
     * The update request should block until the frame has been displayed.
     */
    SYNC(Native.cLGLCD_SYNC_UPDATE),

    /**
     * The update request will return immediately, and the frame may be displayed later.
     */
    ASYNC(Native.cLGLCD_ASYNC_UPDATE),

    /**
     * The update request should block until the frame has been displayed, or will throw
     * an exception if the applet is not currently visible.  
     */
    SYNC_COMPLETE(Native.cLGLCD_SYNC_COMPLETE_WITHIN_FRAME);

    private final int code;

    private SyncType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
