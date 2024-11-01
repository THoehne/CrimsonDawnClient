package net.cdc.gui.viewport;

import javax.swing.*;

public abstract class Viewport extends JPanel {

    private final String title;

    public Viewport(String title) {
        this.title = title;
        this.addComponents();
    }

    public String getTitle() {
        return title;
    }

    /**
     * This method gets called from the constructor and should add the necessary components to the window.
     */
    protected abstract void addComponents();
}