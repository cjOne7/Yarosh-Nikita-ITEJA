package parser.statements;

import parser.expressions.IExpression;
import parser.lib.*;
import parser.lib.datatypes.IValue;

/**
 * Implementation of the <tt>IStatement</tt> interface for assigning to variables new values
 *
 * @see IStatement
 */
public final class AssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    /**
     * @param identifier variable identifier to which will be assigned {@link #expression}'s evaluation result
     * @param expression represents R-value in assignment statement
     */
    public AssignmentStatement(final String identifier, final IExpression expression) {
        this.variableName = identifier;
        this.expression = expression;
    }

    /**
     * Assign a new value and update variables map in {@link Variables}
     */
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
