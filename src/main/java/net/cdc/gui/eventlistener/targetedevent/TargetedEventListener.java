package net.cdc.gui.eventlistener.targetedevent;

import javax.swing.*;

public abstract class TargetedEventListener {
    protected JComponent target;

    public void setTarget(JComponent component) {
        this.target = component;
    }

    public JComponent getTarget() {
        return target;
    }
}
