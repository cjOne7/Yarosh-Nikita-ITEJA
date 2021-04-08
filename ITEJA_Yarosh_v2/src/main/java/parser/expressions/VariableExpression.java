package parser.expressions;

import parser.lib.Constants;
import parser.lib.datatypes.IValue;
import parser.lib.Variables;

/**
 * Implementation of the <tt>IExpression</tt> interface for expressions with variables
 *
 * @see IExpression
 */
public final class VariableExpression implements IExpression {
    private final String name;

    /**
     * @param name variable identifier
     */
    public VariableExpression(final String name) {
        this.name = name;
    }

    /**
     * @return value {@link IValue} from {@link Constants} or {@link Variables} depending on expression evaluation
     * result datatype
     */
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
