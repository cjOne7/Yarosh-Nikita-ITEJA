package lexer.constants;

import token.TokenType;

public final class MathOperators {
    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';

    private MathOperators() {}

    public static boolean isMathOperator(final char character) {
        switch (character){
            case PLUS:
            case MINUS:
            case DIVIDE:
            case MULTIPLY:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectMathOperatorType(final char character) {
        switch (character){
            case DIVIDE:
                return TokenType.DIVIDE;
            case MINUS:
                return TokenType.MINUS;
            case MULTIPLY:
                return TokenType.MULTIPLY;
            case PLUS:
                return TokenType.PLUS;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
