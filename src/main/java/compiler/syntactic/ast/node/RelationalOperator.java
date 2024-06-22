package compiler.syntactic.ast.node;

import compiler.lexical.Token;
import compiler.syntactic.ast.Visitor;

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
