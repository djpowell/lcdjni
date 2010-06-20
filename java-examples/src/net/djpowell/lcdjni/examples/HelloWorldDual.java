package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.*;
import net.djpowell.lcdjni.AppletCapability;
import net.djpowell.lcdjni.LcdException;
import net.djpowell.lcdjni.LcdMonoBitmap;
import net.djpowell.lcdjni.LcdRGBABitmap;

import java.awt.*;

/**
 * Dual-Mode HelloWorld demo.
 * Works on both G15 and G19 keyboards.
 *
 * @author David Powell
 * @since 19-Jun-2010
 */
public class HelloWorldDual {

    public static void main(String[] args) throws Exception {
        LcdConnection con = new LcdConnection("HelloWorldMono", false, AppletCapability.getCaps(AppletCapability.BW, AppletCapability.QVGA), null, null);
        try {
            LcdDevice colorDevice;
            try {
                colorDevice = con.openDevice(DeviceType.QVGA, null);
            } catch (LcdException e) {
                colorDevice = null; // no color device
            }

            LcdDevice monoDevice;
            try {
                monoDevice = con.openDevice(DeviceType.BW, null);
            } catch (LcdException e) {
                monoDevice = null; // no color device
            }

            try {
                if (colorDevice != null) {
                    doColor(colorDevice);
                }
                if (monoDevice != null) {
                    doMono(monoDevice);
                }
                Thread.sleep(10000);
            } finally {
                if (colorDevice != null) colorDevice.close();
                if (monoDevice != null) monoDevice.close();
            }

        } finally {
            con.close();
        }
        LcdConnection.deInit();
    }

    private static void doColor(LcdDevice colorDevice) {
        LcdRGBABitmap colorBmp = colorDevice.createRGBABitmap();
        Graphics g = colorBmp.getGraphics();
        g.setColor(Color.red);
        g.fillRect(0, 0, colorBmp.getImage().getWidth(), colorBmp.getImage().getHeight());
        g.setColor(Color.green);
        g.drawString("Hello World!", 100, 100);
        g.dispose();
        colorBmp.updateScreen(Priority.ALERT, SyncType.SYNC);
        colorDevice.setForeground(true);
    }

    private static void doMono(LcdDevice monoDevice) {
        LcdMonoBitmap monoBmp = monoDevice.createMonoBitmap(PixelColor.G15_REV_2);
        Graphics g = monoBmp.getGraphics();
        g.setColor(monoBmp.UNLIT);
        g.fillRect(0, 0, monoBmp.getImage().getWidth(), monoBmp.getImage().getHeight());
        g.setColor(monoBmp.LIT);
        g.drawString("Hello World!", 40, 20);
        g.dispose();
        monoBmp.updateScreen(Priority.ALERT, SyncType.SYNC);
        monoDevice.setForeground(true);
    }

}