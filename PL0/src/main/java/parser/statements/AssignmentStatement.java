package parser.statements;

import parser.expressions.IExpression;
import parser.lib.*;

public class AssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public AssignmentStatement(String identifier, IExpression expression) {
        this.variableName = identifier;
        this.expression = expression;
    }

    @Override
    public void execute() {
        IValue value = LocalVars.isKeyExists(variableName)
                ? LocalVars.getValueByKey(variableName) : Variables.getValueByKey(variableName);
        IValue rightValue = expression.eval();
        if (value.getClass() != rightValue.getClass()) {
            String firstDatatype = value.getClass().getSimpleName();
            String secondDatatype = rightValue.getClass().getSimpleName();
            throw new RuntimeException("Datatype " + firstDatatype + " doesn't match " + secondDatatype);
        }
        if (LocalVars.isKeyExists(variableName)) {
            LocalVars.put(variableName, rightValue);
        }
        else {
            Variables.put(variableName, rightValue);
        }
    }

    @Override
    public String toString() {
        return String.format("%s := %s;\n", variableName, expression);
    }
}
