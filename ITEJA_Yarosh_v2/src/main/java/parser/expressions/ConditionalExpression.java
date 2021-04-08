package parser.expressions;

import lexer.constants.CompareOperators;
import lexer.constants.KeyWords;
import parser.lib.datatypes.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

/**
 * Implementation of the <tt>IExpression</tt> interface for conditional expressions
 *
 * @see IExpression
 */
public final class ConditionalExpression implements IExpression {
    private IExpression expression1, expression2;
    private String operation;
    private IValue value;

    /**
     * @param operation   this operation will be performed on the expressions
     * @param expression1 left expression
     * @param expression2 right expression
     */
    public ConditionalExpression(final String operation, final IExpression expression1, final IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    /**
     * @param result converted double result into boolean after evaluating
     */
    public ConditionalExpression(final boolean result) {
        value = new DoubleValue(result);
    }

    /**
     * @return value {@link IValue} depending on expression evaluation result datatype
     */
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
                case CompareOperators.NOTEQUAL:
                    return new DoubleValue(!string1.equals(string2));
                case CompareOperators.LESS_OR_EQUAL:
                    return new DoubleValue(string1.compareTo(string2) <= 0);
                case CompareOperators.GREATER_OR_EQUAL:
                    return new DoubleValue(string1.compareTo(string2) >= 0);
                case "<":
                    return new DoubleValue(string1.compareTo(string2) < 0);
                case ">":
                    return new DoubleValue(string1.compareTo(string2) > 0);
                case "=":
                default:
                    return new DoubleValue(string1.equals(string2));
            }
        }

        final double number1 = value1.asDouble();
        final double number2 = value2.asDouble();
        switch (operation) {
            case CompareOperators.NOTEQUAL:
                return new DoubleValue(number1 != number2);
            case "<":
                return new DoubleValue(number1 < number2);
            case CompareOperators.LESS_OR_EQUAL:
                return new DoubleValue(number1 <= number2);
            case ">":
                return new DoubleValue(number1 > number2);
            case CompareOperators.GREATER_OR_EQUAL:
                return new DoubleValue(number1 >= number2);
            case KeyWords.AND:
                return new DoubleValue((number1 != 0) && (number2 != 0));
            case KeyWords.OR:
                return new DoubleValue((number1 != 0) || (number2 != 0));
            case "=":
            default:
                return new DoubleValue(number1 == number2);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", expression1, operation, expression2);
    }

}
