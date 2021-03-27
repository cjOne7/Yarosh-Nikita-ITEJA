package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;

public class UnaryExpression implements IExpression {

    private final IExpression expression;
    private final String operation;

    public UnaryExpression(String operation, IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public IValue eval() {
        switch (operation) {
            case "-":
                return new NumberValue(-expression.eval().asDouble());
            case "odd":
                return new NumberValue(expression.eval().asDouble() % 2 == 0 ? 0 : 1);
            case "+":
            default:
                return new NumberValue(expression.eval().asDouble());
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", operation.length() > 1 ? operation + " " : operation, expression);
    }
}
