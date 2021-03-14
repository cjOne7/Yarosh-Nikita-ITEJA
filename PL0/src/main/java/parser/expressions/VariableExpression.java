package parser.expressions;

import parser.lib.Variables;

public class VariableExpression implements IExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double eval() {
        if (Variables.isKeyExists(name)) {
            return Variables.getValueByKey(name);
        }
        throw new RuntimeException("Variable doesn't exist.");
    }

    @Override
    public String toString() {
        return Variables.isKeyExists(name) ? Double.toString(Variables.getValueByKey(name)) : "";
    }
}
