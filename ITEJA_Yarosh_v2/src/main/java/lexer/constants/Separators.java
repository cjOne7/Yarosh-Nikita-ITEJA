package lexer.constants;

import token.TokenType;

/**
 * This class was created to check character if it is separator or not, and also to detect its token type
 */
public final class Separators {
    public static final char DOT = '.';
    public static final char SEMICOLON = ';';
    public static final char COMMA = ',';
    public static final char COLON = ':';
    public static final char QUOTE = '"';

    private Separators() {}

    /**
     *
     * @param character char to check if it is a some kind of separators
     * @return <tt>true</tt> if character equals to allowed separators
     */
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

    /**
     *
     * @param character char to detect what kind of separators it belongs
     * @return token's type of this character
     */
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
