package parser.expressions;

import parser.lib.datatypes.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

/**
 * Implementation of the <tt>IExpression</tt> interface for expressions with values
 *
 * @see IExpression
 */
public final class ValueExpression implements IExpression {

    private final IValue value;

    /**
     * @param value passed for creating an instance of {@link DoubleValue} class
     */
    public ValueExpression(final double value) {
        this.value = new DoubleValue(value);
    }

    /**
     * @param value passed for creating an instance of {@link StringValue} class
     */
    public ValueExpression(final String value) {
        this.value = new StringValue(value);
    }

    /**
     * @return value attribute of {@link ValueExpression} class
     */
    @Override
    public IValue eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
