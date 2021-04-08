package lexer.constants;

import token.TokenType;

/**
 *This class was created to check character if it is keyword or not, and also to detect its token type
 */
public final class KeyWords {
    public final static String PROGRAM = "program";
    public final static String BEGIN = "begin";
    public final static String END = "end";
    public final static String AND = "and";
    public final static String OR = "or";
    public final static String NOT = "not";
    public final static String IF = "if";
    public final static String THEN = "then";
    public final static String ELSE = "else";
    public final static String WHILE = "while";
    public final static String DO = "do";
    public final static String REPEAT = "repeat";
    public final static String UNTIL = "until";
    public final static String BREAK = "break";
    public final static String FOR = "for";
    public final static String TO = "to";
    public final static String DOWNTO = "downto";
    public final static String STEP = "step";
    public final static String VAR = "var";
    public final static String CONST = "const";
    public final static String READLN = "readln";
    public final static String EXIT = "exit";
    public final static String DOUBLE = "double";
    public final static String STRING = "string";
    public final static String MOD = "mod";
    public final static String DIV = "div";

    private KeyWords() {}

    /**
     *
     * @param value keyword to detect what kind of keywords it belongs
     * @return token's type of this string
     */
    public static TokenType detectKeyWordType(final String value) {
        switch (value.toLowerCase()) {
            case PROGRAM:
                return TokenType.PROGRAM;
            case BEGIN:
                return TokenType.BEGIN;
            case END:
                return TokenType.END;
            case AND:
                return TokenType.AND;
            case OR:
                return TokenType.OR;
            case NOT:
                return TokenType.NOT;
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
            case REPEAT:
                return TokenType.REPEAT;
            case UNTIL:
                return TokenType.UNTIL;
            case BREAK:
                return TokenType.BREAK;
            case FOR:
                return TokenType.FOR;
            case TO:
                return TokenType.TO;
            case DOWNTO:
                return TokenType.DOWNTO;
            case STEP:
                return TokenType.STEP;
            case VAR:
                return TokenType.VAR;
            case CONST:
                return TokenType.CONST;
            case READLN:
                return TokenType.READLN;
            case EXIT:
                return TokenType.EXIT;
            case DOUBLE:
                return TokenType.DOUBLE;
            case STRING:
                return TokenType.STRING;
            case DIV:
                return TokenType.DIV;
            case MOD:
                return TokenType.MOD;
            default:
                return TokenType.IDENTIFIER;
        }
    }
}
