package parser.statements.loops;

import parser.statements.IStatement;

public class BreakStatement extends RuntimeException implements IStatement {

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "break;";
    }
}
