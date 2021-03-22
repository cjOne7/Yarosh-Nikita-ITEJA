package parser.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {
    private static final Map<String, Double> DOUBLE_VARIABLES = new HashMap<>();
    private static final Map<String, String> STRING_VARIABLES = new HashMap<>();

    public static boolean isStringExists(String key) {
        return STRING_VARIABLES.containsKey(key);
    }

    public static String getString(String key) {
        return isStringExists(key) ? STRING_VARIABLES.get(key) : "";
    }

    public static void putStringVar(String key, String value) {
        STRING_VARIABLES.put(key, value);
    }

    public static void addStrings(List<String> identifiers) {
        identifiers.forEach(identifier -> STRING_VARIABLES.put(identifier, ""));
    }

    public static boolean isDoubleExists(String key) {
        return DOUBLE_VARIABLES.containsKey(key);
    }

    public static double getDouble(String key) {
        return isDoubleExists(key) ? DOUBLE_VARIABLES.get(key) : Double.valueOf(Double.NaN);
    }

    public static void putDoubleVar(String key, double value) {
        DOUBLE_VARIABLES.put(key, value);
    }

    public static void addDoubles(List<String> identifiers) {
        identifiers.forEach(identifier -> DOUBLE_VARIABLES.put(identifier, 0.0));
    }
}
