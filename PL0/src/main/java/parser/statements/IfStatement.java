package parser.statements;

import parser.blocks.IBlock;
import parser.expressions.IExpression;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IBlock ifStatement, elseStatement;

    public IfStatement(IExpression expression, IBlock ifStatement, IBlock elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        double result = expression.eval();
        if (result == 1) {
            ifStatement.execute();
        } else {
            elseStatement.execute();
        }
    }

    @Override
    public String toString() {
        double result = expression.eval();
        return "if " + (result == 1 ? "true" : "false") + " then\nbegin\n\t" + ifStatement + "end;"
                + (result == 0 ? "\nelse\nbegin\n\t" + elseStatement + "end;" : "");
    }
}
