package lexer;

import lexer.constants.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Lexer {
    private String code;
    private int currentPosition;
    private int currentLine = 1;
    private final StringBuilder builder = new StringBuilder();
    private final List<Token> tokens = new ArrayList<>();
    private Token token;

    public List<Token> getTokens(String code) {
        this.code = code;
        while (currentPosition < code.length()) {
            char character = code.charAt(currentPosition);
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
                if (peek(1) == '*') {
                    tokenizeMultilineComment(character);
                    next();
                    next();
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
                String possibleConditionalOperator = code.substring(currentPosition, currentPosition + 2);
                Pattern pattern = Pattern.compile(String.format("[%c%c%c]"
                        , CompareOperators.EQUALITY, CompareOperators.GREATER, CompareOperators.LESS));
                Matcher matcher = pattern.matcher(possibleConditionalOperator);
                if (matcher.find()) {
                    builder.append(matcher.group());
                    String operator = builder.toString();
                    if (matcher.find(1)) {
                        String secondOperator = matcher.group();
                        if (character == CompareOperators.EQUALITY) {
                            throw new RuntimeException("Wrong conditional operator " + character + secondOperator + " on line " + currentLine);
                        }
                        builder.append(secondOperator);
                        operator = builder.toString();
                        TokenType type = CompareOperators.detectComparisonOperatorType(operator);
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

    //todo add escaped symbols
    private void readString() {
        addToken(TokenType.QUOTE, Character.toString(Separators.QUOTE));//cut '"' in the beginning
        Matcher matcher = Pattern.compile("[^\"]*").matcher(code);
        matcher.find(currentPosition);
        String value = code.substring(matcher.start(), matcher.end());
        if (!value.isEmpty()) {
            token = new Token(TokenType.STRING, value, currentLine, currentPosition);
            currentPosition += (matcher.end() - matcher.start());//skip string value
            tokens.add(token);
        }
        addToken(TokenType.QUOTE, Character.toString(Separators.QUOTE));//cut '"' in the end
    }

    private void readWord(char character) {
        while (Character.isLetterOrDigit(character) || character == '_') {
            builder.append(character);
            if (++currentPosition == code.length()) {
                break;
            }
            character = code.charAt(currentPosition);
        }
        String str = builder.toString().toLowerCase();
        token = new Token(KeyWords.detectKeyWordType(str), str, currentLine, currentPosition);
        tokens.add(token);
        builder.setLength(0);
    }

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

    private void addToken(TokenType tokenType, String value) {
        if (tokenType == TokenType.UNKNOWN) {
            throw new RuntimeException("Unknown token on the line " + currentLine + " and position "
                    + (currentPosition - currentLine * (currentPosition / currentLine)));
        }
        next();
        token = new Token(tokenType, value, currentLine, currentPosition);
        tokens.add(token);
    }

    private char peek(int relativePosition) {
        final int position = currentPosition + relativePosition;
        if (position >= code.length()) {
            return '\0';
        }
        return code.charAt(position);
    }

    private char next() {
        currentPosition++;
        return peek(0);
    }

    private void tokenizeOneLineComment(char character) {
        while ("\r\n\0".indexOf(character) == -1) {//skip before you meet new line
            character = next();
        }
    }

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
