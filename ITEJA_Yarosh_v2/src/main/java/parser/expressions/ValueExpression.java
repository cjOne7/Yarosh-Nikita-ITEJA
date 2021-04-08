package parser.expressions;

import parser.lib.datatypes.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

public final class ValueExpression implements IExpression {

    private final IValue value;

    public ValueExpression(final double value) {
        this.value = new DoubleValue(value);
    }

    public ValueExpression(final String value) {
        this.value = new StringValue(value);
    }

    @Override
    public IValue eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
