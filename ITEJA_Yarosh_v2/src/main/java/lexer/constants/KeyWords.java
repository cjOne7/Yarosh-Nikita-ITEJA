package lexer.constants;

import token.TokenType;

public class KeyWords {
    public final static String PROGRAM = "program";
    public final static String BEGIN = "begin";
    public final static String END = "end";
    public final static String AND = "and";
    public final static String OR = "or";
    public final static String IF = "if";
    public final static String THEN = "then";
    public final static String ELSE = "else";
    public final static String WHILE = "while";
    public final static String DO = "do";
    public final static String VAR = "var";
    public final static String CONST = "const";
    public final static String WRITELN = "writeln";
    public final static String READLN = "readln";
    public final static String EXIT = "exit";
    public final static String SQRT = "sqrt";
    public static final String SQR = "sqr";
    public static final String ROUND = "round";
    public static final String FLOOR = "floor";
    public static final String CEIL = "ceil";
    public static final String ABS = "abs";
    public static final String LN = "ln";
    public static final String COS = "cos";
    public static final String SIN = "sin";
    public static final String TAN = "tan";
    public static final String COT = "cot";
    public final static String DOUBLE = "double";
    public final static String STRING = "string";

    public static TokenType detectKeyWordType(String value) {
        switch (value) {
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
            case WRITELN:
                return TokenType.WRITELN;
            case READLN:
                return TokenType.READLN;
            case EXIT:
                return TokenType.EXIT;
            case SQRT:
                return TokenType.SQRT;
            case SQR:
                return TokenType.SQR;
            case ROUND:
                return TokenType.ROUND;
            case FLOOR:
                return TokenType.FLOOR;
            case CEIL:
                return TokenType.CEIL;
            case ABS:
                return TokenType.ABS;
            case LN:
                return TokenType.LN;
            case COS:
                return TokenType.COS;
            case SIN:
                return TokenType.SIN;
            case TAN:
                return TokenType.TAN;
            case COT:
                return TokenType.COT;
            case DOUBLE:
                return TokenType.DOUBLE;
            case STRING:
                return TokenType.STRING;
            default:
                return TokenType.IDENTIFIER;
        }
    }
}
