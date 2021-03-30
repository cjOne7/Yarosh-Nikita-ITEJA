package parser.statements.functions;

import parser.expressions.FunctionExpression;
import parser.statements.IStatement;

public class FunctionStatement implements IStatement {
    private final FunctionExpression expression;

    public FunctionStatement(FunctionExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        expression.eval();
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
