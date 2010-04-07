package net.djpowell.lcdjni;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;

/**
 * Common interface for all types of bitmap.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public interface LcdBitmap extends Closeable {

    /**
     * Return the BufferedImage instance that is wrapped by this interface.
     * 
     * @return BufferedImage
     */
    BufferedImage getImage();

    /**
     * Create a Graphics context for the image.
     * Note that after drawing to the image, the updateScreen method must be called to make the
     *
     * changes visible on-screen.
     * @return Graphics context
     */
    Graphics getGraphics();

    /**
     * Update the keyboard screen with the state of the wrapped image.
     * @param priority priority hint to influence automatic screen display switching by LCD Manager
     *
     * @param syncType whether to block until the screen has been updated
     */
    void updateScreen(Priority priority, SyncType syncType);
    
}
