package org.bundestagsbot.LocalUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayObjectUtil {
    /**
     * Parses object into an ArrayList
     * @param obj object to convert
     * @return converted arraylist, may be empty
     */
    public static @NotNull List<?> convertObjectToList(@NotNull Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
}
