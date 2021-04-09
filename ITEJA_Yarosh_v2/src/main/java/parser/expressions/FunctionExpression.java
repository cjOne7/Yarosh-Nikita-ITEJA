package parser.expressions;

import parser.lib.functions.Functions;
import parser.lib.datatypes.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the <tt>IExpression</tt> interface for function expressions
 *
 * @see IExpression
 */
public final class FunctionExpression implements IExpression {
    private final String functionName;
    private final List<IExpression> args;
    private final boolean isExpression;

    /**
     * @param functionName function identifier
     * @param isExpression responsible for separating functions with void return type from others functions
     */
    public FunctionExpression(final String functionName, final boolean isExpression) {
        this.functionName = functionName;
        this.isExpression = isExpression;
        this.args = new ArrayList<>();
    }

    /**
     * @param functionName function identifier
     * @param isExpression responsible for marking functions with void return type
     * @param args         add list of arguments to function header {@link #args}
     */
    public FunctionExpression(final String functionName, final boolean isExpression, final List<IExpression> args) {
        this.functionName = functionName;
        this.isExpression = isExpression;
        this.args = args;
    }

    /**
     * @param argument add one argument to function header {@link #args}
     */
    public void addArgument(final IExpression argument) {
        args.add(argument);
    }

    /**
     * @param args add list of arguments to function header {@link #args}
     */
    public void addArguments(final List<IExpression> args) {
        this.args.addAll(args);
    }

    /**
     * @return function execution result {@link IValue} depending on function return datatype
     * @throws RuntimeException when user try to call void function as expression
     */
    @Override
    public IValue eval() {
        final IValue[] values = new IValue[args.size()];
        for (int i = 0; i < args.size(); i++) {
            values[i] = args.get(i).eval();
        }

        if (Functions.functionHasReturnType(functionName) || (!Functions.functionHasReturnType(functionName) && !isExpression)) {
            return Functions.getFunctionByName(functionName).execute(values);
        }
        throw new RuntimeException("Procedure '" + functionName + "' can't call as expression.");
    }

    @Override
    public String toString() {
        return functionName + "(" + args.stream().map(expression -> expression.eval().asString()).collect(Collectors.joining(",")) + ")";
    }
}
