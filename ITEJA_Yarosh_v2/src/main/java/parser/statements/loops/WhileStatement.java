package parser.statements.loops;

import parser.expressions.IExpression;
import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface for while-do loop
 *
 * @see IStatement
 */
public final class WhileStatement implements IStatement {
    private final IExpression condition;
    private final IStatement blockStatement;

    /**
     * @param condition      when loop must be stopped. It will be working while condition is true
     * @param blockStatement loop's body
     */
    public WhileStatement(final IExpression condition, final IStatement blockStatement) {
        this.condition = condition;
        this.blockStatement = blockStatement;
    }

    /**
     * Execute while-do loop
     */
    @Override
    public void execute() {
        while (condition.eval().asDouble() != 0) {
            try {
                blockStatement.execute();
            } catch (BreakStatement e) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "while " + condition.eval() + " do\nbegin\n" + blockStatement + "end;";
    }
}
