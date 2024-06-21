package compiler.syntactic.ast;

import compiler.lexical.Token;

public class Identifier extends Factor {
    private final String idName;

    public Identifier(Token token) {
        this.idName = token.getSpelling();
    }

    public String getIdName() {
        return idName;
    }

    @Override
    public void visit(Visitor v) {
        v.visitIdentifier(this);
    }
}
