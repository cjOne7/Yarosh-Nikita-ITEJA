package parser.lib;

import parser.lib.IValue;

@FunctionalInterface
public interface IFunction {

    IValue execute(IValue... args);
}
