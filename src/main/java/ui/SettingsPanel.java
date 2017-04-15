package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private static final int MAX_HEIGHT = 170;

    public SettingsPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JSeparator(SwingConstants.HORIZONTAL));

        addLabel(new JLabel("Active display:"));
        add((new JComboBox<>(new String[]{"AAAa", "Bbbbbb"})));

        addLabel(new JLabel("Output directory:"));

        add(new JTextField());

        addCheckbox(new JCheckBox("Record mouse cursor", true));
    }

    private Component add(JComponent component) {
        Dimension tsize = new Dimension(
                component.getMaximumSize().width,
                component.getPreferredSize().height + 5);
        component.setMaximumSize(tsize);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        return super.add(component);
    }

    private Component addLabel(JLabel label) {
        label.setBorder(new EmptyBorder(5,3,0,0));

        return this.add(label);
    }

    private Component addCheckbox(JCheckBox checkbox) {
        checkbox.setBorder(new EmptyBorder(5,0,0,0));

        return this.add(checkbox);
    }

    public int calculateHeight() {
        return MAX_HEIGHT;
    }
}
