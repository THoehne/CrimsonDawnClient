package net.cdc.gui.config;

public class WindowConfigData {
    public int height;
    public int min_height;
    public int width;
    public int min_width;
    public double[] window_location; // Location is relative to the screensize -> Eliminates the possibility of window going of screen, when starting on a smaller display.

    public String theme;
    public boolean tray;
    public boolean allow_resize;
}
