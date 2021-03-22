package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static final IValue ZERO = new NumberValue(0);
    private static final Map<String, IValue> VARIABLES = new HashMap<>();

    public static boolean isKeyExists(String key) {
        return VARIABLES.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        return isKeyExists(key) ? VARIABLES.get(key) : ZERO;
    }

    public static void put(String key, IValue value) {
        VARIABLES.put(key, value);
    }
}
