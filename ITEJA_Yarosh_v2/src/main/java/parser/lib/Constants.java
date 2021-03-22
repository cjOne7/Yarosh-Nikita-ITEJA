package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final IValue ZERO = new NumberValue(0);
    private static final Map<String, IValue> CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("PI", new NumberValue(Math.PI));
        CONSTANTS.put("E", new NumberValue(Math.E));
    }

    public static boolean isKeyExists(String key) {
        return CONSTANTS.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        return isKeyExists(key) ? CONSTANTS.get(key) : ZERO;
    }

    public static void put(String key, IValue value) {
        if (isKeyExists(key)) {
            throw new RuntimeException("Constant with key " + key + " has already existed");
        }
        CONSTANTS.put(key, value);
    }

}
