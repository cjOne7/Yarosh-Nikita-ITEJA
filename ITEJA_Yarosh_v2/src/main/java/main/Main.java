package main;

import lexer.Lexer;
import parser.Parser;
import parser.statements.IStatement;
import token.Token;
import token.TokenType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main {
    public static void main(String[] args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(new File("Example 1.txt"));
//            Scanner scanner = new Scanner(new File("Example 2.txt"));
//            Scanner scanner = new Scanner(new File("Example 3.txt"));
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
//            System.out.println(program);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
