package net.djpowell.nioimage;

import java.awt.image.ColorModel;
import java.awt.image.Raster;

/**
 * Bi-level monochrome color model.
 *
 * @author David Powell
 * @since 23-Mar-2010
 */
public class DirectMonoColorModel extends ColorModel {

    /**
     * Construct a bi-level monochrome color model.
     */
    public DirectMonoColorModel() {
        super(8);
    }

    @Override
    public int getComponentSize(int componentIdx) {
        return 8;
    }

    @Override
    public int[] getComponentSize() {
        return new int[] {8};
    }

    @Override
    public int getPixelSize() {
        return 8;
    }

    @Override
    public int getRed(int pixel) {
        return (pixel & 0x000000ff) / 32 * 11;
    }

    @Override
    public int getRed(Object inData) {
        byte[] data = (byte[])inData;
        return (data[0] & 0xff) / 32 * 11;
    }

    @Override
    public int getGreen(int pixel) {
        return (pixel & 0x000000ff) / 32 * 16;
    }

    @Override
    public int getGreen(Object inData) {
        byte[] data = (byte[])inData;
        return (data[0] & 0xff) / 32 * 16;
    }

    @Override
    public int getBlue(int pixel) {
        return (pixel & 0x000000ff) / 32 * 5;
    }

    @Override
    public int getBlue(Object inData) {
        byte[] data = (byte[])inData;
        return (data[0] & 0xff) / 32 * 11;
    }

    @Override
    public int getAlpha(int pixel) {
        return 0;
    }

    @Override
    public int getAlpha(Object inData) {
        return 0;
    }

    @Override
    public boolean isCompatibleRaster(Raster raster) {
        return (raster instanceof NioWritableRaster &&
                ((NioWritableRaster)raster).getImageClass().isAssignableFrom(NioMonoImage.class));
    }

    @Override
    public Object getDataElements(int rgb, Object pixel) {
        byte a[] = (byte[])pixel;
        if (a == null) {
            a = new byte[1];
        }
        // ignore alpha, simple threshold conversion
        int grey = ( 11 * (getRed(rgb) & 0xff) +
                16 * (getGreen(rgb) & 0xff) +
                5 * (getBlue(rgb) & 0xff) ) / 32;
        a[0] = (byte)(grey & 0xff);
        return a;
    }

}