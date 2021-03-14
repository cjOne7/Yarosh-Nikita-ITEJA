package parser;

import lexer.constants.Separators;
import parser.expressions.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
    private static final Token EOF = new Token(TokenType.END_OF_FILE, Character.toString(Separators.DOT));

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
        return conditional();
    }

    private IExpression conditional() {
        IExpression result = additive();
        while (true) {
            Token current = get(0);
            if (match(TokenType.EQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (match(TokenType.NOTEQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (match(TokenType.LESS)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (match(TokenType.LESS_OR_EQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (match(TokenType.GREATER)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (match(TokenType.GREATER_OR_EQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression additive() {
        IExpression result = multiplicative();
        while (true) {
            Token current = get(0);
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression multiplicative() {
        IExpression result = unary();
        while (true) {
            Token current = get(0);
            if (match(TokenType.MULTIPLY)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, unary());
                continue;
            }
            if (match(TokenType.DIVIDE)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression unary() {
        Token current = get(0);
        if (match(TokenType.MINUS)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        if (match(TokenType.PLUS)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        if (match(TokenType.ODD)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        return primary();
    }

    private IExpression primary() {
        Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getStringToken()));
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
