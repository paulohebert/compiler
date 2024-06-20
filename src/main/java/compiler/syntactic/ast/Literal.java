package compiler.syntactic.ast;

import compiler.lexical.Token;

public class Literal extends Factor {
    private final Token value;

    public Literal(Token value) {
        this.value = value;
    }

    public Token getValue() {
        return value;
    }

    @Override
    void visit() {
    }
}
