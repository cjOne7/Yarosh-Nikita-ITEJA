package parser.statements;

import parser.expressions.IExpression;
import parser.lib.IValue;
import parser.lib.StringValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        IValue value = expression.eval();
        if (value instanceof StringValue) {
            System.out.println(value);
        } else {
            double doubleValue = value.asDouble();
            long result = (long) doubleValue;//For example, 1.0 will be typed as 1
            System.out.println(result == doubleValue ? result + "" :  doubleValue + "");
        }
    }

    @Override
    public String toString() {
        return "writeln(" + expression + ");\n";
    }
}