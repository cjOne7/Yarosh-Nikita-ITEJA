package parser.procedure;

import java.util.ArrayList;
import java.util.List;

public class Procedures {
    private final List<Procedure> procedureList = new ArrayList<>();

    public boolean contains(String name){
        for (Procedure procedure : procedureList){
            if (procedure.getIdentifier().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void add(Procedure procedure) {
        if (contains(procedure.getIdentifier())){
            throw new RuntimeException("Procedure '" + procedure.getIdentifier() + "' exists");
        }
        procedureList.add(procedure);
    }

    public Procedure get(String name) {
        for (Procedure proc : procedureList){
            if (proc.getIdentifier().equals(name)){
                return proc;
            }
        }
        throw new RuntimeException("Procedure " + name + " doesn't exists");
    }

}
