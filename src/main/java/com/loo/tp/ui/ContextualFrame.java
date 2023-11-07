package com.loo.tp.ui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class ContextualFrame<T>  extends BaseFrame {
    protected T context;

    public ContextualFrame(T context) {
        this.context = context;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.init();
        if (shouldRender()) {
            this.context = context;
            this.render();
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            return;
        }
        this.close();
    }
}
