package parser.statements;

import parser.expressions.IExpression;
import parser.lib.IValue;
import parser.lib.datatypes.StringValue;

public class WriteStatement implements IStatement {
    private IExpression expression;

    public WriteStatement() {
    }

    public WriteStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        if (expression == null) {
            System.out.println();
        } else {
            IValue value = expression.eval();
            if (value instanceof StringValue) {
                System.out.println(value);
            }
            else {
                double doubleValue = value.asDouble();
                long result = (long) doubleValue;//For example, 1.0 will be typed as 1
                System.out.println(result == doubleValue ? result + "" : doubleValue + "");
            }
        }
    }

    @Override
    public String toString() {
        return "writeln(" + expression + ");\n";
    }
}