package lexer.constants;

import token.TokenType;

public class CompareOperators {
    public static final char EQUALITY = '=';
    public static final char GREATER = '>';
    public static final char LESS = '<';
    public static final String GREATER_OR_EQUAL = ">=";
    public static final String LESS_OR_EQUAL = "<=";
    public static final String NOTEQUAL = "<>";

    public static boolean isComparisonOperator(char character) {
        switch (character) {
            case EQUALITY:
            case GREATER:
            case LESS:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectComparisonOperatorType(char operator) {
        switch (operator) {
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

    public static TokenType detectComparisonOperatorType(String operator) {
        switch (operator) {
            case NOTEQUAL:
                return TokenType.NOTEQUAL;
            case GREATER_OR_EQUAL:
                return TokenType.GREATER_OR_EQUAL;
            case LESS_OR_EQUAL:
                return TokenType.LESS_OR_EQUAL;
            default:
                return operator.length() == 1 ? detectComparisonOperatorType(operator.charAt(0)) : TokenType.UNKNOWN;
        }
    }
}
