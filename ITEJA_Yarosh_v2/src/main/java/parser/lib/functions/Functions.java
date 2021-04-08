package parser.lib.functions;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.IValue;
import parser.lib.datatypes.StringValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the <tt>IFunction</tt> interface for implementation build-in functions
 *
 * @see IFunction
 */
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

    /**
     * Special return value for functions which can be only statements
     */
    public static final IValue VOID = new StringValue("void");

    /**
     * Map with {@link String} key and {@link IFunction} as value to store executable build-in functions body
     */
    private static final Map<String, IFunction> FUNCTIONS = new HashMap<>();

    /**
     * Map with {@link String} key and {@link Boolean} as value to store return type of build-in functions
     */
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

    /**
     * Add function to {@link #FUNCTIONS}
     *
     * @param functionName      function identifier
     * @param hasFunctionReturn return type (void or not)
     * @param function          function body
     */
    private static void addFunction(final String functionName, final boolean hasFunctionReturn, final IFunction function) {
        FUNCTION_HAS_RETURN_TYPE.put(functionName, hasFunctionReturn);
        FUNCTIONS.put(functionName, function);
    }

    /**
     * @param functionName function identifier
     * @return <tt>true</tt> if function has a non-void return value
     * @throws RuntimeException when received function identifier is unknown
     */
    public static boolean functionHasReturnType(final String functionName) {
        check(isKeyNotExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTION_HAS_RETURN_TYPE.get(functionName);
    }

    /**
     * @param functionName function identifier
     * @return function body
     * @throws RuntimeException when received function identifier is unknown
     */
    public static IFunction getFunctionByName(final String functionName) {
        check(isKeyNotExists(functionName), "Unknown function '" + functionName + "'.");
        return FUNCTIONS.get(functionName);
    }

    /**
     * @param key key to check if function with this key exists
     * @return <tt>true</tt> if function with this key doesn't exist
     */
    public static boolean isKeyNotExists(final String key) {
        return !FUNCTIONS.containsKey(key);
    }

    /**
     * @param condition        condition which should be checked
     * @param exceptionMessage message which will be typed to console if condition is true
     * @throws RuntimeException when condition is true
     */
    private static void check(final boolean condition, final String exceptionMessage) {
        if (condition) {
            throw new RuntimeException(exceptionMessage);
        }
    }
}
