package token;

public final class Token {
    private TokenType tokenType;
    private String stringToken;
    private int rowPosition;
    private int columnPosition;


    public Token(TokenType tokenType, String stringToken, int rowPosition, int columnPosition) {
        this.tokenType = tokenType;
        this.stringToken = stringToken;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public Token(TokenType tokenType, String stringToken) {
        this.tokenType = tokenType;
        this.stringToken = stringToken;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getStringToken() {
        return stringToken;
    }

    public void setStringToken(String stringToken) {
        this.stringToken = stringToken;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + ", value: '" + stringToken + "', row: " + rowPosition + ", column: " + columnPosition;
    }
}
