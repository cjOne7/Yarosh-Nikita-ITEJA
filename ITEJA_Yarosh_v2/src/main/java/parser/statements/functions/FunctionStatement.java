package parser.statements.functions;

import parser.expressions.FunctionExpression;
import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface. Wrapper for function expression
 *
 * @see IStatement
 */
public final class FunctionStatement implements IStatement {
    private final FunctionExpression expression;

    /**
     * @param expression function expression's body to be wrapped in function statement
     */
    public FunctionStatement(final FunctionExpression expression) {
        this.expression = expression;
    }

    /**
     * Execute function as statement
     */
    @Override
    public void execute() {
        expression.eval();
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
