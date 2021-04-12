package lexer;

import lexer.constants.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class cuts input string on defined in language's grammar parts, which called tokens
 */
public final class Lexer {
    private String code;
    private int currentPosition;
    private int currentLine = 1;
    private final StringBuilder builder = new StringBuilder();
    private final List<Token> tokens = new ArrayList<>();
    private Token token;

    /**
     * @param code string, which was read from user and will be processed in this method
     * @return parsed {@link #code} string as {@link #tokens} list
     */
    public List<Token> getTokens(final String code) {
        this.code = code;
        while (currentPosition < code.length()) {
            final char character = code.charAt(currentPosition);
            if (Character.isLetter(character)) {
                readWord(character);
            }
            else if (Character.isDigit(character)) {
                readNumber(character);
            }
            else if (MathOperators.isMathOperator(character)) {
                if (character == '/' && peek(1) == '/') {
                    tokenizeOneLineComment(character);
                    continue;
                }
                addToken(MathOperators.detectMathOperatorType(character), Character.toString(character));
            }
            else if (WhiteChars.isWhiteChar(character)) {
                if (character == WhiteChars.NEW_LINE) {
                    currentLine++;
                }
                next();
            }
            else if (Brackets.isBracket(character)) {
                if (character == Brackets.OPEN_ROUND_BRACKET && peek(1) == '*') {
                    tokenizeMultilineComment(character);
                    next();//skip *
                    next();//skip )
                }
                else if (peek(0) == Brackets.OPEN_CURVE_BRACKET) {
                    tokenizeMultilineComment(character);
                    next();
                }
                else {
                    addToken(Brackets.detectBracketType(character), Character.toString(character));
                }
            }
            else if (Separators.isSeparator(character)) {
                if (character == Separators.COLON && peek(1) == CompareOperators.EQUALITY) {
                    addToken(TokenType.ASSIGNMENT, Character.toString(character).concat(peek(1) + ""));//ADD :=
                    next();
                }
                else if (character == Separators.QUOTE) {
                    readString();
                }
                else {
                    addToken(Separators.detectSeparatorType(character), Character.toString(character));
                }
            }
            else if (CompareOperators.isComparisonOperator(character)) {
                final String possibleConditionalOperator = code.substring(currentPosition, currentPosition + 2);
                final Pattern pattern = Pattern.compile(String.format("[%c%c%c]"
                        , CompareOperators.EQUALITY, CompareOperators.GREATER, CompareOperators.LESS));
                final Matcher matcher = pattern.matcher(possibleConditionalOperator);
                if (matcher.find()) {
                    builder.append(matcher.group());
                    String operator = builder.toString();
                    if (matcher.find(1)) {
                        final String secondOperator = matcher.group();
                        if (character == CompareOperators.EQUALITY) {
                            throw new RuntimeException("Wrong conditional operator " + character + secondOperator + " on line " + currentLine);
                        }
                        builder.append(secondOperator);
                        operator = builder.toString();
                        final TokenType type = CompareOperators.detectComparisonOperatorType(operator);
                        if (type == TokenType.UNKNOWN) {
                            throw new RuntimeException("Wrong conditional operator " + operator + " on line " + currentLine);
                        }
                        addToken(type, operator);
                        next();
                    }
                    else {
                        addToken(CompareOperators.detectComparisonOperatorType(character), operator);
                    }
                    builder.setLength(0);
                }
            }
            else {
                throw new RuntimeException("Unknown token on the line " + currentLine + " and position "
                        + (currentPosition - currentLine * (currentPosition / currentLine)));
            }
        }
        return tokens;
    }

    /**
     * This method reads string value from {@link #code} on {@link #currentPosition} and adds three tokens to
     * {@link #tokens}: QUOTE, STRING VALUE, QUOTE
     */
    private void readString() {
        addToken(TokenType.QUOTE, Character.toString(Separators.QUOTE));//cut '"' in the beginning
        final Matcher matcher = Pattern.compile("[^\"]*").matcher(code);
        matcher.find(currentPosition);
        final String value = code.substring(matcher.start(), matcher.end());
        if (!value.isEmpty()) {
            token = new Token(TokenType.STRING, value, currentLine, currentPosition);
            currentPosition += (matcher.end() - matcher.start());//skip string value
            tokens.add(token);
        }
        addToken(TokenType.QUOTE, Character.toString(Separators.QUOTE));//cut '"' in the end
    }

    /**
     * This method reads identifier. The start character represented as a parameter
     *
     * @param character start char in order to find identifier
     */
    private void readWord(char character) {
        while (Character.isLetterOrDigit(character) || character == '_') {
            builder.append(character);
            if (++currentPosition == code.length()) {
                break;
            }
            character = code.charAt(currentPosition);
        }
        final String str = builder.toString();
        token = new Token(KeyWords.detectKeyWordType(str), str, currentLine, currentPosition);
        tokens.add(token);
        builder.setLength(0);
    }

    /**
     * This method reads decimal floating point number. The start character represented as a parameter
     *
     * @param character start char in order to find decimal number
     */
    private void readNumber(char character) {
        while (Character.isDigit(character)) {
            builder.append(character);
            if (++currentPosition == code.length()) {
                break;
            }
            if (currentPosition < code.length() - 1 && code.charAt(currentPosition) == Separators.DOT) {
                if (builder.indexOf(Character.toString(Separators.DOT)) != -1) {
                    throw new RuntimeException("Incorrect double number on the line " + currentLine
                            + " and on position " + currentPosition);
                }
                builder.append(code.charAt(currentPosition++));
            }
            character = code.charAt(currentPosition);
        }

        token = new Token(TokenType.NUMBER, builder.toString(), currentLine, currentPosition);
        tokens.add(token);
        builder.setLength(0);
    }

    /**
     * Add token with current token type to {@link #tokens}
     *
     * @param tokenType type of token
     * @param value     represents token value as string
     */
    private void addToken(final TokenType tokenType, final String value) {
        if (tokenType == TokenType.UNKNOWN) {
            throw new RuntimeException("Unknown token on the line " + currentLine + " and position "
                    + (currentPosition - currentLine * (currentPosition / currentLine)));
        }
        next();
        token = new Token(tokenType, value, currentLine, currentPosition);
        tokens.add(token);
    }

    /**
     * @param relativePosition integer value for relatively getting the symbol from {@link #code}. If <b>relativePosition</b>
     *                         equals zero, method will return char from {@link #code} on position {@link #currentPosition}
     * @return character on position {@link #currentPosition} + relativePosition
     */
    private char peek(final int relativePosition) {
        final int position = currentPosition + relativePosition;
        if (position >= code.length()) {
            return '\0';
        }
        return code.charAt(position);
    }

    /**
     * Increments {@link #currentPosition} and returns next char in {@link #code}
     *
     * @return next char in {@link #code} line
     */
    private char next() {
        currentPosition++;
        return peek(0);
    }

    /**
     * Skip one line comment
     *
     * @param character start char in order to skip one line comment
     */
    private void tokenizeOneLineComment(char character) {
        while ("\r\n\0".indexOf(character) == -1) {//skip before you meet new line
            character = next();
        }
    }

    /**
     * Skip multiline comment
     *
     * @param character start char in order to skip multiline comment
     */
    private void tokenizeMultilineComment(char character) {
        while (true) {
            if (character == '\0') {
                throw new RuntimeException("Missing close tag *)");
            }
            if (character == '*' && peek(1) == Brackets.CLOSE_ROUND_BRACKET
                    || peek(0) == Brackets.CLOSE_CURVE_BRACKET) {
                break;
            }
            character = next();
        }
    }
}
