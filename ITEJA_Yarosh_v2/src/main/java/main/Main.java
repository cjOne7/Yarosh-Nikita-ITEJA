package main;

import lexer.Lexer;
import parser.Parser;
import parser.statements.IStatement;
import token.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class Main {
    public static void main(String[] args) {
        String[] paths = {"Example 1.txt", "Example 2.txt", "Example 3.txt"};
        try {
            String input = new String(Files.readAllBytes(Paths.get(paths[2])), StandardCharsets.UTF_8);
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.getTokens(input);
//            for (Token token : tokens) {
//                System.out.println(token);
//            }
            Parser parser = new Parser(tokens);
            IStatement program = parser.parseBlock();
            program.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
