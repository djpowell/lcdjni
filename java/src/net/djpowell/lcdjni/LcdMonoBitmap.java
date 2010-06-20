package net.djpowell.lcdjni;

import net.djpowell.nioimage.NioMonoImage;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Monochrome bitmap.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class LcdMonoBitmap implements LcdBitmap {

    private LcdDevice device;
    private ByteBuffer buffer;
    private NioMonoImage image;

    /**
     * Black on first-gen G15, orange on second-gen G15, and white on G19
     */
    public final Color LIT;

    /**
     * White on first-gen G15, dark on second-gen G15, and black on G19
     */
    public final Color UNLIT;

    LcdMonoBitmap(LcdDevice device, PixelColor pixelColor) {
        this.device = device;
        this.buffer = Native.makelgLcdBitmapHeader(Native.cLGLCD_BMP_FORMAT_160x43x1);
        this.image = new NioMonoImage(buffer, Native.cPixelsOffset, 160, 43, pixelColor);
        boolean setIsDark = pixelColor.isSetDark();
        LIT = setIsDark ? Color.black : Color.white;
        UNLIT = setIsDark ? Color.white : Color.black;
    }

    @Override
    public NioMonoImage getImage() {
        return image;
    }

    @Override
    public Graphics getGraphics() {
        return image.getGraphics();
    }

    @Override
    public void updateScreen(Priority priority, SyncType syncType) {
        Native.lgLcdUpdateBitmap(device.getDevice(), buffer, priority.getCode() | syncType.getCode());
    }

    @Override
    public void close() throws IOException {
        ByteBuffer bb = this.buffer;
        this.device = null;
        this.buffer = null;
        this.image = null;
        Native.freelgLcdBitmapHeader(bb);
    }

}