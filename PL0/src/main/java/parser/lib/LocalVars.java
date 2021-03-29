package parser.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LocalVars {
    private static final Stack<Map<String, IValue>> LOCAL_VARS_STACK = new Stack<>();
    private static Map<String, IValue> variables = new HashMap<>();

    public static boolean isKeyExists(String key) {
        return variables.containsKey(key);
    }

    public static IValue getValueByKey(String key) {
        if (isKeyExists(key)) {
            return variables.get(key);
        }
        throw new RuntimeException("Local variable with key '" + key + "' doesn't exist.");
    }

    public static void push() {
        LOCAL_VARS_STACK.push(new HashMap<>(variables));
    }

    public static void pop() {
        variables = LOCAL_VARS_STACK.pop();
    }

    public static void put(String key, IValue value) {
        variables.put(key, value);
    }

    public static void clear() {
        variables.clear();
    }
}
