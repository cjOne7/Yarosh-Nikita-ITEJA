package lexer.constants;

import token.TokenType;

public class KeyWords {
    private final static String PROGRAM = "program";
    private final static String PROCEDURE = "procedure";
    private final static String CALL = "call";
    private final static String BEGIN = "begin";
    private final static String END = "end";
    private final static String IF = "if";
    private final static String THEN = "then";
    private final static String ELSE = "else";
    private final static String WHILE = "while";
    private final static String DO = "do";
    private final static String VAR = "var";
    private final static String CONST = "const";
    private final static String ODD = "odd";

    public static TokenType detectKeyWordType(String value) {
        switch (value.toLowerCase()) {
            case PROGRAM:
                return TokenType.PROGRAM;
            case PROCEDURE:
                return TokenType.PROCEDURE;
            case CALL:
                return TokenType.CALL;
            case BEGIN:
                return TokenType.BEGIN;
            case END:
                return TokenType.END;
            case IF:
                return TokenType.IF;
            case THEN:
                return TokenType.THEN;
            case ELSE:
                return TokenType.ELSE;
            case WHILE:
                return TokenType.WHILE;
            case DO:
                return TokenType.DO;
            case VAR:
                return TokenType.VAR;
            case CONST:
                return TokenType.CONST;
            case ODD:
                return TokenType.ODD;
            default:
                return TokenType.IDENTIFIER;
        }
    }
}
