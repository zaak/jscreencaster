import java.awt.*;

public class Screen {
    public static GraphicsDevice getSourceDevice() {
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];

        for (GraphicsDevice _gd : gs ) {
            if ( _gd.getIDstring().equals(Settings.getSourceDeviceId())) {
                gd = _gd;
                break;
            }
        }

        return gd;
    }
}
