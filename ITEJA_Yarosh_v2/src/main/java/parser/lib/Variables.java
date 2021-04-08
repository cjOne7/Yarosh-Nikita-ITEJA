package parser.lib;

import parser.lib.datatypes.IValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This "static" class was created to control over global variables
 */
public final class Variables {
    /**
     * Map with {@link String} key and {@link IValue} value to store global variables
     */
    private static final Map<String, IValue> VARIABLES = new HashMap<>();

    private Variables() {}

    /**
     * @param key key to check if variable with this key exists
     * @return <tt>true</tt> if variable with this key exists
     */
    public static boolean isKeyExists(final String key) {
        return VARIABLES.containsKey(key);
    }

    /**
     * @param key the key whose associated value is to be returned
     * @return the value {@link IValue} to which the specified key is mapped depending on saved datatype
     * @throws RuntimeException if variable with that key doesn't exist
     */
    public static IValue getValueByKey(final String key) {
        if (isKeyExists(key)) {
            return VARIABLES.get(key);
        }
        throw new RuntimeException("Variable with key '" + key + "' doesn't exist.");
    }

    /**
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws NullPointerException if the specified key or value is null
     */
    public static void put(final String key, final IValue value) {
        if (value == null || key == null) {
            throw new NullPointerException("Arguments can't be null");
        }
        VARIABLES.put(key, value);
    }
}
