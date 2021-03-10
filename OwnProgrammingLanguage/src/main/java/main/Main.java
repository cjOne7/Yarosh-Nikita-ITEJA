package main;

import lexer.Lexer;
import parser.Parser;
import parser.ast.IExpression;
import token.Token;

import java.util.List;

public final class Main {
    public static void main(String[] args) {
        String input = "(-2+2)*-2";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }

        List<IExpression> expressions = new Parser(tokens).parse();
        for (IExpression expression : expressions) {
            System.out.println(expression + " =  " + expression.eval());
        }
    }

}
