package compiler.syntactic.ast;

public abstract class AST {
    protected String name;

    abstract void visit();
}
