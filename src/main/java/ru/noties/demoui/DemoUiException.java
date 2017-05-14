package ru.noties.demoui;

public class DemoUiException extends IllegalStateException {

    public DemoUiException() {
        super();
    }

    public DemoUiException(String s) {
        super(s);
    }

    public DemoUiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoUiException(Throwable cause) {
        super(cause);
    }
}
