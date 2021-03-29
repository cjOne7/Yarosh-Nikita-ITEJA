package parser;

import parser.blocks.StatementBlock;
import parser.expressions.*;
import parser.lib.*;
import parser.procedure.Procedure;
import parser.procedure.Procedures;
import parser.statements.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (tokens.size() == 1 && getCurrentToken(0).getTokenType() == TokenType.END_OF_FILE) {
            return new EndOfFileStatement();
        }
        IStatement programBody = parseStatementOrBlock();
        consumeToken(TokenType.END_OF_FILE);
        return programBody;
    }

    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    private void parseVariableBlock() {
        consumeToken(TokenType.VAR);
        do {
            String identifier = getCurrentToken(0).getStringToken();
            if (Variables.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.COLON);
            if (isMatchTokenType(TokenType.DOUBLE)) {
                Variables.put(identifier, Variables.ZERO);
            }
            else if (isMatchTokenType(TokenType.STRING)) {
                Variables.put(identifier, Variables.EMPTY);
            }
            else {
                throw new RuntimeException("Unknown datatype.");
            }
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.SEMICOLON);
    }

    private void parseConstBlock() {
        consumeToken(TokenType.CONST);
        do {
            if (getCurrentToken(0).getTokenType() != TokenType.IDENTIFIER) {
                break;
            }
            String identifier = getCurrentToken(0).getStringToken();
            if (Constants.isKeyExists(identifier)) {
                throw new RuntimeException("Constant '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.COLON);
            if (isMatchTokenType(TokenType.DOUBLE)) {
                consumeToken(TokenType.EQUAL);
                parseConstNumber(identifier, isMatchTokenType(TokenType.MINUS));
            }
            else if (isMatchTokenType(TokenType.STRING)) {
                consumeToken(TokenType.EQUAL);
                parseConstString(identifier);
            }
            else {
                throw new RuntimeException("Unknown datatype.");
            }
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.SEMICOLON);
    }

    private void parseConstNumber(String identifier, boolean isNegativeNumber) {
        try {
            double value = Double.parseDouble(getCurrentToken(0).getStringToken());
            consumeToken(TokenType.NUMBER);
            Constants.put(identifier, new NumberValue(isNegativeNumber ? -value : value));
        } catch (NumberFormatException e) {
            System.err.println("You can't assign string to number.");
        }
    }

    private void parseConstString(String identifier) {
        consumeToken(TokenType.QUOTE);
        if (getCurrentToken(0).getTokenType() == TokenType.STRING) {
            String value = getCurrentToken(0).getStringToken();
            consumeToken(TokenType.STRING);
            Constants.put(identifier, new StringValue(value));
        }
        else {
            Constants.put(identifier, Constants.EMPTY);
        }
        consumeToken(TokenType.QUOTE);
    }

    private void parseProcedure() {
        consumeToken(TokenType.PROCEDURE);
        String identifier = getCurrentToken(0).getStringToken();
        Identifiers.put(identifier);
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.SEMICOLON);
        Map<String, IValue> variables = null;
        if (getCurrentToken(0).getTokenType() == TokenType.VAR) {
            variables = parseLocalVariableBlock();
        }
        IStatement procedureBlockStatements = parseStatementBlock();
        procedures.add(new Procedure(identifier, procedureBlockStatements, variables));
        consumeToken(TokenType.SEMICOLON);
    }

    private Map<String, IValue> parseLocalVariableBlock() {
        Map<String, IValue> variables = new HashMap<>();
        consumeToken(TokenType.VAR);
        do {
            String identifier = getCurrentToken(0).getStringToken();
            if (LocalVars.isKeyExists(identifier)) {
                throw new RuntimeException("Variable '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.COLON);
            if (isMatchTokenType(TokenType.DOUBLE)) {
                variables.put(identifier, Variables.NAN);
            }
            else if (isMatchTokenType(TokenType.STRING)) {
                variables.put(identifier, Variables.EMPTY);
            }
            else {
                throw new RuntimeException("Unknown datatype.");
            }
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.SEMICOLON);
        LocalVars.push(variables);
        return variables;
    }

    private IStatement parseStatementBlock() {
        List<IStatement> statements = new ArrayList<>();
        consumeToken(TokenType.BEGIN);
        while (!isMatchTokenType(TokenType.END)) {
            if (statements.size() != 0) {
                consumeToken(TokenType.SEMICOLON);
            }
            statements.add(parseStatement());
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
        else if (current.getTokenType().equals(TokenType.EXCLAMATION_MARK)) {
            statement = parsePrintBlock();
        }
        else if (current.getTokenType().equals(TokenType.QUESTION_MARK)) {
            statement = parseReadStatement();
        }
        else if (current.getTokenType().equals(TokenType.CALL)) {
            statement = parseProcedureStatement();
        }
        else {
            throw new RuntimeException("Missing one of next statements: IDENTIFIER, IF, WHILE, BEGIN, '!', '?', CALL, " +
                    "but current token is " + current.getTokenType() + " on position " + current.getRowPosition());
        }
        return statement;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private IStatement parsePrintBlock() {
        consumeToken(TokenType.EXCLAMATION_MARK);
        return new PrintStatement(expression());
    }

    private IStatement parseAssignmentStatement() {
        String identifier = getCurrentToken(0).getStringToken();
        checkConstImmutable(identifier);
        if (LocalVars.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.ASSIGNMENT);
            return new AssignmentStatement(identifier, expression());
        }
        if (Variables.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.ASSIGNMENT);
            return new AssignmentStatement(identifier, expression());
        }
        throw new RuntimeException("Variable '" + identifier + "' is not initialized.");
    }

    private IStatement parseIfStatement() {
        consumeToken(TokenType.IF);
        IExpression expression = expression();
        consumeToken(TokenType.THEN);
        IStatement trueBlock = parseStatementOrBlock();
        return new IfStatement(expression, trueBlock);
    }

    private IStatement parseReadStatement() {
        consumeToken(TokenType.QUESTION_MARK);
        String identifier = getCurrentToken(0).getStringToken();
        checkConstImmutable(identifier);
        if (Variables.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
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
            return new ValueExpression(Double.parseDouble(current.getStringToken()));
        }
        if (isMatchTokenType(TokenType.QUOTE)) {
            return string();
        }
        if (isMatchTokenType(TokenType.IDENTIFIER)) {
            return new VariableExpression(current.getStringToken());
        }
        if (isMatchTokenType(TokenType.OPEN_ROUND_BRACKET)) {
            IExpression expression = expression();
            consumeToken(TokenType.CLOSE_ROUND_BRACKET);
            return expression;
        }
        throw new RuntimeException("Unknown expression on position " + current.getRowPosition());
    }

    private boolean isMatchTokenType(TokenType type) {
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        return false;
    }

    private IExpression string() {
        String value = getCurrentToken(0).getStringToken();
        isMatchTokenType(TokenType.STRING);
        consumeToken(TokenType.QUOTE);
        return new ValueExpression(value);
    }

    private void consumeToken(TokenType type) {
        Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return;
        }
        throw new RuntimeException("Expected " + type + ", but was found " + current.getTokenType() + " on position " + current.getRowPosition());
    }

    private Token getCurrentToken(int relativePosition) {
        int position = pos + relativePosition;
        if (position >= size) {
            throw new RuntimeException("Program must be finished with DOT in the end.");
        }
        return tokens.get(position);
    }

    private void checkConstImmutable(String identifier) {
        if (Constants.isKeyExists(identifier)) {
            throw new RuntimeException("Constant '" + identifier + "' can't be changed.");
        }
    }
}
