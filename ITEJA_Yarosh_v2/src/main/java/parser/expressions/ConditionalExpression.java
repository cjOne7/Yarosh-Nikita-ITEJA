package parser.expressions;

import parser.lib.IValue;
import parser.lib.NumberValue;
import parser.lib.StringValue;

public class ConditionalExpression implements IExpression {
    private IExpression expression1, expression2;
    private String operation;
    private IValue value;

    public ConditionalExpression(String operation, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public ConditionalExpression(boolean result) {
        value = new NumberValue(result);
    }

    @Override
    public IValue eval() {
        if (expression1 == null) {
            return value;
        }
        final IValue value1 = expression1.eval();
        final IValue value2 = expression2.eval();
        if (value1 instanceof StringValue || value2 instanceof StringValue) {
            final String string1 = value1.asString();
            final String string2 = value2.asString();
            switch (operation) {
                case "<>":
                    return new NumberValue(!string1.equals(string2));
                case "<=":
                    return new NumberValue(string1.compareTo(string2) <= 0);
                case ">=":
                    return new NumberValue(string1.compareTo(string2) >= 0);
                case "<":
                    return new NumberValue(string1.compareTo(string2) < 0);
                case ">":
                    return new NumberValue(string1.compareTo(string2) > 0);
                case "=":
                default:
                    return new NumberValue(string1.equals(string2));
            }
        }

        double number1 = value1.asDouble();
        double number2 = value2.asDouble();
        switch (operation) {
            case "<>":
                return new NumberValue(number1 != number2);
            case "<":
                return new NumberValue(number1 < number2);
            case "<=":
                return new NumberValue(number1 <= number2);
            case ">":
                return new NumberValue(number1 > number2);
            case ">=":
                return new NumberValue(number1 >= number2);
            case "and":
                return new NumberValue((number1 != 0) && (number2 != 0));
            case "or":
                return new NumberValue((number1 != 0) || (number2 != 0));
            case "=":
            default:
                return new NumberValue(number1 == number2);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", expression1, operation, expression2);
    }


}
