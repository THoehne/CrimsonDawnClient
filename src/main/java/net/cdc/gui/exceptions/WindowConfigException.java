package net.cdc.gui.exceptions;

public class WindowConfigException extends Exception {

    public WindowConfigException() {
        super("An unexpected error occured.");
    }

    public WindowConfigException(java.lang.String msg) {
        super(msg);
    }

    public WindowConfigException(java.lang.String msg, java.lang.Throwable cause) {
        super(msg, cause);
    }
}
