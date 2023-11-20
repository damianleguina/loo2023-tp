package com.loo.tp.ui.utils.builder;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionPanelBuilder {
    private JPanel panel;
    private ActionListener actionListener;

    public ActionPanelBuilder(ActionListener actionListener) {
        this.panel = new JPanel();
        this.actionListener = actionListener;
    }

    public JButton createButton(String label) {
        var button = new JButton(label);
        button.addActionListener(actionListener);
        panel.add(button);
        return button;
    }

    public JPanel build() {
        return panel;
    }
}
