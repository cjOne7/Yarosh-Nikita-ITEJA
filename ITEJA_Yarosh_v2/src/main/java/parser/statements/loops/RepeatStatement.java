package parser.statements.loops;

import parser.expressions.IExpression;
import parser.statements.IStatement;

public class RepeatStatement implements IStatement {
    private final IExpression condition;
    private final IStatement blockStatement;

    public RepeatStatement(IExpression condition, IStatement blockStatement) {
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
