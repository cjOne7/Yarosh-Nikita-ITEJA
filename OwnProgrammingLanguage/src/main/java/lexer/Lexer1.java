package lexer;

import lexer.constants.KeyWords;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer1 {
    private String code;
    private int currentPosition;
    private int currentLine;
    private final StringBuilder builder = new StringBuilder();
    private final List<Token> tokens = new ArrayList<>();
    private Token token;

    public List<Token> getTokens(String code) {
        this.code = code;
        while (currentPosition < code.length()) {
            char character = code.charAt(currentPosition);
            if (Character.isLetter(character)) {
                readWord(character);
            } else if (Character.isDigit(character)) {
                readNumber(character);
            }
        }
        return tokens;
    }

    private void readWord(char character) {
        while (Character.isLetter(character)) {
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

}
