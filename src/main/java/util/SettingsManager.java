package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsManager {
    private static String activeDisplayId;
    private static String outputDirectory = System.getProperty("user.dir");
    private static boolean recordCursor = true;

    private static Path getSettingsFilePath() {
        return Paths.get(System.getProperty("user.home"), ".config", "jscreencaster", "settings.properties");
    }

    public static void loadSettings() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(getSettingsFilePath().toString()));

            activeDisplayId = properties.getProperty("activeDisplayId");
            outputDirectory = properties.getProperty("outputDirectory");
            recordCursor = Boolean.parseBoolean(properties.getProperty("recordCursor"));
        } catch (Exception e) {
            System.err.println("Couldn't load settings file. Used path: " + getSettingsFilePath());
            System.err.println(e.toString());
        }
    }

    private static void saveSettings() {
        try {
            Properties properties = new Properties();
            properties.setProperty("activeDisplayId", activeDisplayId);
            properties.setProperty("outputDirectory", outputDirectory);
            properties.setProperty("recordCursor", Boolean.toString(recordCursor));

            Path settingsFilePath = getSettingsFilePath();
            Path settingsDirectoryPath = settingsFilePath.getParent();

            if (!Files.exists(settingsDirectoryPath)) {
                Files.createDirectory(settingsDirectoryPath);
            }

            FileOutputStream outStream = new FileOutputStream(new File(settingsFilePath.toString()));
            properties.store(outStream, "JScreencaster settings");
        } catch (Exception e) {
            System.err.println("Couldn't save settings file. Used path: " + getSettingsFilePath());
            System.err.println(e.toString());
        }

    }

    public static String getActiveDisplayId() {
        return activeDisplayId;
    }

    public static String getOutputDirectoryPath() {
        return outputDirectory;
    }

    public static boolean getRecordCursor() {
        return recordCursor;
    }

    public static void setActiveDisplayId(String _activeDisplayId) {
        activeDisplayId = _activeDisplayId;

        saveSettings();
    }

    public static void setOutputDirectoryPath(String _outputDirectory) {
        outputDirectory = _outputDirectory;

        saveSettings();
    }

    public static void setRecordCursor(boolean _recordCursor) {
        recordCursor = _recordCursor;

        saveSettings();
    }
}
