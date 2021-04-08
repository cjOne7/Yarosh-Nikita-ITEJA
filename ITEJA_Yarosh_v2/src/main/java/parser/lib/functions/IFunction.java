package parser.lib.functions;

import parser.lib.datatypes.IValue;

/**
 * Interface with only one method <tt>execute</tt> provides execution of build-in functions
 *
 * @see Functions
 */
@FunctionalInterface
public interface IFunction {
    /**
     * @param args arguments' list of function header
     * @return function execution result {@link IValue} depending on function return datatype
     */
    IValue execute(IValue... args);
}
