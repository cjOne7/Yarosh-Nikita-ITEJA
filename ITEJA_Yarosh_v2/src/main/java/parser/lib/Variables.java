package parser.lib;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.IValue;
import parser.lib.datatypes.StringValue;

import java.util.HashMap;
import java.util.Map;

public final class Variables {
    public static final IValue ZERO = new DoubleValue(0);
    public static final IValue EMPTY = new StringValue("");
    private static final Map<String, IValue> VARIABLES = new HashMap<>();

    private Variables() {}

    public static boolean isKeyExists(final String key) {
        return VARIABLES.containsKey(key);
    }

    public static IValue getValueByKey(final String key) {
        if (isKeyExists(key)) {
            return VARIABLES.get(key);
        }
        throw new RuntimeException("Variable with key '" + key + "' doesn't exist.");
    }

    public static void put(final String key, final IValue value) {
        VARIABLES.put(key, value);
    }
}
