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
            Scanner scanner = new Scanner(new File("Program.txt"));
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            stringBuilder.deleteCharAt(0);//delete \uFEFF
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.getTokens(stringBuilder.toString());
//            for (Token token : tokens) {
//                System.out.println(token);
//            }

            Parser parser = new Parser(tokens);
            IStatement program = parser.parseBlock();
            program.execute();
//            System.out.println(program);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
