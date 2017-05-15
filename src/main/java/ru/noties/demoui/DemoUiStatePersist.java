package ru.noties.demoui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.noties.demoui.utils.TextUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

public class DemoUiStatePersist {

    private static final String EXTENSION = ".demoui";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // in order to provide a bit of safety we allow overriding of files with `.demoui` extension only
    public void save(@Nonnull String path, @Nonnull DemoUiState state) {
        try {
            write(createPath(path), gson.toJson(state));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
    }

    @Nullable
    public DemoUiState load(@Nonnull String path) {

        DemoUiState out = null;

        try {

            final String json = read(createPath(path));
            if (!TextUtils.isEmpty(json)) {
                out = gson.fromJson(json, DemoUiState.class);
            }

        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        return out;
    }

    private static String createPath(@Nonnull String in) {
        final String out;
        if (in.endsWith(EXTENSION)) {
            // do nothing it already has our extension
            out = in;
        } else {
            out = in + EXTENSION;
        }
        return out;
    }

    private static void write(String path, String content) {

        final File file = new File(path);

        if (!file.exists()) {
            final File parent = file.getParentFile();
            if (parent != null
                    && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new RuntimeException("Cannot create parent file for the path: " + path);
                }
            }
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Cannot create a file for path: " + path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // no op
                }
            }
        }
    }

    private static String read(String path) {

        final File file = new File(path);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + path);
        }

        final StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line)
                        .append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // no op
                }
            }
        }
        return builder.toString();
    }
}
