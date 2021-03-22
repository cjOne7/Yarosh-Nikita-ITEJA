package token;

public enum TokenType {
    PROGRAM, PROCEDURE, CALL, ODD,
    DOT, SEMICOLON, COMMA, COLON, QUOTE,
    BEGIN, END,
    IF, THEN,
    DO, WHILE,
    VAR, CONST, ASSIGNMENT, IDENTIFIER, DOUBLE, STRING,
    NUMBER,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    EQUAL, NOTEQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL,
    WRITELN, READLN,
    CLOSE_ROUND_BRACKET, OPEN_ROUND_BRACKET,
    UNKNOWN, END_OF_FILE;
}
