package parser.expressions;

import parser.lib.datatypes.IValue;

@FunctionalInterface
public interface IExpression {
    IValue eval();
}
