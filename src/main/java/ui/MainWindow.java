package ui;

import recorder.ByzanzRecorder;
import recorder.ScreenRecorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private Rectangle sourceDesktopRectangle = new Rectangle();
    private ScreenRecorder screenRecorder;
    private Thread recordingThread;

    private JButton buttonSelect;
    private JButton buttonRecordStop;
    private JButton buttonSettings;

//    private JLabel statusLabel;

    public MainWindow() {
        setTitle("JScreencaster");
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
//                    statusLabel.setText("Recording...");
                    buttonRecordStop.setIcon(iconStop);
                    buttonSelect.setEnabled(false);
                    buttonSettings.setEnabled(false);
                } );

                screenRecorder.onRecordStop(() -> {
//                    statusLabel.setText("");
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
        buttonSettings = new JButton(iconSettings);
        buttonSettings.setPreferredSize(buttonSize);
        buttonsPanel.add(buttonSettings);

        add(buttonsPanel, BorderLayout.CENTER);
//
//        statusLabel = new JLabel("Select area to record.");
//        statusLabel.setBorder(new EmptyBorder(2, 5, 2, 5));
//        add(statusLabel, BorderLayout.PAGE_END);
    }
}
