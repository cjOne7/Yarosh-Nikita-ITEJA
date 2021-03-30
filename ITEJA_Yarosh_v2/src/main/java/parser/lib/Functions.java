package parser.lib;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Functions {
    private final static String SQRT = "sqrt";
    private static final String SQR = "sqr";
    private static final String ROUND = "round";
    private static final String FLOOR = "floor";
    private static final String CEIL = "ceil";
    private static final String ABS = "abs";
    private static final String LN = "ln";
    private static final String COS = "cos";
    private static final String SIN = "sin";
    private static final String TAN = "tan";
    private static final String COT = "cot";
    private enum FunctionReturnType {
        RETURNED,
        VOID
    }

    public static final IValue VOID = new StringValue("void");
    private static final Map<String, IFunction> FUNCTIONS = new HashMap<>();
    private static final Map<String, FunctionReturnType> FUNCTION_RETURN_TYPE = new HashMap<>();

    private Functions() {}

    static {
        //maths
        FUNCTIONS.put(COS, args -> {
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
        FUNCTIONS.put("cotan", args -> {
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
        //Input/Output
        FUNCTIONS.put("write", args -> {
            String output = Arrays.stream(args).map(IValue::asString).collect(Collectors.joining());
            System.out.print(output);
            return VOID;
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
            throw new RuntimeException(String.format("The required number of arguments (%d) does not match current amount (%d)", rightNumber, length));
        }
    }
}
