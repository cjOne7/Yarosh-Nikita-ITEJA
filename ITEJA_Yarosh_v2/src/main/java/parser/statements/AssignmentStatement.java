package parser.statements;

import parser.expressions.IExpression;
import parser.lib.IValue;
import parser.lib.Variables;

public class AssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public AssignmentStatement(String identifier, IExpression expression) {
        this.variableName = identifier;
        this.expression = expression;
    }

    @Override
    public void execute() {
        Variables.put(variableName, expression.eval());
    }

    @Override
    public String toString() {
        return String.format("%s := %s;\n", variableName, expression);
    }
}
