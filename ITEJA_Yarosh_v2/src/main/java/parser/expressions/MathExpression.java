package parser.expressions;

import lexer.constants.KeyWords;
import parser.lib.IValue;
import parser.lib.DoubleValue;

public class MathExpression implements IExpression {
    private final IExpression expression;
    private final String functionName;

    public MathExpression(String functionName, IExpression expression) {
        this.functionName = functionName;
        this.expression = expression;
    }

    public static boolean isMathExpression(String functionName) {
        switch (functionName.toLowerCase()) {
            case KeyWords.SQRT:
            case KeyWords.SQR:
            case KeyWords.ROUND:
            case KeyWords.FLOOR:
            case KeyWords.CEIL:
            case KeyWords.ABS:
            case KeyWords.LN:
            case KeyWords.COS:
            case KeyWords.SIN:
            case KeyWords.TAN:
            case KeyWords.COT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public IValue eval() {
        IValue value = expression.eval();
        if (value instanceof DoubleValue) {
            switch (functionName) {
                case KeyWords.SQRT:
                    return new DoubleValue(Math.sqrt(value.asDouble()));
                case KeyWords.SQR:
                    return new DoubleValue(Math.pow(value.asDouble(), 2));
                case KeyWords.ROUND:
                    return new DoubleValue(Math.round(value.asDouble()));
                case KeyWords.FLOOR:
                    return new DoubleValue(Math.floor(value.asDouble()));
                case KeyWords.LN:
                    return new DoubleValue(Math.log(value.asDouble()));
                case KeyWords.COS:
                    return new DoubleValue(Math.cos(value.asDouble()));
                case KeyWords.SIN:
                    return new DoubleValue(Math.sin(value.asDouble()));
                case KeyWords.TAN:
                    return new DoubleValue(Math.tan(value.asDouble()));
                case KeyWords.COT:
                    return new DoubleValue(1 / Math.tan(value.asDouble()));
                case KeyWords.ABS:
                default:
                    return new DoubleValue(Math.abs(value.asDouble()));
            }
        }

        throw new RuntimeException("Can't provide math operations to string.");
    }
}
