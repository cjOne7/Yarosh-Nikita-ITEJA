package parser.expressions;

import parser.lib.Constants;
import parser.lib.datatypes.IValue;
import parser.lib.Variables;

public class VariableExpression implements IExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public IValue eval() {
        if (Variables.isKeyExists(name)) {
            return Variables.getValueByKey(name);
        }
        if (Constants.isKeyExists(name)) {
            return Constants.getValueByKey(name);
        }
        throw new RuntimeException("Variable '" + name + "' doesn't exist.");
    }

    @Override
    public String toString() {
        return name;
    }
}
