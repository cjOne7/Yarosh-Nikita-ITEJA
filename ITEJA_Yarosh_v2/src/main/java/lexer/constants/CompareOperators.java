package lexer.constants;

import token.TokenType;

/**
 * This class was created to check character if it is compare operator or not, and also to detect its token type
 */
public final class CompareOperators {
    public static final char EQUALITY = '=';
    public static final char GREATER = '>';
    public static final char LESS = '<';
    public static final String GREATER_OR_EQUAL = ">=";
    public static final String LESS_OR_EQUAL = "<=";
    public static final String NOTEQUAL = "<>";

    private CompareOperators() {}

    /**
     *
     * @param character char to check if it is a some kind of compare operators
     * @return <tt>true</tt> if character equals to allowed compare operators
     */
    public static boolean isComparisonOperator(final char character) {
        switch (character) {
            case EQUALITY:
            case GREATER:
            case LESS:
                return true;
            default:
                return false;
        }
    }

    /**
     *
     * @param character char to detect what kind of compare operators it belongs
     * @return token's type of this character
     */
    public static TokenType detectComparisonOperatorType(final char character) {
        switch (character) {
            case EQUALITY:
                return TokenType.EQUAL;
            case GREATER:
                return TokenType.GREATER;
            case LESS:
                return TokenType.LESS;
            default:
                return TokenType.UNKNOWN;
        }
    }

    /**
     *
     * @param character two chars to detect what kind of compare operators they belong
     * @return token's type of this two-chars-string
     */
    public static TokenType detectComparisonOperatorType(final String character) {
        switch (character) {
            case NOTEQUAL:
                return TokenType.NOTEQUAL;
            case GREATER_OR_EQUAL:
                return TokenType.GREATER_OR_EQUAL;
            case LESS_OR_EQUAL:
                return TokenType.LESS_OR_EQUAL;
            default:
                return character.length() == 1 ? detectComparisonOperatorType(character.charAt(0)) : TokenType.UNKNOWN;
        }
    }
}
