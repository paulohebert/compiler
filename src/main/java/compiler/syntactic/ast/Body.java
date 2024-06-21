package compiler.syntactic.ast;

public class Body extends AST {
    private final Declarations declarations;
    private final CompoundCommand compoundCommand;

    public Body(Declarations declarations, CompoundCommand compoundCommand) {
        this.declarations = declarations;
        this.compoundCommand = compoundCommand;
    }

    public Declarations getDeclarations() {
        return declarations;
    }

    public CompoundCommand getCompoundCommand() {
        return compoundCommand;
    }

    @Override
    public void visit(Visitor v) {
        v.visitBody(this);
    }
}
