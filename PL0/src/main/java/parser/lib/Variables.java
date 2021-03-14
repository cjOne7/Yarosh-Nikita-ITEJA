package parser.lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variables {
    private static final Map<String, Double> VARIABLES = new HashMap<>();

    static {
        VARIABLES.put("PI", 3.14);
        VARIABLES.put("E", 2.7);
    }

    public static boolean isKeyExists(String key) {
        return VARIABLES.containsKey(key);
    }

    public static double getValueByKey(String key) {
        return isKeyExists(key) ? VARIABLES.get(key) : Double.valueOf(Double.NaN);
    }

    public static void put(String key, double value) {
        VARIABLES.put(key, value);
    }
}
