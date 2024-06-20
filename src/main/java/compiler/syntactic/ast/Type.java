package compiler.syntactic.ast;

import compiler.lexical.Token;

public class Type extends AST {
    private final Token type;

    public Type(Token type) {
        this.type = type;
    }

    public Token getType() {
        return type;
    }

    @Override
    void visit() {
    }
}
