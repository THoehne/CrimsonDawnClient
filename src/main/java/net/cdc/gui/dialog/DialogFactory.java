package net.cdc.gui.dialog;

import javax.swing.*;

public class DialogFactory {

    /**
     * Show message dialog, without specifying the parent object.
     * @param msg Message to print.
     */
    public static void createInfoDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show message dialog.
     * @param msg Message to print
     * @param parent Parent of dialog
     */
    public static void createInfoDialog(String msg, JComponent parent) {
        JOptionPane.showMessageDialog(parent, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
