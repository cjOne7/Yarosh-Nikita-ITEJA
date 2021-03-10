package parser.ast;

public class BinaryExpression implements IExpression {
    private final IExpression expression1, expression2;
    private final char operation;

    public BinaryExpression(char operation, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation) {
            case '-':
                return expression1.eval() - expression2.eval();
            case '*':
                return expression1.eval() * expression2.eval();
            case '/':
                return expression1.eval() / expression2.eval();
            case '+':
            default:
                return expression1.eval() + expression2.eval();
        }
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", expression1, operation, expression2);
    }
}
