package parser.lib;

import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.IValue;
import parser.lib.datatypes.StringValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This "static" class was created to control over global constants
 */
public final class Constants {
    public static final IValue ZERO = new DoubleValue(0);
    public static final IValue EMPTY = new StringValue("");
    /**
     * Map with {@link String} key and {@link IValue} value to store global constants. It has two pre-declared
     * constants: PI, E
     */
    private static final Map<String, IValue> CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("PI", new DoubleValue(3.14));
        CONSTANTS.put("E", new DoubleValue(2.7));
    }

    private Constants() {}

    /**
     * @param key key to check if constant with this key exists
     * @return <tt>true</tt> if constant with this key exists
     */
    public static boolean isKeyExists(final String key) {
        return CONSTANTS.containsKey(key);
    }

    /**
     * @param key the key whose associated value is to be returned
     * @return the value {@link IValue} to which the specified key is mapped depending on saved datatype
     * @throws RuntimeException if constant with that key doesn't exist
     */
    public static IValue getValueByKey(final String key) {
        if (isKeyExists(key)) {
            return CONSTANTS.get(key);
        }
        throw new RuntimeException("Constant with key '" + key + "' doesn't exist.");
    }

    /**
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws RuntimeException     check using {@link #isKeyExists(String)} and throw exception if constant with
     *                              the specified key exists
     * @throws NullPointerException if the specified key or value is null
     */
    public static void put(final String key, final IValue value) {
        if (value == null || key == null) {
            throw new NullPointerException("Arguments can't be null");
        }
        if (isKeyExists(key)) {
            throw new RuntimeException("Constant with key '" + key + "' has already existed");
        }
        CONSTANTS.put(key, value);
    }

}
