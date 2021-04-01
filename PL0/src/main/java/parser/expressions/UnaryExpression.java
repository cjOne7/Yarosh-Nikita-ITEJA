package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;
import parser.lib.StringValue;

public class UnaryExpression implements IExpression {

    private final IExpression expression;
    private final String operation;

    public UnaryExpression(String operation, IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public IValue eval() {
        IValue value = expression.eval();
        switch (operation) {
            case "-":
                return new NumberValue(-value.asNumber());
            case "odd":
                return new NumberValue(value.asNumber() % 2 == 0 ? 0 : 1);
            case "+":
                if (value instanceof StringValue) {
                    return new NumberValue(value.asNumber());
                }
                return new NumberValue(value.asNumber());
            default:
                return new NumberValue(value.asNumber());
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", operation.length() > 1 ? operation + " " : operation, expression);
    }
}
