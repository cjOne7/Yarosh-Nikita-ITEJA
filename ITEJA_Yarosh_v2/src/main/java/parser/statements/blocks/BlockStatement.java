package parser.statements.blocks;

import parser.statements.IStatement;

/**
 * @see IStatement
 */
public final class BlockStatement implements IStatement {
    private final IStatement block;

    /**
     *
     * @param block
     */
    public BlockStatement(final IStatement block) {
        this.block = block;
    }

    /**
     *
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
