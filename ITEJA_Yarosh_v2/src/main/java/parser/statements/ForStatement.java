package parser.statements;

import parser.expressions.IExpression;
import parser.lib.NumberValue;
import parser.lib.Variables;

public class ForStatement implements IStatement {
    private final String identifier;
    private final IExpression initialValueExpression;
    private final IExpression toExpression;
    private final IStatement body;
    private final boolean isReverse;


    public ForStatement(String identifier, IExpression initialValueExpression, IExpression toExpression, IStatement body, boolean isReverse) {
        this.identifier = identifier;
        this.initialValueExpression = initialValueExpression;
        this.toExpression = toExpression;
        this.body = body;
        this.isReverse = isReverse;
    }

    @Override
    public void execute() {
        double initialValue = initialValueExpression.eval().asDouble();
        Variables.put(identifier, new NumberValue(initialValue));
        double toValue = toExpression.eval().asDouble();
        if (isReverse) {
            for (double d = initialValue; d >= toValue; d--) {
                try {
                    d = executeBody(d);
                } catch (BreakStatement e) {
                    break;
                }
            }
        }
        else {
            for (double d = initialValue; d <= toValue; d++) {
                try {
                    d = executeBody(d);
                } catch (BreakStatement e) {
                    break;
                }
            }
        }
    }

    private double executeBody(double d) {
        Variables.put(identifier, new NumberValue(d));
        body.execute();
        d = Variables.getValueByKey(identifier).asDouble();
        return d;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
