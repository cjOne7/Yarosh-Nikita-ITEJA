package parser.statements;

/**
 * @see IStatement
 */
public final class ExitStatement implements IStatement {
    /**
     *
     */
    @Override
    public void execute() {
        System.exit(0);
    }
}
