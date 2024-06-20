package compiler.syntactic.ast;

public class Term extends AST {
    private final Factor factor;
    private MultiplyOperator operator;
    private Term term;

    public Term(Factor factor) {
        this.factor = factor;
    }

    public Factor getFactor() {
        return factor;
    }

    public MultiplyOperator getOperator() {
        return operator;
    }

    public void setOperator(MultiplyOperator operator) {
        this.operator = operator;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    void visit() {
    }
}