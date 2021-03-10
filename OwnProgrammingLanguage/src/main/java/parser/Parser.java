package parser;

import parser.ast.expressions.BinaryExpression;
import parser.ast.expressions.IExpression;
import parser.ast.expressions.NumberExpression;
import parser.ast.expressions.UnaryExpression;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Token EOF = new Token(TokenType.END_OF_FILE, "");

    private final List<Token> tokens;
    private int pos;
    private final int size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public List<IExpression> parse() {
        List<IExpression> result = new ArrayList<>();
        while (!match(TokenType.END_OF_FILE)) {
            result.add(expression());
        }
        return result;
    }


    //Recursive descending parser
    private IExpression expression() {
        return additive();
    }

    private IExpression additive() {
        IExpression result = multiplicative();
        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression multiplicative() {
        IExpression result = unary();
        while (true) {
            if (match(TokenType.MULTIPLY)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.DIVIDE)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return new UnaryExpression('+', primary());
        }
        return primary();
    }

    private IExpression primary() {
        Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }
        if (match(TokenType.OPEN_ROUND_BRACKET)) {
            IExpression expression = expression();
            match(TokenType.CLOSE_ROUND_BRACKET);
            return expression;
        }
        throw new RuntimeException("Unknown expression");
    }

    private boolean match(TokenType type) {
        Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= size) {
            return EOF;
        }
        return tokens.get(position);
    }
}
