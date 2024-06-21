package compiler.syntactic.ast;

import compiler.lexical.Token;

public class AddOperator extends AST {
    private final Token operator;

    public AddOperator(Token operator) {
        this.operator = operator;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public void visit(Visitor v) {
        v.visitAddOperator(this);
    }
}
