package parser.statements;

import parser.expressions.IExpression;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement ifStatement, elseStatement;

    public IfStatement(IExpression expression, IStatement ifStatement, IStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        if (expression.eval() != 0) {
            ifStatement.execute();
        }
        else if (elseStatement != null) {
            elseStatement.execute();
        }
    }
}
