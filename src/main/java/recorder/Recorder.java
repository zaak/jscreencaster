package recorder;

import java.awt.*;

public abstract class Recorder implements Runnable {
    protected Rectangle sourceDesktopRectangle;
    protected Runnable onRecordStartCallback = () -> {};
    protected Runnable onRecordStopCallback = () -> {};

    public void setSourceDesktopRectangle(Rectangle sourceDesktopRectangle) {
        this.sourceDesktopRectangle = sourceDesktopRectangle;
    }

    public abstract void finish();

    public void onRecordStart(Runnable onRecordStartCallback) {
        this.onRecordStartCallback = onRecordStartCallback;
    }

    public void onRecordStop(Runnable onRecordStopCallback) {
        this.onRecordStopCallback = onRecordStopCallback;
    }
}
