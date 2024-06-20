package compiler.syntactic.ast;

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
    void visit() {
    }
}
