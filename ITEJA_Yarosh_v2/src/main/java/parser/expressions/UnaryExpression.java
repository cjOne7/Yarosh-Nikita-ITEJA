package parser.expressions;

import lexer.constants.MathOperators;
import parser.lib.IValue;
import parser.lib.NumberValue;

public class UnaryExpression implements IExpression {

    private final IExpression expression;
    private final char operation;

    public UnaryExpression(char operation, IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public IValue eval() {
        switch (operation) {
            case MathOperators.MINUS:
                return new NumberValue(-expression.eval().asDouble());
            case MathOperators.PLUS:
            default:
                return new NumberValue(expression.eval().asDouble());
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", operation, expression);
    }
}
