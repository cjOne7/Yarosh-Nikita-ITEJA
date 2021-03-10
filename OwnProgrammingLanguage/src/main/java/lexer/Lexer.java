package lexer;

import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Lexer {
    private static final TokenType[] OPERATOR_TOKENS =
            {TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.OPEN_ROUND_BRACKET, TokenType.CLOSE_ROUND_BRACKET};
    private static final String OPERATOR_CHARS = "+-*/()";

    private final String input;
    private final int length;
    private final List<Token> tokens = new ArrayList<>();
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    public List<Token> tokenize() {
        while (pos < length) {
            char current = peek(0);
            if (Character.isDigit(current)) {
                tokenizeNumber();
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                next();
            }
        }
        return tokens;
    }

    private void tokenizeOperator() {
        int position = OPERATOR_CHARS.indexOf(peek(0));
        addToken(OPERATOR_TOKENS[position], OPERATOR_CHARS.charAt(position) + "");
        next();
    }

    private void tokenizeNumber() {
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (Character.isDigit(current)) {
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, buffer.toString());
    }

    private char next() {
        pos++;
        return peek(0);
    }

    private char peek(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= length) {
            return '\0';
        }
        return input.charAt(position);
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type, ""));
    }

    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }


}
