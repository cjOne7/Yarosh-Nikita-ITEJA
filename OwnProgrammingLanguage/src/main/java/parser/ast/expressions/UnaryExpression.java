package parser.ast.expressions;

public class UnaryExpression implements IExpression {

    private final IExpression expression;
    private final char operation;

    public UnaryExpression(char operation, IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation) {
            case '-':
                return -expression.eval();
            case '+':
            default:
                return expression.eval();
        }
    }

    @Override
    public String toString() {
        return String.format("%c%s", operation, expression);
    }
}
