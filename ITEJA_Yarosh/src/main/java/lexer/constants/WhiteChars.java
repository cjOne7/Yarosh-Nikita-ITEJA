package lexer.constants;

import token.TokenType;

public class WhiteChars {
    public static final char WHITE_SPACE = ' ';
    public static final char NEW_LINE = '\n';
    public static final char TABULATOR = '\t';
    public static final char CARRIAGE_RETURN = '\r';
    public static final String ESCAPED_CARRIAGE_RETURN = "\\r";
    public static final String ESCAPED_TABULATOR = "\\t";
    public static final String ESCAPED_NEW_LINE = "\\n";

    public static boolean isWhiteChar(char character) {
        switch (character){
            case NEW_LINE:
            case TABULATOR:
            case CARRIAGE_RETURN:
            case WHITE_SPACE:
                return true;
            default:
                return false;
        }
    }

    public static TokenType detectWhiteCharType(char character) {
        switch (character){
            case WHITE_SPACE:
                return TokenType.WHITE_SPACE;
            case TABULATOR:
                return TokenType.TABULATOR;
            case CARRIAGE_RETURN:
                return TokenType.CARRIAGE_RETURN;
            case NEW_LINE:
                return TokenType.NEW_LINE;
            default:
                return TokenType.UNKNOWN;
        }
    }

    public static String escapeWhiteChars(char character) {
        switch (character){
            case NEW_LINE:
                return ESCAPED_NEW_LINE;
            case TABULATOR:
                return ESCAPED_TABULATOR;
            case CARRIAGE_RETURN:
                return ESCAPED_CARRIAGE_RETURN;
            case WHITE_SPACE:
                return " ";
        }

        return "";
    }
}
