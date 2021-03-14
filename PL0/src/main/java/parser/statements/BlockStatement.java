package parser.statements;


import parser.blocks.IBlock;

public class BlockStatement implements IStatement {
    private final IBlock block;

    public BlockStatement(IBlock block) {
        this.block = block;
    }

    @Override
    public void execute() {
        block.execute();
    }

    @Override
    public String toString() {
        return block.toString();
    }
}
