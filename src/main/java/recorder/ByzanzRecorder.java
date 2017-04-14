package recorder;

import helper.CommandBuilder;
import helper.Screen;

import java.awt.*;

public class ByzanzRecorder extends ScreenRecorder {
    protected static final String DELAY_COMMAND = "sleep 191337";

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

            CommandBuilder commandBuilder = new CommandBuilder();
            commandBuilder.setCommand("byzanz-record")
                          .addParam("-x", x)
                          .addParam("-y", y)
                          .addParam("-w", width)
                          .addParam("-h", height)
                          .addParam("-e", DELAY_COMMAND)
                          .addParam("-c")
                          .addParam("/home/zaak/tmp/o.gif");

            ProcessBuilder pb = new ProcessBuilder(commandBuilder.build());
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
            ProcessBuilder pb = new ProcessBuilder("pkill", "-nf", "sleep 191337");
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
