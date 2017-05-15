package ru.noties.demoui;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class DemoUiStateMerger {

    void merge(@Nonnull DemoUiState full, @Nonnull DemoUiState s) {

        // the merge is simple -> all non null values for `s` are applied for the `full` state
        // if `full` has no object at field -> apply from `s`
        // if both have -> recursively check inner fields of an object

        try {
            mergeInternal(DemoUiState.class, full, s);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static void mergeInternal(@Nonnull Class<?> cl, @Nonnull Object full, Object s) throws Throwable {
        final Field[] fields = cl.getDeclaredFields();
        if (fields != null
                && fields.length > 0) {
            for (Field field : fields) {
                if (!Modifier.isTransient(field.getModifiers())) {
                    field.setAccessible(true);
                    final Object fo = field.get(full);
                    final Object so = field.get(s);
                    // if full is null and s is not -> apply so to full
                    if (fo == null
                            && so != null) {
                        field.set(full, so);
                    } else if (fo != null
                            && so != null) {

                        // if it's root type, then apply what we have (no recursive call)
                        if (rootType(fo.getClass())) {
                            field.set(full, so);
                        } else {
                            mergeInternal(fo.getClass(), fo, so);
                        }
                    }
                }
            }
        }
    }

    // we actually can check for enum case & package...
    private static boolean rootType(Class<?> type) {
        return type.isEnum()
                || type.isArray()
                || type.isPrimitive()
                || jvmClass(type);
    }

    private static boolean jvmClass(Class<?> type) {

        final Class<?>[] classes = {
                Boolean.class,
                Byte.class,
                Character.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,
                String.class
        };

        for (Class<?> cl : classes) {
            if (cl.equals(type)) {
                return true;
            }
        }

        return false;
    }
}
