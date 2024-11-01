package net.cdc.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import net.cdc.gui.Window;
import net.cdc.gui.config.WindowConfig;
import net.cdc.gui.viewport.LoginViewport;

public class CrimsonDawnClient {
    public CrimsonDawnClient() {
        init();
    }

    private void init() {
        WindowConfig mainWindowPref = new WindowConfig("conf.d/window_config.json");

        loadTheme(mainWindowPref.getTheme());

        Window mainWindow = new Window(mainWindowPref);
        mainWindow.setViewport(new LoginViewport());
        mainWindow.setVisible(true);
    }


    private void loadTheme(String theme_id) {
        switch (theme_id) {
            case "dark":
                FlatLaf.registerCustomDefaultsSource("themes");
                FlatDarkLaf.setup();
                break;
            default:
                System.err.println("WARNING: Unsupported theme " + theme_id + " fallback to swing default theme.");
        }
    }
}
