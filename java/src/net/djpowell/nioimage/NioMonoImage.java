package net.djpowell.nioimage;

import net.djpowell.lcdjni.PixelColor;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SinglePixelPackedSampleModel;
import java.nio.ByteBuffer;
import java.util.Hashtable;

/**
 * A BufferedImage for monochrome images, backed by a NIO ByteBuffer
 *
 * @author David Powell
 * @since 22-Mar-2010
 */
public class NioMonoImage extends BufferedImage {

    /**
     * Construct a BufferedImage for mono images, backed by a NIO ByteBuffer.
     * @param bb NIO ByteBuffer
     * @param headerLen number of bytes to skip before the image data when writing to the ByteBuffer
     * @param width width of the image in pixels
     * @param height height of the image in pixels
     * @param pixelColor indicates whether set pixels are dark or light
     */
    public NioMonoImage(ByteBuffer bb, int headerLen, int width, int height, PixelColor pixelColor) {
        this(pixelColor.isSetDark() ? new DirectInvertedMonoColorModel() : new DirectMonoColorModel(),
                bb, headerLen, width, height);
    }
    
    /*
     * Helper constructor
     */
    private NioMonoImage(ColorModel cm, ByteBuffer bb, int headerLen, int width, int height) {
        super(cm,
                new NioWritableRaster(
                        NioMonoImage.class,
                        new SinglePixelPackedSampleModel(
                                DataBuffer.TYPE_BYTE,
                                width, height,
                                new int[] { 0 }),
                        new NioDataBuffer(bb, headerLen)
                ),
        false, new Hashtable());
    }

}