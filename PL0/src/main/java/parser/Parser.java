package parser;

import lexer.constants.Separators;
import parser.blocks.IBlock;
import parser.blocks.StatementBlock;
import parser.expressions.*;
import parser.lib.Variables;
import parser.statements.IStatement;
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

    public IBlock parseBlock() {
        while (true) {
            if (get(0).getType() == TokenType.VAR) {
                parseVariableBlock();
                continue;
            }
            if (get(0).getType() == TokenType.CONST) {
                parseConstBlock();
                continue;
            }
            if (get(0).getType() == TokenType.PROCEDURE) {
                parseProcedure();
                continue;
            }
            break;
        }
        IBlock programBody = parseStatementBlock();
        if (!match(TokenType.END_OF_FILE)) {
            throw new RuntimeException("Program is not ended by DOT.");
        }
        return programBody;
    }

    private void parseVariableBlock() {
        match(TokenType.VAR);
        while (!match(TokenType.SEMICOLON)) {
            String identifier = get(0).getStringToken();
            Variables.put(identifier, 0);
            match(TokenType.IDENTIFIER);
            match(TokenType.COMMA);
        }
    }

    private void parseConstBlock() {
        match(TokenType.CONST);
        String identifier = get(0).getStringToken();
        match(TokenType.IDENTIFIER);
        match(TokenType.EQUAL);
        double value = Double.parseDouble(get(0).getStringToken());
        match(TokenType.NUMBER);
        Variables.put(identifier, value);
        while (!match(TokenType.SEMICOLON)) {
            match(TokenType.COMMA);
            identifier = get(0).getStringToken();
            match(TokenType.IDENTIFIER);
            match(TokenType.EQUAL);
            value = Double.parseDouble(get(0).getStringToken());
            match(TokenType.NUMBER);
            Variables.put(identifier, value);
        }
    }

    private void parseProcedure() {
    }

    private IBlock parseStatementBlock() {
        List<IStatement> statements = new ArrayList<>();
        match(TokenType.BEGIN);
        while (!match(TokenType.END)) {
            IStatement statement = parseStatement();
            if (statement != null) {
                statements.add(statement);
            }
        }
        return new StatementBlock(statements);
    }

    private IStatement parseStatement() {
        IStatement statement = null;
        if (match(TokenType.IF)) {
            statement = parseIfStatement();
        }
        return statement;
    }

    private IStatement parseIfStatement() {
        IExpression expression = expression();
        match(TokenType.THEN);

        return null;
    }


//    public List<IStatement> parse1() {
//        List<IStatement> result = new ArrayList<>();
//        while (!match(TokenType.END_OF_FILE)) {
//            result.add(statement());
//        }
//        return result;
//    }
//
//    private IStatement statement() {
//        return assignmentStatement();
//    }
//
//    private IStatement assignmentStatement() {
//        Token current = get(0);
//        if (current.getType() == TokenType.IDENTIFIER && get(1).getType() == TokenType.ASSIGNMENT) {
//            match(TokenType.IDENTIFIER);
//            String variable = current.getStringToken();
//            match(TokenType.ASSIGNMENT);
//            return new AssignmentStatement(variable, expression());
//        }
//        throw new RuntimeException("Unknown operator");
//    }

//    public List<IStatement> parse1() {
//        List<IStatement> result = new ArrayList<>();
//        while (!match(TokenType.END_OF_FILE)) {
//            result.add(parseStatement());
//        }
//        return result;
//    }

    //Recursive descending parser
    public List<IExpression> parse() {
        List<IExpression> result = new ArrayList<>();
        while (!match(TokenType.END_OF_FILE)) {
            result.add(expression());
        }
        return result;
    }

    private IExpression expression() {
        return condition();
    }

    private IExpression condition() {
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
        if (match(TokenType.IDENTIFIER)) {
            return new VariableExpression(current.getStringToken());
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
        if (type == current.getType()) {
            pos++;
            return true;
        }
        return false;
    }

    private Token get(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= size) {
            return EOF;
        }
        return tokens.get(position);
    }
}
