package compiler.syntactic;

import java.io.IOException;

import compiler.lexical.Scanner;
import compiler.lexical.Token;

public class Parser {
    private Token currentToken;
    private Scanner scanner;

    public boolean parse(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.currentToken = scanner.scan();
        parseProgram();
        return true;
    }

    private void acceptIt() {
        try {
            currentToken = scanner.scan();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Erro - Não foi possível ler o arquivo\n");
        }
    }

    private void accept(Token expectedToken) {
        // Verifica se é o terminal esperado
        if (currentToken == expectedToken) {
            acceptIt();
        } else {
            throw new Error("Erro - Era esperado \"" + expectedToken.name() + "\", mas foi encontrado \""
                    + currentToken.name() + "\"\n");
        }
    }

    /* <program> ::= program <identifier> ; <body> . */
    private void parseProgram() {
        accept(Token.PROGRAM);
        parseIdentifier();
        accept(Token.SEMICOLON);
        parseBody();
        accept(Token.DOT);
        accept(Token.EOF);
    }

    /* <body> ::= <declarations> <compound-command> */
    private void parseBody() {
        parseDeclarations();
        parseCompoundCommand();
    }

    /*
     * <declarations> ::= <variable-declaration> ;
     * | <declarations> <variable-declaration> ;
     * | <empty>
     */
    private void parseDeclarations() {
        while (currentToken == Token.VAR) {
            parseVariableDeclaration();
            accept(Token.SEMICOLON);
        }
    }

    /* <variable-declaration> ::= var <identifier> : <type> */
    private void parseVariableDeclaration() {
        accept(Token.VAR);
        accept(Token.IDENTIFIER);
        accept(Token.COLON);
        parseType();
    }

    /* <type> ::= integer | boolean */
    private void parseType() {
        switch (currentToken) {
            case INTEGER:
            case BOOLEAN:
                acceptIt();
                break;
            default:
                throw new Error("Erro - Era esperado um tipo \"integer\" ou \"boolean\"\n");
        }
    }

    /* <compound-command> ::= begin <command-list> end */
    private void parseCompoundCommand() {
        accept(Token.BEGIN);
        parseCommandList();
        accept(Token.END);
    }

    /* <command-list> ::= <command> ; | <command-list> <command> ; | <empty> */
    private void parseCommandList() {
        while (parseCommand()) {
            accept(Token.SEMICOLON);
        }
    }

    /*
     * <command> ::= <assignment> | <conditional> | <iterative> | <compound-command>
     */
    private boolean parseCommand() {
        switch (currentToken) {
            case BEGIN:
                parseCompoundCommand();
                break;
            case IF:
                parseConditional();
                break;
            case WHILE:
                parseIterative();
                break;
            case IDENTIFIER:
                parseAssignment();
                break;
            default:
                return false;
        }
        return true;
    }

    /*
     * <conditional> ::= if <expression> then <command> ( else <command> | <empty> )
     */
    private void parseConditional() {
        accept(Token.IF);
        parseExpression();
        accept(Token.THEN);
        parseCommand();
        if (currentToken == Token.ELSE) {
            acceptIt();
            parseCommand();
        }
    }

    /* <iterative> ::= while <expression> do <command> */
    private void parseIterative() {
        accept(Token.WHILE);
        parseExpression();
        accept(Token.DO);
        parseCommand();
    }

    /* <assignment> ::= <identifier> := <expression> */
    private void parseAssignment() {
        accept(Token.IDENTIFIER);
        accept(Token.BECOMES);
        parseExpression();
    }

    /*
     * <expression> ::= <simple-expression>
     * | <simple-expression> <relational-operator> <simple-expression>
     */
    private void parseExpression() {
        parseSimpleExpression();
        if (parseRelationalOperator()) {
            parseSimpleExpression();
        }
    }

    /*
     * <simple-expression> ::= <simple-expression> <add-operator> <term> | <term>
     */
    private void parseSimpleExpression() {
        parseTerm();
        while (parseAddOperator()) {
            parseTerm();
        }
    }

    /* <term> ::= <term> <multiply-operator> <fator> | <fator> */
    private void parseTerm() {
        parseFactor();
        while (parseMultiplyOperator()) {
            parseFactor();
        }
    }

    /* <factor> ::= <identifier> | <literal> | "(" <expression> ")" */
    private void parseFactor() {
        switch (currentToken) {
            case IDENTIFIER:
                acceptIt();
                break;
            case LEFT_PARENTHESIS:
                acceptIt();
                parseExpression();
                accept(Token.RIGHT_PARENTHESIS);
                break;
            default:
                parseLiteral();
                break;
        }
    }

    /* <literal> ::= true | false | <integer-literal> */
    private void parseLiteral() {
        switch (currentToken) {
            case TRUE:
            case FALSE:
            case INTEGER_LITERAL:
                acceptIt();
                break;
            default:
                throw new Error("Erro - Era esperado algum dos literais como: \"true\", \"false\" ou inteiro\n");
        }
    }

    /* <relational-operator> ::= < | > | = */
    private boolean parseRelationalOperator() {
        switch (currentToken) {
            case LESS:
            case GREATER:
            case EQUAL:
                acceptIt();
                return true;
            default:
                return false;
        }
    }

    /* <add-operator> ::= + | - | or */
    private boolean parseAddOperator() {
        switch (currentToken) {
            case PLUS:
            case MINUS:
            case OR:
                acceptIt();
                return true;
            default:
                return false;
        }
    }

    /* <multiply-operator> ::= * | / | and */
    private boolean parseMultiplyOperator() {
        switch (currentToken) {
            case ASTERISK:
            case SLASH:
            case AND:
                acceptIt();
                return true;
            default:
                return false;
        }
    }

    private void parseIdentifier() {
        accept(Token.IDENTIFIER);
    }
}
