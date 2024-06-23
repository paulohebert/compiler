package compiler.codeGenerator;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import compiler.syntactic.ast.Visitor;
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

public class Coder implements Visitor {

    private BufferedOutputStream writer;
    private int labelCounter;

    public Coder(String outputFileName) throws FileNotFoundException {
        this.writer = new BufferedOutputStream(new FileOutputStream(outputFileName));
        this.labelCounter = 0;
    }

    public void encode(Program program) {
        program.visit(this);
        close();
    }

    private String generateLabel() {
        return "L" + labelCounter++;
    }

    private void writeLine(String line) {
        try {
            writer.write((line + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* <add-operator> ::= + | - | or */
    @Override
    public void visitAddOperator(AddOperator addOperator) {
        switch (addOperator.getOperator().getKind()) {
            case PLUS:
                writeLine("CALL add");
                break;
            case MINUS:
                writeLine("CALL sub");
                break;
            case OR:
                writeLine("CALL or");
                break;
            default:
        }
    }

    /* <assignment> ::= <identifier> := <expression> */
    @Override
    public void visitAssignment(Assignment assignment) {
        assignment.getExpression().visit(this);
        writeLine("STORE " + assignment.getIdentifier().getIdName());
    }

    /* <body> ::= <declarations> <compound-command> */
    @Override
    public void visitBody(Body body) {
        body.getDeclarations().visit(this);
        body.getCompoundCommand().visit(this);
    }

    /* <boolean-literal> ::= true | false */
    @Override
    public void visitBooleanLiteral(BooleanLiteral booleanLiteral) {
        writeLine("LOADL " + (booleanLiteral.getValue() ? "1" : "0"));
    }

    /* <command-list> ::= <command> ; | <command-list> <command> ; | <empty> */
    @Override
    public void visitCommandList(CommandList commandList) {
        for (Command command : commandList.getCommandList()) {
            command.visit(this);
        }
    }

    /* <compound-command> ::= begin <command-list> end */
    @Override
    public void visitCompoundCommand(CompoundCommand compoundCommand) {
        compoundCommand.getCommandList().visit(this);
    }

    /*
     * <conditional> ::= if <expression> then <command> ( else <command> | <empty> )
     */
    @Override
    public void visitConditional(Conditional conditional) {
        String elseLabel = generateLabel();
        String endLabel = generateLabel();

        conditional.getExpression().visit(this);
        writeLine("JUMPIF (0) " + elseLabel);
        conditional.getCommandIf().visit(this);
        writeLine("JUMP " + endLabel);
        writeLine(elseLabel + ":");
        if (conditional.getCommandElse() != null) {
            conditional.getCommandElse().visit(this);
        }
        writeLine(endLabel + ":");
    }

    /*
     * <declarations> ::= <variable-declaration> ;
     * | <declarations> <variable-declaration> ;
     * | <empty>
     */
    @Override
    public void visitDeclarations(Declarations declarations) {
        for (VariableDeclaration declaration : declarations.getDeclarations()) {
            declaration.visit(this);
        }
    }

    /*
     * <expression> ::= <simple-expression>
     * | <simple-expression> <relational-operator> <simple-expression>
     */
    @Override
    public void visitExpression(Expression expression) {
        expression.getSimpleExpressionLeft().visit(this);
        if (expression.getOperator() != null) {
            expression.getSimpleExpressionRight().visit(this);
            expression.getOperator().visit(this);
        }
    }

    /* <identifier> ::= <letter> | <identifier> <letter> | <identifier> <digit> */
    @Override
    public void visitIdentifier(Identifier identifier) {
        writeLine("LOAD " + identifier.getIdName());
    }

    /* <integer-literal> ::= <digit> | <integer-literal> <digit> */
    @Override
    public void visitIntegerLiteral(IntegerLiteral integerLiteral) {
        writeLine("LOADL " + integerLiteral.getValue());
    }

    /* <iterative> ::= while <expression> do <command> */
    @Override
    public void visitIterative(Iterative iterative) {
        String startLabel = generateLabel();
        String endLabel = generateLabel();

        writeLine(startLabel + ":");
        iterative.getExpression().visit(this);
        writeLine("JUMPIF (0) " + endLabel);
        iterative.getCommand().visit(this);
        writeLine("JUMP " + startLabel);
        writeLine(endLabel + ":");
    }

    /* <multiply-operator> ::= * | / | and */
    @Override
    public void visitMultiplyOperator(MultiplyOperator multiplyOperator) {
        switch (multiplyOperator.getOperator().getKind()) {
            case ASTERISK:
                writeLine("CALL mult");
                break;
            case SLASH:
                writeLine("CALL div");
                break;
            case AND:
                writeLine("CALL and");
                break;
            default:
        }
    }

    /* <program> ::= program <identifier> ; <body> . */
    @Override
    public void visitProgram(Program program) {
        program.getBody().visit(this);
        writeLine("HALT");
    }

    /* <relational-operator> ::= < | > | = */
    @Override
    public void visitRelationalOperator(RelationalOperator relationalOperator) {
        switch (relationalOperator.getOperator().getKind()) {
            case LESS:
                writeLine("CALL lt");
                break;
            case GREATER:
                writeLine("CALL gt");
                break;
            case EQUAL:
                writeLine("CALL eq");
                break;
            default:
        }
    }

    /*
     * <simple-expression> ::= <simple-expression> <add-operator> <term> | <term>
     */
    @Override
    public void visitSimpleExpression(SimpleExpression simpleExpression) {
        simpleExpression.getTerm().visit(this);
        if (simpleExpression.getOperator() != null) {
            simpleExpression.getSimpleExpression().visit(this);
            simpleExpression.getOperator().visit(this);
        }
    }

    /* <term> ::= <term> <multiply-operator> <fator> | <fator> */
    @Override
    public void visitTerm(Term term) {
        term.getFactor().visit(this);
        if (term.getOperator() != null) {
            term.getTerm().visit(this);
            term.getOperator().visit(this);
        }
    }

    /* <type> ::= integer | boolean */
    @Override
    public void visitType(Type type) {
        // No action needed for type
    }

    /* <variable-declaration> ::= var <identifier> : <type> */
    @Override
    public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
        writeLine("DECLARE " + variableDeclaration.getIdentifier().getIdName());
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
