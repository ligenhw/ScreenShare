package org.gen.screensharesdk;

public class ScreenShareException extends Exception {

    public ScreenShareException() {
    }

    public ScreenShareException(String message) {
        super(message);
    }

    public ScreenShareException(String message, Throwable cause) {
        super(message, (cause.getCause() != null)
                ? cause.getCause()
                : cause);
    }
}
