package util;

import java.awt.*;

public class DisplayManager {
    public static GraphicsDevice getSourceDevice() {
        GraphicsDevice[] gds = listDevices();
        GraphicsDevice graphicsDevice = gds[0];

        for (GraphicsDevice gd : gds ) {
            if ( gd.getIDstring().equals(SettingsManager.getActiveDisplayId())) {
                graphicsDevice = gd;
                break;
            }
        }

        return graphicsDevice;
    }

    public static GraphicsDevice[] listDevices() {
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();

        return ge.getScreenDevices();
    }
}
