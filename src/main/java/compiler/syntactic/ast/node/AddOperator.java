package compiler.syntactic.ast.node;

import compiler.lexical.Token;
import compiler.syntactic.ast.Visitor;

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
