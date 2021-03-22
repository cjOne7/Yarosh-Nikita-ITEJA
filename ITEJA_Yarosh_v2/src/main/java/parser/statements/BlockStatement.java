package parser.statements;


public class BlockStatement implements IStatement {
    private final IStatement block;

    public BlockStatement(IStatement block) {
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
