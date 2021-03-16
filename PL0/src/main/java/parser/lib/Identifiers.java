package parser.lib;

import java.util.HashSet;
import java.util.Set;

public class Identifiers {
    private static final Set<String> IDENTIFIERS = new HashSet<>();

    public static boolean isIdentifierExists(String identifier) {
        return IDENTIFIERS.contains(identifier);
    }

    public static String get(String identifier) {
        return IDENTIFIERS.stream().filter(identifier::equals).findAny().orElse(null);
    }

    public static void put(String identifier) {
        if (!IDENTIFIERS.add(identifier)) {
            throw new RuntimeException("Identifier '" + identifier + "' has already existed");
        }
    }

}
