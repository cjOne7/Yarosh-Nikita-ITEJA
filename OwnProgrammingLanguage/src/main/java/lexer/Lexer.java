package lexer;

import lexer.constants.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

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
//                addToken(WhiteChars.detectWhiteCharType(character), WhiteChars.escapeWhiteChars(character));
                currentPosition++;
            }
            else if (Brackets.isBracket(character)) {
                addToken(Brackets.detectBracketType(character), Character.toString(character));
            }
            else if (CompareOperators.isComparisonOperator(character)) {
                if (currentPosition < code.length() - 1 && code.charAt(currentPosition + 1) == CompareOperators.EQUALITY) {
                    TokenType type = character == CompareOperators.GREATER ? TokenType.GREATER_OR_EQUAL : TokenType.LESS_OR_EQUAL;
                    addToken(type, Character.toString(character).concat(Character.toString(code.charAt(currentPosition + 1))));
                    currentPosition++;
                }
                else {
                    addToken(CompareOperators.detectComparisonOperatorType(character), Character.toString(character));
                }
            }
            else if (Separators.isSeparator(character)) {
                if (currentPosition < code.length() - 1 && character == Separators.COLON
                        && code.charAt(currentPosition + 1) == CompareOperators.EQUALITY) {
                    addToken(TokenType.ASSIGNMENT, Character.toString(character).concat(Character.toString(code.charAt(currentPosition + 1))));
                    currentPosition++;
                }
                else if (currentPosition == code.length() - 1 && character == Separators.DOT) {
                    addToken(TokenType.END_OF_FILE, Character.toString(character));
                }
                else {
                    addToken(Separators.detectSeparatorType(character), Character.toString(character));
                }
            }
            else {
                addToken(TokenType.UNKNOWN, Character.toString(character));
            }
        }
        return tokens;
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
            character = code.charAt(currentPosition);
        }

        token = new Token(TokenType.NUMBER, builder.toString(), currentLine, currentPosition);
        tokens.add(token);
        builder.setLength(0);
    }

    private void addToken(TokenType tokenType, String value) {
        currentPosition++;
        token = new Token(tokenType, value, currentLine, currentPosition);
        tokens.add(token);
        if (value.equals(WhiteChars.ESCAPED_NEW_LINE)) {
            currentLine++;
        }
    }

}
