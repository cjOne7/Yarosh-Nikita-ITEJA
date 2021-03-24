package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;

public class SqrtExpression implements IExpression {
    private final IExpression expression;

    public SqrtExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval() {
        return new NumberValue(Math.sqrt(expression.eval().asDouble()));
    }

    @Override
    public String toString() {
        return expression.eval().asString();
    }
}
