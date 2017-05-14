package ru.noties.demoui.options;

// Control the clock display
public class Clock {

    // Sets the time in millis
    private Long millis;

    // Sets the time in hh:mm
    private String hhmm;

    public Long millis() {
        return millis;
    }

    public Clock millis(Long millis) {
        this.millis = millis;
        return this;
    }

    public String hhmm() {
        return hhmm;
    }

    public Clock hhmm(String hhmm) {
        this.hhmm = hhmm;
        return this;
    }

    @Override
    public String toString() {
        return "Clock{" +
                "millis=" + millis +
                ", hhmm='" + hhmm + '\'' +
                '}';
    }
}
