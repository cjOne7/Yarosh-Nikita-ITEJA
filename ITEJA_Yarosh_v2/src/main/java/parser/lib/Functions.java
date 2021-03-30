package parser.lib;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Functions {
    //math
    private final static String SQRT = "sqrt";
    private static final String POWER = "power";
    private static final String ROUND = "round";
    private static final String FLOOR = "floor";
    private static final String CEIL = "ceil";
    private static final String ABS = "abs";
    private static final String LN = "ln";
    private static final String COS = "cos";
    private static final String SIN = "sin";
    private static final String TAN = "tan";
    private static final String COTAN = "cot";
    //Input/Output
    private static final String WRITE = "write";

    public static final IValue VOID = new StringValue("void");
    private static final Map<String, IFunction> FUNCTIONS = new HashMap<>();
    private static final Map<String, Boolean> FUNCTION_HAS_RETURN_TYPE = new HashMap<>();

    private Functions() {}

    static {
        String errorMess = "The required number of arguments does not match current amount.";
        //maths
        FUNCTION_HAS_RETURN_TYPE.put(COS, true);
        FUNCTIONS.put(COS, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.cos(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(SIN, true);
        FUNCTIONS.put(SIN, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.sin(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(TAN, true);
        FUNCTIONS.put(TAN, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.tan(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(COTAN, true);
        FUNCTIONS.put(COTAN, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(1 / Math.tan(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(SQRT, true);
        FUNCTIONS.put(SQRT, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.sqrt(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(ROUND, true);
        FUNCTIONS.put(ROUND, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.round(args[0].asDouble()));
        });
        FUNCTION_HAS_RETURN_TYPE.put(POWER, true);
        FUNCTIONS.put(POWER, args -> {
            check(args.length != 2, errorMess + "Required " + 2 + ", but found " + args.length);
            return new DoubleValue(Math.pow(args[0].asDouble(), args[1].asDouble()));
        });
        //Input/Output
        FUNCTION_HAS_RETURN_TYPE.put(WRITE, false);
        FUNCTIONS.put(WRITE, args -> {
            String output = Arrays.stream(args).map(IValue::asString).collect(Collectors.joining());
            System.out.print(output);
            return VOID;
        });
    }

    private static void addFunction(String functionName, boolean hasFunctionReturn, IFunction function) {
        FUNCTION_HAS_RETURN_TYPE.put(functionName, hasFunctionReturn);
        FUNCTIONS.put(functionName, function);
    }

    public static boolean functionHasReturnType(String functionName) {
        check(!isKeyExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTION_HAS_RETURN_TYPE.get(functionName);
    }

    public static IFunction getFunctionByName(String functionName) {
        check(!isKeyExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTIONS.get(functionName);
    }

    public static void put(String key, IFunction function) {
        FUNCTIONS.put(key, function);
    }

    public static boolean isKeyExists(String key) {
        return FUNCTIONS.containsKey(key);
    }

    private static void check(boolean condition, String exceptionMessage) {
        if (condition) {
            throw new RuntimeException(exceptionMessage);
        }
    }
}
