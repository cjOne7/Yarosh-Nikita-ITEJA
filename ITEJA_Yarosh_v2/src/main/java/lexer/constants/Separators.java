package lexer.constants;

import token.TokenType;

public final class Separators {
    public static final char DOT = '.';
    public static final char SEMICOLON = ';';
    public static final char COMMA = ',';
    public static final char COLON = ':';
    public static final char QUOTE = '"';

    private Separators() {}

    public static boolean isSeparator(final char character) {
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

    public static TokenType detectSeparatorType(final char character) {
        switch (character){
            case DOT:
                return TokenType.DOT;
            case COMMA:
                return TokenType.COMMA;
            case COLON:
                return TokenType.COLON;
            case SEMICOLON:
                return TokenType.SEMICOLON;
            case QUOTE:
                return TokenType.QUOTE;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
