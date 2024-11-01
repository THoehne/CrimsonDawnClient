package net.cdc.gui.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cdc.gui.config.WindowConfigData;
import net.cdc.gui.exceptions.WindowConfigException;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WindowConfig {

    private WindowConfigData windowConfigData;

    private final String config_path;

    /**
     * Maps the specified config file on to the {@code WindowPrefData} POJO during construction.
     *
     * @param config Name of the config file stored in conf.d.
     */
    public WindowConfig(String config) {
        this.config_path = config;

        ObjectMapper mapper = new ObjectMapper();
        File window_conf = new File(config);

        try {
            InputStream window_conf_in = new FileInputStream(window_conf);
            this.windowConfigData = mapper.readValue(window_conf_in, WindowConfigData.class);
        } catch (IOException e) {
            System.err.println("WARNING: Couldn't read file \"" + config + "\" at: " + window_conf.getAbsolutePath());
            setWindowDefault();
        }
    }

    /**
     * Writes the window preferences stored in the {@code WindowPrefData} POJO to the associated json file.
     */
    public void writeWindowConfig() throws WindowConfigException {
        ObjectMapper mapper = new ObjectMapper();
        File window_conf = new File(this.config_path);

        String json;

        try {
            json = mapper.writeValueAsString(this.windowConfigData);
        } catch (JsonProcessingException e) {
            throw new WindowConfigException("Error while converting window config to json.", e);
        }

        try {
            FileWriter writer = new FileWriter(window_conf);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new WindowConfigException("Error while trying to store window config.", e);
        }

    }

    /**
     * Contains window defaults, if the config cannot be read.
     */
    private void setWindowDefault() {
        this.windowConfigData = new WindowConfigData();
        this.windowConfigData.height = 240;
        this.windowConfigData.width = 320;
        this.windowConfigData.theme = "dark";
        this.windowConfigData.tray = false;
        this.windowConfigData.window_location = new double[]{-1.0, -1.0};
    }

    /**
     * Get height stored in config file.
     * @return Height
     */
    public int getHeight() {
        return this.windowConfigData.height;
    }

    /**
     * Get width stored in config file.
     * @return Width
     */
    public int getWidth() {
        return this.windowConfigData.width;
    }

    /**
     * Returns the absolute location of the window, calculated from main_viewport.json.<br>
     *
     * On window_location being [-1.0,-1.0], the center position of the window is calculated and returned.
     *
     * @return Absolute location of window on active screen.
     */
    public Point getLocation() {
        double x = this.windowConfigData.window_location[0];
        double y = this.windowConfigData.window_location[1];

        // return screen center, if x and y are -1
        if (x == -1 && y == -1) {
            int win_with = Toolkit.getDefaultToolkit().getScreenSize().width;
            int win_height = Toolkit.getDefaultToolkit().getScreenSize().height;

            return new Point(win_with / 2 - this.getWidth() / 2, win_height / 2 - this.getHeight() / 2);
        }

        int x_screen = (int)Math.floor(x * Toolkit.getDefaultToolkit().getScreenSize().width);
        int y_screen = (int)Math.floor(y * Toolkit.getDefaultToolkit().getScreenSize().height);

        // return position
        return new Point(x_screen, y_screen);
    }


    /**
     * Return name of the theme stored in config file. <br><br>
     * @return Theme name
     */
    public String getTheme() {
        return this.windowConfigData.theme;
    }

    /**
     * Stores whether window can be called from tray.
     *
     * @implNote Setting the tray to {@code True}
     * should cause the window default close operation to be {@code WindowConstants.HIDE_ON_CLOSE}.
     * Tray {@code false} should set the default close operation to {@code WindowConstants.EXIT_ON_CLOSE}.
     *
     * @return True, if tray active, false if not.
     */
    public boolean isTray() {
        return this.windowConfigData.tray;
    }


    /**
     * Gets the required minimum height of the window.
     * @return Minimum height
     */
    public int getMinHeight() {
        return this.windowConfigData.min_height;
    }


    /**
     * Gets the required minimum width of the window
     * @return Minimum width
     */
    public int getMinWidth() {
        return this.windowConfigData.min_width;
    }

    /**
     * Sets a preferred height in the window config.
     * @param height Preferred window height.
     */
    public void setHeight(int height) {
        if (height > 0) {
            this.windowConfigData.height = height;
        }
    }

    /**
     * Sets a preferred width in the window config.
     * @param width Preferred window width.
     */
    public void setWidth(int width) {
        if (width > 0) {
            this.windowConfigData.width = width;
        }
    }

    /**
     * Sets a preferred theme in the window config.
     * @param theme Preferred window theme.
     */
    public void setTheme(String theme) {
        this.windowConfigData.theme = theme;
    }

    /**
     * Sets whether tray is preferred for the window.
     * <p>See getTray for more information.</p>
     *
     * @param tray True activates tray, false deactivates it.
     */
    public void setTray(boolean tray) {
        this.windowConfigData.tray = tray;
    }

    /**
     * Sets whether the window should be resizable
     * @return True, if it should be made resizable, False if not.
     */
    public boolean getAllowResize() {
        return this.windowConfigData.allow_resize;
    }

    /**
     * Sets whether the window should be resizable.
     * @param allow_resize True allows resize. False disallows it.
     */
    public void setAllowResizable(boolean allow_resize) {
        this.windowConfigData.allow_resize = allow_resize;
    }


    /**
     * Sets the location for the window relative to the screen size.
     * <p>Relative locations prohibit the window from spawning of screen on a smaller window.</p>
     * <p>If [-1.0, -1.0] (default center) is stored in the window config, then nothing will be written.</p>
     * @param x Absolut x location on the screen
     * @param y Absolut y location on the screen
     * @param frame Reference to the frame, for width and height.
     */
    public void setLocation(int x, int y, JFrame frame) {

        // Don't override default center
        if (this.windowConfigData.window_location[0] == -1 && this.windowConfigData.window_location[1] == -1) {
            return;
        }

        double x_rel = (double) x / Toolkit.getDefaultToolkit().getScreenSize().width;
        double y_rel = (double) y / Toolkit.getDefaultToolkit().getScreenSize().height;

        this.windowConfigData.window_location[0] = x_rel;
        this.windowConfigData.window_location[1] = y_rel;
    }
}
