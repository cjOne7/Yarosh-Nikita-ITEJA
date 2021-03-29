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
    private Map<String, IValue> variables;

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
            Set<String> keys = new HashSet<>(variables.keySet());
            keys.forEach(key -> LocalVars.put(key, variables.get(key)));
            procedureBody.execute();
            LocalVars.clear(keys);
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
