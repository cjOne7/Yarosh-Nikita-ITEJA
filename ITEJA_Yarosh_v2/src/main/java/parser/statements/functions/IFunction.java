package parser.statements.functions;

import parser.lib.IValue;

@FunctionalInterface
public interface IFunction {

    IValue execute(IValue... args);
}
