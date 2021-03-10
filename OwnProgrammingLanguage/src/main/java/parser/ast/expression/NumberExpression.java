package parser.ast.expression;

public class NumberExpression implements IExpression {

    private final double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
