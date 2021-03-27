package main;

import lexer.Lexer;
import parser.Parser;
import parser.statements.IStatement;
import token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class Main {
    public static void main(String[] args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(new File("Program 1.txt"));
//            Scanner scanner = new Scanner(new File("Program 2.txt"));//test values: a = 3, b = -14, c = -5, discriminant = 256, x1 = 5, x2 = -1/3
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.getTokens(stringBuilder.toString());
            for (Token token : tokens) {
                System.out.println(token);
            }
//            Parser parser = new Parser(tokens);
//            IStatement program = parser.parseBlock();
//            program.execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
