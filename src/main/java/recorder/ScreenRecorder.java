package recorder;

import java.awt.*;

public abstract class ScreenRecorder implements Runnable {
    protected Rectangle sourceDesktopRectangle;

    public void setSourceDesktopRectangle(Rectangle sourceDesktopRectangle) {
        this.sourceDesktopRectangle = sourceDesktopRectangle;
    }

    public abstract void finish();
}
