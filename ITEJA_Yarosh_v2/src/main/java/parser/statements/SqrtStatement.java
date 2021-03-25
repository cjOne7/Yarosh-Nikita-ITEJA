package parser.statements;

import parser.expressions.IExpression;

public class SqrtStatement implements IStatement {
    private final IExpression expression;

    public SqrtStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
         expression.eval().asString();
    }
}
