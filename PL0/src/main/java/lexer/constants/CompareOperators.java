package lexer.constants;

import token.TokenType;

public class CompareOperators {
    public static final char EQUALITY = '=';
    public static final char GREATER = '>';
    public static final char LESS = '<';
    public static final char NOTEQUAL = '#';
    public static final char QUESTION_MARK = '?';
    public static final char EXCLAMATION_MARK = '!';

    public static boolean isComparisonOperator(char character) {
        switch (character){
            case EQUALITY:
            case GREATER:
            case LESS:
            case NOTEQUAL:
            case EXCLAMATION_MARK:
            case QUESTION_MARK:
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
            case NOTEQUAL:
                return TokenType.NOTEQUAL;
            case EXCLAMATION_MARK:
                return TokenType.EXCLAMATION_MARK;
            case QUESTION_MARK:
                return TokenType.QUESTION_MARK;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
