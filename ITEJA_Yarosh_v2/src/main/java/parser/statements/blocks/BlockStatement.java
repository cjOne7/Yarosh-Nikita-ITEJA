package parser.statements.blocks;

import parser.statements.IStatement;

/**
 * Implementation of the <tt>IStatement</tt> interface for saving parsed begin-end block of the program as one statement
 *
 * @see IStatement
 */
public final class BlockStatement implements IStatement {
    private final IStatement block;

    /**
     * @param block one or more statements wrapped in begin-end
     */
    public BlockStatement(final IStatement block) {
        this.block = block;
    }

    /**
     * Execute body of begin-end statement block
     */
    @Override
    public void execute() {
        block.execute();
    }

    @Override
    public String toString() {
        return block.toString();
    }
}
