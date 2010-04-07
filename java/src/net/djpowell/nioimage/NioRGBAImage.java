package net.djpowell.nioimage;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.PixelInterleavedSampleModel;
import java.nio.ByteBuffer;
import java.util.Hashtable;

/**
 * A BufferedImage for RGBA32 images, backed by a NIO ByteBuffer
 *
 * @author David Powell
 * @since 22-Mar-2010
 */
public class NioRGBAImage extends BufferedImage {

    /**
     * Construct a BufferedImage for RGBA32 images, backed by a NIO ByteBuffer. 
     * @param bb NIO ByteBuffer
     * @param headerLen number of bytes to skip before the image data when writing to the ByteBuffer
     * @param width width of the image in pixels
     * @param height height of the image in pixels
     */
    public NioRGBAImage(ByteBuffer bb, int headerLen, int width, int height) {
        this(new DirectRGBAColorModel(), bb, headerLen, width, height);
    }

    /*
     * Helper constructor taking ColorModel
     */
    private NioRGBAImage(ColorModel cm, ByteBuffer bb, int headerLen, int width, int height) {
        super(cm,
                new NioWritableRaster(
                        NioRGBAImage.class,
                        new PixelInterleavedSampleModel(
                                DataBuffer.TYPE_BYTE,
                                width, height,
                                cm.getPixelSize() / 8,
                                width * cm.getPixelSize() / 8,
                                new int[] { 3, 2, 1, 0 }),
                        new NioDataBuffer(bb, headerLen)
                ),
        false, new Hashtable());
    }

}
