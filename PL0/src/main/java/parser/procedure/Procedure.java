package parser.procedure;

import parser.lib.IValue;
import parser.lib.LocalVars;
import parser.statements.IStatement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Procedure {
    private final String identifier;
    private final IStatement procedureBody;
    private final Map<String, IValue> variables;

    public Procedure(String identifier, IStatement procedureBody, Map<String, IValue> variables) {
        this.identifier = identifier;
        this.procedureBody = procedureBody;
        this.variables = variables;
    }

    public void execute() {
        if (variables == null) {
            procedureBody.execute();
        }
        else {
            LocalVars.push(variables);
            procedureBody.execute();
            LocalVars.pop(variables.keySet());
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "procedure " + identifier + ";\nbegin\n" + procedureBody + "end;";
    }
}
