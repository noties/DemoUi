package ru.noties.demoui.options;

// Control the notification icons
public class Notifications {

    // `false` to hide the notification icons, any other value to show
    private Boolean visible;

    public Boolean visible() {
        return visible;
    }

    public Notifications visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "visible=" + visible +
                '}';
    }
}
