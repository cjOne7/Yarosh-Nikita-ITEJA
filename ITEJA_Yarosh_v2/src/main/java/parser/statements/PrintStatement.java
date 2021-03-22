package parser.statements;

import parser.expressions.IExpression;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        double value = expression.eval().asDouble();
        long result = (long) value;
        System.out.println(result == value ? result + "" : value + "");//For example, 1.0 will be typed as 1
    }

    @Override
    public String toString() {
        return "writeln(" + expression + ");\n";
    }
}