package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public abstract class AST {
    protected String name;

    abstract public void visit(Visitor v);
}
