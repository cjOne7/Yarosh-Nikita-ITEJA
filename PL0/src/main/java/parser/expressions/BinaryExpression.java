package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;
import parser.lib.StringValue;

import static lexer.constants.MathOperators.*;

public class BinaryExpression implements IExpression {
    private final IExpression expression1, expression2;
    private final char operation;

    public BinaryExpression(char operation, IExpression expression1, IExpression expression2) {
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
                case DIVIDE:
                    throw new RuntimeException("Strings don't support '" + operation + "' operation");
                case MINUS:
                    if (value2 instanceof NumberValue) {
                        int value = (int) value2.asDouble();
                        String stringValue1 = value1.asString();
                        if (stringValue1.length() < value) {
                            throw new RuntimeException(stringValue1 + " (" + stringValue1.length() + ") is smaller than subtracted value (" + value + ").");
                        }
                        return new StringValue(stringValue1.substring(0, stringValue1.length() - value));
                    }
                    throw new RuntimeException("You can't string minus string");
                case MULTIPLY:
                    if (value1.getClass() != value2.getClass()) {
                        return value1 instanceof NumberValue
                                ? multiplyStrings(value1, value2) : multiplyStrings(value2, value1);
                    }
                    throw new RuntimeException("You can't string times string");
                case PLUS:
                default:
                    return new StringValue(string1 + string2);
            }
        }
        final double number1 = value1.asDouble();
        final double number2 = value2.asDouble();
        double result;
        switch (operation) {
            case MINUS:
                result = number1 - number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case MULTIPLY:
                result = number1 * number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case DIVIDE:
                result = number1 / number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
            case PLUS:
            default:
                result = number1 + number2;
                return new NumberValue(Math.round(result * 100.0) / 100.0);
        }
    }

    private StringValue multiplyStrings(IValue number, IValue string) {
        final int iterations = (int) number.asDouble();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            buffer.append(string);
        }
        return new StringValue(buffer.toString());
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
