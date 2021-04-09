package parser.expressions;

import parser.lib.datatypes.IValue;

/**
 * Interface with only one method <tt>eval</tt> provides generalization and implementation for all expressions
 * in programming language
 *
 * @see BinaryExpression
 * @see ConditionalExpression
 * @see FunctionExpression
 * @see UnaryExpression
 * @see ValueExpression
 * @see VariableExpression
 */
@FunctionalInterface
public interface IExpression {

    /**
     * Evaluate expression
     * @return result of code evaluation
     */
    IValue eval();
}
