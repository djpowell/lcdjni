package net.djpowell.nioimage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

/**
 * A WritableRaster implementation that is capable of writing the image data to a NIO ByteBuffer.
 * 
 * @author David Powell
 * @since 23-Mar-2010
 */
public class NioWritableRaster extends WritableRaster {

    private final Class<? extends BufferedImage> imageClass;

    /**
     * Construct a WritableRaster suitable for writing to a NIO ByteBuffer.
     * 
     * @param imageClass must be NioMonoImage.class or NioRGBAImage.class
     * @param sampleModel sampleModel
     * @param dataBuffer dataBuffer
     */
    public NioWritableRaster(Class<? extends BufferedImage> imageClass, SampleModel sampleModel, DataBuffer dataBuffer) {
        super(sampleModel, dataBuffer, new Point(0, 0));
        this.imageClass = imageClass;
    }

    final Class<? extends BufferedImage> getImageClass() {
        return imageClass;
    }

}
