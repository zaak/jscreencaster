import ui.MainWindow;
import util.SettingsManager;

import javax.swing.*;
import javax.swing.UIManager.*;

public class App {
    public static void main(String[] args) {
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
}
