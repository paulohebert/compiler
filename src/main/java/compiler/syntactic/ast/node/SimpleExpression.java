package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class SimpleExpression extends AST {
    private final Term term;
    private AddOperator operator;
    private SimpleExpression simpleExpression;

    public SimpleExpression(Term term) {
        this.term = term;
    }

    public Term getTerm() {
        return term;
    }

    public AddOperator getOperator() {
        return operator;
    }

    public void setOperator(AddOperator operator) {
        this.operator = operator;
    }

    public SimpleExpression getSimpleExpression() {
        return simpleExpression;
    }

    public void setSimpleExpression(SimpleExpression simpleExpression) {
        this.simpleExpression = simpleExpression;
    }

    @Override
    public void visit(Visitor v) {
        v.visitSimpleExpression(this);
    }
}