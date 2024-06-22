package compiler.syntactic.ast.node;

import compiler.lexical.Token;
import compiler.syntactic.ast.Visitor;

public class Type extends AST {
    private final Token type;

    public Type(Token type) {
        this.type = type;
    }

    public Token getType() {
        return type;
    }

    @Override
    public void visit(Visitor v) {
        v.visitType(this);
    }
}
