package ru.noties.demoui.utils;

public abstract class BooleanUtils {

    public static boolean bool(String text) {

        final boolean out;

        if (TextUtils.isEmpty(text)) {
            out = false;
        } else {
            // can be `1|true`
            Integer intValue;
            try {
                intValue = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                intValue = null;
            }
            if (intValue != null) {
                out = intValue == 1;
            } else {
                out = Boolean.parseBoolean(text);
            }
        }

        return out;
    }

    private BooleanUtils() {}
}
