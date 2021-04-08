package lexer.constants;

import token.TokenType;

public final class Brackets {
    public static final char OPEN_ROUND_BRACKET = '(';
    public static final char CLOSE_ROUND_BRACKET = ')';
    public static final char OPEN_CURVE_BRACKET = '{';
    public static final char CLOSE_CURVE_BRACKET = '}';

    private Brackets() {}

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
