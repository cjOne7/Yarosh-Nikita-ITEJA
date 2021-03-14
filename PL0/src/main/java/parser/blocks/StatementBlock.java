package parser.blocks;

import parser.statements.IStatement;

import java.util.List;

public class StatementBlock implements IBlock {
    List<IStatement> statementList;

    public StatementBlock(List<IStatement> statementList) {
        this.statementList = statementList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IStatement statement : statementList) {
            stringBuilder.append(statement.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void execute() {
        for (IStatement statement : statementList) {
            statement.execute();
        }
    }
}
