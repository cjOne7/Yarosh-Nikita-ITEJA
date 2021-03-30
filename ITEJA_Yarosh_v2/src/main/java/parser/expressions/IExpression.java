package parser.expressions;

import parser.lib.IValue;

@FunctionalInterface
public interface IExpression {
    IValue eval();
}
