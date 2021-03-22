package parser.statements;

import parser.procedure.Procedure;

public class ProcedureStatement implements IStatement {
    private final Procedure procedure;

    public ProcedureStatement(Procedure procedure) {
        this.procedure = procedure;
    }

    @Override
    public void execute() {
        procedure.execute();
    }

    @Override
    public String toString() {
        return procedure.toString();
    }
}
