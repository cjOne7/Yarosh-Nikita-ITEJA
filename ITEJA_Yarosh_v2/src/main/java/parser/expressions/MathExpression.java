package parser.expressions;

import lexer.constants.KeyWords;
import parser.lib.IValue;
import parser.lib.NumberValue;

public class MathExpression implements IExpression {
    private final IExpression expression;
    private final String functionName;

    public MathExpression(IExpression expression, String functionName) {
        this.expression = expression;
        this.functionName = functionName;
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
        if (value instanceof NumberValue) {
            switch (functionName) {
                case KeyWords.SQRT:
                    return new NumberValue(Math.sqrt(value.asDouble()));
                case KeyWords.SQR:
                    return new NumberValue(Math.pow(value.asDouble(), 2));
                case KeyWords.ROUND:
                    return new NumberValue(Math.round(value.asDouble()));
                case KeyWords.FLOOR:
                    return new NumberValue(Math.floor(value.asDouble()));
                case KeyWords.LN:
                    return new NumberValue(Math.log(value.asDouble()));
                case KeyWords.COS:
                    return new NumberValue(Math.cos(value.asDouble()));
                case KeyWords.SIN:
                    return new NumberValue(Math.sin(value.asDouble()));
                case KeyWords.TAN:
                    return new NumberValue(Math.tan(value.asDouble()));
                case KeyWords.COT:
                    return new NumberValue(1 / Math.tan(value.asDouble()));
                case KeyWords.ABS:
                default:
                    return new NumberValue(Math.abs(value.asDouble()));
            }
        }

        throw new RuntimeException("Can't provide math operations to string.");
    }
}
