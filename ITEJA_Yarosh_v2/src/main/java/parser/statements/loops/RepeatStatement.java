package parser.statements.loops;

import parser.expressions.IExpression;
import parser.statements.IStatement;

public final class RepeatStatement implements IStatement {
    private final IExpression condition;
    private final IStatement blockStatement;

    public RepeatStatement(final IExpression condition, final IStatement blockStatement) {
        this.condition = condition;
        this.blockStatement = blockStatement;
    }

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
