package recorder;

import util.CommandBuilder;
import util.DisplayManager;
import util.SettingsManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ByzanzRecorder extends Recorder {
    private static final String DELAY_COMMAND = "sleep 191337";

    @Override
    public void run() {
        System.out.println("ByzanzRecorder: Starting recording...");

        try {
            GraphicsDevice gd = DisplayManager.getSourceDevice();
            Rectangle desktopBounds = gd.getDefaultConfiguration().getBounds();

            int x = Math.min((int) sourceDesktopRectangle.getMaxX(), sourceDesktopRectangle.x) + desktopBounds.x;
            int y = Math.min((int) sourceDesktopRectangle.getMaxY(), sourceDesktopRectangle.y) + desktopBounds.y;
            int width = Math.abs(sourceDesktopRectangle.width);
            int height = Math.abs(sourceDesktopRectangle.height);

            CommandBuilder commandBuilder = new CommandBuilder();
            commandBuilder
                    .setCommand("byzanz-record")
                    .addParam("-x", x)
                    .addParam("-y", y)
                    .addParam("-w", width)
                    .addParam("-h", height)
                    .addParam("-e", DELAY_COMMAND);

            String targetFileName = "screenscast_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".gif";
            String targetFilePath = Paths.get(SettingsManager.getOutputDirectoryPath(), targetFileName).toString();
            commandBuilder.addParam(targetFilePath);

            System.out.println("Running command: \n" + String.join(" ", commandBuilder.build()));

            ProcessBuilder processBuilder = new ProcessBuilder(commandBuilder.build());
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process process = processBuilder.start();

            onRecordStartCallback.run();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

            onRecordStopCallback.run();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }

        System.out.println("ByzanzRecorder: Recording stopped.");
    }

    @Override
    public void finish() {
        try {
            System.out.println("ByzanzRecorder: Stopping recording...");
            ProcessBuilder pb = new ProcessBuilder("pkill", "-nf", DELAY_COMMAND);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
