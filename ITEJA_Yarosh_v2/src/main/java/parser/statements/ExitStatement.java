package parser.statements;

/**
 * Implementation of the <tt>IStatement</tt> interface for exit from the system
 *
 * @see IStatement
 */
public final class ExitStatement implements IStatement {

    /**
     * Exit from the system with return code 0
     */
    @Override
    public void execute() {
        System.exit(0);
    }
}
