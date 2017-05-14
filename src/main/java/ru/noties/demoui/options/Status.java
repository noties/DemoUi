package ru.noties.demoui.options;

// Control the system status icons
public class Status {


    public enum Volume {
        HIDE, SILENT, VIBRATE
    }

    public enum Bluetooth {
        HIDE, CONNECTED, DISCONNECTED
    }


    // 	Sets the icon in the volume slot (silent, vibrate, any other value to hide)
    private Volume volume;

    // Sets the icon in the bluetooth slot (connected, disconnected, any other value to hide)
    private Bluetooth bluetooth;

    // Sets the icon in the location slot (`show`, any other value to hide)
    private Boolean location;

    // Sets the icon in the alarm_clock slot (`show`, any other value to hide)
    private Boolean alarm;

    // Sets the icon in the sync_active slot (`show`, any other value to hide)
    private Boolean sync;

    // Sets the icon in the tty slot (`show`, any other value to hide)
    private Boolean tty;

    // Sets the icon in the cdma_eri slot (`show`, any other value to hide)
    private Boolean eri;

    // Sets the icon in the mute slot (`show`, any other value to hide)
    private Boolean mute;

    // Sets the icon in the speakerphone slot (`show`, any other value to hide)
    private Boolean speakerphone;

    public Volume volume() {
        return volume;
    }

    public Status volume(Volume volume) {
        this.volume = volume;
        return this;
    }

    public Bluetooth bluetooth() {
        return bluetooth;
    }

    public Status bluetooth(Bluetooth bluetooth) {
        this.bluetooth = bluetooth;
        return this;
    }

    public Boolean location() {
        return location;
    }

    public Status location(Boolean location) {
        this.location = location;
        return this;
    }

    public Boolean alarm() {
        return alarm;
    }

    public Status alarm(Boolean alarm) {
        this.alarm = alarm;
        return this;
    }

    public Boolean sync() {
        return sync;
    }

    public Status sync(Boolean sync) {
        this.sync = sync;
        return this;
    }

    public Boolean tty() {
        return tty;
    }

    public Status tty(Boolean tty) {
        this.tty = tty;
        return this;
    }

    public Boolean eri() {
        return eri;
    }

    public Status eri(Boolean eri) {
        this.eri = eri;
        return this;
    }

    public Boolean mute() {
        return mute;
    }

    public Status mute(Boolean mute) {
        this.mute = mute;
        return this;
    }

    public Boolean speakerphone() {
        return speakerphone;
    }

    public Status speakerphone(Boolean speakerphone) {
        this.speakerphone = speakerphone;
        return this;
    }

    @Override
    public String toString() {
        return "Status{" +
                "volume='" + volume + '\'' +
                ", bluetooth='" + bluetooth + '\'' +
                ", location=" + location +
                ", alarm=" + alarm +
                ", sync=" + sync +
                ", tty=" + tty +
                ", eri=" + eri +
                ", mute=" + mute +
                ", speakerphone=" + speakerphone +
                '}';
    }
}
