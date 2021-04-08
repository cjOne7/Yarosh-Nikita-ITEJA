package lexer.constants;

import token.TokenType;

/**
 * This class was created to check character if it is bracket or not, and also to detect its token type
 */
public final class Brackets {
    public static final char OPEN_ROUND_BRACKET = '(';
    public static final char CLOSE_ROUND_BRACKET = ')';
    public static final char OPEN_CURVE_BRACKET = '{';
    public static final char CLOSE_CURVE_BRACKET = '}';

    private Brackets() {}

    /**
     * @param character char to check if it is a some kind of brackets
     * @return <tt>true</tt> if character equals to allowed brackets
     */
    public static boolean isBracket(final char character) {
        switch (character) {
            case OPEN_ROUND_BRACKET:
            case CLOSE_ROUND_BRACKET:
            case OPEN_CURVE_BRACKET:
            case CLOSE_CURVE_BRACKET:
                return true;
            default:
                return false;
        }
    }

    /**
     *
     * @param character char to detect what kind of brackets it belongs
     * @return token's type of this character
     */
    public static TokenType detectBracketType(final char character) {
        switch (character) {
            case OPEN_ROUND_BRACKET:
                return TokenType.OPEN_ROUND_BRACKET;
            case CLOSE_ROUND_BRACKET:
                return TokenType.CLOSE_ROUND_BRACKET;
            case OPEN_CURVE_BRACKET:
                return TokenType.OPEN_CURVE_BRACKET;
            case CLOSE_CURVE_BRACKET:
                return TokenType.CLOSE_CURVE_BRACKET;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
