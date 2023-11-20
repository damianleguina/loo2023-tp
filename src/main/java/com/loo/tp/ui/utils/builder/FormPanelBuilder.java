package com.loo.tp.ui.utils.builder;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class FormPanelBuilder {
    private JPanel formPanel;

    public FormPanelBuilder() {
        this.formPanel = new JPanel();
        this.formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        this.formPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    }

    public JTextField addTextField(String labelText) {
        var panel = this.getFieldPanel();
        panel.add(this.getLabel(labelText));

        var textField = new JTextField();
        panel.add(textField);

        formPanel.add(panel);
        return textField;
    }

    public JPasswordField addPasswordField(String labelText) {
        var panel = this.getFieldPanel();
        panel.add(this.getLabel(labelText));

        var passwordField = new JPasswordField();
        panel.add(passwordField);

        formPanel.add(panel);
        return passwordField;
    }

    public JCheckBox addCheckbox(String labelText) {
        var panel = this.getFieldPanel();
        panel.add(this.getLabel(labelText));

        var checkBox = new JCheckBox();
        panel.add(checkBox);

        formPanel.add(panel);
        return checkBox;
    }

    public JSpinner addSpinner(String labelText) {
        var panel = this.getFieldPanel();
        panel.add(this.getLabel(labelText));

        var spinner = new JSpinner();
        panel.add(spinner);

        formPanel.add(panel);
        return spinner;
    }

    public JComboBox<String> addComboBox(String labelText, String[] values) {
        var panel = this.getFieldPanel();
        panel.add(this.getLabel(labelText));

        var comboBox = new JComboBox<String>(values);
        panel.add(comboBox);

        formPanel.add(panel);

        return comboBox;
    }

    public JPanel build() {
        return this.formPanel;
    }

    private JPanel getFieldPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        var dimension = new Dimension(300, 26);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        panel.setPreferredSize(dimension);
        return panel;
    }

    private JLabel getLabel(String labelText) {
        var label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 26));
        return label;
    }
}
