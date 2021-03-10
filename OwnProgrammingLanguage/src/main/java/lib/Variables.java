package lib;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static final Map<String, Double> VARIABLES;
    static  {
        VARIABLES = new HashMap<>();
        VARIABLES.put("PI", Math.PI);
        VARIABLES.put("E", Math.E);
    }

    public static void set(String key, double value) {
        VARIABLES.put(key, value);
    }

    public static boolean isExists(String key) {
        return VARIABLES.containsKey(key);
    }

    public static double get(String key) {
        if (!isExists(key)) {
            return 0;
        }
        return VARIABLES.get(key);
    }
}
