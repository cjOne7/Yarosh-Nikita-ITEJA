package parser.statements;

public class BreakStatement extends RuntimeException implements IStatement{

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "break;";
    }
}
