package parser.statements.loops;

import parser.expressions.IExpression;
import parser.statements.IStatement;

public class WhileStatement implements IStatement {
    private final IExpression condition;
    private final IStatement blockStatement;

    public WhileStatement(IExpression condition, IStatement blockStatement) {
        this.condition = condition;
        this.blockStatement = blockStatement;
    }

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
