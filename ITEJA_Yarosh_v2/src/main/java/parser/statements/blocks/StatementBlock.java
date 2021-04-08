package parser.statements.blocks;

import parser.statements.IStatement;

import java.util.List;

public final class StatementBlock implements IStatement {
    private final List<IStatement> statementList;

    public StatementBlock(final List<IStatement> statementList) {
        this.statementList = statementList;
    }

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
