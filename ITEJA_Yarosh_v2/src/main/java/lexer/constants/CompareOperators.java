package lexer.constants;

import token.TokenType;

public class CompareOperators {
    public static final char EQUALITY = '=';
    public static final char GREATER = '>';
    public static final char LESS = '<';

    public static boolean isComparisonOperator(char character) {
        switch (character){
            case EQUALITY:
            case GREATER:
            case LESS:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectComparisonOperatorType(char character) {
        switch (character){
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
}
