package token;

public final class Token {
    private TokenType type;
    private String text;
    private int rowPosition;
    private int columnPosition;


    public Token(TokenType type, String text, int rowPosition, int columnPosition) {
        this.type = type;
        this.text = text;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return "Type: " + type + ", text: '" + text + '\'';
    }
}
