package ui;

import util.DisplayManager;
import util.SettingsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class SettingsPanel extends JPanel {
    private static final int MAX_HEIGHT = 140;

    public SettingsPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JSeparator(SwingConstants.HORIZONTAL));

        addLabel(new JLabel("Active display:"));
        add(createDisplaysComboBox());

        //

        addLabel(new JLabel("Output directory:"));

        JPanel directoryChooserPanel = new JPanel();
        directoryChooserPanel.setLayout(new BoxLayout(directoryChooserPanel, BoxLayout.X_AXIS));

        JTextField outputhDirPathText = new JTextField(SettingsManager.getOutputDirectoryPath());
        outputhDirPathText.setEditable(false);
        directoryChooserPanel.add(outputhDirPathText);

        JButton outputDirBrowseButton = new JButton("...");
        outputDirBrowseButton.addActionListener(e -> {
            JFileChooser dirChooser = new JFileChooser();
            dirChooser.setCurrentDirectory(new java.io.File(SettingsManager.getOutputDirectoryPath()));
            dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            dirChooser.setAcceptAllFileFilterUsed(false);

            if (dirChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String chosenDirPath = dirChooser.getSelectedFile().toString();
                outputhDirPathText.setText(chosenDirPath);
                SettingsManager.setOutputDirectoryPath(chosenDirPath);
            }
        });
        directoryChooserPanel.add(outputDirBrowseButton);

        add(directoryChooserPanel);
    }

    private Component add(JComponent component) {
        Dimension alteredMaxSize = new Dimension(
                component.getMaximumSize().width,
                component.getPreferredSize().height + 5);
        component.setMaximumSize(alteredMaxSize);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        return super.add(component);
    }

    private Component addLabel(JLabel label) {
        label.setBorder(new EmptyBorder(5, 3, 0, 0));

        return this.add(label);
    }

    private JComboBox<GDInfoItem> createDisplaysComboBox() {
        GraphicsDevice[] gds = DisplayManager.listDevices();

        ArrayList<GDInfoItem> gdInfoItems = new ArrayList<>();

        GDInfoItem selectedItem = null;

        for (GraphicsDevice gd : gds) {
            GDInfoItem gdInfoItem = new GDInfoItem(gd);
            gdInfoItems.add(gdInfoItem);

            if (SettingsManager.getActiveDisplayId().equals(gdInfoItem.getIdString())) {
                selectedItem = gdInfoItem;
            }
        }

        JComboBox<GDInfoItem> displaysComboBox = new JComboBox<>(gdInfoItems.toArray(new GDInfoItem[gdInfoItems.size()]));

        if (selectedItem != null) {
            displaysComboBox.setSelectedItem(selectedItem);
        }

        displaysComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                GDInfoItem gdInfo = (GDInfoItem) e.getItem();
                SettingsManager.setActiveDisplayId(gdInfo.getIdString());
                System.out.println("Changed active display to: " + gdInfo.getIdString());
            }
        });

        return displaysComboBox;
    }

    public int calculateHeight() {
        return MAX_HEIGHT;
    }
}
