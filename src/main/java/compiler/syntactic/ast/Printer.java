package compiler.syntactic.ast;

import java.io.IOException;
import java.io.OutputStream;

public class Printer implements Visitor {
    private int indentLevel = 0;
    private OutputStream writer;

    public Printer() {
        this(System.out);
    }

    public Printer(OutputStream writer) {
        this.writer = writer;
    }

    /* Imprime a AST */
    public void print(Program program) {
        System.out.println("---> Iniciando impressao da arvore");
        program.visit(this);
    }

    /* Escreve na Saída Padrão ou em um Arquivo */
    private void write(byte[] bytes) {
        try {
            writer.write(bytes);
        } catch (IOException e) {
            System.out.println("Erro - Não foi possível imprimir a árvore");
        }
    }

    /* Cria uma indentação na linha */
    private void printIndent() {
        write("|    ".repeat(indentLevel).getBytes());
    }

    /* Imprime o Nó da Árvore com seu Valor */
    private void output(String node, Object value) {
        printIndent();
        String txt = node + ": \033[0;93m" + value + "\033[0m\n";
        write(txt.getBytes());
    }

    /* Imprime o Nó da Árvore */
    private void output(String node) {
        printIndent();
        String txt = "\033[0;96m" + node + "\033[0m:\n";
        write(txt.getBytes());
    }

    /* <add-operator> ::= + | - | or */
    @Override
    public void visitAddOperator(AddOperator addOperator) {
        output("<add-operator>", addOperator.getOperator().getSpelling()); // Exibe o Nó Atual
    }

    /* <assignment> ::= <identifier> := <expression> */
    @Override
    public void visitAssignment(Assignment assignment) {
        output("<assignment>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        assignment.getIdentifier().visit(this);
        assignment.getExpression().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <body> ::= <declarations> <compound-command> */
    @Override
    public void visitBody(Body body) {
        output("<body>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        body.getDeclarations().visit(this);
        body.getCompoundCommand().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <boolean-literal> ::= true | false */
    @Override
    public void visitBooleanLiteral(BooleanLiteral booleanLiteral) {
        output("<boolean-literal>", booleanLiteral.getValue()); // Exibe o Nó Atual
    }

    /* <command-list> ::= <command> ; | <command-list> <command> ; | <empty> */
    @Override
    public void visitCommandList(CommandList commandList) {
        output("<command-list>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        for (Command command : commandList.getCommandList()) {
            command.visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <compound-command> ::= begin <command-list> end */
    @Override
    public void visitCompoundCommand(CompoundCommand compoundCommand) {
        output("<compound-command>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar o Nó Filho

        /* Visita o nó filho */
        compoundCommand.getCommandList().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /*
     * <conditional> ::= if <expression> then <command> ( else <command> | <empty> )
     */
    @Override
    public void visitConditional(Conditional conditional) {
        output("<conditional>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        conditional.getExpression().visit(this);
        conditional.getCommandIf().visit(this);
        if (conditional.getCommandElse() != null) {
            conditional.getCommandElse().visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /*
     * <declarations> ::= <variable-declaration> ;
     * | <declarations> <variable-declaration> ;
     * | <empty>
     */
    @Override
    public void visitDeclarations(Declarations declarations) {
        output("<declarations>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        for (VariableDeclaration declaration : declarations.getDeclarations()) {
            declaration.visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /*
     * <expression> ::= <simple-expression>
     * | <simple-expression> <relational-operator> <simple-expression>
     */
    @Override
    public void visitExpression(Expression expression) {
        output("<expression>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        expression.getSimpleExpressionLeft().visit(this);
        if (expression.getOperator() != null) {
            expression.getOperator().visit(this);
            expression.getSimpleExpressionRight().visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <identifier> ::= <letter> | <identifier> <letter> | <identifier> <digit> */
    @Override
    public void visitIdentifier(Identifier identifier) {
        output("<identifier>", identifier.getIdName()); // Exibe o Nó Atual
    }

    /* <integer-literal> ::= <digit> | <integer-literal> <digit> */
    @Override
    public void visitIntegerLiteral(IntegerLiteral integerLiteral) {
        output("<integer-literal>", integerLiteral.getValue()); // Exibe o Nó Atual
    }

    /* <iterative> ::= while <expression> do <command> */
    @Override
    public void visitIterative(Iterative iterative) {
        output("<iterative>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        iterative.getExpression().visit(this);
        iterative.getCommand().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <multiply-operator> ::= * | / | and */
    @Override
    public void visitMultiplyOperator(MultiplyOperator multiplyOperator) {
        output("<multiply-operator>", multiplyOperator.getOperator().getSpelling()); // Exibe o Nó Atual
    }

    /* <program> ::= program <identifier> ; <body> . */
    @Override
    public void visitProgram(Program program) {
        output("<program>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        program.getIdentifier().visit(this);
        program.getBody().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <relational-operator> ::= < | > | = */
    @Override
    public void visitRelationalOperator(RelationalOperator relationalOperator) {
        output("<relational-operator>", relationalOperator.getOperator().getSpelling()); // Exibe o Nó Atual
    }

    /*
     * <simple-expression> ::= <simple-expression> <add-operator> <term> | <term>
     */
    @Override
    public void visitSimpleExpression(SimpleExpression simpleExpression) {
        output("<simple-expression>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        simpleExpression.getTerm().visit(this);
        if (simpleExpression.getOperator() != null) {
            simpleExpression.getOperator().visit(this);
            simpleExpression.getSimpleExpression().visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <term> ::= <term> <multiply-operator> <fator> | <fator> */
    @Override
    public void visitTerm(Term term) {
        output("<term>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        term.getFactor().visit(this);
        if (term.getOperator() != null) {
            term.getOperator().visit(this);
            term.getTerm().visit(this);
        }

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }

    /* <type> ::= integer | boolean */
    @Override
    public void visitType(Type type) {
        output("<type>", type.getType().getSpelling()); // Exibe o Nó Atual
    }

    /* <variable-declaration> ::= var <identifier> : <type> */
    @Override
    public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
        output("<variable-declaration>"); // Exibe o Nó Atual

        indentLevel++; // Aumenta a Indentação para mostrar os Nós Filhos

        /* Visita os nós filhos */
        variableDeclaration.getIdentifier().visit(this);
        variableDeclaration.getType().visit(this);

        indentLevel--; // Diminui a Indentação para mostrar os Nós Irmãos
    }
}
