package parser.expressions;

import parser.lib.datatypes.IValue;

/**
 * Interface with only one method <tt>eval</tt> provides generalization and implementation for all expressions
 * in programming language
 */
@FunctionalInterface
public interface IExpression {
    /**
     *
     * @return result of code evaluation
     */
    IValue eval();
}
