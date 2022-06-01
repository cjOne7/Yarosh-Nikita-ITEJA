package parser;

import lexer.Lexer;
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

/**
 * This class uses the received tokens from {@link Lexer#getTokens(String)} to build AST (Abstract Syntax Tree), which
 * will be evaluated and executed later in the implementations of {@link IStatement} class
 */
public final class Parser {
    private final List<Token> tokens;
    private int pos;

    /**
     * @param tokens set token's list which was get after execution {@link Lexer#getTokens(String)} method to {@link #tokens}
     */
    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * @return {@link StatementBlock} which contains the whole parsed program code
     */
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

    /**
     * Parse variable block, which starts with VAR keyword
     */
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

    /**
     * Parse constant block, which starts with CONST keyword
     */
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

    /**
     * Method gets value from {@link #tokens} on {@link #pos} and put it to constants' map in {@link Constants} as
     * decimal floating point number
     *
     * @param identifier       constant name
     * @param isNegativeNumber determines whether the number is negative or not
     */
    private void parseConstNumber(final String identifier, final boolean isNegativeNumber) {
        final double value = Double.parseDouble(getCurrentToken(0).getStringToken());
        consumeToken(TokenType.NUMBER);
        Constants.put(identifier, new DoubleValue(isNegativeNumber ? -value : value));
    }

    /**
     * Method gets value from {@link #tokens} on {@link #pos} and put it to constants' map in {@link Constants} as string
     *
     * @param identifier constant name
     */
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

    /**
     * Decide one statement or statements' block should be parsed
     *
     * @return one statement or {@link StatementBlock}
     */
    private IStatement parseStatementOrBlock() {
        return getCurrentToken(0).getTokenType() == TokenType.BEGIN
                ? parseStatementBlock() : parseStatement();
    }

    /**
     * Parse begin-end block
     *
     * @return {@link StatementBlock}
     */
    private IStatement parseStatementBlock() {
        final List<IStatement> statements = new ArrayList<>();
        consumeToken(TokenType.BEGIN);
        parseTokensInList(statements, TokenType.END);
        return new StatementBlock(statements);
    }

    /**
     * @param statements statements' list to which will be saved all statements
     * @param type       if the token with this type still meeting - continue reading, in the other case - stop
     */
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

    /**
     * Parse statement
     *
     * @return one of the implementations of the {@link IStatement} class
     */
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

    /**
     * Parse user input statement
     *
     * @return {@link ReadStatement}
     */
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

    /**
     * Parse assignment statement
     *
     * @return {@link AssignmentStatement}
     */
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

    /**
     * Parse while-do statement
     *
     * @return {@link WhileStatement}
     */
    private IStatement parseWhileStatement() {
        consumeToken(TokenType.WHILE);
        final IExpression condition = expression();
        consumeToken(TokenType.DO);
        final IStatement statement = parseStatementOrBlock();
        return new WhileStatement(condition, statement);
    }

    /**
     * Parse repeat-until statement
     *
     * @return {@link RepeatStatement}
     */
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

    /**
     * Parse for-do statement
     *
     * @return {@link ForStatement}
     */
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

    /**
     * Parse if-else statement
     *
     * @return {@link IfStatement}
     */
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

    /**
     * Parse expression
     *
     * @return {@link ConditionalExpression}
     */
    private IExpression expression() {
        return logicalOr();
    }

    /**
     * Parse logical or expression
     *
     * @return {@link ConditionalExpression}
     */
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

    /**
     * Parse logical and expression
     *
     * @return {@link ConditionalExpression}
     */
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

    /**
     * Parse condition expression
     *
     * @return {@link ConditionalExpression}
     */
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

    /**
     * Parse +, - operations binary expression
     *
     * @return {@link BinaryExpression}
     */
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

    /**
     * Parse *, / operations binary expression
     *
     * @return {@link BinaryExpression}
     */
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

    /**
     * Parse +, - operations unary expression
     *
     * @return {@link UnaryExpression}
     */
    private IExpression unary() {
        if (isMatchTokenType(TokenType.MINUS)) {
            return new UnaryExpression(MathOperators.MINUS, primary());
        }
        if (isMatchTokenType(TokenType.PLUS)) {
            return new UnaryExpression(MathOperators.PLUS, primary());
        }
        return primary();
    }

    /**
     * Parse recursively expressions in round brackets. Parse numbers or strings values. Parse functions identifiers as
     * {@link FunctionExpression}. Parse conditional negation
     *
     * @return a one of the following types presented: {@link ValueExpression}, {@link VariableExpression},
     * {@link ConditionalExpression}, {@link FunctionExpression}
     */
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

    /**
     * Parse function as expression
     *
     * @param functionName function identifier
     * @param isExpression responsible for separating functions with void return type from others functions
     * @return {@link FunctionExpression}
     */
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

    /**
     * Parse string
     *
     * @return {@link ValueExpression}
     */
    private ValueExpression string() {
        final String value = getCurrentToken(0).getStringToken();
        isMatchTokenType(TokenType.STRING);
        consumeToken(TokenType.QUOTE);
        return new ValueExpression(value);
    }

    /**
     * Match token type. If <b>type</b> matches with token's type on {@link #pos}, {@link #pos} will be incremented and
     * will be returned <tt>true</tt>. In other case will be returned <tt>false</tt>
     *
     * @param type type, which should be matched with token's type on {@link #pos}
     * @return <tt>true</tt> if <b>type</b> matches with token's type on {@link #pos}
     */
    private boolean isMatchTokenType(final TokenType type) {
        final Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return true;
        }
        return false;
    }

    /**
     * @param type type, which should be matched with token's type on {@link #pos}
     * @throws RuntimeException if <b>type</b> doesn't match with token's type on {@link #pos}
     */
    private void consumeToken(final TokenType type) {
        final Token current = getCurrentToken(0);
        if (type == current.getTokenType()) {
            pos++;
            return;
        }
        throw new RuntimeException("Expected " + type + ", but was found " + current.getTokenType() + " on position " + current.getRowPosition());
    }

    /**
     * @param relativePosition integer value for relatively getting the token from {@link #tokens}. If relativePosition
     *                         equals zero, method will return char from {@link #tokens} on position {@link #pos}
     * @return token from {@link #tokens} on position {@link #pos}
     * @throws RuntimeException if in the end of program DOT isn't found
     */
    private Token getCurrentToken(final int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= tokens.size()) {
            throw new RuntimeException("Program must be finished with DOT in the end.");
        }
        return tokens.get(position);
    }

    /**
     * Throw exception if constant name is found in {@link Constants} map
     *
     * @param identifier constant name
     * @throws RuntimeException if constant identifier is in {@link Constants} map
     */
    private void checkConstImmutable(final String identifier) {
        if (Constants.isKeyExists(identifier)) {
            throw new RuntimeException("Constant '" + identifier + "' can't be changed.");
        }
    }
}
