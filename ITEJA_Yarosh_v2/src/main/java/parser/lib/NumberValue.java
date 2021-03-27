package parser.lib;

public class NumberValue implements IValue {
    private final double value;

    public NumberValue(boolean value) {
        this.value = value ? 1 : 0;
    }

    public NumberValue(double value) {
        this.value = value;
    }

    @Override
    public double asDouble() {
        return Math.round(value * 1000.0) / 1000.0;
    }

    @Override
    public String asString() {
        long result = (long) value;
        if (result == value) {
            return String.format("%.0f", value);
        }
        return Double.toString(value);
    }

    @Override
    public String toString() {
        return asString();
    }
}
