package parser;

import lexer.constants.KeyWords;
import lexer.constants.MathOperators;
import parser.lib.datatypes.*;
import parser.statements.blocks.*;
import parser.expressions.*;
import parser.lib.*;
import parser.statements.*;
import parser.statements.functions.FunctionStatement;
import parser.statements.loops.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
    private final List<Token> tokens;
    private int pos;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public IStatement parseBlock() {
        if (isMatchTokenType(TokenType.PROGRAM)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.SEMICOLON);
        }
        while (true) {
            if (getCurrentToken(0).getTokenType() == TokenType.VAR) {
                parseVariableBlock();
                continue;
            }
            if (getCurrentToken(0).getTokenType() == TokenType.CONST) {
                parseConstBlock();
                continue;
            }
            break;
        }
        final IStatement programBody = parseStatementBlock();
        consumeToken(TokenType.DOT);
        return programBody;
    }

    private void parseVariableBlock() {
        final List<String> identifiersList = new ArrayList<>();
        consumeToken(TokenType.VAR);
        loop:
        while (true) {
            do {
                if (getCurrentToken(0).getTokenType() != TokenType.IDENTIFIER) {
                    break loop;
                }
                final String identifier = getCurrentToken(0).getStringToken();
                if (identifiersList.contains(identifier)) {
                    throw new RuntimeException("Variable '" + identifier + "' is already defined.");
                }
                identifiersList.add(identifier);
                consumeToken(TokenType.IDENTIFIER);
            } while (isMatchTokenType(TokenType.COMMA));
            consumeToken(TokenType.COLON);
            if (isMatchTokenType(TokenType.DOUBLE)) {
                identifiersList.forEach(identifier -> Variables.put(identifier, Constants.ZERO));
            }
            else if (isMatchTokenType(TokenType.STRING)) {
                identifiersList.forEach(identifier -> Variables.put(identifier, Constants.EMPTY));
            }
            else {
                throw new RuntimeException("Unknown datatype.");
            }
            identifiersList.clear();
            consumeToken(TokenType.SEMICOLON);
        }
    }

    private void parseConstBlock() {
        consumeToken(TokenType.CONST);
        do {
            if (getCurrentToken(0).getTokenType() != TokenType.IDENTIFIER) {
                break;
            }
            final String identifier = getCurrentToken(0).getStringToken();
            if (Constants.isKeyExists(identifier)) {
                throw new RuntimeException("Constant '" + identifier + "' is already defined.");
            }
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.EQUAL);
            if (getCurrentToken(0).getTokenType() == TokenType.QUOTE) {
                parseConstString(identifier);
            }
            else if (getCurrentToken(0).getTokenType() == TokenType.NUMBER) {
                parseConstNumber(identifier, false);
            }
            else if (isMatchTokenType(TokenType.MINUS)) {
                parseConstNumber(identifier, true);
            }
            else {
                throw new RuntimeException("Unknown datatype.");
            }
        } while (isMatchTokenType(TokenType.SEMICOLON));
    }

    private void parseConstNumber(final String identifier, final boolean isNegativeNumber) {
        final double value = Double.parseDouble(getCurrentToken(0).getStringToken());
        consumeToken(TokenType.NUMBER);
        Constants.put(identifier, new DoubleValue(isNegativeNumber ? -value : value));
    }

    private void parseConstString(final String identifier) {
        consumeToken(TokenType.QUOTE);
        if (getCurrentToken(0).getTokenType() == TokenType.STRING) {
            final String value = getCurrentToken(0).getStringToken();
            consumeToken(TokenType.STRING);
            Constants.put(identifier, new StringValue(value));
        }
        else {
            Constants.put(identifier, Constants.EMPTY);
        }
        consumeToken(TokenType.QUOTE);
    }

    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    private IStatement parseStatementBlock() {
        final List<IStatement> statements = new ArrayList<>();
        consumeToken(TokenType.BEGIN);
        parseTokensInList(statements, TokenType.END);
        return new StatementBlock(statements);
    }

    private void parseTokensInList(final List<IStatement> statements, final TokenType type) {
        while (!isMatchTokenType(type)) {
            if (statements.size() != 0) {
                consumeToken(TokenType.SEMICOLON);
                if (isMatchTokenType(type)) {
                    break;
                }
            }
            statements.add(parseStatement());
        }
    }

    private IStatement parseStatement() {
        final Token current = getCurrentToken(0);
        switch (current.getTokenType()) {
            case IDENTIFIER:
                if (getCurrentToken(1).getTokenType().equals(TokenType.OPEN_ROUND_BRACKET)) {
                    consumeToken(TokenType.IDENTIFIER);
                    consumeToken(TokenType.OPEN_ROUND_BRACKET);
                    return new FunctionStatement(function(current.getStringToken(), false));
                }
                return parseAssignmentStatement();
            case IF:
                return parseIfStatement();
            case BEGIN:
                consumeToken(TokenType.BEGIN);
                return new BlockStatement(parseStatementBlock());
            case WHILE:
                return parseWhileStatement();
            case REPEAT:
                return parseRepeatStatement();
            case FOR:
                return parseForStatement();
            case BREAK:
                consumeToken(TokenType.BREAK);
                return new BreakStatement();
            case READLN:
                return parseReadStatement();
            case EXIT:
                consumeToken(TokenType.EXIT);
                return new ExitStatement();
            default:
                throw new RuntimeException("Missing one of next statements: IDENTIFIER, IF, WHILE, BEGIN, WRITELN, READLN, EXIT, " +
                        "but current token is " + current.getTokenType() + " on position " + current.getRowPosition());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private IStatement parseReadStatement() {
        consumeToken(TokenType.READLN);
        consumeToken(TokenType.OPEN_ROUND_BRACKET);
        final String identifier = getCurrentToken(0).getStringToken();
        checkConstImmutable(identifier);
        if (Variables.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.CLOSE_ROUND_BRACKET);
            return new ReadStatement(identifier);
        }
        throw new RuntimeException("Variable '" + identifier + "' doesn't exist.");
    }

    private IStatement parseAssignmentStatement() {
        final String identifier = getCurrentToken(0).getStringToken();
        checkConstImmutable(identifier);
        if (Variables.isKeyExists(identifier)) {
            consumeToken(TokenType.IDENTIFIER);
            consumeToken(TokenType.ASSIGNMENT);
            return new AssignmentStatement(identifier, expression());
        }
        throw new RuntimeException("Variable '" + identifier + "' is not initialized.");
    }

    private IStatement parseWhileStatement() {
        consumeToken(TokenType.WHILE);
        final IExpression condition = expression();
        consumeToken(TokenType.DO);
        final IStatement statement = parseStatementOrBlock();
        return new WhileStatement(condition, statement);
    }

    private IStatement parseRepeatStatement() {
        consumeToken(TokenType.REPEAT);
        final IStatement statement;
        if (getCurrentToken(0).getTokenType() == TokenType.BEGIN) {
            statement = parseStatementOrBlock();
            isMatchTokenType(TokenType.SEMICOLON);
            consumeToken(TokenType.UNTIL);
        }
        else {
            final List<IStatement> statements = new ArrayList<>();
            parseTokensInList(statements, TokenType.UNTIL);
            statement = new StatementBlock(statements);
        }
        final IExpression condition = expression();
        return new RepeatStatement(condition, statement);
    }

    private IStatement parseForStatement() {
        boolean isReverse = false;
        consumeToken(TokenType.FOR);
        final String identifier = getCurrentToken(0).getStringToken();
        consumeToken(TokenType.IDENTIFIER);
        consumeToken(TokenType.ASSIGNMENT);
        final IExpression assignmentExpression = additive();

        if (isMatchTokenType(TokenType.DOWNTO)) {
            isReverse = true;
        }
        else {
            isMatchTokenType(TokenType.TO);
        }
        final IExpression toExpression = additive();
        IExpression step = null;
        if (isMatchTokenType(TokenType.STEP)) {
            step = additive();
        }
        consumeToken(TokenType.DO);
        final IStatement body = parseStatementOrBlock();
        return new ForStatement(identifier, assignmentExpression, toExpression, body, isReverse, step);

    }

    private IStatement parseIfStatement() {
        consumeToken(TokenType.IF);
        final IExpression expression = expression();
        consumeToken(TokenType.THEN);
        final IStatement ifBlock = parseStatementOrBlock();
        IStatement elseBlock = null;
        if (isMatchTokenType(TokenType.ELSE)) {
            elseBlock = parseStatementOrBlock();
        }
        return new IfStatement(expression, ifBlock, elseBlock);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Recursive descending parser
    private IExpression expression() {
        return logicalOr();
    }

    private IExpression logicalOr() {
        IExpression result = logicalAnd();
        while (true) {
            if (isMatchTokenType(TokenType.OR)) {
                result = new ConditionalExpression(KeyWords.OR, result, logicalAnd());
                continue;
            }
            break;
        }
        return result;
    }

    private IExpression logicalAnd() {
        IExpression result = condition();
        while (true) {
            if (isMatchTokenType(TokenType.AND)) {
                result = new ConditionalExpression(KeyWords.AND, result, condition());
                continue;
            }
            break;
        }
        return result;
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
                result = new BinaryExpression(current.getStringToken(), result, multiplicative());
                continue;
            }
            if (isMatchTokenType(TokenType.MINUS)) {
                result = new BinaryExpression(current.getStringToken(), result, multiplicative());
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
                result = new BinaryExpression(current.getStringToken(), result, unary());
                continue;
            }
            if (isMatchTokenType(TokenType.DIVIDE)) {
                result = new BinaryExpression(current.getStringToken(), result, unary());
                continue;
            }
            if (isMatchTokenType(TokenType.DIV)) {
                result = new BinaryExpression(current.getStringToken(), result, unary());
                continue;
            }
            if (isMatchTokenType(TokenType.MOD)) {
                result = new BinaryExpression(current.getStringToken(), result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private IExpression unary() {
        if (isMatchTokenType(TokenType.MINUS)) {
            return new UnaryExpression(MathOperators.MINUS, primary());
        }
        if (isMatchTokenType(TokenType.PLUS)) {
            return new UnaryExpression(MathOperators.PLUS, primary());
        }
        return primary();
    }

    private IExpression primary() {
        final Token current = getCurrentToken(0);
        if (isMatchTokenType(TokenType.NUMBER)) {
            return new ValueExpression(Double.parseDouble(current.getStringToken()));
        }
        if (isMatchTokenType(TokenType.QUOTE)) {
            return string();
        }
        if (isMatchTokenType(TokenType.IDENTIFIER)) {
            if (isMatchTokenType(TokenType.OPEN_ROUND_BRACKET)) {
                return function(current.getStringToken(), true);
            }
            return new VariableExpression(current.getStringToken());
        }
        if (isMatchTokenType(TokenType.OPEN_ROUND_BRACKET)) {
            final IExpression expression = expression();
            consumeToken(TokenType.CLOSE_ROUND_BRACKET);
            return expression;
        }
        if (isMatchTokenType(TokenType.NOT)) {
            if (isMatchTokenType(TokenType.OPEN_ROUND_BRACKET)) {
                final IExpression expression = expression();
                consumeToken(TokenType.CLOSE_ROUND_BRACKET);
                return new ConditionalExpression(expression.eval().asDouble() != 1);
            }
            final IExpression expression = condition();
            return new ConditionalExpression(expression.eval().asDouble() != 1);
        }
        throw new RuntimeException("Unknown expression on position " + current.getRowPosition());
    }

    private FunctionExpression function(final String functionName, final boolean isExpression) {
        final FunctionExpression functionExpression = new FunctionExpression(functionName, isExpression);
        if (isMatchTokenType(TokenType.CLOSE_ROUND_BRACKET)) {
            return functionExpression;
        }
        do {
            functionExpression.addArgument(expression());
        } while (isMatchTokenType(TokenType.COMMA));
        consumeToken(TokenType.CLOSE_ROUND_BRACKET);
        return functionExpression;
    }

    private ValueExpression string() {
        final String value = getCurrentToken(0).getStringToken();
        isMatchTokenType(TokenType.STRING);
        consumeToken(TokenType.QUOTE);
        return new ValueExpression(value);
    }

    private boolean isMatchTokenType(final TokenType type) {
        final Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        return false;
    }

    private void consumeToken(TokenType type) {
        final Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return;
        }
        throw new RuntimeException("Expected " + type + ", but was found " + current.getTokenType() + " on position " + current.getRowPosition());
    }

    private Token getCurrentToken(final int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= tokens.size()) {
            throw new RuntimeException("Program must be finished with DOT in the end.");
        }
        return tokens.get(position);
    }

    private void checkConstImmutable(final String identifier) {
        if (Constants.isKeyExists(identifier)) {
            throw new RuntimeException("Constant '" + identifier + "' can't be changed.");
        }
    }
}
