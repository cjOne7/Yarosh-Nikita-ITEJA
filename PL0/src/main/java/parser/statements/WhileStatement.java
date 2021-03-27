package parser.statements;

import parser.expressions.IExpression;

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
            blockStatement.execute();
        }
    }

    @Override
    public String toString() {
        return "while " + condition.eval() + " do\nbegin\n" + blockStatement + "end;";
    }
}
