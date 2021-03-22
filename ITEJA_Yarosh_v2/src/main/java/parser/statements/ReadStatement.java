package parser.statements;

import parser.lib.Variables;

import java.util.Scanner;

public class ReadStatement implements IStatement{
    private final String identifier;

    public ReadStatement(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double value = scanner.nextDouble();
        Variables.put(identifier, value);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
