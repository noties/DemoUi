package ru.noties.demoui;

import ru.noties.demoui.options.*;

public class DemoUiConfiguration {

    private Bars bars;
    private Battery battery;
    private Clock clock;
    private Network network;
    private Notifications notifications;
    private Status status;

    public Bars bars() {
        return bars;
    }

    public DemoUiConfiguration bars(Bars bars) {
        this.bars = bars;
        return this;
    }

    public Battery battery() {
        return battery;
    }

    public DemoUiConfiguration battery(Battery battery) {
        this.battery = battery;
        return this;
    }

    public Clock clock() {
        return clock;
    }

    public DemoUiConfiguration clock(Clock clock) {
        this.clock = clock;
        return this;
    }

    public Network network() {
        return network;
    }

    public DemoUiConfiguration network(Network network) {
        this.network = network;
        return this;
    }

    public Notifications notifications() {
        return notifications;
    }

    public DemoUiConfiguration notifications(Notifications notifications) {
        this.notifications = notifications;
        return this;
    }

    public Status status() {
        return status;
    }

    public DemoUiConfiguration status(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "DemoUiConfiguration{" +
                "bars=" + bars +
                ", battery=" + battery +
                ", clock=" + clock +
                ", network=" + network +
                ", notifications=" + notifications +
                ", status=" + status +
                '}';
    }
}
