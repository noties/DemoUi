package ru.noties.demoui;

import edu.rice.cs.util.ArgumentTokenizer;
import org.apache.commons.cli.HelpFormatter;
import ru.noties.demoui.utils.ProcessRedirect;
import ru.noties.demoui.utils.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DemoUi {


    public static void main(String[] args) {
        final DemoUi demoUi = new DemoUi(new DemoUiController(), args);
        demoUi.run();
    }


    private final DemoUiController controller;
    private BufferedReader reader;
    private DemoUiState state;
    private boolean live;

    private DemoUi(DemoUiController controller, String[] arguments) {
        this.controller = controller;
        this.state = controller.state(arguments, true);
    }

    private void run() {

        System.out.printf("state: %s%n", state);

        if (state != null) {

            if (state.showHelp()) {
                showHelp();
            }

            if (!state.demoMode()) {
                exitDemoMode();
            } else {
                applyDemoUiState();
            }

            // we need previous flag if we are in live mode
            // so, if there was an error in live mode -> continue execution, else finish as usual
            final Boolean live = state.live();
            if (live != null && !live) {
                this.live = false;
            }

            if ((live != null && live) || this.live) {

                if (reader == null) {
                    reader = new BufferedReader(new InputStreamReader(System.in));
                }

                DemoUiState newState = null;
                while (newState == null) {
                    try {
                        final String[] arguments = parseInputLine(reader.readLine());
                        if (arguments != null) {
                            newState = controller.state(arguments);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.live = live != null ? live : this.live;
                state = newState;
                run();
            } else {
                this.live = false;
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // no op
                    } finally {
                        reader = null;
                    }
                }
            }

        } else {
            // here can be only initial state
            showHelp();
        }
    }

    private void showHelp() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("demoui", controller.options(), true);
    }

    private void exitDemoMode() {

    }

    private void applyDemoUiState() {

//        System.out.printf("config: %s%n", state.configuration());

        final Runtime runtime = Runtime.getRuntime();
        final List<String> commands = DemoUiCommandBuilder.commands(state);

        for (String command: commands) {
            System.out.printf("command: %s%n", command);
            Process process = null;
            try {
                process = runtime.exec(command);
                ProcessRedirect.redirect(process.getInputStream());
                ProcessRedirect.redirect(process.getErrorStream());
                process.waitFor();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
        }
    }

    private static String[] parseInputLine(String input) {
        final String[] out;
        if (TextUtils.isEmpty(input)) {
            out = null;
        } else {
            final List<String> list = ArgumentTokenizer.tokenize(input);
            final int size = list != null ? list.size() : 0;
            if (size == 0) {
                out = null;
            } else {
                out = list.toArray(new String[size]);
            }
        }
        return out;
    }
}
