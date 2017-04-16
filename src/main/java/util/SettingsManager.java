package util;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsManager {
    private static String activeDisplayId = DisplayManager.getSourceDevice().getIDstring();
    private static String outputDirectory = System.getProperty("user.dir");
    private static Point windowLocation = new Point(0, 0);

    private static Path getSettingsFilePath() {
        return Paths.get(System.getProperty("user.home"), ".config", "jscreencaster", "settings.properties");
    }

    public static void loadSettings() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(getSettingsFilePath().toString()));

            activeDisplayId = properties.getProperty("activeDisplayId");
            outputDirectory = properties.getProperty("outputDirectory");
            windowLocation = new Point(
                    Integer.parseInt(properties.getProperty("windowLocationX")),
                    Integer.parseInt(properties.getProperty("windowLocationY"))
            );
        } catch (Exception e) {
            System.err.println("Couldn't load settings file. Used path: " + getSettingsFilePath());
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }

    private static void saveSettings() {
        try {
            Properties properties = new Properties();
            properties.setProperty("activeDisplayId", activeDisplayId);
            properties.setProperty("outputDirectory", outputDirectory);
            properties.setProperty("windowLocationX", Integer.toString(windowLocation.x));
            properties.setProperty("windowLocationY", Integer.toString(windowLocation.y));

            Path settingsFilePath = getSettingsFilePath();
            Path settingsDirectoryPath = settingsFilePath.getParent();

            if (!Files.exists(settingsDirectoryPath)) {
                Files.createDirectory(settingsDirectoryPath);
            }

            FileOutputStream outStream = new FileOutputStream(new File(settingsFilePath.toString()));
            properties.store(outStream, "JScreencaster settings");
            outStream.close();
        } catch (Exception e) {
            System.err.println("Couldn't save settings file. Used path: " + getSettingsFilePath());
            System.err.println(e.toString());
            e.printStackTrace();
        }

    }

    public static String getActiveDisplayId() {
        return activeDisplayId;
    }

    public static String getOutputDirectoryPath() {
        return outputDirectory;
    }

    public static Point getWindowLocation() {
        return windowLocation;
    }

    public static void setActiveDisplayId(String _activeDisplayId) {
        activeDisplayId = _activeDisplayId;

        saveSettings();
    }

    public static void setOutputDirectoryPath(String _outputDirectory) {
        outputDirectory = _outputDirectory;

        saveSettings();
    }

    public static void setWindowLocation(Point _windowLocation) {
        windowLocation = _windowLocation;

        saveSettings();
    }
}
