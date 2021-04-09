package parser.statements.loops;

import parser.expressions.IExpression;
import parser.lib.datatypes.DoubleValue;
import parser.lib.Variables;
import parser.statements.IStatement;

/**
 * @see IStatement
 */
public final class ForStatement implements IStatement {
    private final String identifier;
    private final IExpression initialValueExpression;
    private final IExpression toExpression;
    private final IStatement body;
    private final boolean isReverse;
    private double step = 1;

    /**
     *
     * @param identifier
     * @param initialValueExpression
     * @param toExpression
     * @param body
     * @param isReverse
     * @param step
     */
    public ForStatement(String identifier, IExpression initialValueExpression, IExpression toExpression
            , IStatement body, boolean isReverse, IExpression step) {
        if (step != null) {
            double stepDouble = step.eval().asDouble();
            if (stepDouble == 0) {
                throw new RuntimeException("Step in for loop can't be equal 0.");
            }
            this.step = stepDouble;
        }
        this.identifier = identifier;
        this.initialValueExpression = initialValueExpression;
        this.toExpression = toExpression;
        this.body = body;
        this.isReverse = isReverse;
    }

    /**
     *
     */
    @Override
    public void execute() {
        final double initialValue = initialValueExpression.eval().asDouble();
        Variables.put(identifier, new DoubleValue(initialValue));
        final double toValue = toExpression.eval().asDouble();
        double i = initialValue;
        if (isReverse) {
            for (; i >= toValue; i -= step) {
                try {
                    i = executeBody(i);
                } catch (BreakStatement e) {
                    break;
                }
            }
        }
        else {
            for (; i <= toValue; i += step) {
                try {
                    i = executeBody(i);
                } catch (BreakStatement e) {
                    break;
                }
            }
        }
    }

    /**
     *
     * @param i
     * @return
     */
    private double executeBody(double i) {
        Variables.put(identifier, new DoubleValue(i));
        body.execute();
        return Variables.getValueByKey(identifier).asDouble();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
