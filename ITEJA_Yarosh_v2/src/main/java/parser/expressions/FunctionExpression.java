package parser.expressions;

import parser.lib.Functions;
import parser.lib.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionExpression implements IExpression {
    private final String function;
    private final List<IExpression> args;

    public FunctionExpression(String function) {
        this.function = function;
        this.args = new ArrayList<>();
    }

    public FunctionExpression(String function, List<IExpression> args) {
        this.function = function;
        this.args = args;
    }

    public void addArgument(IExpression argument) {
        args.add(argument);
    }

    @Override
    public IValue eval() {
        IValue[] values = new IValue[args.size()];
        for (int i = 0; i < args.size(); i++) {
            values[i] = args.get(i).eval();
        }
        return Functions.getFunctionByName(function).execute(values);
    }

    @Override
    public String toString() {
        return function + "(" + args.stream().map(expression -> expression.eval().asString()).collect(Collectors.joining(",")) + ")";
    }
}
