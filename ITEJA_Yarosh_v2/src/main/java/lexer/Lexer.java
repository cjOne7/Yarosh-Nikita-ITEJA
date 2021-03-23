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
                addToken(MathOperators.detectMathOperatorType(character), Character.toString(character));
            }
            else if (WhiteChars.isWhiteChar(character)) {
                if (character == WhiteChars.NEW_LINE) {
                    currentLine++;
                }
                currentPosition++;
            }
            else if (Brackets.isBracket(character)) {
                addToken(Brackets.detectBracketType(character), Character.toString(character));
            }
            else if (CompareOperators.isComparisonOperator(character)) {
                if (currentPosition < code.length() - 1 && !CompareOperators.isComparisonOperator(code.charAt(currentPosition + 1))) {//<,>,=
                    addToken(CompareOperators.detectComparisonOperatorType(character), Character.toString(character));
                }
                else if (currentPosition < code.length() - 1
                        && code.charAt(currentPosition) == CompareOperators.EQUALITY
                        && CompareOperators.isComparisonOperator(code.charAt(currentPosition + 1))) {
                    throw new RuntimeException("After '=' cannot be other condition operator. Your operator: " + character + code.charAt(currentPosition + 1));
                }
                else if (currentPosition < code.length() - 1 && code.charAt(currentPosition) == CompareOperators.LESS) {
                    char nextChar = code.charAt(currentPosition + 1);
                    String compareOperator = Character.toString(character).concat(Character.toString(nextChar));
                    switch (nextChar) {
                        case CompareOperators.EQUALITY:
                            addToken(TokenType.LESS_OR_EQUAL, compareOperator);
                            break;
                        case CompareOperators.GREATER:
                            addToken(TokenType.NOTEQUAL, compareOperator);
                            break;
                        default:
                            throw new RuntimeException("Wrong conditional operator " + character + nextChar);
                    }
                    currentPosition++;
                }
                else if (currentPosition < code.length() - 1 && code.charAt(currentPosition) == CompareOperators.GREATER) {//>=
                    char nextChar = code.charAt(currentPosition + 1);
                    String compareOperator = Character.toString(character).concat(Character.toString(nextChar));
                    if (nextChar == CompareOperators.EQUALITY) {//>=
                        addToken(TokenType.GREATER_OR_EQUAL, compareOperator);
                        currentPosition++;
                    }
                    else {
                        throw new RuntimeException("Wrong conditional operator " + character + nextChar);
                    }
                }
                else {
                    addToken(CompareOperators.detectComparisonOperatorType(character), Character.toString(character));
                }
            }
            else if (Separators.isSeparator(character)) {
                if (currentPosition < code.length() - 1 && character == Separators.COLON
                        && code.charAt(currentPosition + 1) == CompareOperators.EQUALITY) {
                    addToken(TokenType.ASSIGNMENT, Character.toString(character).concat(code.charAt(currentPosition + 1) + ""));//ADD :=
                    currentPosition++;
                }
                else if (currentPosition == code.length() - 1 && character == Separators.DOT) {
                    addToken(TokenType.END_OF_FILE, Character.toString(character));//ADD EOF
                }
                else if (code.charAt(currentPosition) == Separators.QUOTE) {
                    readString();
                }
                else {
                    addToken(Separators.detectSeparatorType(character), Character.toString(character));
                }
            }
            else {
                throw new RuntimeException("Unknown token on the line " + currentLine + " and position "
                        + (currentPosition - currentLine * (currentPosition / currentLine)));
            }
        }
        return tokens;
    }

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
        String str = builder.toString();
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
        currentPosition++;
        token = new Token(tokenType, value, currentLine, currentPosition);
        tokens.add(token);
    }

}
