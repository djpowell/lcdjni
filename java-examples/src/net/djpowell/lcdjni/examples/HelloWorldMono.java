package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.*;
import net.djpowell.lcdjni.LcdMonoBitmap;

import java.awt.*;

/**
 * Monochrome HelloWorld demo.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class HelloWorldMono {

    public static void main(String[] args) throws Exception {
        LcdConnection con = new LcdConnection("HelloWorldMono", false, AppletCapability.getCaps(AppletCapability.BW), null, null);
        try {
            LcdDevice device = con.openDevice(DeviceType.BW, null);
            try {
                LcdMonoBitmap bmp = device.createMonoBitmap(PixelColor.G15_REV_2);
                Graphics g = bmp.getGraphics();
                g.setColor(bmp.UNLIT);
                g.fillRect(0, 0, bmp.getImage().getWidth(), bmp.getImage().getHeight());
                g.setColor(bmp.LIT);
                g.drawString("Hello World!", 40, 20);
                g.dispose();
                bmp.updateScreen(Priority.ALERT, SyncType.SYNC);
                device.setForeground(true);
                Thread.sleep(10000);
            } finally {
                device.close();
            }
        } finally {
            con.close();
        }
        LcdConnection.deInit();
    }

}