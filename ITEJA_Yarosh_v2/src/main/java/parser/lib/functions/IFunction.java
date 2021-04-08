package parser.lib.functions;

import parser.lib.datatypes.IValue;

@FunctionalInterface
public interface IFunction {

    IValue execute(IValue... args);
}
