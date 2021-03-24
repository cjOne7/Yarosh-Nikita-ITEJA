package parser.statements;

public class ExitStatement implements IStatement {
    @Override
    public void execute() {
        System.exit(0);
    }
}
