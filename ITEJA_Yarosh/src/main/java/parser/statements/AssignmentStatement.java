package parser.statements;

import parser.expressions.IExpression;
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
        double result = expression.eval();
        Variables.putDoubleVar(variableName, result);
    }

    @Override
    public String toString() {
        return String.format("%s := %s;\n", variableName, expression);
    }
}
