package compiler.syntactic.ast;

import compiler.lexical.Token;

public class MultiplyOperator extends AST {
    private final Token operator;

    public MultiplyOperator(Token operator) {
        this.operator = operator;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public void visit(Visitor v) {
        v.visitMultiplyOperator(this);
    }
}
