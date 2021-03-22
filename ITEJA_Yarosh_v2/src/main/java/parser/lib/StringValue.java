package parser.lib;

import token.TokenType;

public class StringValue implements IValue {
    private final TokenType type = TokenType.STRING;
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public double asDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public String toString() {
        return asString();
    }
}
