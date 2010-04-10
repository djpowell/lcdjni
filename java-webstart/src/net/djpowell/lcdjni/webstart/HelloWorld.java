package net.djpowell.lcdjni.webstart;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.PrintStream;

import net.djpowell.lcdjni.*;

/**
 * Color HelloWorld demo.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class HelloWorld {

    public static void main(String[] args) throws Throwable {
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
                bmp.updateScreen(Priority.NORMAL, SyncType.SYNC);
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