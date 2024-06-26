package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class Assignment extends Command {
    private final Identifier identifier;
    private final Expression expression;

    public Assignment(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void visit(Visitor v) {
        v.visitAssignment(this);
    }
}
