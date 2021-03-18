package parser.procedure;

import parser.statements.IStatement;

public class Procedure {
    private final String identifier;
    private final IStatement procedureBody;

    public Procedure(String identifier, IStatement procedureBody) {
        this.identifier = identifier;
        this.procedureBody = procedureBody;
    }

    public void execute() {
        procedureBody.execute();
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "procedure " + identifier + ";\nbegin\n" + procedureBody + "end;";
    }
}
