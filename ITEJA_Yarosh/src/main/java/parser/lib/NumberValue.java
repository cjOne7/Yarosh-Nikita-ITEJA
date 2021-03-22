package parser.lib;

public class NumberValue implements IValue {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
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
