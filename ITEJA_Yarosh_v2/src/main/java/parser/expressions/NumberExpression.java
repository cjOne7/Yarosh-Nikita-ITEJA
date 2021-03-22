package parser.expressions;

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
        long result = (long) value;
        return result == value ? result + "" : value + "";
    }
}
