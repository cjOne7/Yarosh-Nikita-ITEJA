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
        if (value1 instanceof StringValue) {
            final String string1 = value1.asString();
            final String string2 = value2.asString();
            switch (operation) {
                case MINUS:
                case DIVIDE:
                    throw new RuntimeException("Strings don't support '" + operation + "' operation");
                case MULTIPLY:
                    if (value2 instanceof NumberValue) {
                        final int iterations = (int) value2.asDouble();
                        final StringBuilder buffer = new StringBuilder();
                        for (int i = 0; i < iterations; i++) {
                            buffer.append(string1);
                        }
                        return new StringValue(buffer.toString());
                    }
                    throw new RuntimeException("You can't string times string");
                case PLUS:
                default:
                    return new StringValue(string1 + string2);
            }
        }
        final double number1 = value1.asDouble();
        final double number2 = value2.asDouble();
        switch (operation) {
            case MINUS:
                return new NumberValue(number1 - number2);
            case MULTIPLY:
                return new NumberValue(number1 * number2);
            case DIVIDE:
                return new NumberValue(number1 / number2);
            case PLUS:
            default:
                return new NumberValue(number1 + number2);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
