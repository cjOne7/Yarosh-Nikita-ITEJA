package parser.expressions;

import lexer.constants.KeyWords;
import parser.lib.IValue;
import parser.lib.NumberValue;
import parser.lib.StringValue;

public class BinaryExpression implements IExpression {
    private final IExpression expression1, expression2;
    private final String operation;

    public BinaryExpression(String operation, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public IValue eval() {
        final IValue value1 = expression1.eval();
        final IValue value2 = expression2.eval();
        if (value1 instanceof StringValue || value2 instanceof StringValue) {
            final String string1 = value1.asString();
            final String string2 = value2.asString();
            switch (operation) {
                case KeyWords.DIV:
                case KeyWords.MOD:
                case "-":
                case "/":
                    throw new RuntimeException("Strings don't support '" + operation + "' operation");
                case "*":
                    if (value2 instanceof NumberValue) {
                        final int iterations = (int) value2.asDouble();
                        final StringBuilder buffer = new StringBuilder();
                        for (int i = 0; i < iterations; i++) {
                            buffer.append(string1);
                        }
                        return new StringValue(buffer.toString());
                    }
                    throw new RuntimeException("You can't string times string");
                case "+":
                default:
                    return new StringValue(string1 + string2);
            }
        }
        final double number1 = value1.asDouble();
        final double number2 = value2.asDouble();
        double result;
        switch (operation) {
            case "-":
                result = number1 - number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case "*":
                result = number1 * number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case "/":
                result = number1 / number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case KeyWords.MOD:
                result = number1 % number2;
                return new NumberValue(result);
            case KeyWords.DIV:
                return new NumberValue((long) number1 / (long) number2);
            case "+":
            default:
                result = number1 + number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
