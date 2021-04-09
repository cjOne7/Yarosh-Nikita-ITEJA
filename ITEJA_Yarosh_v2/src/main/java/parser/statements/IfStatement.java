package parser.statements;

import parser.expressions.IExpression;

/**
 * Implementation of the <tt>IStatement</tt> interface for implementation conditional statements
 *
 * @see IStatement
 */
public final class IfStatement implements IStatement {
    private final IExpression condition;
    private final IStatement ifStatement;
    private final IStatement elseStatement;

    /**
     * @param condition     expression, which after evaluation return value 1 if true and 0 if false
     * @param ifStatement   one or more statements for executing if {@link #condition} is true
     * @param elseStatement one or more statements for executing if {@link #condition} is false
     */
    public IfStatement(final IExpression condition, final IStatement ifStatement, final IStatement elseStatement) {
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    /**
     * Execute if-else statement
     */
    @Override
    public void execute() {
        if (condition.eval().asDouble() == 1) {
            ifStatement.execute();
        }
        else if (elseStatement != null) {
            elseStatement.execute();
        }
    }

    @Override
    public String toString() {
        final double result = condition.eval().asDouble();
        return "if " + (result == 1 ? "true" : "false") + " then\nbegin\n\t" + ifStatement + "end;"
                + (result == 0 ? "\nelse\nbegin\n\t" + elseStatement + "end;" : "");
    }
}
