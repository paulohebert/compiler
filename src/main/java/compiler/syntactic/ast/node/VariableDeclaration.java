package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class VariableDeclaration extends AST {
    private final Identifier identifier;
    private final Type type;

    public VariableDeclaration(Identifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void visit(Visitor v) {
        v.visitVariableDeclaration(this);
    }
}
