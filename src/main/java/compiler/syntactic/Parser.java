package compiler.syntactic;

import java.io.IOException;

import compiler.lexical.Scanner;
import compiler.lexical.Token;
import compiler.syntactic.ast.node.AddOperator;
import compiler.syntactic.ast.node.Assignment;
import compiler.syntactic.ast.node.Body;
import compiler.syntactic.ast.node.BooleanLiteral;
import compiler.syntactic.ast.node.Command;
import compiler.syntactic.ast.node.CommandList;
import compiler.syntactic.ast.node.CompoundCommand;
import compiler.syntactic.ast.node.Conditional;
import compiler.syntactic.ast.node.Declarations;
import compiler.syntactic.ast.node.Expression;
import compiler.syntactic.ast.node.Factor;
import compiler.syntactic.ast.node.Identifier;
import compiler.syntactic.ast.node.IntegerLiteral;
import compiler.syntactic.ast.node.Iterative;
import compiler.syntactic.ast.node.MultiplyOperator;
import compiler.syntactic.ast.node.Program;
import compiler.syntactic.ast.node.RelationalOperator;
import compiler.syntactic.ast.node.SimpleExpression;
import compiler.syntactic.ast.node.Term;
import compiler.syntactic.ast.node.Type;
import compiler.syntactic.ast.node.VariableDeclaration;

public class Parser {
    private Token currentToken;
    private Scanner scanner;

    /* Analisador Sintático */
    public Program parse(Scanner scanner) throws IOException {
        this.scanner = scanner; // Analisador Léxico
        this.currentToken = scanner.scan(); // Obtém o primeiro token

        // Obtém o Programa
        Program program = parseProgram();

        accept(Token.Kind.EOF); // Avalia se chegou no fim do arquivo

        return program; // Retorna o Programa
    }

    /* Avança para o próximo token */
    private void acceptIt() {
        try {
            currentToken = scanner.scan();
        } catch (IOException e) {
            throw new Error("Erro - Não foi possível ler o arquivo\n" + e.getMessage());
        }
    }

    /* Verifica se é o símbolo terminal esperado e avança para o próximo token */
    private void accept(Token.Kind expectedTokenKind) {
        if (currentToken.getKind() == expectedTokenKind) {
            acceptIt();
        } else {
            throw new Error(
                    "\nErro - Era esperado \"" + expectedTokenKind.name() + "\", mas foi encontrado um \""
                            + currentToken.getSpelling() + "\"\n");
        }
    }

    /* <program> ::= program <identifier> ; <body> . */
    private Program parseProgram() {
        accept(Token.Kind.PROGRAM); // Avalia se o token atual é um "program" e avança para o próximo token

        // Obtém o Identificador do Programa
        Identifier identifier = parseIdentifier();

        accept(Token.Kind.SEMICOLON); // Avalia se o token atual é um ";" e avança para o próximo token

        // Obtém o Corpo do Programa
        Body body = parseBody();

        accept(Token.Kind.DOT); // Avalia se o token atual é um "." e avança para o próximo token
        return new Program(identifier, body); // Retorna o programa
    }

    /* <identifier> ::= <letter> | <identifier> <letter> | <identifier> <digit> */
    private Identifier parseIdentifier() {
        Token idToken = currentToken; // Salva o token atual
        accept(Token.Kind.IDENTIFIER); // Avalia se o token atual é um Identificador e avança para o próximo token
        return new Identifier(idToken); // Retorna o Identificador
    }

    /* <body> ::= <declarations> <compound-command> */
    private Body parseBody() {
        // Obtém as Declarações da Variáveis
        Declarations declarations = parseDeclarations();

        // Obtém um Comando Composto
        CompoundCommand compoundCommand = parseCompoundCommand();

        return new Body(declarations, compoundCommand); // Retorna o Corpo do Programa
    }

    /*
     * <declarations> ::= <variable-declaration> ;
     * | <declarations> <variable-declaration> ;
     * | <empty>
     */
    private Declarations parseDeclarations() {
        Declarations declarations = new Declarations(); // Cria uma Lista de Declarações de Variáveis

        // Verifica se o token atual é uma declaração de variável
        while (currentToken.getKind() == Token.Kind.VAR) {
            // Obtém a Declaração da Variável
            VariableDeclaration variableDeclaration = parseVariableDeclaration();

            accept(Token.Kind.SEMICOLON); // Avalia se o token atual é um ";" e avança para o próximo token

            // Adiciona a declaração da variável à lista de declarações
            declarations.add(variableDeclaration);
        }

        return declarations; // Retorna as Declarações de Variáveis
    }

    /* <variable-declaration> ::= var <identifier> : <type> */
    private VariableDeclaration parseVariableDeclaration() {
        accept(Token.Kind.VAR); // Avalia se o token atual é um "var" e avança para o próximo token

        // Obtém o Identificador da Variável
        Identifier identifier = parseIdentifier();

        accept(Token.Kind.COLON); // Avalia se o token atual é um ":" e avança para o próximo token

        // Obtém o Tipo da Variável
        Type type = parseType();

        return new VariableDeclaration(identifier, type); // Retorna a Declaração da Variável
    }

    /* <type> ::= integer | boolean */
    private Type parseType() {
        // Avalia se o token atual é um "integer" ou "boolean"
        switch (currentToken.getKind()) {
            case INTEGER:
            case BOOLEAN:
                Type type = new Type(currentToken); // Cria um Tipo com o token atual
                acceptIt(); // Avança para o próximo token

                return type; // Retorna o Tipo da Variável
            default:
                throw new Error("Erro - Era esperado um tipo \"integer\" ou \"boolean\"\n");
        }
    }

    /* <compound-command> ::= begin <command-list> end */
    private CompoundCommand parseCompoundCommand() {
        accept(Token.Kind.BEGIN); // Avalia se o token atual é um "begin" e avança para o próximo token

        // Obtém uma Lista de Comandos
        CommandList commandList = parseCommandList();

        accept(Token.Kind.END); // Avalia se o token atual é um "end" e avança para o próximo token
        return new CompoundCommand(commandList); // Retorna o Comando Composto
    }

    /* <command-list> ::= <command> ; | <command-list> <command> ; | <empty> */
    private CommandList parseCommandList() {
        CommandList commandList = new CommandList(); // Cria uma Lista de Comandos

        Command command;

        // Avalia se o token atual é um comando
        while ((command = parseCommand()) != null) {
            commandList.add(command); // Adiciona o Comando na Lista
            accept(Token.Kind.SEMICOLON); // Avalia se o token atual é um ";" e avança para o próximo token
        }

        return commandList; // Retorna uma Lista de Comandos
    }

    /*
     * <command> ::= <assignment> | <conditional> | <iterative> | <compound-command>
     */
    private Command parseCommand() {
        // Avalia qual comando representa o token atual
        switch (currentToken.getKind()) {
            case BEGIN:
                // Retorna um Comando Composto
                return parseCompoundCommand();
            case IF:
                // Retorna um Comando Condicional
                return parseConditional();
            case WHILE:
                // Retorna um Comando Iterativo
                return parseIterative();
            case IDENTIFIER:
                // Retorna um Comando de Atribuição
                return parseAssignment();
            default:
                return null;
        }
    }

    /*
     * <conditional> ::= if <expression> then <command> ( else <command> | <empty> )
     */
    private Conditional parseConditional() {
        accept(Token.Kind.IF); // Avalia se o token atual é um "if" e avança para o próximo token

        // Obtém a Expressão
        Expression expression = parseExpression();

        accept(Token.Kind.THEN); // Avalia se o token atual é um "then" e avança para o próximo token

        // Obtém o Comando do If
        Command commandIf = parseCommand();

        // Avalia se o token atual é um "else"
        Command commandElse = null;
        if (currentToken.getKind() == Token.Kind.ELSE) {
            acceptIt(); // Avança para o próximo token

            // Obtém o Comando do Else
            commandElse = parseCommand();
        }

        return new Conditional(expression, commandIf, commandElse); // Retorna a Condicional
    }

    /* <iterative> ::= while <expression> do <command> */
    private Iterative parseIterative() {
        accept(Token.Kind.WHILE); // Avalia se o token atual é um "while" e avança para o próximo token

        // Obtém a Expressão
        Expression expression = parseExpression();

        accept(Token.Kind.DO); // Avalia se o token atual é um "do" e avança para o próximo token

        // Obtém o Comando
        Command command = parseCommand();

        return new Iterative(expression, command); // Retorna o Iterativo
    }

    /* <assignment> ::= <identifier> := <expression> */
    private Assignment parseAssignment() {
        // Obtém o Identificador da Variável
        Identifier identifier = parseIdentifier();

        accept(Token.Kind.BECOMES); // Avalia se o token atual é um ":=" e avança para o próximo token

        // Obtém a Expressão
        Expression expression = parseExpression();

        return new Assignment(identifier, expression); // Retorna a Atribuição
    }

    /*
     * <expression> ::= <simple-expression>
     * | <simple-expression> <relational-operator> <simple-expression>
     */
    private Expression parseExpression() {
        SimpleExpression simpleExpression = parseSimpleExpression(); // Obtém a Exp. Simples do lado esquerdo
        Expression expression = new Expression(simpleExpression); // Cria a Expressão

        /*
         * Avalia se o token atual é um operador relacional e
         * Se precisa adicionar a Exp. Simples do lado direito
         */
        RelationalOperator operator = parseRelationalOperator();
        if (operator != null) {
            expression.setOperator(operator); // Define o operador da Expressão

            simpleExpression = parseSimpleExpression(); // Obtém a Exp. Simples do lado direito
            expression.setSimpleExpressionRight(simpleExpression); // Define a Exp. Simple do lado direito
        }

        return expression; // Retorna a Expressão
    }

    /*
     * <simple-expression> ::= <simple-expression> <add-operator> <term> | <term>
     */
    private SimpleExpression parseSimpleExpression() {
        Term term = parseTerm(); // Obtém o Termo
        SimpleExpression rootSimpleExpression = new SimpleExpression(term); // Cria a Exp. Simples Raiz
        SimpleExpression simpleExpression = rootSimpleExpression; // Salva a raiz como nó atual

        AddOperator operator;

        // Avalia se o token atual é um operador aditivo
        while ((operator = parseAddOperator()) != null) {
            simpleExpression.setOperator(operator); // Define o operador do nó atual

            term = parseTerm(); // Obtém o Termo
            // Cria uma Exp. Simples para ser o nó filho
            SimpleExpression nextSimpleExpression = new SimpleExpression(term);
            simpleExpression.setSimpleExpression(nextSimpleExpression); // Adiciona o novo nó como filho do nó atual

            simpleExpression = nextSimpleExpression; // Atualiza para o próximo nó da árvore
        }

        return rootSimpleExpression; // Retorna a Exp. Simples Raiz
    }

    /* <term> ::= <term> <multiply-operator> <fator> | <fator> */
    private Term parseTerm() {
        Factor factor = parseFactor(); // Obtém o Fator
        Term rootTerm = new Term(factor); // Cria o Termo Raiz
        Term term = rootTerm; // Salva a raiz como nó atual

        MultiplyOperator operator;

        // Avalia se o token atual é um operador multiplicativo
        while ((operator = parseMultiplyOperator()) != null) {
            term.setOperator(operator); // Define o operador do nó atual

            factor = parseFactor(); // Obtém o Fator
            Term nextTerm = new Term(factor); // Cria um Termo para ser o nó filho
            term.setTerm(nextTerm); // Adiciona o novo nó como filho do nó atual

            term = nextTerm; // Atualiza para o próximo nó da árvore
        }

        return rootTerm; // Retorna o Termo Raiz
    }

    /* <factor> ::= <identifier> | <literal> | "(" <expression> ")" */
    private Factor parseFactor() {
        /*
         * Avalia se o token atual é um identificador, literal ou
         * se precisa analisar uma expressão
         */
        switch (currentToken.getKind()) {
            case IDENTIFIER:
                // Retorna o Identificador da Variável
                return parseIdentifier();
            case LEFT_PARENTHESIS:
                acceptIt(); // Avança para o próximo token

                // Obtém uma Expressão
                Expression expression = parseExpression();

                accept(Token.Kind.RIGHT_PARENTHESIS); // Avalia se o token atual é um ")" e avança para o próximo token

                return expression; // Retorna a Expressão

            /* <literal> ::= <boolean-literal> | <integer-literal> */
            case TRUE:
            case FALSE:
                // Retorna o Literal Booleano
                return parseBooleanLiteral();
            case INTEGER_LITERAL:
                // Retorna o Literal Inteiro
                return parseIntegerLiteral();
            default:
                throw new Error("Erro - Nenhum fator foi encontrado\n");
        }
    }

    /* <integer-literal> ::= <digit> | <integer-literal> <digit> */
    private IntegerLiteral parseIntegerLiteral() {
        Integer integer = Integer.valueOf(currentToken.getSpelling()); // Converte o Spelling do Token em um Integer
        acceptIt(); // Avança para o próximo token

        return new IntegerLiteral(integer); // Retorna um Literal Inteiro
    }

    /* <boolean-literal> ::= true | false */
    private BooleanLiteral parseBooleanLiteral() {
        Boolean bool = Boolean.valueOf(currentToken.getSpelling()); // Converte o Spelling do Token em um Boolean
        acceptIt(); // Avança para o próximo token

        return new BooleanLiteral(bool); // Retorna um Literal Booleano
    }

    /* <relational-operator> ::= < | > | = */
    private RelationalOperator parseRelationalOperator() {
        // Avalia se o token atual é um "<", ">" ou "="
        switch (currentToken.getKind()) {
            case LESS:
            case GREATER:
            case EQUAL:
                // Cria um Operador com o token atual
                RelationalOperator operator = new RelationalOperator(currentToken);
                acceptIt(); // Avança para o próximo token

                return operator; // Retorna o Operador
            default:
                return null;
        }
    }

    /* <add-operator> ::= + | - | or */
    private AddOperator parseAddOperator() {
        // Avalia se o token atual é um "+", "-" ou "or"
        switch (currentToken.getKind()) {
            case PLUS:
            case MINUS:
            case OR:
                AddOperator operator = new AddOperator(currentToken); // Cria um Operador com o token atual
                acceptIt(); // Avança para o próximo token

                return operator; // Retorna o Operador
            default:
                return null;
        }
    }

    /* <multiply-operator> ::= * | / | and */
    private MultiplyOperator parseMultiplyOperator() {
        // Avalia se o token atual é um "*", "/" ou "and"
        switch (currentToken.getKind()) {
            case ASTERISK:
            case SLASH:
            case AND:
                MultiplyOperator operator = new MultiplyOperator(currentToken); // Cria um Operador com o token atual
                acceptIt(); // Avança para o próximo token

                return operator; // Retorna o Operador
            default:
                return null;
        }
    }
}
