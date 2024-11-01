package net.cdc.exceptions;

public class ApplicationCorruption extends Exception {
    public ApplicationCorruption() {
        super("An unexpected error occured.");
    }

    public ApplicationCorruption(java.lang.String msg) {
        super("The application got currupted: " + msg);
    }

    public ApplicationCorruption(java.lang.String msg, java.lang.Throwable cause) {
        super("The application got currupted: " + msg);
    }
}
