package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Color EventTest demo.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class EventTest {

    public static void main(String[] args) throws Exception {

        ConfigCallback configCallback = new ConfigCallback() {
            public void onConfig() {
                JOptionPane.showMessageDialog(null, "Example Config Screen");
            }
        };

        final List<String> notifications = new Vector<String>();

        NotificationCallback notifCallback = new NotificationCallback() {
            public void onDeviceArrival(DeviceType deviceType) {
                notifications.add(0, "ARRIVE " + deviceType);
            }

            public void onDeviceRemoval(DeviceType deviceType) {
                notifications.add(0, "REMOVE " + deviceType);
            }

            public void onAppletEnable() {
                notifications.add(0, "ENABLE");
            }

            public void onAppletDisable() {
                notifications.add(0, "DISABLE");
            }

            public void onConnectionClosure() {
                notifications.add(0, "CLOSE");
            }
        };

        LcdConnection con = new LcdConnection("EventTest", false, AppletCapability.getCaps(AppletCapability.QVGA), configCallback, notifCallback);
        try {
            final AtomicBoolean exit = new AtomicBoolean(false);
            final AtomicInteger key = new AtomicInteger(0);

            KeyCallback keyCallback = new KeyCallback() {
                public void onKey(int buttons) {
                    key.set(buttons);
                }

                public void onKeyDown(int button) {
                    // do nothing
                }

                public void onKeyUp(int button) {
                    if (button == CANCEL) exit.set(true);
                }
            };
            
            LcdDevice device = con.openDevice(DeviceType.QVGA, keyCallback);
            try {
                LcdRGBABitmap bmp = device.createRGBABitmap();
                bmp.updateScreen(Priority.NORMAL, SyncType.SYNC);
                device.setForeground(true);

                float color = 0.0f;
                while (!exit.get()) {
                    color += 0.01f;
                    Graphics g = bmp.getGraphics();
                    g.setColor(Color.getHSBColor(color, 1.0f, 1.0f));
                    g.fillRect(0, 0, 320, 240);
                    g.setColor(Color.black);
                    g.setFont(g.getFont().deriveFont(Font.BOLD));
                    g.drawString("Keys: " + key.get(), 100, 100);
                    for (int n=0; n<notifications.size(); n++) {
                        g.drawString("Notif: " + notifications.get(n), 120, 120 + (n * 20));
                    }
                    g.dispose();
                    bmp.updateScreen(Priority.NORMAL, SyncType.SYNC);
                }

            } finally {
                device.close();
            }
        } finally {
            con.close();
        }
        LcdConnection.deInit();
    }

}