package ru.noties.demoui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DemoUiState {

    private DemoUiConfiguration configuration;

    private String adb;
    private transient boolean showHelp;
    // yep, we need 3 states: true/false/not present
    private transient Boolean isLive;
    private transient Boolean isDemoMode; // if have configuration || specific argument option
    private Boolean demoGlobalSettingEnabled;

    private transient String saveConfiguration;
    private transient String loadConfiguration;

    public String adb() {
        return adb;
    }

    public DemoUiState adb(String adb) {
        this.adb = adb;
        return this;
    }

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

    public Boolean demoMode() {
        return isDemoMode;
    }

    public DemoUiState demoMode(Boolean demoMode) {
        isDemoMode = demoMode;
        return this;
    }

    public Boolean demoGlobalSettingEnabled() {
        return demoGlobalSettingEnabled;
    }

    public DemoUiState demoGlobalSettingEnabled(Boolean demoGlobalSettingEnabled) {
        this.demoGlobalSettingEnabled = demoGlobalSettingEnabled;
        return this;
    }

    public String saveConfiguration() {
        return saveConfiguration;
    }

    public DemoUiState saveConfiguration(String saveConfiguration) {
        this.saveConfiguration = saveConfiguration;
        return this;
    }

    public String loadConfiguration() {
        return loadConfiguration;
    }

    public DemoUiState loadConfiguration(String loadConfiguration) {
        this.loadConfiguration = loadConfiguration;
        return this;
    }

    @Override
    public String toString() {
        return "DemoUiState{" +
                "configuration=" + configuration +
                ", adb='" + adb + '\'' +
                ", showHelp=" + showHelp +
                ", isLive=" + isLive +
                ", isDemoMode=" + isDemoMode +
                ", demoGlobalSettingEnabled=" + demoGlobalSettingEnabled +
                ", saveConfiguration='" + saveConfiguration + '\'' +
                ", loadConfiguration='" + loadConfiguration + '\'' +
                '}';
    }
}
