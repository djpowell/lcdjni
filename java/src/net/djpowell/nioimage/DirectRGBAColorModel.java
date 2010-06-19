package net.djpowell.nioimage;

import java.awt.image.ColorModel;
import java.awt.image.Raster;

/**
 * RGB32 color model.
 *
 * @author David Powell
 * @since 23-Mar-2010
 */
public class DirectRGBAColorModel extends ColorModel {

    /**
     * Construct an RGB32 color model.
     */
    public DirectRGBAColorModel() {
        super(8);
    }

    @Override
    public int getComponentSize(int componentIdx) {
        return 8;
    }

    @Override
    public int[] getComponentSize() {
        return new int[] {8, 8, 8, 8};
    }

    @Override
    public int getPixelSize() {
        return 32;
    }

    @Override
    public int getRed(int pixel) {
        return ((pixel & 0x00ff0000) >> 16) & 0xff;
    }

    @Override
    public int getRed(Object inData) {
        byte[] data = (byte[])inData;
        return data[1] & 0xff;
    }

    @Override
    public int getGreen(int pixel) {
        return ((pixel & 0x0000ff00) >> 8) & 0xff;
    }

    @Override
    public int getGreen(Object inData) {
        byte[] data = (byte[])inData;
        return data[2] & 0xff;        
    }

    @Override
    public int getBlue(int pixel) {
        return (pixel & 0x000000ff);
    }

    @Override
    public int getBlue(Object inData) {
        byte[] data = (byte[])inData;
        return data[3] & 0xff;        
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
                ((NioWritableRaster)raster).getImageClass().isAssignableFrom(NioRGBAImage.class));
    }

    @Override
    public Object getDataElements(int rgb, Object pixel) {
        byte a[] = (byte[])pixel;
        if (a == null) {
            a = new byte[4];
        }
        a[0] = 0;
        a[1] = (byte)(getRed(rgb) & 0xff);
        a[2] = (byte)(getGreen(rgb) & 0xff);
        a[3] = (byte)(getBlue(rgb) & 0xff);
        return a;
    }

}
