import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setSize(260, 80);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new FlowLayout());

        Dimension buttonSize = new Dimension(40, 40);

        Icon iconTarget = new ImageIcon(getClass().getResource("icons/target.png"));
        JButton buttonSelect = new JButton(iconTarget);
        buttonSelect.setPreferredSize(buttonSize);
        add(buttonSelect);

        Icon iconRecord = new ImageIcon(getClass().getResource("icons/record.png"));
        JButton buttonRecordStop = new JButton(iconRecord);
        buttonRecordStop.setPreferredSize(buttonSize);
        add(buttonRecordStop);

        Icon iconSettings = new ImageIcon(getClass().getResource("icons/settings.png"));
        JButton buttonSettings = new JButton(iconSettings);
        buttonSettings.setPreferredSize(buttonSize);
        add(buttonSettings);
    }
}
