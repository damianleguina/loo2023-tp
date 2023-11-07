package com.loo.tp.ui;

public abstract class AppFrame extends BaseFrame {
    public AppFrame() {
        this.init();
        if (shouldRender()) {
            this.render();
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            return;
        }
        this.onFailure();
    }
}
