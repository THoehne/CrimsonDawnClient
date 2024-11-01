package net.cdc.gui;

import net.cdc.gui.config.WindowConfig;
import net.cdc.gui.exceptions.WindowConfigException;
import net.cdc.gui.viewport.Viewport;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Window extends JFrame {

    private final WindowConfig windowConfig;
    private Viewport viewport;

    public Window(WindowConfig windowConfig) {
        this.windowConfig = windowConfig;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(this.windowConfig.getWidth(), this.windowConfig.getHeight());
        this.setResizable(this.windowConfig.getAllowResize());
        this.setLocation(this.windowConfig.getLocation());
        this.setMinimumSize(new Dimension(this.windowConfig.getMinWidth(), this.windowConfig.getMinHeight()));

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/CrimsonDawnLogoSmall.png")));
        this.setIconImage(icon.getImage());

        this.setCloseLogic();
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
        this.setContentPane(viewport);
        this.setTitle(viewport.getTitle());
    }

    private void setCloseLogic() {
        Window mvt = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mvt.windowConfig.setHeight(mvt.getHeight());
                mvt.windowConfig.setWidth(mvt.getWidth());
                mvt.windowConfig.setLocation(mvt.getLocation().x, mvt.getLocation().y, mvt);

                try {
                    mvt.windowConfig.writeWindowConfig();
                } catch (WindowConfigException ex) {
                    System.err.println("WARNING: Couldn't write window pref: " + ex);
                }

                super.windowClosing(e);
            }
        });
    }
}