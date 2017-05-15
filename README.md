# DemoUi
Command line tool to access Android Demo Ui

## Modes

This tools has 2 modes:
* Standart mode, application will evaluate all arguments, apply them and exit
* Live mode, application starts to listen for supplied arguments and apply them. This mode helps to tweak options and receive fast feedback. To start it use `-l` argument, to exit from live mode: `-le`

## Configuration
Application supports saving/loading of configurations.
```
# Loads configuration for a file name `first.demoui`
./demoui -cf first

# Saves current configuration to a filename `second.demoui`
./demoui -cs second
```

Configurations can be used with arguments:
```
./demoui -nwl 3 -cf first
```
Here `first` configuration will be used as a base. All supplied arguments will override base options.

```
# Loads `first` configuration, applies `-na 1` argument and saves this configuration to `second`
./demoui -cf first -na 1 -cs second
```

Application saves/loads only files with `.demoui` extensions (which _can_ be omitted when typing).

## -h, --help
All demo ui options are taken from [this](https://android.googlesource.com/platform/frameworks/base/+/android-6.0.0_r1/packages/SystemUI/docs/demo_mode.md) document

All options have short and long names

```
usage: demoui [-a <adb-path>] [-ad | -ae | -as <serial>]   [-bl <level>]
       [-bp <charging>] [-bs <style>] [-cf <file>] [-ch <hhmm> | -cm
       <millis>]  [-cs <file>] [-d <enable>] [-de | -e]  [-h] [-l | -le]
       [-na <show>] [-nc <show>] [-nf <fully>] [-nm <mobile>] [-nmd
       <datatype>] [-nml <level>] [-nns <show>] [-ns <sims>] [-nv <show>]
       [-nw <wifi>] [-nwl <wifi-level>] [-sa <show>] [-sb <status>] [-sda
       <enable>] [-se <show>] [-sl <show>] [-sm <show>] [-ss <show>] [-ssp
       <show>] [-st <show>] [-sv <type>]
 -a,--adb-path <adb-path>                      Path to the `adb`
                                               executable, if not provided
                                               adb is considered to be
                                               found in PATH
 -ad,--adb-device                              Uses physical device as a
                                               target for adb
 -ae,--adb-emulator                            Uses emulator as a target
                                               for adb
 -as,--adb-serial <serial>                     Uses device with serial
                                               number as a target for adb
 -bl,--battery-level <level>                   Battery. Sets the battery
                                               level (0 - 100)
 -bp,--battery-plugged <charging>              Battery. Sets charging
                                               state (`1|true` for
                                               charging, everything else
                                               for non-charging)
 -bs,--bar-style <style>                       Bars. Control the visual
                                               style of the bars
                                               ([o]paque, [t]ranslucent,
                                               [s]emi-transparent)
 -cf,--configuration-file <file>               Loads demo configuration
                                               from a file
 -ch,--clock-hhmm <hhmm>                       Clock. Sets the time in
                                               `hhmm`
 -cm,--clock-millis <millis>                   Clock. Sets the time in
                                               millis
 -cs,--configuration-save <file>               Saves current demo
                                               configuration
 -d,--debug <enable>                           `1|true` enables printing
                                               of adb executed commands,
                                               everything else disables
 -de,--enter                                   Enters demo mode, bar state
                                               allowed to be modified (for
                                               convenience, any of the
                                               other non-exit commands
                                               will automatically flip
                                               demo mode on, no need to
                                               call this explicitly in
                                               practice)
 -e,--exit                                     Exits demo mode, bars back
                                               to their system-driven
                                               state
 -h,--help                                     Prints help
 -l,--live                                     Enters `live` mode
 -le,--live-exit                               Exits `live` mode
 -na,--network-airplane <show>                 Network. `1|true` to show
                                               icon, any other value to
                                               hide
 -nc,--network-carrier-network-change <show>   Network. Sets mobile signal
                                               icon to carrier network
                                               change UX when disconnected
                                               (`1|true` to show icon, any
                                               other value to hide)
 -nf,--network-fully <fully>                   Network. Sets MCS state to
                                               fully connected (`1|true`
                                               for true, everything else
                                               for false)
 -nm,--network-mobile <mobile>                 Network. `1|true` to show
                                               mobile icon, any other
                                               value to hide
 -nmd,--network-mobile-datatype <datatype>     Network. Values: `1x`,
                                               `3g`, `4g`, `e`, `g`, `h`,
                                               `lte`, `roam`, any other
                                               value to hide
 -nml,--network-mobile-level <level>           Network. Sets mobile signal
                                               strength level (0-4)
 -nns,--network-no-sim <show>                  Network. `1|true` to show
                                               no-sim icon, any other
                                               value to hide
 -ns,--network-sims <sims>                     Network. Sets the number of
                                               sims (1-8)
 -nv,--notifications-hide <show>               Notifications. `0|false` to
                                               hide the notification
                                               icons, any other value to
                                               show
 -nw,--network-wifi <wifi>                     Network. `1|true` to show
                                               wifi icon, any other value
                                               to hide
 -nwl,--network-wifi-level <wifi-level>        Network. Sets wifi level
                                               (0-4)
 -sa,--status-alarm <show>                     Status. Sets the icon in
                                               the alarm_clock slot
                                               (`1|true` to show, any
                                               other value to hide)
 -sb,--status-bluetooth <status>               Status. Sets the icon in
                                               the bluetooth slot
                                               ([c]onnected,
                                               [d]isconnected, any other
                                               value to hide)
 -sda,--sysui-demo-allowed <enable>            Modifies global
                                               `sysui_demo_allowed` state,
                                               `1|true` to enable,
                                               everything else to disable
 -se,--status-eri <show>                       Status. Sets the icon in
                                               the cdma_eri slot (`1|true`
                                               to show, any other value to
                                               hide)
 -sl,--status-location <show>                  Status. Sets the icon in
                                               the location slot (`1|true`
                                               to show, any other value to
                                               hide)
 -sm,--status-mute <show>                      Status. Sets the icon in
                                               the mute slot (`1|true` to
                                               show, any other value to
                                               hide)
 -ss,--status-sync <show>                      Status. Sets the icon in
                                               the sync_active slot
                                               (`1|true` to show, any
                                               other value to hide)
 -ssp,--status-speakerphone <show>             Status. Sets the icon in
                                               the speakerphone slot
                                               (`1|true` to show, any
                                               other value to hide)
 -st,--status-tty <show>                       Status. Sets the icon in
                                               the tty slot (`1|true` to
                                               show, any other value to
                                               hide)
 -sv,--status-volume <type>                    Status. Sets the icon in
                                               the volume slot ([s]ilent,
                                               [v]ibrate, any other value
                                               to hide)


```

## License

```
  Copyright 2017 Dimitry Ivanov (mail@dimitryivanov.ru)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```