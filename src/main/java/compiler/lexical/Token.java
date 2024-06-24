package compiler.lexical;

import compiler.cli.Config;

public class Token {
    public static enum Kind {
        EOF,
        ERROR,
        IDENTIFIER,
        INTEGER_LITERAL,
        TRUE,
        FALSE,
        PROGRAM,
        BEGIN,
        END,
        VAR,
        INTEGER,
        BOOLEAN,
        IF,
        THEN,
        ELSE,
        BECOMES,
        PLUS,
        MINUS,
        OR,
        ASTERISK,
        SLASH,
        AND,
        EQUAL,
        GREATER,
        LESS,
        DO,
        WHILE,
        SEMICOLON,
        COLON,
        COMMA,
        DOT,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS;
    }

    private final Kind kind;
    private final String spelling;
    private final int line;
    private final int column;

    public Token(Kind kind, String spelling, int line, int column) {
        this.kind = kind;
        this.spelling = spelling;
        this.line = line;
        this.column = column;
    }

    public static Kind fromString(String spelling) {
        switch (spelling) {
            case "program":
                return Kind.PROGRAM;
            case "begin":
                return Kind.BEGIN;
            case "end":
                return Kind.END;
            case "var":
                return Kind.VAR;
            case "do":
                return Kind.DO;
            case "while":
                return Kind.WHILE;
            case "if":
                return Kind.IF;
            case "then":
                return Kind.THEN;
            case "else":
                return Kind.ELSE;
            case "and":
                return Kind.AND;
            case "or":
                return Kind.OR;
            case "true":
                return Kind.TRUE;
            case "false":
                return Kind.FALSE;
            case "integer":
                return Kind.INTEGER;
            case "boolean":
                return Kind.BOOLEAN;
            default:
                return Kind.IDENTIFIER;
        }
    }

    public Kind getKind() {
        return kind;
    }

    public String getSpelling() {
        return spelling;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        String msg = Config.isColor()
                ? "\033[44;4;1;53m  Token  \033[0m \033[33m%s\033[0m (\033[36m%s\033[0m) - [Ln %d, Col %d]\n"
                : "|  Token  | %s (%s) - [Ln %d, Col %d]\n";

        return String.format(
                msg,
                this.kind.name(),
                this.spelling,
                this.line + 1,
                this.column + 1);
    }
}
