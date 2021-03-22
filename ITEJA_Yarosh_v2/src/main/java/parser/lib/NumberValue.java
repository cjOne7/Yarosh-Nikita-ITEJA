package parser.lib;

import token.TokenType;

public class NumberValue implements IValue {
    private final TokenType type = TokenType.DOUBLE;
    private final double value;

    public NumberValue(boolean value) {
        this.value = value ? 1 : 0;
    }

    public NumberValue(double value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public String asString() {
        return Double.toString(value);
    }
}
