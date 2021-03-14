package parser.expressions;

import static lexer.constants.MathOperators.*;

public class UnaryExpression implements IExpression {

    private final IExpression expression;
    private final String operation;

    public UnaryExpression(String operation, IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation) {
            case MINUS + "":
                return -expression.eval();
            case "odd":
                return expression.eval() % 2 == 0 ? 0 : 1;
            case PLUS + "":
            default:
                return expression.eval();
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", operation.length() > 1 ? operation + " " : operation, expression);
    }
}
