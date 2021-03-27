package parser.statements;

import parser.expressions.IExpression;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement ifStatement;

    public IfStatement(IExpression expression, IStatement ifStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
    }

    @Override
    public void execute() {
        if (expression.eval().asDouble() == 1) {
            ifStatement.execute();
        }
    }

    @Override
    public String toString() {
        double result = expression.eval().asDouble();
        return "if " + (result == 1 ? "true" : "false") + " then\nbegin\n\t" + ifStatement + "end;";
    }
}
