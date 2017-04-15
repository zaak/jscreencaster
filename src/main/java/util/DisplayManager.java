package util;

import java.awt.*;

public class DisplayManager {
    public static GraphicsDevice getSourceDevice() {
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice graphicsDevice = gs[0];

        for (GraphicsDevice gd : gs ) {
            if ( gd.getIDstring().equals(SettingsManager.getSourceDeviceId())) {
                graphicsDevice = gd;
                break;
            }
        }

        return graphicsDevice;
    }
}
