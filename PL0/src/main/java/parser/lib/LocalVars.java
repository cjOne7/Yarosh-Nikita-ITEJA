package parser.lib;

import java.util.*;

public class LocalVars {
    private static final Map<String, IValue> variables = new HashMap<>();

    public static boolean isKeyExists(String key) {
        return variables.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        if (isKeyExists(key)) {
            return variables.get(key);
        }
        throw new RuntimeException("Local variable with key '" + key + "' doesn't exist.");
    }

    public static void push(Map<String, IValue> variables) {
        variables.keySet().forEach(key -> LocalVars.variables.put(key, variables.get(key)));
    }

    public static void pop(Set<String> keys) {
        keys.forEach(variables::remove);
    }

    public static void put(String key, IValue value) {
        variables.put(key, value);
    }
}
