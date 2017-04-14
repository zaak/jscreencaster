package helper;

import java.awt.*;

public class Screen {
    public static GraphicsDevice getSourceDevice() {
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice graphicsDevice = gs[0];

        for (GraphicsDevice gd : gs ) {
            if ( gd.getIDstring().equals(Settings.getSourceDeviceId())) {
                graphicsDevice = gd;
                break;
            }
        }

        return graphicsDevice;
    }
}
