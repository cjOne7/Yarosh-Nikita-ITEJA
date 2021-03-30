package parser.lib.datatypes;

import parser.lib.IValue;

public class DoubleValue implements IValue {
    private final double value;

    public DoubleValue(boolean value) {
        this.value = value ? 1 : 0;
    }

    public DoubleValue(double value) {
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
