package compiler.syntactic.ast;

public class IntegerLiteral extends Literal<Integer> {
    public IntegerLiteral(Integer value) {
        super(value);
    }

    @Override
    public void visit(Visitor v) {
        v.visitIntegerLiteral(this);
    }
}
