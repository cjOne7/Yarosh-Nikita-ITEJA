package parser.statements.loops;

import parser.expressions.IExpression;
import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface for repeat-until loop
 *
 * @see IStatement
 */
public final class RepeatStatement implements IStatement {
    private final IExpression condition;
    private final IStatement blockStatement;

    /**
     * @param condition      when loop must be stopped. It will be working while condition is true
     * @param blockStatement loop's body
     */
    public RepeatStatement(final IExpression condition, final IStatement blockStatement) {
        this.condition = condition;
        this.blockStatement = blockStatement;
    }

    /**
     * Execute repeat-until loop
     */
    @Override
    public void execute() {
        do {
            try {
                blockStatement.execute();
            } catch (BreakStatement e) {
                break;
            }
        } while (condition.eval().asDouble() != 0);
    }

    @Override
    public String toString() {
        return "repeat\n\tbegin\n\t" + blockStatement + "end\nuntil" + condition + ";";
    }
}
