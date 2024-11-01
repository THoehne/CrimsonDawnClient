package net.cdc.gui.eventlistener.targetedevent;

import javax.swing.*;

public abstract class TargetedEventListener {
    protected JComponent component;

    public void setComponent(JComponent component) {
        this.component = component;
    }
}
