package lexer.constants;

public final class WhiteChars {
    public static final char WHITE_SPACE = ' ';
    public static final char NEW_LINE = '\n';
    public static final char TABULATOR = '\t';
    public static final char CARRIAGE_RETURN = '\r';

    private WhiteChars() {}

    public static boolean isWhiteChar(final char character) {
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
}
