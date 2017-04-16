import ui.MainWindow;
import util.SettingsManager;

import javax.swing.*;
import javax.swing.UIManager.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        if(!isByzanzInstalled()) {
            return;
        }

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Nimbus is not available, use default look and feel.
        }

        SettingsManager.loadSettings();

        MainWindow mw = new MainWindow();
        mw.setVisible(true);
    }

    private static boolean isByzanzInstalled() {
        try {
            Runtime.getRuntime().exec("byzanz-record");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Byzanz executable not found.\nThe application will now close.");
            System.err.println(e.toString());
        }

        return false;
    }
}
