package compiler.syntactic.ast;

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