package com.loo.tp.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Dimension;
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

    protected void setDefaultSize() {
        this.setPreferredSize(new Dimension(800, 400));
    }

    protected void onRender() {
        this.setDefaultSize();
        this.render();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    protected void onFailure() {
        this.close();
    }

    protected void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE, null);
    }

    protected void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Éxito",
                JOptionPane.INFORMATION_MESSAGE, null);
    }
}
