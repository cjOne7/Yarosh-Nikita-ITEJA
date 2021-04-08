package parser.statements;

import parser.expressions.IExpression;
import parser.lib.*;
import parser.lib.datatypes.IValue;

public final class AssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public AssignmentStatement(final String identifier, final IExpression expression) {
        this.variableName = identifier;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final IValue value = Variables.getValueByKey(variableName);
        final IValue rightValue = expression.eval();
        if (value.getClass() != rightValue.getClass()) {
            final String firstDatatype = value.getClass().getSimpleName();
            final String secondDatatype = rightValue.getClass().getSimpleName();
            throw new RuntimeException("Datatype " + firstDatatype + " doesn't match " + secondDatatype);
        }
        Variables.put(variableName, rightValue);
    }

    @Override
    public String toString() {
        return String.format("%s := %s;\n", variableName, expression);
    }
}
