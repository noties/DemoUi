package ru.noties.demoui;

import org.apache.commons.cli.*;

import javax.annotation.Nonnull;

public class DemoUiController {

    private final CommandLineParser parser;
    private final Options options;

    DemoUiController() {
        this.parser = new DefaultParser();
        this.options = createOptions();
    }

    public Options options() {
        return options;
    }

    public DemoUiState state(String[] arguments) {
        try {
            return parse(parser.parse(options, arguments));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
            return null;
        }
    }

    @Nonnull
    private static DemoUiState parse(CommandLine line) {
        return DemoUiCommandLineParser.parse(line);
    }

    private static Options createOptions() {
        final Options options = new Options();
        app(options);
        bars(options);
        battery(options);
        clock(options);
        network(options);
        notifications(options);
        status(options);
        return options;
    }

    private static void app(@Nonnull Options options) {

        final Option help = Option.builder("h")
                .hasArg(false)
                .desc("Prints help")
                .longOpt("help")
                .build();

        final Option debug = Option.builder("d")
                .hasArg(true)
                .argName("enable")
                .desc("`1|true` enables printing of adb executed commands, everything else disables")
                .longOpt("debug")
                .build();

        final Option adb = Option.builder("a")
                .hasArg(true)
                .argName("adb-path")
                .desc("Path to the `adb` executable, if not provided adb is considered to be found in PATH")
                .longOpt("adb-path")
                .build();

        final Option adbDevice = Option.builder("ad")
                .hasArg(false)
                .desc("Uses physical device as a target for adb")
                .longOpt("adb-device")
                .build();

        final Option adbEmulator = Option.builder("ae")
                .hasArg(false)
                .desc("Uses emulator as a target for adb")
                .longOpt("adb-emulator")
                .build();

        final Option adbSerial = Option.builder("as")
                .hasArg(true)
                .argName("serial")
                .desc("Uses device with serial number as a target for adb")
                .longOpt("adb-serial")
                .build();

        final OptionGroup adbGroup = new OptionGroup()
                .addOption(adbDevice)
                .addOption(adbEmulator)
                .addOption(adbSerial);

        final Option demoAllowed = Option.builder("sda")
                .hasArg(true)
                .desc("Modifies global `sysui_demo_allowed` state, `1|true` to enable, everything else to disable")
                .argName("enable")
                .longOpt("sysui-demo-allowed")
                .build();

        final Option liveStart = Option.builder("l")
                .hasArg(false)
                .desc("Enters `live` mode")
                .longOpt("live")
                .build();

        final Option liveEnd = Option.builder("le")
                .hasArg(false)
                .desc("Exits `live` mode")
                .longOpt("live-exit")
                .build();

        final OptionGroup liveGroup = new OptionGroup()
                .addOption(liveStart)
                .addOption(liveEnd);

        final Option demoEnter = Option.builder("de")
                .hasArg(false)
                .desc("Enters demo mode, bar state allowed to be modified (for convenience, any of the other non-exit commands will automatically flip demo mode on, no need to call this explicitly in practice)")
                .longOpt("enter")
                .build();

        final Option demoExit = Option.builder("e")
                .hasArg(false)
                .desc("Exits demo mode, bars back to their system-driven state")
                .longOpt("exit")
                .build();

        final OptionGroup demoGroup = new OptionGroup()
                .addOption(demoEnter)
                .addOption(demoExit);

        final Option configurationFile = Option.builder("cf")
                .hasArg(true)
                .argName("file")
                .desc("Loads demo configuration from a file")
                .longOpt("configuration-file")
                .build();

        final Option configurationSave = Option.builder("cs")
                .hasArg(true)
                .argName("file")
                .desc("Saves current demo configuration")
                .longOpt("configuration-save")
                .build();

        final Option screenshot = Option.builder("s")
                .hasArg(true)
                .argName("file")
                .desc("Takes screenshot and saves it to <file>")
                .longOpt("screenshot")
                .build();

        options.addOption(help)
                .addOption(debug)
                .addOption(adb)
                .addOption(demoAllowed)
                .addOptionGroup(adbGroup)
                .addOptionGroup(liveGroup)
                .addOptionGroup(demoGroup)
                .addOption(configurationFile)
                .addOption(configurationSave)
                .addOption(screenshot);
    }

    private static void bars(@Nonnull Options options) {
        final Option bars = Option.builder("bs")
                .argName("style")
                .desc("Bars. Control the visual style of the bars ([o]paque, [t]ranslucent, [s]emi-transparent)")
                .longOpt("bar-style")
                .hasArg()
                .build();
        options.addOption(bars);
    }

    private static void battery(@Nonnull Options options) {

        final Option batteryLevel = Option.builder("bl")
                .argName("level")
                .desc("Battery. Sets the battery level (0 - 100)")
                .longOpt("battery-level")
                .hasArg()
                .build();

        final Option batteryPlugged = Option.builder("bp")
                .argName("charging")
                .desc("Battery. Sets charging state (`1|true` for charging, everything else for non-charging)")
                .longOpt("battery-plugged")
                .hasArg()
                .build();

        options.addOption(batteryLevel)
                .addOption(batteryPlugged);
    }

    private static void clock(@Nonnull Options options) {

        final Option clockMillis = Option.builder("cm")
                .desc("Clock. Sets the time in millis")
                .argName("millis")
                .hasArg()
                .longOpt("clock-millis")
                .build();

        final Option clockHhMm = Option.builder("ch")
                .desc("Clock. Sets the time in `hhmm`")
                .argName("hhmm")
                .hasArg()
                .longOpt("clock-hhmm")
                .build();

        // we are using `group` to enable only one property of 2 possible
        final OptionGroup clockGroup = new OptionGroup()
                .addOption(clockMillis)
                .addOption(clockHhMm);

        options.addOptionGroup(clockGroup);
    }

    private static void network(@Nonnull Options options) {

        final Option networkAirplane = Option.builder("na")
                .desc("Network. `1|true` to show icon, any other value to hide")
                .argName("show")
                .longOpt("network-airplane")
                .hasArg()
                .build();

        final Option networkFully = Option.builder("nf")
                .desc("Network. Sets MCS state to fully connected (`1|true` for true, everything else for false)")
                .argName("fully")
                .longOpt("network-fully")
                .hasArg()
                .build();

        final Option networkWifi = Option.builder("nw")
                .desc("Network. `1|true` to show wifi icon, any other value to hide")
                .argName("wifi")
                .longOpt("network-wifi")
                .hasArg()
                .build();

        final Option networkWifiLevel = Option.builder("nwl")
                .desc("Network. Sets wifi level (0-4)")
                .argName("wifi-level")
                .hasArg()
                .longOpt("network-wifi-level")
                .hasArg()
                .build();

        final Option networkMobile = Option.builder("nm")
                .desc("Network. `1|true` to show mobile icon, any other value to hide")
                .argName("mobile")
                .longOpt("network-mobile")
                .hasArg()
                .build();

        final Option networkMobileDatatype = Option.builder("nmd")
                .desc("Network. Values: `1x`, `3g`, `4g`, `e`, `g`, `h`, `lte`, `roam`, any other value to hide")
                .argName("datatype")
                .longOpt("network-mobile-datatype")
                .hasArg()
                .build();

        final Option networkMobileLevel = Option.builder("nml")
                .desc("Network. Sets mobile signal strength level (0-4)")
                .argName("level")
                .hasArg()
                .longOpt("network-mobile-level")
                .build();

        final Option networkCarrierNetworkChange = Option.builder("nc")
                .desc("Network. Sets mobile signal icon to carrier network change UX when disconnected (`1|true` to show icon, any other value to hide)")
                .argName("show")
                .longOpt("network-carrier-network-change")
                .hasArg()
                .build();

        final Option networkSims = Option.builder("ns")
                .desc("Network. Sets the number of sims (1-8)")
                .argName("sims")
                .hasArg()
                .longOpt("network-sims")
                .build();

        final Option networkNoSim = Option.builder("nns")
                .desc("Network. `1|true` to show no-sim icon, any other value to hide")
                .argName("show")
                .longOpt("network-no-sim")
                .hasArg()
                .build();

        options.addOption(networkAirplane)
                .addOption(networkFully)
                .addOption(networkWifi)
                .addOption(networkWifiLevel)
                .addOption(networkMobile)
                .addOption(networkMobileDatatype)
                .addOption(networkMobileLevel)
                .addOption(networkCarrierNetworkChange)
                .addOption(networkSims)
                .addOption(networkNoSim);
    }

    private static void notifications(@Nonnull Options options) {

        final Option notifications = Option.builder("nv")
                .desc("Notifications. `0|false` to hide the notification icons, any other value to show")
                .argName("show")
                .longOpt("notifications-hide")
                .hasArg()
                .build();

        options.addOption(notifications);
    }

    private static void status(@Nonnull Options options) {

        final Option statusVolume = Option.builder("sv")
                .desc("Status. Sets the icon in the volume slot ([s]ilent, [v]ibrate, any other value to hide)")
                .argName("type")
                .longOpt("status-volume")
                .hasArg()
                .build();

        final Option statusBluetooth = Option.builder("sb")
                .desc("Status. Sets the icon in the bluetooth slot ([c]onnected, [d]isconnected, any other value to hide)")
                .argName("status")
                .longOpt("status-bluetooth")
                .hasArg()
                .build();

        final Option statusLocation = Option.builder("sl")
                .desc("Status. Sets the icon in the location slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-location")
                .hasArg()
                .build();

        final Option statusAlarm = Option.builder("sa")
                .desc("Status. Sets the icon in the alarm_clock slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-alarm")
                .hasArg()
                .build();

        final Option statusSync = Option.builder("ss")
                .desc("Status. Sets the icon in the sync_active slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-sync")
                .hasArg()
                .build();

        final Option statusTty = Option.builder("st")
                .desc("Status. Sets the icon in the tty slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-tty")
                .hasArg()
                .build();

        final Option statusEri = Option.builder("se")
                .desc("Status. Sets the icon in the cdma_eri slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-eri")
                .hasArg()
                .build();

        final Option statusMute = Option.builder("sm")
                .desc("Status. Sets the icon in the mute slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-mute")
                .hasArg()
                .build();

        final Option statusSpeakerphone = Option.builder("ssp")
                .desc("Status. Sets the icon in the speakerphone slot (`1|true` to show, any other value to hide)")
                .argName("show")
                .longOpt("status-speakerphone")
                .hasArg()
                .build();

        options.addOption(statusVolume)
                .addOption(statusBluetooth)
                .addOption(statusLocation)
                .addOption(statusAlarm)
                .addOption(statusSync)
                .addOption(statusTty)
                .addOption(statusEri)
                .addOption(statusMute)
                .addOption(statusSpeakerphone);
    }
}
