package parser.ast.expression;

import lib.Variables;

public class VariableExpression implements IExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double eval() {
        if (!Variables.isExists(name)) {
            throw new RuntimeException("Constant doesn't exists.");
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
