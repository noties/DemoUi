package ru.noties.demoui.options;

// Control the visual style of the bars (opaque, translucent, etc)
public class Bars {

    public enum Mode {
        OPAQUE
        , TRANSLUCENT
        , SEMI_TRANSPARENT
    }

    // Sets the bars visual style (opaque, translucent, semi-transparent)
    private Mode mode;

    public Mode mode() {
        return mode;
    }

    public Bars mode(Mode mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public String toString() {
        return "Bars{" +
                "mode=" + mode +
                '}';
    }
}
