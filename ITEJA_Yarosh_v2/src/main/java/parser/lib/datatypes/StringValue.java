package parser.lib.datatypes;

/**
 * Implementation of the <tt>IValue</tt> interface to represent double values
 *
 * @see IValue
 */
public final class StringValue implements IValue {
    private final String value;

    /**
     * @param value set string value to {@link #value}
     */
    public StringValue(final String value) {
        this.value = value;
    }

    /**
     * @return string {@link #value} as double
     */
    @Override
    public double asDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * @return {@link #value}
     */
    @Override
    public String asString() {
        return value;
    }

    @Override
    public String toString() {
        return asString();
    }
}
