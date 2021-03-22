package parser;

import parser.blocks.StatementBlock;
import parser.expressions.*;
import parser.lib.Constants;
import parser.lib.Identifiers;
import parser.lib.NumberValue;
import parser.lib.Variables;
import parser.procedure.Procedure;
import parser.procedure.Procedures;
import parser.statements.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
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
        consumeToken(TokenType.PROGRAM);
        String programIdentifier = getCurrentToken(0).getStringToken();
        System.out.println("Starting parse '" + programIdentifier + "' program.\n.\n..\n...");
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.SEMICOLON);
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
        System.out.println("Successfully finished parse '" + programIdentifier + "'.");
        return programBody;
    }

    private void parseVariableBlock() {
        consumeToken(TokenType.VAR);
        do {
            String identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            Variables.put(identifier, Variables.ZERO);
            consumeToken(TokenType.IDENTIFIER);
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.SEMICOLON);
    }

    private void parseConstBlock() {
        consumeToken(TokenType.CONST);
        do {
            String identifier = getCurrentToken(0).getStringToken();
            if (Constants.isKeyExists(identifier)) {
                throw new RuntimeException("Constant '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.EQUAL);
            double value = Double.parseDouble(getCurrentToken(0).getStringToken());
            consumeToken(TokenType.NUMBER);
            Constants.put(identifier, new NumberValue(value));
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.SEMICOLON);
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

    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    private IStatement parseStatementBlock() {
        List<IStatement> statements = new ArrayList<>();
        consumeToken(TokenType.BEGIN);
        while (!isMatchTokenType(TokenType.END)) {
            IStatement statement;
            if (statements.size() != 0) {
                consumeToken(TokenType.SEMICOLON);
            }
            statement = parseStatement();
            if (statement != null) {
                statements.add(statement);
            }
        }
        return new StatementBlock(statements);
    }

    private IStatement parseStatement() {
        IStatement statement;
        Token current = getCurrentToken(0);
        if (current.getTokenType().equals(TokenType.IDENTIFIER)) {
            statement = parseAssignmentStatement();
        }
        else if (current.getTokenType().equals(TokenType.IF)) {
            statement = parseIfStatement();
        }
        else if (current.getTokenType().equals(TokenType.BEGIN)) {
            statement = new BlockStatement(parseStatementBlock());
        }
        else if (current.getTokenType().equals(TokenType.WHILE)) {
            statement = parseWhileStatement();
        }
        else if (current.getTokenType().equals(TokenType.WRITELN)) {
            statement = parsePrintBlock();
        }
        else if (current.getTokenType().equals(TokenType.READLN)) {
            statement = parseReadStatement();
        }
        else if (current.getTokenType().equals(TokenType.CALL)) {
            statement = parseProcedureStatement();
        }
        else {
            throw new RuntimeException("Missing one of next statements: " +
                    "IDENTIFIER, IF, WHILE, BEGIN, '!', '?', CALL, but current token is " + current.getTokenType());
        }
        return statement;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private IStatement parsePrintBlock() {
        consumeToken(TokenType.WRITELN);
        consumeToken(TokenType.OPEN_ROUND_BRACKET);
        IExpression expression = expression();
        consumeToken(TokenType.CLOSE_ROUND_BRACKET);
        return new PrintStatement(expression);
    }

    private IStatement parseAssignmentStatement() {
        String identifier = getCurrentToken(0).getStringToken();
        if (Constants.isKeyExists(identifier)) {
            throw new RuntimeException("Constant '" + identifier + "' can't be changed.");
        }
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.ASSIGNMENT);
        return new AssignmentStatement(identifier, expression());
    }

    private IStatement parseIfStatement() {
        consumeToken(TokenType.IF);
        IExpression expression = expression();
        consumeToken(TokenType.THEN);
        IStatement trueBlock = parseStatementOrBlock();
        return new IfStatement(expression, trueBlock);
    }

    private IStatement parseReadStatement() {
        consumeToken(TokenType.READLN);
        consumeToken(TokenType.OPEN_ROUND_BRACKET);
        String identifier = getCurrentToken(0).getStringToken();
        if (Variables.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.CLOSE_ROUND_BRACKET);
            return new ReadStatement(identifier);
        }
        throw new RuntimeException("Variable '" + identifier + "' doesn't exist.");
    }

    private IStatement parseProcedureStatement() {
        consumeToken(TokenType.CALL);
        String identifier = getCurrentToken(0).getStringToken();
        consumeToken(TokenType.IDENTIFIER);
        return new ProcedureStatement(procedures.get(identifier));
    }

    private IStatement parseWhileStatement() {
        consumeToken(TokenType.WHILE);
        IExpression condition = expression();
        consumeToken(TokenType.DO);
        IStatement statement = parseStatementOrBlock();
        return new WhileStatement(condition, statement);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private void consumeToken(TokenType type) {
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return;
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