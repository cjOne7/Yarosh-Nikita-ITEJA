package parser.statements;

public class EndOfFileStatement implements IStatement {

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return ".";
    }
}
