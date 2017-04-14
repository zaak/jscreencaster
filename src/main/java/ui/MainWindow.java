package ui;

import recorder.ByzanzRecorder;
import recorder.ScreenRecorder;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    protected Rectangle sourceDesktopRectangle = new Rectangle();
    protected ScreenRecorder screenRecorder;
    protected Thread recordingThread;

    public MainWindow() {
        setSize(260, 80);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new FlowLayout());

        Dimension buttonSize = new Dimension(40, 40);

        Icon iconTarget = new ImageIcon(getClass().getResource("/icons/target.png"));
        JButton buttonSelect = new JButton(iconTarget);
        buttonSelect.setPreferredSize(buttonSize);
        buttonSelect.addActionListener(e -> {
            SelectionOverlay so = new SelectionOverlay(sourceDesktopRectangle);
            so.display();
        });
        add(buttonSelect);

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
        add(buttonRecordStop);

        Icon iconSettings = new ImageIcon(getClass().getResource("/icons/settings.png"));
        JButton buttonSettings = new JButton(iconSettings);
        buttonSettings.setPreferredSize(buttonSize);
        add(buttonSettings);
    }
}

/*
      "byzanz": {
         "title": "Byzanz",
         "enabled": true,
         "-sound-on": "-a",
         "-sound-off": "",
         "custom": {
            "GIF (Window)": "#DC_WINDOW_HELPER# byzanz-record {SOUND} -e 'sleep 191449' -x {X} -y {Y} -w {WIDTH} -h {HEIGHT} {DIRECTORY}/{FILENAME}.gif",
            "GIF (Select)": "#DC_AREA_HELPER# byzanz-record {SOUND} -e 'sleep 191449' -x {X} -y {Y} -w {WIDTH} -h {HEIGHT} {DIRECTORY}/{FILENAME}.gif",
            "Stop recording": "pkill -nf 'sleep 191449'"
         }
      }
 */