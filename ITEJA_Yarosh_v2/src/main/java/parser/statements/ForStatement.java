package parser.statements;

import parser.expressions.IExpression;
import parser.lib.NumberValue;
import parser.lib.Variables;

public class ForStatement implements IStatement {
    private final String identifier;
    private final IExpression initialValueExpression;
    private final IExpression toExpression;
    private final IStatement body;
    private boolean isReverse;

    public ForStatement(String identifier, IExpression initialValueExpression, IExpression toExpression, IStatement body) {
        this.identifier = identifier;
        this.initialValueExpression = initialValueExpression;
        this.toExpression = toExpression;
        this.body = body;
    }

    @Override
    public void execute() {
        double initialValue = initialValueExpression.eval().asDouble();
        Variables.put(identifier, new NumberValue(initialValue));
        double toValue = toExpression.eval().asDouble();
        for (double d = initialValue; d <= toValue; d++) {
            try {
                Variables.put(identifier, new NumberValue(d));
                body.execute();
                d = Variables.getValueByKey(identifier).asDouble();
            } catch (BreakStatement e) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
