package ru.noties.demoui;

import org.apache.commons.cli.CommandLine;
import ru.noties.demoui.options.*;
import ru.noties.demoui.utils.BooleanUtils;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class DemoUiCommandLineParser {

    private static Pattern PATTERN_CLOCK_HHMM = Pattern.compile("(\\d{2})(\\d{2})");

    static DemoUiState parse(CommandLine line, boolean initial) {

        // if we have `help`, then no need to evaluate configuration
        final DemoUiState state = new DemoUiState();

        final DemoUiConfiguration configuration;

        final boolean help = help(line);
        state.showHelp(help);
        if (!help) {
            configuration = configuration(line);
            state.configuration(configuration);
        } else {
            configuration = null;
        }

        final Boolean live = live(line);
        if (live != null) {
            state.live(live);
        } else {
            if (initial
                    && configuration == null
                    && !state.showHelp()) {
                state.live(Boolean.TRUE);
            }
        }

        // if we have configuration, then it's a demo mode anyway
        final boolean demo = configuration != null
                || demo(line);
        state.demoMode(demo);

        state.adb(adb(line));
        state.demoMode(demo(line));
        state.demoEnabled(demoEnabled(line));
        state.loadConfiguration(loadConfiguration(line));
        state.saveConfiguration(saveConfiguration(line));

        return state;
    }

    private static boolean help(CommandLine line) {
        return line.hasOption("h");
    }

    @Nullable
    private static Boolean live(CommandLine line) {
        final Boolean out;
        // if we have one of the options, then evaluate it, else return null
        if (line.hasOption("le") || line.hasOption("l")) {
            out = !line.hasOption("le") && line.hasOption("l");
        } else {
            out = null;
        }
        return out;
    }

    private static boolean demo(CommandLine line) {
        return !line.hasOption("e") && !line.hasOption("le");
    }

    private static String adb(CommandLine line) {

        final StringBuilder builder = new StringBuilder();
        if (line.hasOption("a")) {
            builder.append(line.getOptionValue("a"));
        } else {
            builder.append("adb");
        }

        if (line.hasOption("ad")) {
            builder.append(" -d");
        } else if (line.hasOption("ae")) {
            builder.append(" -e");
        } else if (line.hasOption("as")) {
            builder.append(" -s ")
                    .append(line.getOptionValue("as"));
        }

        return builder.toString();
    }

    private static Boolean demoEnabled(CommandLine line) {
        final Boolean out;
        if (line.hasOption("sda")) {
            out = BooleanUtils.bool(line.getOptionValue("sda"));
        } else {
            out = null;
        }
        return out;
    }

    private static String loadConfiguration(CommandLine line) {
        final String out;
        if (line.hasOption("cf")) {
            out = line.getOptionValue("cf");
        } else {
            out = null;
        }
        return out;
    }

    private static String saveConfiguration(CommandLine line) {
        final String out;
        if (line.hasOption("cs")) {
            out = line.getOptionValue("cs");
        } else {
            out = null;
        }
        return out;
    }

    private static DemoUiConfiguration configuration(CommandLine line) {

        final Bars bars = bars(line);
        final Battery battery = battery(line);
        final Clock clock = clock(line);
        final Network network = network(line);
        final Notifications notifications = notifications(line);
        final Status status = status(line);

        final DemoUiConfiguration configuration;
        if (bars == null
                && battery == null
                && clock == null
                && network == null
                && notifications == null
                && status == null) {
            configuration = null;
        } else {
            configuration = new DemoUiConfiguration()
                    .bars(bars)
                    .battery(battery)
                    .clock(clock)
                    .network(network)
                    .notifications(notifications)
                    .status(status);
        }

        return configuration;
    }

    private static Bars bars(CommandLine line) {
        final Bars bars;
        if (line.hasOption("bs")) {

            final String value = line.getOptionValue("bs");
            // validate input: `o`, `t`, `s`
            final Bars.Mode mode;
            switch (value) {
                case "o":
                case "opaque":
                    mode = Bars.Mode.OPAQUE;
                    break;
                case "t":
                case "translucent":
                    mode = Bars.Mode.TRANSLUCENT;
                    break;
                case "s":
                case "semi-transparent":
                    mode = Bars.Mode.SEMI_TRANSPARENT;
                    break;

                default:
                    throw new DemoUiException("Unrecognized value for `bs` (bars-style): " + value);
            }
            bars = new Bars()
                    .mode(mode);
        } else {
            bars = null;
        }

        return bars;
    }

    private static Battery battery(CommandLine line) {

        final Battery battery;

        final Integer level;
        if (line.hasOption("bl")) {
            level = Integer.parseInt(line.getOptionValue("bl"));
            // validate bounds
            if (level < 0
                    || level > 100) {
                throw new DemoUiException("Battery level is not within bounds, value: " + level);
            }
        } else {
            level = null;
        }

        final Boolean charging;
        if (line.hasOption("bp")) {
            charging = BooleanUtils.bool(line.getOptionValue("bp"));
        } else {
            charging = null;
        }

        if (level == null
                && charging == null) {
            battery = null;
        } else {
            battery = new Battery()
                    .level(level)
                    .plugged(charging);
        }

        return battery;
    }

    private static Clock clock(CommandLine line) {

        final Clock clock;

        final Long millis;
        if (line.hasOption("cm")) {
            millis = Long.parseLong(line.getOptionValue("cm"));
        } else {
            millis = null;
        }

        final String hhmm;
        if (line.hasOption("ch")) {
            hhmm = line.getOptionValue("ch");

            // validate
            final Matcher matcher = PATTERN_CLOCK_HHMM.matcher(hhmm);
            if (!matcher.matches()) {
                throw new DemoUiException("Clock `hhmm` value does not follow pattern: " + hhmm);
            } else {

                final String hh = matcher.group(1);
                final String mm = matcher.group(2);

                final int h = Integer.parseInt(hh);
                final int m = Integer.parseInt(mm);

                if (h < 0 || h > 23) {
                    throw new DemoUiException("Clock `hh` part is out of range(0-23): " + hh);
                }

                if (m < 0 || m > 59) {
                    throw new DemoUiException("Clock `mm` part is out of range(0-59): " + mm);
                }
            }
        } else {
            hhmm = null;
        }

        if (millis != null
                || hhmm != null) {
            clock = new Clock()
                    .millis(millis)
                    .hhmm(hhmm);
        } else {
            clock = null;
        }

        return clock;
    }

    private static Network network(CommandLine line) {

        final Network network;

        final Boolean airplane;
        if (line.hasOption("na")) {
            airplane = BooleanUtils.bool(line.getOptionValue("na"));
        } else {
            airplane = null;
        }

        final Boolean fully;
        if (line.hasOption("nf")) {
            fully = BooleanUtils.bool(line.getOptionValue("nf"));
        } else {
            fully = null;
        }

        final Boolean wifi;
        if (line.hasOption("nw")) {
            wifi = BooleanUtils.bool(line.getOptionValue("nw"));
        } else {
            wifi = null;
        }

        final Integer wifiLevel;
        if (line.hasOption("nwl")) {
            wifiLevel = Integer.parseInt(line.getOptionValue("nwl"));
            // validate
            if (wifiLevel < 0 || wifiLevel > 4) {
                throw new DemoUiException("Network wifi level is out of bounds(0-4): " + line.getOptionValue("nwl"));
            }
        } else {
            wifiLevel = null;
        }

        final Boolean mobile;
        if (line.hasOption("nm")) {
            mobile = BooleanUtils.bool(line.getOptionValue("nm"));
        } else {
            mobile = null;
        }

        final String mobileDatatype;
        if (line.hasOption("nmd")) {
            mobileDatatype = line.getOptionValue("nmd");
            // validate
            switch (mobileDatatype) {
                case "1x":
                case "3g":
                case "4g":
                case "e":
                case "g":
                case "h":
                case "lte":
                case "roam":
                    // ok
                    break;
                default:
                    throw new DemoUiException("Network mobile datatype is not recognized: " + mobileDatatype);
            }
        } else {
            mobileDatatype = null;
        }

        final Integer mobileLevel;
        if (line.hasOption("nml")) {
            mobileLevel = Integer.parseInt(line.getOptionValue("nml"));
            // validate
            if (mobileLevel < 0 || mobileLevel > 4) {
                throw new DemoUiException("Network mobile level is out of bounds(0-4): " + line.getOptionValue("nml"));
            }
        } else {
            mobileLevel = null;
        }

        final Boolean carrierNetworkChange;
        if (line.hasOption("nc")) {
            carrierNetworkChange = BooleanUtils.bool(line.getOptionValue("nc"));
        } else {
            carrierNetworkChange = null;
        }

        final Integer sims;
        if (line.hasOption("ns")) {
            sims = Integer.parseInt(line.getOptionValue("ns"));
            // validate
            if (sims < 1 || sims > 8) {
                throw new DemoUiException("Network sims is out of range(1-8): " + sims);
            }
        } else {
            sims = null;
        }

        final Boolean noSim;
        if (line.hasOption("nns")) {
            noSim = BooleanUtils.bool(line.getOptionValue("nns"));
        } else {
            noSim = null;
        }

        if (airplane == null
                && fully == null
                && wifi == null
                && wifiLevel == null
                && mobile == null
                && mobileDatatype == null
                && mobileLevel == null
                && carrierNetworkChange == null
                && sims == null
                && noSim == null) {
            network = null;
        } else {
            network = new Network()
                    .airplane(airplane)
                    .fully(fully)
                    .wifi(wifi)
                    .wifiLevel(wifiLevel)
                    .mobile(mobile)
                    .mobileDatatype(mobileDatatype)
                    .mobileLevel(mobileLevel)
                    .carriernetworkchange(carrierNetworkChange)
                    .sims(sims)
                    .nosim(noSim);
        }

        return network;
    }

    private static Notifications notifications(CommandLine line) {
        final Notifications notifications;
        final Boolean visible;
        if (line.hasOption("nv")) {
            visible = BooleanUtils.bool(line.getOptionValue("nv"));
        } else {
            visible = null;
        }
        if (visible == null) {
            notifications = null;
        } else {
            notifications = new Notifications()
                    .visible(visible);
        }
        return notifications;
    }

    private static Status status(CommandLine line) {

        final Status status;

        final Status.Volume volume;
        if (line.hasOption("sv")) {
            final String value = line.getOptionValue("sv");
            switch (value) {
                case "s":
                case "silent":
                    volume = Status.Volume.SILENT;
                    break;
                case "v":
                case "vibrate":
                    volume = Status.Volume.VIBRATE;
                    break;
                default:
                    volume = Status.Volume.HIDE;
            }
        } else {
            volume = null;
        }

        final Status.Bluetooth bluetooth;
        if (line.hasOption("sb")) {
            final String value = line.getOptionValue("sb");
            switch (value) {
                case "c":
                case "connected":
                    bluetooth = Status.Bluetooth.CONNECTED;
                    break;
                case "d":
                case "disconnected":
                    bluetooth = Status.Bluetooth.DISCONNECTED;
                    break;
                default:
                    bluetooth = Status.Bluetooth.HIDE;
            }
        } else {
            bluetooth = null;
        }

        final Boolean location;
        if (line.hasOption("sl")) {
            location = BooleanUtils.bool(line.getOptionValue("sl"));
        } else {
            location = null;
        }

        final Boolean alarm;
        if (line.hasOption("sa")) {
            alarm = BooleanUtils.bool(line.getOptionValue("sa"));
        } else {
            alarm = null;
        }

        final Boolean sync;
        if (line.hasOption("ss")) {
            sync = BooleanUtils.bool(line.getOptionValue("ss"));
        } else {
            sync = null;
        }

        final Boolean tty;
        if (line.hasOption("st")) {
            tty = BooleanUtils.bool(line.getOptionValue("st"));
        } else {
            tty = null;
        }

        final Boolean eri;
        if (line.hasOption("se")) {
            eri = BooleanUtils.bool(line.getOptionValue("se"));
        } else {
            eri = null;
        }

        final Boolean mute;
        if (line.hasOption("sm")) {
            mute = BooleanUtils.bool(line.getOptionValue("sm"));
        } else {
            mute = null;
        }

        final Boolean speakerphone;
        if (line.hasOption("ssp")) {
            speakerphone = BooleanUtils.bool(line.getOptionValue("ssp"));
        } else {
            speakerphone = null;
        }

        if (volume == null
                && bluetooth == null
                && location == null
                && alarm == null
                && sync == null
                && tty == null
                && eri == null
                && mute == null
                && speakerphone == null) {
            status = null;
        } else {
            status = new Status()
                    .volume(volume)
                    .bluetooth(bluetooth)
                    .location(location)
                    .alarm(alarm)
                    .sync(sync)
                    .tty(tty)
                    .eri(eri)
                    .mute(mute)
                    .speakerphone(speakerphone);
        }

        return status;
    }

    private DemoUiCommandLineParser() {
    }
}
