package parser.statements;

import parser.expressions.IExpression;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.println(expression.eval());
    }

    @Override
    public String toString() {
        return "! " + expression + ";\n";
    }
}
