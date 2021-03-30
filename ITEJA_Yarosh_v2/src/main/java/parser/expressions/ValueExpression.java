package parser.expressions;

import parser.lib.IValue;
import parser.lib.DoubleValue;
import parser.lib.StringValue;

public class ValueExpression implements IExpression {

    private final IValue value;

    public ValueExpression(double value) {
        this.value = new DoubleValue(value);
    }

    public ValueExpression(String value) {
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
