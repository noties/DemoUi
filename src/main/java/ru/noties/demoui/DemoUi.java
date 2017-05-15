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
    private final DemoUiStateMerger merger;
    private final DemoUiStatePersist persist;

    private BufferedReader reader;
    private DemoUiState state;
    private DemoUiState fullState;
    private boolean live;

    private DemoUi(DemoUiController controller, String[] arguments) {
        this.controller = controller;
        this.merger = new DemoUiStateMerger();
        this.persist = new DemoUiStatePersist();
        this.state = controller.state(arguments);
        this.fullState = new DemoUiState();
    }

    private void run() {

        if (state != null) {

            // if have load -> do it now
            if (!TextUtils.isEmpty(state.loadConfiguration())) {
                // okay, here is what we do here:
                // if load configuration is requested -> we load it and then apply all (optional) arguments that were passed
                final DemoUiState load = persist.load(state.loadConfiguration());
                if (load != null) {

                    final String save = state.saveConfiguration();

                    // we load a configuration and use it as a base state object on which we apply current state
                    merger.merge(load, state);
                    state = load;

                    // hm, we need additionally apply transient fields...
                    state.saveConfiguration(save);
                }
            }

            // we save it for future use (and possible save of configuration)
            // it doesn't make sense to do it if we are not in `live` mode thought
            merger.merge(fullState, state);

            if (!TextUtils.isEmpty(state.saveConfiguration())) {
                // we save was requested -> do it now
                persist.save(state.saveConfiguration(), fullState);
            }

            if (state.showHelp()) {
                showHelp();
            }

            applyDemoUiState();

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

    // we should send only changed values, but still save the whole state to be saved to a file
    private void applyDemoUiState() {

        final Runtime runtime = Runtime.getRuntime();
        final List<String> commands = DemoUiCommandBuilder.commands(state);

        for (String command : commands) {
//            System.out.printf("command: %s%n", command);
            Process process = null;
            try {
                process = runtime.exec(command);
//                ProcessRedirect.redirect(process.getInputStream());
                ProcessRedirect.redirect(process.getErrorStream());
                process.waitFor();
            } catch (Throwable t) {
                System.err.println(t.getMessage());
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
