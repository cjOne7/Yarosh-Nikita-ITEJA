package parser.statements;

import parser.lib.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;
import parser.lib.Variables;

import java.util.Scanner;

public class ReadStatement implements IStatement {
    private final String identifier;

    public ReadStatement(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void execute() {
        IValue value = Variables.getValueByKey(identifier);
        Scanner scanner = new Scanner(System.in);
        String newValue = scanner.nextLine();
        try {
            if (value instanceof StringValue) {
                Variables.put(identifier, new StringValue(newValue));
            }
            else {
                Variables.put(identifier, new DoubleValue(Double.parseDouble(newValue)));
            }
        } catch (NumberFormatException e) {
            System.err.println("Datatype " + value.getClass().getSimpleName() + " doesn't match with '" + newValue + "' value.");
        }
    }

    @Override
    public String toString() {
        return String.format("readln(%s)", identifier);
    }
}
