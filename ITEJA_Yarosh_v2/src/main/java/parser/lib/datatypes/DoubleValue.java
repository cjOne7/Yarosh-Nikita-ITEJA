package parser.lib.datatypes;

/**
 * Implementation of the <tt>IValue</tt> interface to represent double values
 *
 * @see IValue
 */
public final class DoubleValue implements IValue {
    private final double value;

    /**
     * @param value boolean value that can be obtained by after conditional expression
     */
    public DoubleValue(final boolean value) {
        this.value = value ? 1 : 0;
    }

    /**
     * @param value set double value to {@link #value}
     */
    public DoubleValue(final double value) {
        this.value = value;
    }

    /**
     * @return rounded double {@link #value}
     */
    @Override
    public double asDouble() {
        return Math.round(value * 1000.0) / 1000.0;
    }

    /**
     * @return double {@link #value} as string
     */
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
