package parser.lib;

import java.util.HashMap;
import java.util.Map;

public class Variables implements IVariable {
    private static final Map<String, IValue> VARIABLES = new HashMap<>();

    public static boolean isKeyExists(String key) {
        return VARIABLES.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        return isKeyExists(key) ? VARIABLES.get(key) : EMPTY;
    }

    public static void put(String key, IValue value) {
        VARIABLES.put(key, value);
    }
}
