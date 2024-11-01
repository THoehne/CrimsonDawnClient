package net.cdc.gui.viewport;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import net.cdc.gui.dialog.DialogFactory;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LoginViewport extends Viewport {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox rememberMe;
    private JComboBox<String> serverSelector;
    
    private JLabel forgotPassword;

    public LoginViewport() {
        super("CrimsonDawn | Login");
        setLayout(new MigLayout("fill, insets 0", "[][]", "fill"));
    }

    @Override
    protected void addComponents() {
        JPanel panel = new JPanel(new MigLayout("wrap, fill, insets 0", "fill, 250, 350", "[center]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "[dark]background:darken(@background, 3%)");
        Insets border_insets = new Insets(0, 0, 0, 8);
        panel.setBorder(new FlatDropShadowBorder(Color.BLACK, border_insets, 0.5f));

        // Inner panel, to center form.
        JPanel innerPanel = new JPanel(new MigLayout("wrap, fill, insets 20 35 20 35", "fill, 250, 350"));
        innerPanel.putClientProperty(FlatClientProperties.STYLE,
                "[dark]background:darken(@background, 3%)");


        JLabel lbl_title = new JLabel("Welcome back.");
        lbl_title.putClientProperty(FlatClientProperties.STYLE,
                "font: bold +12");


        JLabel lbl_subtitle = new JLabel("Please log in to your account.");
        lbl_subtitle.putClientProperty(FlatClientProperties.STYLE,
                "[dark]background:darken(@foreground, 10%)");


        ImageIcon logo = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/CrimsonDawnLogo.png")));
        Image img_logo = logo.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH);
        logo.setImage(img_logo);
        JLabel pnl_logo_panel = new JLabel(logo);


        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.passwordField.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton:true");

        this.loginButton = new JButton("Login");
        this.rememberMe = new JCheckBox("Remember me");

        String[] testList = {"cds.crimsondawn.com", "server2.com", "server3.com"};


        // Fetch icon of server upon input. Allows a more customizable design for self-hosted solutions.

        this.serverSelector = new JComboBox<>(testList);
        this.serverSelector.setEditable(true);


        innerPanel.add(lbl_title);
        innerPanel.add(lbl_subtitle);

        this.forgotPassword = new JLabel("Forgot password?");
        this.forgotPassword.putClientProperty(FlatClientProperties.STYLE,
                "[dark]foreground: darken(@foreground, 20%)");


        innerPanel.add(new JLabel("Username"), "gapy 20");
        innerPanel.add(this.usernameField);
        innerPanel.add(new JLabel("Password"), "gapy 5");
        innerPanel.add(this.passwordField);
        innerPanel.add(new JLabel("Server"), "gapy 5");
        innerPanel.add(this.serverSelector);
        innerPanel.add(this.rememberMe, "gapy 2, grow 0");
        innerPanel.add(this.loginButton, "gapy 20");
        innerPanel.add(this.forgotPassword, "grow 0"); // push up

        panel.add(innerPanel);

        this.add(panel);
        this.add(pnl_logo_panel);
    }
}