package parser.statements.loops;

import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface for break statement
 *
 * @see IStatement
 */
public final class BreakStatement extends RuntimeException implements IStatement {

    /**
     * @throws BreakStatement as exception
     */
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "break;";
    }
}
