package parser.ast.expressions;

public class ConditionalExpression implements IExpression {
    private final IExpression expression1, expression2;
    private final String operation;

    public ConditionalExpression(String operation, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation) {
            case "#":
                return expression1.eval() != expression2.eval() ? 1 : 0;
            case "<":
                return expression1.eval() < expression2.eval() ? 1 : 0;
            case "<=":
                return expression1.eval() <= expression2.eval() ? 1 : 0;
            case ">":
                return expression1.eval() > expression2.eval() ? 1 : 0;
            case ">=":
                return expression1.eval() >= expression2.eval() ? 1 : 0;
            case "=":
            default:
                return expression1.eval() == expression2.eval() ? 1 : 0;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", expression1, operation, expression2);
    }


}
