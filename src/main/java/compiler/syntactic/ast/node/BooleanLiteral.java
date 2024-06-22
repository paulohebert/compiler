package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class BooleanLiteral extends Literal<Boolean> {
    public BooleanLiteral(Boolean value) {
        super(value);
    }

    @Override
    public void visit(Visitor v) {
        v.visitBooleanLiteral(this);
    }
}
