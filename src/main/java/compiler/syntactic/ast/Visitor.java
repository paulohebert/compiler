package compiler.syntactic.ast;

import compiler.syntactic.ast.node.AddOperator;
import compiler.syntactic.ast.node.Assignment;
import compiler.syntactic.ast.node.Body;
import compiler.syntactic.ast.node.BooleanLiteral;
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

public interface Visitor {
    public void visitAddOperator(AddOperator addOperator);

    public void visitAssignment(Assignment assignment);

    public void visitBody(Body body);

    public void visitBooleanLiteral(BooleanLiteral booleanLiteral);

    public void visitCommandList(CommandList commandList);

    public void visitCompoundCommand(CompoundCommand compoundCommand);

    public void visitConditional(Conditional conditional);

    public void visitDeclarations(Declarations declarations);

    public void visitExpression(Expression expression);

    public void visitIdentifier(Identifier identifier);

    public void visitIntegerLiteral(IntegerLiteral integerLiteral);

    public void visitIterative(Iterative iterative);

    public void visitMultiplyOperator(MultiplyOperator multiplyOperator);

    public void visitProgram(Program program);

    public void visitRelationalOperator(RelationalOperator relationalOperator);

    public void visitSimpleExpression(SimpleExpression simpleExpression);

    public void visitTerm(Term term);

    public void visitType(Type type);

    public void visitVariableDeclaration(VariableDeclaration variableDeclaration);
}