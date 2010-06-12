package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.*;

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
                LcdMonoBitmap bmp = device.createMonoBitmap();
                Graphics g = bmp.getGraphics();
                g.setColor(Color.black);
                g.fillRect(0, 0, bmp.getImage().getWidth(), bmp.getImage().getHeight());
                g.setColor(Color.white);
                g.drawString("Hello World!", 40, 20);
                g.dispose();
                bmp.updateScreen(Priority.ALERT, SyncType.SYNC);
                device.setForeground(true);
                Thread.sleep(5000);
            } finally {
                device.close();
            }
        } finally {
            con.close();
        }
        LcdConnection.deInit();
    }

}