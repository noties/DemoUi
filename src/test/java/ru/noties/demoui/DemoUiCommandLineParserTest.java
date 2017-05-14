package ru.noties.demoui;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import ru.noties.demoui.options.Bars;

import javax.annotation.Nullable;

import static org.junit.Assert.*;

public class DemoUiCommandLineParserTest {

    private Options options;
    private CommandLineParser parser;

    @Before
    public void before() {
        options = new DemoUiController().options();
        parser = new DefaultParser();
    }

    @Test
    public void empty_args_enters_live_mode() {

        final DemoUiState state = state(true);

        assertNull(state.configuration());
        assertNotNull(state.live());

        assertTrue(state.demoMode());
        assertFalse(state.showHelp());
        assertTrue(state.live());
    }

    @Test
    public void show_help_ignores_configuration() {
        final DemoUiState state = state("-h", "-bs", "o");
        assertTrue(state.showHelp());
        assertNull(state.configuration());
        assertNull(state.live());
        assertTrue(state.demoMode());
    }

    @Test
    public void option_help() {
        for (DemoUiState state: new DemoUiState[] { state("-h"), state("--help") }) {
            assertTrue(state.showHelp());
        }
    }

    @Test
    public void option_live() {

        final DemoUiState[] live = new DemoUiState[] {
                state(true),
                state("-l"),
                state("--live")
        };

        final DemoUiState[] notLive = new DemoUiState[] {
                state("-le"),
                state("--live-exit")
        };

        final DemoUiState[] notPresent = new DemoUiState[] {
                state("bs", "o")
        };

        for (DemoUiState state: live) {
            assertTrue(state.live());
        }

        for (DemoUiState state: notLive) {
            assertFalse(state.live());
        }

        for (DemoUiState state: notPresent) {
            assertNull(state.live());
        }
    }

    @Test
    public void option_demo() {
        // demo is on all the time except the `-e` option is specified

        final DemoUiState[] t = new DemoUiState[] {
                state("-bs", "o"),
                state("-de"),
                state("--enter")
        };

        final DemoUiState[] f = new DemoUiState[] {
                state("-e"),
                state("--exit")
        };

        for (DemoUiState state: t) {
            assertTrue(state.demoMode());
        }

        for (DemoUiState state: f) {
            assertFalse(state.demoMode());
        }
    }

    @Test
    public void option_configuration_bars() {

        final DemoUiState[] o = new DemoUiState[] {
                state("-bs", "o"),
                state("--bar-style", "o"),
                state("-bs", "opaque"),
                state("--bar-style", "opaque")
        };

        final DemoUiState[] t = new DemoUiState[] {
                state("-bs", "t"),
                state("--bar-style", "t"),
                state("-bs", "translucent"),
                state("--bar-style", "translucent")
        };

        final DemoUiState[] s = new DemoUiState[] {
                state("-bs", "s"),
                state("--bar-style", "s"),
                state("-bs", "semi-transparent"),
                state("--bar-style", "semi-transparent")
        };

        final String[][] errorArgs = {
                { "-bs", "b" },
                { "--bar-style", "b" },
                { "-bs", "1" },
                { "--bar-style", "true" }
        };

        for (String[] args: errorArgs) {
            try {
                state(args);
                assertTrue(false);
            } catch (DemoUiException e) {
                assertTrue(true);
            }
        }

        final class Checker {
            void check(Bars.Mode mode, DemoUiState[] states) {
                for (DemoUiState state: states) {
                    assertNotNull(state.configuration());
                    assertNotNull(state.configuration().bars());
                    assertEquals(mode, state.configuration().bars().mode());
                }
            }
        }
        final Checker checker = new Checker();
        checker.check(Bars.Mode.OPAQUE, o);
        checker.check(Bars.Mode.TRANSLUCENT, t);
        checker.check(Bars.Mode.SEMI_TRANSPARENT, s);
    }

    @Test
    public void option_configuration_battery() {

        final DemoUiState[] l42 = new DemoUiState[] {
                state("-bl", "42"),
                state("--battery-level", "42")
        };

        final DemoUiState[] pt = new DemoUiState[] {
                state("-bp", "1"),
                state("--battery-plugged", "1"),
                state("-bp", "true"),
                state("--battery-plugged", "true")
        };

        final DemoUiState[] pf = new DemoUiState[] {
                state("-bp", "0"),
                state("--battery-plugged", "0"),
                state("-bp", "false"),
                state("--battery-plugged", "false"),
                state("-bp", "whatever"),
                state("--battery-plugged", "whatever")
        };

        final String[][] errorArgs = {
                { "-bl", "-1" },
                { "--battery-level", "101" }
        };

        for (String[] args: errorArgs) {
            try {
                state(args);
                assertTrue(false);
            } catch (DemoUiException e) {
                assertTrue(true);
            }
        }

        for (DemoUiState state: l42) {
            assertEquals(42, (int) state.configuration().battery().level());
        }

        for (DemoUiState state: pt) {
            assertTrue(state.configuration().battery().plugged());
        }

        for (DemoUiState state: pf) {
            assertFalse(state.configuration().battery().plugged());
        }
    }

    @Test
    public void option_configuration_clock() {

        final DemoUiState[] m42 = new DemoUiState[] {
                state("-cm", "42"),
                state("--clock-millis", "42")
        };

        final DemoUiState[] hhmm1012 = new DemoUiState[] {
                state("-ch", "1012"),
                state("--clock-hhmm", "1012")
        };

        final String[][] errorArgs = {
                { "-ch", "0:0" },
                { "--clock-hhmm", "00" },
                { "-ch", "2460" },
                { "--clock-hhmm", "-1-1" }
        };

        for (String[] args: errorArgs) {
            try {
                state(args);
                assertTrue(false);
            } catch (DemoUiException e) {
                assertTrue(true);
            }
        }

        for (DemoUiState state: m42) {
            assertEquals(42L, (long) state.configuration().clock().millis());
        }

        for (DemoUiState state: hhmm1012) {
            assertEquals("1012", state.configuration().clock().hhmm());
        }
    }

    private DemoUiState state(String... args) {
        return state(false, args);
    }

    private DemoUiState state(boolean initial, String... args) {
        try {
            return DemoUiCommandLineParser.parse(parser.parse(options, args), initial);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}