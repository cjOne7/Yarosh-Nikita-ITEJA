package parser.lib;

import parser.statements.functions.IFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Functions {
    private static final Map<String, IFunction> FUNCTIONS = new HashMap<>();

    private Functions() {}

    static {
        FUNCTIONS.put("cos", args -> {
            checkNumberOfArguments(1, args.length);
            return new DoubleValue(Math.cos(args[0].asDouble()));
        });
        FUNCTIONS.put("sin", args -> {
            checkNumberOfArguments(1, args.length);
            return new DoubleValue(Math.sin(args[0].asDouble()));
        });
        FUNCTIONS.put("tan", args -> {
            checkNumberOfArguments(1, args.length);
            return new DoubleValue(Math.tan(args[0].asDouble()));
        });
        FUNCTIONS.put("cot", args -> {
            checkNumberOfArguments(1, args.length);
            return new DoubleValue(1 / Math.tan(args[0].asDouble()));
        });
        FUNCTIONS.put("sqrt", args -> {
            checkNumberOfArguments(1, args.length);
            return new DoubleValue(Math.sqrt(args[0].asDouble()));
        });
        FUNCTIONS.put("power", args -> {
            checkNumberOfArguments(2, args.length);
            return new DoubleValue(Math.pow(args[0].asDouble(), args[1].asDouble()));
        });
    }

    public static boolean isKeyExists(String key) {
        return FUNCTIONS.containsKey(key);
    }

    public static IFunction getFunctionByName(String name) {
        if (isKeyExists(name)) {
            return FUNCTIONS.get(name);
        }
        throw new RuntimeException("Unknown function '" + name + "'.");
    }

    public static void put(String key, IFunction function) {
        FUNCTIONS.put(key, function);
    }

    private static void checkNumberOfArguments(int rightNumber, int length) {
        if (length != rightNumber) {
            throw new RuntimeException("Too many arguments");
        }
    }
}
