package compiler.codeGenerator;

import java.io.FileWriter;
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
import compiler.syntactic.ast.node.AST;
import compiler.syntactic.ast.Visitor;

public class Coder implements Visitor {

    private FileWriter writer;
    private int labelCounter;

    public Coder(String outputFileName) throws IOException {
        this.writer = new FileWriter(outputFileName);
        this.labelCounter = 0;
    }

    private String generateLabel() {
        return "L" + labelCounter++;
    }

    private void writeLine(String line) {
        try {
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitAddOperator(AddOperator addOperator) {
        writeLine("CALL addOperator");
    }

    @Override
    public void visitAssignment(Assignment assignment) {
        assignment.getExpression().visit(this);
        writeLine("STORE " + assignment.getIdentifier());
    }

    @Override
    public void visitBody(Body body) {
        for (VariableDeclaration command : body.getDeclarations().getDeclarations()) {
            command.visit(this);
        }
    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral booleanLiteral) {
        writeLine("PUSH " + (booleanLiteral.getValue() ? "true" : "false"));
    }

    @Override
    public void visitCommandList(CommandList commandList) {
        for (Command command : commandList.getCommandList()) {
            command.visit(this);
        }
    }

    @Override
    public void visitCompoundCommand(CompoundCommand compoundCommand) {
        compoundCommand.getCommandList().visit(this);
    }

    @Override
    public void visitConditional(Conditional conditional) {
        String elseLabel = generateLabel();
        String endLabel = generateLabel();

        conditional.getCommandIf().visit(this);
        writeLine("JUMPIF false " + elseLabel);
        conditional.getExpression().visit(this);
        writeLine("JUMP " + endLabel);
        writeLine(elseLabel + ":");
        if (conditional.getCommandElse()!= null) {
            conditional.getCommandElse().visit(this);
        }
        writeLine(endLabel + ":");
    }

    @Override
    public void visitDeclarations(Declarations declarations) {
        for (VariableDeclaration declaration : declarations.getDeclarations()) {
            declaration.visit(this);
        }
    }

    @Override
    public void visitExpression(Expression expression) {
        expression.getSimpleExpressionRight().getTerm().visit(this);
        expression.getSimpleExpressionLeft().getTerm().visit(this);
        expression.getSimpleExpressionRight().visit(this);
        expression.getSimpleExpressionLeft().visit(this);
    }

    @Override
    public void visitIdentifier(Identifier identifier) {
        writeLine("LOAD " + identifier.getIdName());
    }

    @Override
    public void visitIntegerLiteral(IntegerLiteral integerLiteral) {
        writeLine("PUSH " + integerLiteral.getValue());
    }

    @Override
    public void visitIterative(Iterative iterative) {
        String startLabel = generateLabel();
        String endLabel = generateLabel();

        writeLine(startLabel + ":");
        iterative.getExpression().visit(this);
        writeLine("JUMPIF false " + endLabel);
        iterative.getCommand().visit(this);
        writeLine("JUMP " + startLabel);
        writeLine(endLabel + ":");
    }

    @Override
    public void visitMultiplyOperator(MultiplyOperator multiplyOperator) {
        writeLine("CALL multiplyOperator");
    }

    @Override
    public void visitProgram(Program program) {
        program.getBody().visit(this);
        writeLine("HALT");
    }

    @Override
    public void visitRelationalOperator(RelationalOperator relationalOperator) {
        writeLine("CALL relationalOperator " + relationalOperator.getOperator());
    }

    @Override
    public void visitSimpleExpression(SimpleExpression simpleExpression) {
        simpleExpression.getTerm().visit(this);
        simpleExpression.getOperator().visit(this);
       
    }

    @Override
    public void visitTerm(Term term) {
        term.getFactor().visit(this);
        term.getOperator().visit(this);
    }

    @Override
    public void visitType(Type type) {
        // No action needed for type
    }

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
