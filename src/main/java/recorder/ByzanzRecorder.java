package recorder;

import helper.Screen;

import java.awt.*;

public class ByzanzRecorder extends ScreenRecorder {
    @Override
    public void run() {
        System.out.println("Starting recording...");

        try {
            GraphicsDevice gd = Screen.getSourceDevice();
            Rectangle desktopBounds = gd.getDefaultConfiguration().getBounds();

            int x = Math.min((int)sourceDesktopRectangle.getMaxX(), sourceDesktopRectangle.x) + desktopBounds.x;
            int y = Math.min((int)sourceDesktopRectangle.getMaxY(), sourceDesktopRectangle.y) + desktopBounds.y;
            int width = Math.abs(sourceDesktopRectangle.width);
            int height = Math.abs(sourceDesktopRectangle.height);

            System.out.println(">>>  " + sourceDesktopRectangle.width + " - " + sourceDesktopRectangle.height);

            ProcessBuilder pb = new ProcessBuilder("byzanz-record", "-x", Integer.toString(x), "-y", Integer.toString(y), "-w", Integer.toString(width), "-h", Integer.toString(height), "-e", "sleep 191449", "-c", "/home/zaak/tmp/o.gif");
            pb.redirectOutput();
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Stopping recording...");
    }

    @Override
    public void finish() {
        try {
            ProcessBuilder pb = new ProcessBuilder("pkill", "-nf", "sleep 191449");
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
