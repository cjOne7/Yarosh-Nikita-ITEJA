package parser.statements;

public final class ExitStatement implements IStatement {
    @Override
    public void execute() {
        System.exit(0);
    }
}
