package token;

/**
 *This class was created for convenient saving of string values with suitable type for further using in the parser
 */
public final class Token {
    private final TokenType tokenType;
    private final String stringToken;
    private final int rowPosition;
    private final int columnPosition;

    /**
     *
     * @param tokenType type of string representation of value
     * @param stringToken value
     * @param rowPosition row position in the code
     * @param columnPosition column position in the code
     */
    public Token(final TokenType tokenType, final String stringToken, final int rowPosition, final int columnPosition) {
        this.tokenType = tokenType;
        this.stringToken = stringToken;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getStringToken() {
        return stringToken;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + ", value: '" + stringToken + "', row: " + rowPosition + ", column: " + columnPosition;
    }
}
