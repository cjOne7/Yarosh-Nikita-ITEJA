package parser.statements;

import parser.expressions.IExpression;
import parser.lib.IValue;
import parser.lib.Variables;

public class AssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public AssignmentStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void execute() {
        IValue result = expression.eval();
        Variables.put(variableName, result);
    }

    @Override
    public String toString() {
        return String.format("%s := %s;\n", variableName, expression);
    }
}
