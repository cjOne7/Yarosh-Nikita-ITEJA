package parser.expressions;

import static lexer.constants.CompareOperators.*;

public class ConditionalExpression implements IExpression {
    private final IExpression expression1, expression2;
    private final String operation;

    public ConditionalExpression(String operation, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation) {
            case NOTEQUAL + "":
                return expression1.eval() != expression2.eval() ? 1 : 0;
            case LESS + "":
                return expression1.eval() < expression2.eval() ? 1 : 0;
            case LESS + EQUALITY + "":
                return expression1.eval() <= expression2.eval() ? 1 : 0;
            case GREATER + "":
                return expression1.eval() > expression2.eval() ? 1 : 0;
            case GREATER + EQUALITY + "":
                return expression1.eval() >= expression2.eval() ? 1 : 0;
            case EQUALITY + "":
            default:
                return expression1.eval() == expression2.eval() ? 1 : 0;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", expression1, operation, expression2);
    }


}
