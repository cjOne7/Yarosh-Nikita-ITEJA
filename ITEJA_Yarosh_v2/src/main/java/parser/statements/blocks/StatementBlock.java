package parser.statements.blocks;

import parser.statements.IStatement;

import java.util.List;

public class StatementBlock implements IStatement {
    List<IStatement> statementList;

    public StatementBlock(List<IStatement> statementList) {
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
        StringBuilder stringBuilder = new StringBuilder();
        for (IStatement statement : statementList) {
            stringBuilder.append(statement.toString());
        }
        return stringBuilder.toString();
    }
}
