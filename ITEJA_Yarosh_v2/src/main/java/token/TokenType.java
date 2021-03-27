package token;

public enum TokenType {
    PROGRAM,
    DOT, SEMICOLON, COMMA, COLON, QUOTE,
    BEGIN, END,
    IF, THEN, ELSE,
    DO, WHILE, REPEAT, UNTIL, BREAK, FOR, TO, DOWNTO,
    VAR, CONST, ASSIGNMENT, IDENTIFIER, NUMBER, DOUBLE, STRING,
    PLUS, MINUS, MULTIPLY, DIVIDE, MOD, DIV,
    EQUAL, NOTEQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL,
    OR, AND, NOT,
    WRITELN, READLN, EXIT, SQRT, SQR, ROUND, FLOOR, CEIL, ABS, LN, COS, SIN, TAN, COT,
    CLOSE_ROUND_BRACKET, OPEN_ROUND_BRACKET, OPEN_CURVE_BRACKET, CLOSE_CURVE_BRACKET,
    UNKNOWN;
}
