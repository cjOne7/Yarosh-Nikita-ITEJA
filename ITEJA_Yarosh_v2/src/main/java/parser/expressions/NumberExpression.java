package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;

public class NumberExpression implements IExpression {

    private final IValue value;

    public NumberExpression(double value) {
        this.value = new NumberValue(value);
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
