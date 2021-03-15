package parser;

import lexer.constants.Separators;
import parser.blocks.IBlock;
import parser.blocks.StatementBlock;
import parser.expressions.*;
import parser.lib.Variables;
import parser.statements.*;
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

    public IStatement parseBlock() {
        while (true) {
            if (getCurrentToken(0).getTokenType() == TokenType.VAR) {
                parseVariableBlock();
                continue;
            }
            if (getCurrentToken(0).getTokenType() == TokenType.CONST) {
                parseConstBlock();
                continue;
            }
            if (getCurrentToken(0).getTokenType() == TokenType.PROCEDURE) {
                parseProcedure();
                continue;
            }
            break;
        }
        IStatement programBody = parseStatementBlock();
        if (!match(TokenType.END_OF_FILE)) {
            throw new RuntimeException("Program is not ended by DOT.");
        }
        return programBody;
    }

    private void parseVariableBlock() {
        match(TokenType.VAR);
        while (!match(TokenType.SEMICOLON)) {
            String identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            Variables.put(identifier, 0);
            match(TokenType.IDENTIFIER);
            match(TokenType.COMMA);
        }
    }

    private void parseConstBlock() {
        match(TokenType.CONST);
        String identifier = getCurrentToken(0).getStringToken();
        match(TokenType.IDENTIFIER);
        match(TokenType.EQUAL);
        double value = Double.parseDouble(getCurrentToken(0).getStringToken());
        match(TokenType.NUMBER);
        Variables.put(identifier, value);
        while (!match(TokenType.SEMICOLON)) {
            match(TokenType.COMMA);
            identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            match(TokenType.IDENTIFIER);
            match(TokenType.EQUAL);
            value = Double.parseDouble(getCurrentToken(0).getStringToken());
            match(TokenType.NUMBER);
            Variables.put(identifier, value);
        }
    }

    private void parseProcedure() {
    }

    private IStatement parseStatementBlock() {
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

    private IStatement parseStatement() {//add CALL, WHILE and ! as print
        IStatement statement = null;
        Token current = getCurrentToken(0);
        if (current.getTokenType().equals(TokenType.IDENTIFIER)) {
            statement = parseAssignmentStatement();
        }
        else if (current.getTokenType().equals(TokenType.IF)) {
            statement = parseIfStatement();
        }
        else if (current.getTokenType().equals(TokenType.BEGIN)) {
            statement = new BlockStatement(parseStatementBlock());
            match(TokenType.SEMICOLON);
        }
        else if (current.getTokenType().equals(TokenType.WHILE)) {
            statement = parseWhileStatement();
        }
        else if (current.getTokenType().equals(TokenType.EXCLAMATION_MARK)) {
            statement = parsePrintBlock();
        }
        else if (current.getTokenType().equals(TokenType.CALL)) {

        }
        else {
            throw new RuntimeException("Missing one of next statements: " +
                    "IDENTIFIER, IF, WHILE, BEGIN, !, CALL, but current token is " + current.getTokenType());
        }
        return statement;
    }

    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    private IStatement parseAssignmentStatement() {
        String identifier = getCurrentToken(0).getStringToken();
        match(TokenType.IDENTIFIER);
        match(TokenType.ASSIGNMENT);
        IExpression expression = expression();
        match(TokenType.SEMICOLON);
        return new AssignmentStatement(identifier, expression);
    }

    private IStatement parseIfStatement() {
        match(TokenType.IF);
        IExpression expression = expression();
        match(TokenType.THEN);
        IStatement trueBlock = parseStatementOrBlock();
        match(TokenType.SEMICOLON);
        IStatement falseBlock = null;
        if (match(TokenType.ELSE)) {
            falseBlock = parseStatementOrBlock();
            match(TokenType.SEMICOLON);
        }

        return new IfStatement(expression, trueBlock, falseBlock);
    }

    private IStatement parseWhileStatement() {
        match(TokenType.WHILE);
        IExpression condition = expression();
        match(TokenType.DO);
        IStatement statement = parseStatementOrBlock();
        match(TokenType.SEMICOLON);
        return new WhileStatement(condition, statement);
    }

    private IStatement parsePrintBlock() {
        match(TokenType.EXCLAMATION_MARK);
        IStatement printStatement = new PrintStatement(expression());
        match(TokenType.SEMICOLON);
        return printStatement;
    }

    //Recursive descending parser
    private IExpression expression() {
        return condition();
    }

    private IExpression condition() {
        IExpression result = additive();
        while (true) {
            Token current = getCurrentToken(0);
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
            Token current = getCurrentToken(0);
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
            Token current = getCurrentToken(0);
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
        Token current = getCurrentToken(0);
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
        Token current = getCurrentToken(0);
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
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        return false;
    }

    private Token getCurrentToken(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= size) {
            throw new RuntimeException("Program must be finished with DOT in the end.");
        }
        return tokens.get(position);
    }
}
