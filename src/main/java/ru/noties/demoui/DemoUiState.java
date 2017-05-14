package ru.noties.demoui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DemoUiState {

    private DemoUiConfiguration configuration;

    private boolean showHelp;
    // yep, we need 3 states: true/false/not present
    private Boolean isLive;
    private boolean isDemoMode; // if have configuration || specific argument option

    public DemoUiConfiguration configuration() {
        return configuration;
    }

    public DemoUiState configuration(DemoUiConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public boolean showHelp() {
        return showHelp;
    }

    public DemoUiState showHelp(boolean showHelp) {
        this.showHelp = showHelp;
        return this;
    }

    @Nullable
    public Boolean live() {
        return isLive;
    }

    @Nonnull
    public DemoUiState live(Boolean live) {
        isLive = live;
        return this;
    }

    public boolean demoMode() {
        return isDemoMode;
    }

    public DemoUiState demoMode(boolean demoMode) {
        isDemoMode = demoMode;
        return this;
    }

    @Override
    public String toString() {
        return "DemoUiState{" +
                "configuration=" + configuration +
                ", showHelp=" + showHelp +
                ", isLive=" + isLive +
                ", isDemoMode=" + isDemoMode +
                '}';
    }
}
