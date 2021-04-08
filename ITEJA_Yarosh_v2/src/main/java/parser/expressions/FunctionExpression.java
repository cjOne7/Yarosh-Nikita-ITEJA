package parser.expressions;

import parser.lib.functions.Functions;
import parser.lib.datatypes.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionExpression implements IExpression {
    private final String functionName;
    private final List<IExpression> args;
    private boolean isExpression;

    public FunctionExpression(String functionName) {
        this.functionName = functionName;
        this.args = new ArrayList<>();
    }

    public FunctionExpression(String functionName, boolean isExpression) {
        this.functionName = functionName;
        this.isExpression = isExpression;
        this.args = new ArrayList<>();
    }

    public FunctionExpression(String functionName, List<IExpression> args) {
        this.functionName = functionName;
        this.args = args;
    }

    public void addArgument(IExpression argument) {
        args.add(argument);
    }

    public void addArguments(List<IExpression> args) {
        this.args.addAll(args);
    }

    @Override
    public IValue eval() {
        IValue[] values = new IValue[args.size()];
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
