package ru.noties.demoui.utils;

import javax.annotation.Nullable;

public abstract class TextUtils {

    public static boolean isEmpty(@Nullable CharSequence text) {
        return text == null || text.length() == 0;
    }

    private TextUtils() {}
}
