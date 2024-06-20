package compiler.syntactic.ast;

public class Program extends AST {
    private final Identifier identifier;
    private final Body body;

    public Program(Identifier identifier, Body body) {
        this.identifier = identifier;
        this.body = body;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Body getBody() {
        return body;
    }

    @Override
    void visit() {
    }

}
