package util;

public class SettingsManager {
    public static String getSourceDeviceId() {
        return ":0.1";
    }

    public static String getOutputDirectoryPath() {
        return System.getProperty("user.dir");
    }
}
