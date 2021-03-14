package token;

public final class Token {
    private TokenType type;
    private String stringToken;
    private int rowPosition;
    private int columnPosition;


    public Token(TokenType type, String stringToken, int rowPosition, int columnPosition) {
        this.type = type;
        this.stringToken = stringToken;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public Token(TokenType type, String stringToken) {
        this.type = type;
        this.stringToken = stringToken;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
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
        return "Type: " + type + ", value: '" + stringToken + "', row: " + rowPosition + ", column: " + columnPosition;
    }
}
