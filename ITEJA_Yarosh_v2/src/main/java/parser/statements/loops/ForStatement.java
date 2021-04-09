package parser.statements.loops;

import parser.expressions.IExpression;
import parser.lib.datatypes.DoubleValue;
import parser.lib.Variables;
import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface for for-do loop
 *
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
     * @param identifier             variable's name which will be used as a counter for for-do loop
     * @param initialValueExpression sets expression's evaluation result to variable before the loop starts
     * @param toExpression           defines the value for the loop to run. If this value is less than {@link #identifier},
     *                               the loop will start over again, if it is bigger, the loop will end.
     * @param body                   loop's body
     * @param isReverse              defines in which order loop will be executed
     * @param step                   defines the step by which {@link #identifier} variable will be increased
     */
    public ForStatement(final String identifier, final IExpression initialValueExpression, final IExpression toExpression
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
     * Execute for-do loop
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
     * @param i loop's counter
     * @return updated loop's counter after code {@link #body} was executed
     */
    private double executeBody(final double i) {
        Variables.put(identifier, new DoubleValue(i));
        body.execute();
        return Variables.getValueByKey(identifier).asDouble();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
