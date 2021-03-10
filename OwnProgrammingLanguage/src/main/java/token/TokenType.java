package token;

public enum TokenType {
    PROGRAM("program"), PROCEDURE("procedure"), CALL("call"), ODD("odd"),
    DOT("."), SEMICOLON(";"), COMMA(","),
    WHITE_SPACE(" "), NEW_LINE("\n"), TABULATOR("\t"), CARRIAGE_RETURN("\r"),
    BEGIN("begin"), END("end"),
    IF("if"), THEN("then"), ELSE("else"),
    DO("do"), WHILE("while"),
    VAR("var"), CONST("const"), ASSIGNMENT("assignment"), IDENTIFIER("identifier"),
    NUMBER("number"), STRING("string"),
    PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"),
    EQUAL("="), NOTEQUAL("#"),
    GREATER(">"), GREATER_OR_EQUAL(">="),
    LESS("<"), LESS_OR_EQUAL("<="),
    EXCLAMATION_MARK("!"), QUESTION_MARK("?"),
    CLOSE_ROUND_BRACKET(")"), OPEN_ROUND_BRACKET("("),
    UNKNOWN("unknown"), END_OF_FILE(".");

//    PROGRAM, PROCEDURE, CALL, ODD,
//    DOT, SEMICOLON, COMMA,
//    WHITE_SPACE, NEW_LINE, TABULATOR, CARRIAGE_RETURN,
//    BEGIN, END,
//    IF, THEN, ELSE,
//    DO, WHILE,
//    VAR, CONST, ASSIGNMENT, IDENTIFIER,
//    NUMBER, STRING,
//    PLUS, MINUS, MULTIPLY, DIVIDE,
//    EQUAL, NOTEQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, EXCLAMATION_MARK, QUESTION_MARK,
//    CLOSE_BRACKET, OPEN_BRACKET, OPEN_BLOCK, CLOSE_BLOCK,
//    UNKNOWN, END_OF_FILE;

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
