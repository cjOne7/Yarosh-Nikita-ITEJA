package parser.statements.blocks;

import parser.statements.IStatement;

import java.util.List;

/**
 * Implementation of the <tt>IStatement</tt> interface for storing the block of the program as the statements' list
 *
 * @see IStatement
 */
public final class StatementBlock implements IStatement {
    private final List<IStatement> statementList;

    /**
     * @param statementList statement's list to be saved in {@link #statementList}
     */
    public StatementBlock(final List<IStatement> statementList) {
        this.statementList = statementList;
    }

    /**
     * Execute code's block
     */
    @Override
    public void execute() {
        for (IStatement statement : statementList) {
            statement.execute();
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (IStatement statement : statementList) {
            stringBuilder.append(statement.toString());
        }
        return stringBuilder.toString();
    }
}
