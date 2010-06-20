package net.djpowell.nioimage;

import java.awt.image.ColorModel;
import java.awt.image.Raster;

/**
 * Bi-level monochrome color model, with 0 as white, and 255 as black.
 *
 * @author David Powell
 * @since 19-Jun-2010
 */
public class DirectInvertedMonoColorModel extends ColorModel {

    /**
     * Construct a bi-level monochrome color model.
     */
    public DirectInvertedMonoColorModel() {
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
        return (~ pixel) & 0xff;
    }

    @Override
    public int getRed(Object inData) {
        byte[] data = (byte[])inData;
        return (~ data[0]) & 0xff;
    }

    @Override
    public int getGreen(int pixel) {
        return (~ pixel) & 0xff;
    }

    @Override
    public int getGreen(Object inData) {
        byte[] data = (byte[])inData;
        return (~ data[0]) & 0xff;
    }

    @Override
    public int getBlue(int pixel) {
        return (~ pixel) & 0xff;
    }

    @Override
    public int getBlue(Object inData) {
        byte[] data = (byte[])inData;
        return (~ data[0]) & 0xff;
    }

    @Override
    public int getAlpha(int pixel) {
        return 255; // don't support alpha, full opacity
    }

    @Override
    public int getAlpha(Object inData) {
        return 255; // don't support alpha, full opacity
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
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = rgb & 0xff;
        int grey = ( (red * 77) +
                (green * 150) +
                (blue * 28) ) / 255;
        a[0] = (byte)((~ grey) & 0xff);
        return a;
    }

}