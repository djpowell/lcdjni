package net.djpowell.lcdjni;

import net.djpowell.nioimage.NioRGBAImage;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Color bitmap.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class LcdRGBABitmap implements LcdBitmap {

    private LcdDevice device;
    private ByteBuffer buffer;
    private NioRGBAImage image;

    LcdRGBABitmap(LcdDevice device) {
        this.device = device;
        this.buffer = Native.makelgLcdBitmapHeader(Native.cLGLCD_BMP_FORMAT_QVGAx32);
        this.image = new NioRGBAImage(buffer, Native.cPixelsOffset, 320, 240);
    }

    @Override
    public NioRGBAImage getImage() {
        return image;
    }

    @Override
    public Graphics getGraphics() {
        return image.getGraphics();
    }

    @Override
    public void updateScreen(Priority priority, SyncType syncType) {
        LcdException.checkResult(Native.lgLcdUpdateBitmap(device.getDevice(), buffer, priority.getCode() | syncType.getCode()));
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
