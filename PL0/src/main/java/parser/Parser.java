package parser;

import lexer.constants.Separators;
import parser.blocks.StatementBlock;
import parser.expressions.*;
import parser.lib.Identifiers;
import parser.lib.Variables;
import parser.procedure.Procedure;
import parser.procedure.Procedures;
import parser.statements.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
    private static final Token EOF = new Token(TokenType.END_OF_FILE, Character.toString(Separators.DOT));

    private final List<Token> tokens;
    private final Procedures procedures;
    private int pos;
    private final int size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
        procedures = new Procedures();
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
        consumeToken(TokenType.END_OF_FILE);
        return programBody;
    }

    private void parseVariableBlock() {
        consumeToken(TokenType.VAR);
        String identifier = getCurrentToken(0).getStringToken();
        if (Variables.isKeyExists(identifier)) {
            throw new RuntimeException("Variable '" + identifier + "' is already defined.");
        }
        Variables.put(identifier, 0);
        consumeToken(TokenType.IDENTIFIER);
        while (isMatchTokenType(TokenType.COMMA)) {
            identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            Variables.put(identifier, 0);
            consumeToken(TokenType.IDENTIFIER);
        }
        consumeToken(TokenType.SEMICOLON);
    }

    private void parseConstBlock() {
        consumeToken(TokenType.CONST);
        String identifier = getCurrentToken(0).getStringToken();
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.EQUAL);
        double value = Double.parseDouble(getCurrentToken(0).getStringToken());
        consumeToken(TokenType.NUMBER);
        Variables.put(identifier, value);
        while (!isMatchTokenType(TokenType.SEMICOLON)) {
            consumeToken(TokenType.COMMA);
            identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.EQUAL);
            value = Double.parseDouble(getCurrentToken(0).getStringToken());
            consumeToken(TokenType.NUMBER);
            Variables.put(identifier, value);
        }
    }

    private void parseProcedure() {
        consumeToken(TokenType.PROCEDURE);
        String identifier = getCurrentToken(0).getStringToken();
        Identifiers.put(identifier);
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.SEMICOLON);
        if (getCurrentToken(0).getTokenType() == TokenType.VAR) {
            parseVariableBlock();
        }
        IStatement procedureBlockStatements = parseStatementBlock();
        procedures.add(new Procedure(identifier, procedureBlockStatements));
        consumeToken(TokenType.SEMICOLON);
    }

    private IStatement parseStatementBlock() {
        List<IStatement> statements = new ArrayList<>();
        isMatchTokenType(TokenType.BEGIN);
        while (!isMatchTokenType(TokenType.END)) {
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
            isMatchTokenType(TokenType.SEMICOLON);
        }
        else if (current.getTokenType().equals(TokenType.WHILE)) {
            statement = parseWhileStatement();
        }
        else if (current.getTokenType().equals(TokenType.EXCLAMATION_MARK)) {
            statement = parsePrintBlock();
        }
        else if (current.getTokenType().equals(TokenType.CALL)) {
            statement = parseProcedureStatement();
        }
        else {
            throw new RuntimeException("Missing one of next statements: " +
                    "IDENTIFIER, IF, WHILE, BEGIN, !, CALL, but current token is " + current.getTokenType());
        }
        return statement;
    }

    private IStatement parseProcedureStatement() {
        consumeToken(TokenType.CALL);
        String identifier = getCurrentToken(0).getStringToken();
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.SEMICOLON);
        return new ProcedureStatement(procedures.get(identifier));
    }

    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    private IStatement parseAssignmentStatement() {
        String identifier = getCurrentToken(0).getStringToken();
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.ASSIGNMENT);
        IExpression expression = expression();
        consumeToken(TokenType.SEMICOLON);
        return new AssignmentStatement(identifier, expression);
    }

    private IStatement parseIfStatement() {
        consumeToken(TokenType.IF);
        IExpression expression = expression();
        consumeToken(TokenType.THEN);
        IStatement trueBlock = parseStatementOrBlock();
        consumeToken(TokenType.SEMICOLON);
        IStatement falseBlock = null;
        if (isMatchTokenType(TokenType.ELSE)) {
            falseBlock = parseStatementOrBlock();
            isMatchTokenType(TokenType.SEMICOLON);
        }

        return new IfStatement(expression, trueBlock, falseBlock);
    }

    private IStatement parseWhileStatement() {
        isMatchTokenType(TokenType.WHILE);
        IExpression condition = expression();
        isMatchTokenType(TokenType.DO);
        IStatement statement = parseStatementOrBlock();
        isMatchTokenType(TokenType.SEMICOLON);
        return new WhileStatement(condition, statement);
    }

    private IStatement parsePrintBlock() {
        isMatchTokenType(TokenType.EXCLAMATION_MARK);
        IStatement printStatement = new PrintStatement(expression());
        isMatchTokenType(TokenType.SEMICOLON);
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
            if (isMatchTokenType(TokenType.EQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (isMatchTokenType(TokenType.NOTEQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (isMatchTokenType(TokenType.LESS)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (isMatchTokenType(TokenType.LESS_OR_EQUAL)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (isMatchTokenType(TokenType.GREATER)) {
                result = new ConditionalExpression(current.getStringToken(), result, additive());
                continue;
            }
            if (isMatchTokenType(TokenType.GREATER_OR_EQUAL)) {
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
            if (isMatchTokenType(TokenType.PLUS)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, multiplicative());
                continue;
            }
            if (isMatchTokenType(TokenType.MINUS)) {
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
            if (isMatchTokenType(TokenType.MULTIPLY)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, unary());
                continue;
            }
            if (isMatchTokenType(TokenType.DIVIDE)) {
                result = new BinaryExpression(current.getStringToken().charAt(0), result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression unary() {
        Token current = getCurrentToken(0);
        if (isMatchTokenType(TokenType.MINUS)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        if (isMatchTokenType(TokenType.PLUS)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        if (isMatchTokenType(TokenType.ODD)) {
            return new UnaryExpression(current.getStringToken(), primary());
        }
        return primary();
    }

    private IExpression primary() {
        Token current = getCurrentToken(0);
        if (isMatchTokenType(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getStringToken()));
        }
        if (isMatchTokenType(TokenType.IDENTIFIER)) {
            return new VariableExpression(current.getStringToken());
        }
        if (isMatchTokenType(TokenType.OPEN_ROUND_BRACKET)) {
            IExpression expression = expression();
            isMatchTokenType(TokenType.CLOSE_ROUND_BRACKET);
            return expression;
        }
        throw new RuntimeException("Unknown expression");
    }

    private boolean isMatchTokenType(TokenType type) {
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        return false;
    }

    private boolean consumeToken(TokenType type) {
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        throw new RuntimeException("Expected " + type + ", but was found " + current.getTokenType());
    }

    private Token getCurrentToken(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= size) {
            throw new RuntimeException("Program must be finished with DOT in the end.");
        }
        return tokens.get(position);
    }
}
