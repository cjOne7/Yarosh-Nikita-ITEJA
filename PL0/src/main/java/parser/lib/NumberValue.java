package parser.lib;

public class NumberValue implements IValue {
    private final int value;

    public NumberValue(boolean value) {
        this.value = value ? 1 : 0;
    }

    public NumberValue(int value) {
        this.value = value;
    }

    @Override
    public int asNumber() {
        return value;
    }

    @Override
    public String asString() {
        return Integer.toString(value);
    }

    @Override
    public String toString() {
        return asString();
    }
}