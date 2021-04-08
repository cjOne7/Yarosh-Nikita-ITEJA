package parser.expressions;

import lexer.constants.MathOperators;
import parser.lib.datatypes.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;

/**
 * Implementation of the <tt>IExpression</tt> interface for unary expressions
 *
 * @see IExpression
 */
public final class UnaryExpression implements IExpression {
    private final IExpression expression;
    private final char operation;

    /**
     * @param operation  this operation will be performed on the expression
     * @param expression expression on which will be performed operation
     */
    public UnaryExpression(final char operation, final IExpression expression) {
        this.expression = expression;
        this.operation = operation;
    }

    /**
     * @return value {@link IValue} depending on expression evaluation result datatype
     * @throws RuntimeException when result of evaluation expression doesn't support the operation
     */
    @Override
    public IValue eval() {
        final IValue value = expression.eval();
        if (value instanceof StringValue) {
            throw new RuntimeException("You can't provide unary operations on string values.");
        }
        switch (operation) {
            case MathOperators.MINUS:
                return new DoubleValue(-value.asDouble());
            case MathOperators.PLUS:
            default:
                return new DoubleValue(value.asDouble());
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", operation, expression);
    }
}
