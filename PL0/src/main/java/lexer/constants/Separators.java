package lexer.constants;

import token.TokenType;

public class Separators {
    public static final char DOT = '.';
    public static final char COLON = ':';
    private static final char SEMICOLON = ';';
    private static final char COMMA = ',';
    public static final char QUOTE = '"';

    public static boolean isSeparator(char character) {
        switch (character){
            case DOT:
            case COMMA:
            case COLON:
            case SEMICOLON:
            case QUOTE:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectSeparatorType(char character) {
        switch (character){
            case COMMA:
                return TokenType.COMMA;
            case DOT:
                return TokenType.DOT;
            case SEMICOLON:
                return TokenType.SEMICOLON;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
