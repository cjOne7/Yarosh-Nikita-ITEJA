package lexer.constants;

import token.TokenType;

public class KeyWords {
    public final static String PROGRAM = "program";
    public final static String PROCEDURE = "procedure";
    public final static String CALL = "call";
    public final static String BEGIN = "begin";
    public final static String END = "end";
    public final static String IF = "if";
    public final static String THEN = "then";
    public final static String WHILE = "while";
    public final static String DO = "do";
    public final static String VAR = "var";
    public final static String CONST = "const";
    public final static String ODD = "odd";
    public final static String DOUBLE = "double";
    public final static String STRING = "string";

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
            case DOUBLE:
                return TokenType.DOUBLE;
            case STRING:
                return TokenType.STRING;
            default:
                return TokenType.IDENTIFIER;
        }
    }
}
