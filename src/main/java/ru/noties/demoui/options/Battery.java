package ru.noties.demoui.options;

// Control the battery display
public class Battery {

    // Sets the battery level (0 - 100)
    private Integer level;

    // Sets charging state (true, false)
    private Boolean plugged;

    public Integer level() {
        return level;
    }

    public Battery level(Integer level) {
        this.level = level;
        return this;
    }

    public Boolean plugged() {
        return plugged;
    }

    public Battery plugged(Boolean plugged) {
        this.plugged = plugged;
        return this;
    }

    @Override
    public String toString() {
        return "Battery{" +
                "level=" + level +
                ", plugged=" + plugged +
                '}';
    }
}
