package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Constants implements IVariable {
    private static final Map<String, IValue> CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("PI", new NumberValue(3.14));
        CONSTANTS.put("E", new NumberValue(2.7));
    }

    public static boolean isKeyExists(String key) {
        return CONSTANTS.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        if (isKeyExists(key)) {
            return CONSTANTS.get(key);
        }
        throw new RuntimeException("Constant with key '" + key + "' doesn't exist.");
    }

    public static void put(String key, IValue value) {
        if (isKeyExists(key)) {
            throw new RuntimeException("Constant with key '" + key + "' has already existed");
        }
        CONSTANTS.put(key, value);
    }

}
