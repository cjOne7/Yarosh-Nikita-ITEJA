package parser.lib.functions;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.IValue;
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
    private static final String WRITELN = "writeln";

    public static final IValue VOID = new StringValue("void");
    private static final Map<String, IFunction> FUNCTIONS = new HashMap<>();
    private static final Map<String, Boolean> FUNCTION_HAS_RETURN_TYPE = new HashMap<>();

    private Functions() {}

    static {
        final String errorMess = "The required number of arguments does not match current amount.";
        //maths
        addFunction(COS, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.cos(args[0].asDouble()));
        });
        addFunction(SIN, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.sin(args[0].asDouble()));
        });
        addFunction(TAN, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.tan(args[0].asDouble()));
        });
        addFunction(COTAN, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(1 / Math.tan(args[0].asDouble()));
        });
        addFunction(SQRT, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.sqrt(args[0].asDouble()));
        });
        addFunction(ROUND, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.round(args[0].asDouble()));
        });
        addFunction(FLOOR, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.floor(args[0].asDouble()));
        });
        addFunction(CEIL, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.ceil(args[0].asDouble()));
        });
        addFunction(ABS, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.abs(args[0].asDouble()));
        });
        addFunction(LN, true, args -> {
            check(args.length != 1, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.log(args[0].asDouble()));
        });
        addFunction(POWER, true, args -> {
            check(args.length != 2, errorMess + "Required " + 1 + ", but found " + args.length);
            return new DoubleValue(Math.pow(args[0].asDouble(), args[1].asDouble()));
        });
        //Input/Output
        addFunction(WRITE, false, args -> {
            final String output = Arrays.stream(args).map(IValue::asString).collect(Collectors.joining());
            System.out.print(output);
            return VOID;
        });
        addFunction(WRITELN, false, args -> {
            System.out.println(Arrays.stream(args).map(IValue::asString).collect(Collectors.joining()));
            return VOID;
        });
    }

    private static void addFunction(final String functionName, final boolean hasFunctionReturn, final IFunction function) {
        FUNCTION_HAS_RETURN_TYPE.put(functionName, hasFunctionReturn);
        FUNCTIONS.put(functionName, function);
    }

    public static boolean functionHasReturnType(final String functionName) {
        check(isKeyNotExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTION_HAS_RETURN_TYPE.get(functionName);
    }

    public static IFunction getFunctionByName(final String functionName) {
        check(isKeyNotExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTIONS.get(functionName);
    }

    public static boolean isKeyNotExists(final String key) {
        return !FUNCTIONS.containsKey(key);
    }

    private static void check(final boolean condition, final String exceptionMessage) {
        if (condition) {
            throw new RuntimeException(exceptionMessage);
        }
    }
}
