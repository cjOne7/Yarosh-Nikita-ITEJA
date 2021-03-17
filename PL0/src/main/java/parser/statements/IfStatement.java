package parser.statements;

import parser.expressions.IExpression;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement ifStatement, elseStatement;

    public IfStatement(IExpression expression, IStatement ifStatement, IStatement elseStatement) {
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
            if (elseStatement != null) {
                elseStatement.execute();
            }
        }
    }

    @Override
    public String toString() {
        double result = expression.eval();
        return "if " + (result == 1 ? "true" : "false") + " then\nbegin\n\t" + ifStatement + "end;"
                + (result == 0 ? "\nelse\nbegin\n\t" + elseStatement + "end;" : "");
    }
}
