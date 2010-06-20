package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.*;

import java.awt.*;

/**
 * Color HelloWorld demo.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class HelloWorld {

    public static void main(String[] args) throws Exception {
        LcdConnection con = new LcdConnection("HelloWorld", false, AppletCapability.getCaps(AppletCapability.QVGA), null, null);
        try {
            LcdDevice device = con.openDevice(DeviceType.QVGA, null);
            try {
                LcdRGBABitmap bmp = device.createRGBABitmap();
                Graphics g = bmp.getGraphics();
                g.setColor(Color.red);
                g.fillRect(0, 0, 320, 240);
                g.setColor(Color.white);
                g.drawString("Hello World!", 100, 100);
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
