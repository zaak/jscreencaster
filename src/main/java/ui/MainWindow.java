package ui;

import recorder.ByzanzRecorder;
import recorder.ScreenRecorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private static final String TITLE = "JScreencaster";

    private Rectangle sourceDesktopRectangle = new Rectangle();
    private ScreenRecorder screenRecorder;
    private Thread recordingThread;

    private JButton buttonSelect;
    private JButton buttonRecordStop;
    private JToggleButton buttonSettings;
    private SettingsPanel settingsPanel;

//    private JLabel statusLabel;

    public MainWindow() {
        setTitle(TITLE);
        setSize(260, 75);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //getContentPane().setBackground(new Color(0x00444444));

        JPanel buttonsPanel = new JPanel();

        setLayout(new BorderLayout());

        Dimension buttonSize = new Dimension(75, 40);

        Icon iconTarget = new ImageIcon(getClass().getResource("/icons/target2.png"));
        buttonSelect = new JButton(iconTarget);
        buttonSelect.setPreferredSize(buttonSize);
        buttonSelect.addActionListener(e -> {
            SelectionOverlay so = new SelectionOverlay(sourceDesktopRectangle);
            so.onSelected(() -> buttonRecordStop.setEnabled(true));
            so.display();
        });
        buttonsPanel.add(buttonSelect);

        Icon iconRecord = new ImageIcon(getClass().getResource("/icons/record.png"));
        Icon iconStop = new ImageIcon(getClass().getResource("/icons/stop.png"));
        buttonRecordStop = new JButton(iconRecord);
        buttonRecordStop.setPreferredSize(buttonSize);
        buttonRecordStop.addActionListener(e -> {
            if (recordingThread != null && recordingThread.isAlive()) {
                buttonRecordStop.setEnabled(false);
                screenRecorder.finish();
            } else {
                screenRecorder = new ByzanzRecorder();
                screenRecorder.setSourceDesktopRectangle(sourceDesktopRectangle);

                screenRecorder.onRecordStart(() -> {
                    setTitle("Recording...");
                    buttonRecordStop.setIcon(iconStop);
                    buttonSelect.setEnabled(false);
                    buttonSettings.setEnabled(false);
                } );

                screenRecorder.onRecordStop(() -> {
                    setTitle(TITLE);
                    buttonRecordStop.setIcon(iconRecord);
                    buttonSelect.setEnabled(true);
                    buttonRecordStop.setEnabled(true);
                    buttonSettings.setEnabled(true);
                });

                recordingThread = new Thread(screenRecorder);
                recordingThread.start();
            }
        });
        buttonRecordStop.setEnabled(false);
        buttonsPanel.add(buttonRecordStop);

        Icon iconSettings = new ImageIcon(getClass().getResource("/icons/settings.png"));
        buttonSettings = new JToggleButton(iconSettings);
        buttonSettings.setPreferredSize(buttonSize);
        buttonSettings.addActionListener(e -> {
            if (buttonSettings.isSelected()) {
                setSize(getWidth(), getHeight() + settingsPanel.calculateHeight());
            } else {
                setSize(getWidth(), getHeight() - settingsPanel.calculateHeight());
            }
        });
        buttonsPanel.add(buttonSettings);

        add(buttonsPanel, BorderLayout.PAGE_START);

        settingsPanel = new SettingsPanel();
        add(settingsPanel, BorderLayout.CENTER);
    }
}
