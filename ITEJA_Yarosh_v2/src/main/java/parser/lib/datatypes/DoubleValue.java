package parser.lib.datatypes;

public final class DoubleValue implements IValue {
    private final double value;

    public DoubleValue(final boolean value) {
        this.value = value ? 1 : 0;
    }

    public DoubleValue(final double value) {
        this.value = value;
    }

    @Override
    public double asDouble() {
        return Math.round(value * 1000.0) / 1000.0;
    }

    @Override
    public String asString() {
        final long result = (long) value;
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
