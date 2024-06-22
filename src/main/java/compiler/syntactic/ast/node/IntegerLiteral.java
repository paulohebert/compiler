package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class IntegerLiteral extends Literal<Integer> {
    public IntegerLiteral(Integer value) {
        super(value);
    }

    @Override
    public void visit(Visitor v) {
        v.visitIntegerLiteral(this);
    }
}
