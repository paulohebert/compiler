package compiler.lexical;

public enum Token {
    EOF("<eof>"),
    ERROR("<error>"),

    /* <identifier> ::= <letter> | <identifier> <letter> | <identifier> <digit> */
    IDENTIFIER("<identifier>"),

    /* <integer-literal> ::= <digit> | <integer-literal> <digit> */
    INTEGER_LITERAL("<integer-literal>"),

    /*
     * <float-literal> ::= <integer-literal> . <integer-literal>
     * | <integer-literal> .
     * | . <integer-literal>
     */
    FLOAT_LITERAL("<float-literal>"),

    /* <boolean-literal> */
    TRUE("true"),
    FALSE("false"),

    /* <program> ::= program <identifier> ; <body> . */
    PROGRAM("program"),

    /* <compound statement> ::= begin <statement>{ ; <statement> } end */
    BEGIN("begin"),
    END("end"),

    /* <variable declaration> ::= var <identifier> : <type> */
    VAR("var"),
    INTEGER("integer"),
    BOOLEAN("boolean"),

    /* <if statement> ::= if <exp> then <statement> (else <statement> | <empty>) */
    IF("if"),
    THEN("then"),
    ELSE("else"),

    /* <assignment statement> ::= <variable> := <expression> */
    BECOMES(":="),

    /* <adding operator> ::= + | - | or */
    PLUS("+"),
    MINUS("-"),
    OR("or"),

    /* <multiplying operator> ::= * | / | and */
    ASTERISK("*"),
    SLASH("/"),
    AND("and"),

    /* <relational operator> ::= < | > | = */
    EQUAL("="),
    GREATER(">"),
    LESS("<"),

    /* <while statement> ::= while <expression> do <statement> */
    DO("do"),
    WHILE("while"),

    /* <special symbol> */
    SEMICOLON(";"),
    COLON(":"),
    COMMA(","),
    DOT("."),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")");

    private final String spelling;
    private int line;
    private int column;

    private Token(String spelling) {
        this.spelling = spelling;
        this.line = this.column = -1;
    }

    public static Token fromString(String spelling) {
        for (Token token : Token.values()) {
            if (token.spelling.equals(spelling)) {
                return token;
            }
        }
        return Token.IDENTIFIER;
    }

    public String getSpelling() {
        return spelling;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format(
                "\033[44;4;1;53m  Token  \033[0m \033[33m%s\033[0m (\033[36m%s\033[0m) - [%d, %d]",
                this.name(),
                this.spelling,
                this.line + 1,
                this.column + 1);
    }
}
