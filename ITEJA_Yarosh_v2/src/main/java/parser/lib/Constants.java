package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final Map<String, Double> CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("PI", Math.PI);
        CONSTANTS.put("E", Math.E);
    }

    public static boolean isKeyExists(String key) {
        return CONSTANTS.containsKey(key);
    }

    public static double getValueByKey(String key) {
        return isKeyExists(key) ? CONSTANTS.get(key) : Double.valueOf(Double.NaN);
    }

    public static void put(String key, double value) {
        if (isKeyExists(key)) {
            throw new RuntimeException("Constant with key " + key + " has already existed");
        }
        CONSTANTS.put(key, value);
    }

}
