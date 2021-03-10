package lexer.constants;

import token.TokenType;

public class Brackets {
    private static final char OPEN_ROUND_BRACKET = '(';
    private static final char CLOSE_ROUND_BRACKET = ')';

    public static boolean IsBracket(char character) {
        switch (character){
            case OPEN_ROUND_BRACKET:
            case CLOSE_ROUND_BRACKET:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectBracketType(char character) {
        switch (character){
            case OPEN_ROUND_BRACKET:
                return TokenType.OPEN_ROUND_BRACKET;
            case CLOSE_ROUND_BRACKET:
                return TokenType.CLOSE_ROUND_BRACKET;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
