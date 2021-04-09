package parser.statements.functions;

import parser.expressions.FunctionExpression;
import parser.statements.IStatement;

/**
 * @see IStatement
 */
public final class FunctionStatement implements IStatement {
    private final FunctionExpression expression;

    /**
     * @param expression
     */
    public FunctionStatement(final FunctionExpression expression) {
        this.expression = expression;
    }

    /**
     *
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
