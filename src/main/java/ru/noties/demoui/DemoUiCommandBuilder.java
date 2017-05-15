package ru.noties.demoui;

import ru.noties.demoui.options.*;

import java.util.ArrayList;
import java.util.List;

public abstract class DemoUiCommandBuilder {

    private static final String ENABLE = "%1$s shell settings put global sysui_demo_allowed %2$d";
    private static final String ACTION = "%1$s shell am broadcast -a com.android.systemui.demo -e command %2$s";

    public static List<String> commands(DemoUiState state) {

        final List<String> list = new ArrayList<>();
        final String adb = state.adb();
        final DemoUiConfiguration configuration = state.configuration();
        final Boolean demo = state.demoMode();

        if (state.demoGlobalSettingEnabled() != null) {
            list.add(String.format(ENABLE, adb, state.demoGlobalSettingEnabled() ? 1 : 0));
        }

        if (demo != null && demo) {
            list.add(String.format(ACTION, adb, "enter"));
        }

        if (demo != null && !demo) {
            list.add(String.format(ACTION, adb, "exit"));
        } else {
            if (configuration != null) {
                bars(adb, configuration.bars(), list);
                battery(adb, configuration.battery(), list);
                clock(adb, configuration.clock(), list);
                network(adb, configuration.network(), list);
                notifications(adb, configuration.notifications(), list);
                status(adb, configuration.status(), list);
            }
        }

        return list;
    }

    private static void bars(String adb, Bars bars, List<String> list) {
        if (bars != null) {
            final String command = String.format(ACTION, adb, "bars")
                    + " -e mode " + bars.mode().name().toLowerCase();
            list.add(command);
        }
    }

    private static void battery(String adb, Battery battery, List<String> list) {
        if (battery != null) {
            final StringBuilder builder = new StringBuilder();
            if (battery.level() != null) {
                builder.append(" -e level ")
                        .append(battery.level());
            }
            if (battery.plugged() != null) {
                builder.append(" -e plugged ")
                        .append(battery.plugged());
            }

            if (builder.length() > 0) {
                final String command = String.format(ACTION, adb, "battery")
                        + builder.toString();
                list.add(command);
            }
        }
    }

    private static void clock(String adb, Clock clock, List<String> list) {
        if (clock != null) {
            final String arg = clock.millis() != null
                    ? " -e millis " + clock.millis()
                    : " -e hhmm " + clock.hhmm();
            final String command = String.format(ACTION, adb, "clock") + arg;
            list.add(command);
        }
    }

    private static void network(String adb, Network network, List<String> list) {
        if (network != null) {

            final String command = String.format(ACTION, adb, "network");

            final StringBuilder builder = new StringBuilder();

            if (network.airplane() != null) {
                builder.append(" -e airplane ")
                        .append(network.airplane() ? "show" : "hide");
            }

            if (network.fully() != null) {
                builder.append(" -e fully ")
                        .append(network.fully());
            }

            // f*ck, wifi & mobile have the same `level` argument, so we cannot send both
            if (network.wifiLevel() != null) {

                final boolean wifi = network.wifi() == null || network.wifi();

                builder.append(" -e wifi ")
                        .append(wifi ? "show" : "hide");

                builder.append(" -e level ")
                        .append(network.wifiLevel());

                list.add(command + builder.toString());
                builder.setLength(0);

            } else {
                if (network.wifi() != null) {

                    builder.append(" -e wifi ")
                            .append(network.wifi() ? "show" : "hide");

                    list.add(command + builder.toString());
                    builder.setLength(0);
                }
            }

            if (network.mobileLevel() != null
                    || network.mobileDatatype() != null) {
                final boolean mobile = network.mobile() == null || network.mobile();
                builder.append(" -e mobile ")
                        .append(mobile ? "show" : "hide");
                if (network.mobileLevel() != null) {
                    builder.append(" -e level ")
                            .append(network.mobileLevel());
                }
                if (network.mobileDatatype() != null) {
                    builder.append(" -e datatype ")
                            .append(network.mobileDatatype());
                }
            } else {
                if (network.mobile() != null) {
                    builder.append(" -e mobile ")
                            .append(network.mobile() ? "show" : "hide");
                }
            }

            if (network.carriernetworkchange() != null) {
                builder.append(" -e carriernetworkchange ")
                        .append(network.carriernetworkchange() ? "show" : "hide");
            }

            if (network.sims() != null) {
                builder.append(" -e sims ")
                        .append(network.sims());
            }

            if (network.nosim() != null) {
                builder.append(" -e nosim ")
                        .append(network.nosim() ? "show" : "hide");
            }

            if (builder.length() > 0) {
                list.add(command + builder.toString());
            }
        }
    }

    private static void notifications(String adb, Notifications notifications, List<String> list) {
        if (notifications != null) {
            if (notifications.visible() != null) {
                final String command = String.format(ACTION, adb, "notifications")
                        + " -e visible " + notifications.visible();
                list.add(command);
            }
        }
    }

    private static void status(String adb, Status status, List<String> list) {
        if (status != null) {

            final StringBuilder builder = new StringBuilder();

            if (status.volume() != null) {
                builder.append(" -e volume ")
                        .append(status.volume().name().toLowerCase());
            }

            if (status.bluetooth() != null) {
                builder.append(" -e bluetooth ")
                        .append(status.bluetooth().name().toLowerCase());
            }

            if (status.location() != null) {
                builder.append(" -e location ")
                        .append(status.location() ? "show" : "hide");
            }

            if (status.alarm() != null) {
                builder.append(" -e alarm ")
                        .append(status.alarm() ? "show" : "hide");
            }

            if (status.sync() != null) {
                builder.append(" -e sync ")
                        .append(status.sync() ? "show" : "hide");
            }

            if (status.tty() != null) {
                builder.append(" -e tty ")
                        .append(status.tty() ? "show" : "hide");
            }

            if (status.eri() != null) {
                builder.append(" -e eri ")
                        .append(status.eri() ? "show" : "hide");
            }

            if (status.mute() != null) {
                builder.append(" -e mute ")
                        .append(status.mute() ? "show" : "hide");
            }

            if (status.speakerphone() != null) {
                builder.append(" -e speakerphone ")
                        .append(status.speakerphone() ? "show" : "hide");
            }

            if (builder.length() > 0) {
                final String command = String.format(ACTION, adb, "status")
                        + builder.toString();
                list.add(command);
            }
        }
    }

    private DemoUiCommandBuilder() {
    }
}
