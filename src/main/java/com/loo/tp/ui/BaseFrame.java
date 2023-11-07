package com.loo.tp.ui;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Container;
import java.awt.event.ActionListener;

public abstract class BaseFrame extends JFrame implements ActionListener {
    public BaseFrame() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    protected void close() {
        this.dispose();
    }

    protected void init() {
    };

    protected boolean shouldRender() {
        return true;
    };

    protected abstract void render();

    protected JButton createButton(String label, Container container) {
        var button = new JButton(label);
        button.addActionListener(this);
        container.add(button);
        return button;
    }

    protected JButton createButton(String label) {
        return this.createButton(label, this);
    }
}
