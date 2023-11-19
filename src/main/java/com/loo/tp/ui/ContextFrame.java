package com.loo.tp.ui;

public abstract class ContextFrame<T>  extends BaseFrame {
    protected T context;

    public ContextFrame(T context) {
        this.context = context;
        this.init();
        if (shouldRender()) {
            this.context = context;
            this.onRender();
            return;
        }
        this.close();
    }
}
