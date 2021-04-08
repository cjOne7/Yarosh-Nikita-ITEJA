package lexer.constants;

import token.TokenType;

/**
 *This class was created to check character if it is a math operator or not, and also to detect its token type
 */
public final class MathOperators {
    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';

    private MathOperators() {}

    /**
     *
     * @param character char to check if it is a some kind of math operators
     * @return <tt>true</tt> if character equals to allowed math operators
     */
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

    /**
     *
     * @param character char to detect what kind of math operators it belongs
     * @return token's type of this character
     */
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
