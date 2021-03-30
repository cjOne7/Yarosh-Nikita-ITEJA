package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    public static final IValue ZERO = new DoubleValue(0);
    public static final IValue EMPTY = new StringValue("");
    private static final Map<String, IValue> VARIABLES = new HashMap<>();

    public static boolean isKeyExists(String key) {
        return VARIABLES.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        if (isKeyExists(key)) {
            return VARIABLES.get(key);
        }
        throw new RuntimeException("Variable with key '" + key + "' doesn't exist.");
    }

    public static void put(String key, IValue value) {
        VARIABLES.put(key, value);
    }
}
