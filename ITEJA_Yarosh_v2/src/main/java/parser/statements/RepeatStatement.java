package parser.statements;

import parser.expressions.IExpression;

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
            blockStatement.execute();
        } while (condition.eval().asDouble() != 0);
    }

    @Override
    public String toString() {
        return "repeat\n\tbegin\n\t" + blockStatement + "end\nuntil" + condition + ";";
    }
}
