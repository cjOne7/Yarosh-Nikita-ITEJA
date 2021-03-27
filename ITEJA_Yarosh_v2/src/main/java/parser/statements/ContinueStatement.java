package parser.statements;

public class ContinueStatement extends RuntimeException implements IStatement {
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "continue;";
    }
}
