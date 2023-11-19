package com.loo.tp.ui;

public abstract class AppFrame extends BaseFrame {
    public AppFrame() {
        this.init();
        if (shouldRender()) {
            this.onRender();
            return;
        }
        this.onFailure();
    }
}
