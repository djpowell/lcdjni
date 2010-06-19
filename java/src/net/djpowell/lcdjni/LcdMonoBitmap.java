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

    public static final Color LIT = Color.white;
    public static final Color UNLIT = Color.black;

    private LcdDevice device;
    private ByteBuffer buffer;
    private NioMonoImage image;

    LcdMonoBitmap(LcdDevice device) {
        this.device = device;
        this.buffer = Native.makelgLcdBitmapHeader(Native.cLGLCD_BMP_FORMAT_160x43x1);
        this.image = new NioMonoImage(buffer, Native.cPixelsOffset, 160, 43);
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