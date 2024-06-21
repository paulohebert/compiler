package compiler.syntactic.ast;

public abstract class AST {
    protected String name;

    abstract public void visit(Visitor v);
}
