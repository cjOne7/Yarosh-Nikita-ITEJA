package main;

import lexer.Lexer1;
import lib.Variables;
import parser.Parser;
import parser.ast.expression.IExpression;
import parser.ast.statement.IStatement;
import token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(new File("Program.txt"));
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            stringBuilder.deleteCharAt(0);//delete \uFEFF
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            Lexer1 lexer1 = new Lexer1();
            List<Token> tokens = lexer1.getTokens(stringBuilder.toString());
//            for (Token token : tokens) {
//                System.out.println(token);
//            }

            List<IStatement> statements = new Parser(tokens).parse();
            for (IStatement statement : statements) {
                System.out.println(statement);
            }
            for (IStatement statement : statements) {
               statement.execute();
            }

            System.out.printf("%s = %f\n", "word", Variables.get("word"));
            System.out.printf("%s = %f\n", "word2", Variables.get("word2"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
