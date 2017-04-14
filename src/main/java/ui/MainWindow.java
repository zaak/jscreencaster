package ui;

import recorder.ByzanzRecorder;
import recorder.ScreenRecorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    protected Rectangle sourceDesktopRectangle = new Rectangle();
    protected ScreenRecorder screenRecorder;
    protected Thread recordingThread;

    public MainWindow() {
        setTitle("JScreencaster");
        setSize(260, 95);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //getContentPane().setBackground(new Color(0x00444444));

        JPanel buttonsPanel = new JPanel();

        setLayout(new BorderLayout());

        Dimension buttonSize = new Dimension(75, 40);

        Icon iconTarget = new ImageIcon(getClass().getResource("/icons/target2.png"));
        JButton buttonSelect = new JButton(iconTarget);
        buttonSelect.setPreferredSize(buttonSize);
        buttonSelect.addActionListener(e -> {
            SelectionOverlay so = new SelectionOverlay(sourceDesktopRectangle);
            so.display();
        });
        buttonsPanel.add(buttonSelect);

        Icon iconRecord = new ImageIcon(getClass().getResource("/icons/record.png"));
        JButton buttonRecordStop = new JButton(iconRecord);
        buttonRecordStop.setPreferredSize(buttonSize);
        buttonRecordStop.addActionListener(e -> {
            if (recordingThread != null && recordingThread.isAlive()) {
                screenRecorder.finish();
            } else {
                screenRecorder = new ByzanzRecorder();
                screenRecorder.setSourceDesktopRectangle(sourceDesktopRectangle);
                recordingThread = new Thread(screenRecorder);
                recordingThread.start();
            }
        });
        buttonsPanel.add(buttonRecordStop);

        Icon iconSettings = new ImageIcon(getClass().getResource("/icons/settings.png"));
        JButton buttonSettings = new JButton(iconSettings);
        buttonSettings.setPreferredSize(buttonSize);
        buttonsPanel.add(buttonSettings);

        add(buttonsPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Select area to record");
        statusLabel.setBorder(new EmptyBorder(2, 5, 2, 5));
        add(statusLabel, BorderLayout.PAGE_END);
    }
}
