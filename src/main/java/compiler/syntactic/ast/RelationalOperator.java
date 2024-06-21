package compiler.syntactic.ast;

import compiler.lexical.Token;

public class RelationalOperator extends AST {
    private final Token operator;

    public RelationalOperator(Token operator) {
        this.operator = operator;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public void visit(Visitor v) {
        v.visitRelationalOperator(this);
    }
}
