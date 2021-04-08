package parser.statements;

import parser.lib.datatypes.IValue;
import parser.lib.datatypes.DoubleValue;
import parser.lib.datatypes.StringValue;
import parser.lib.Variables;

import java.util.Scanner;

public final class ReadStatement implements IStatement {
    private final String identifier;

    public ReadStatement(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void execute() {
        final IValue value = Variables.getValueByKey(identifier);
        final Scanner scanner = new Scanner(System.in);
        final String newValue = scanner.nextLine();
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
