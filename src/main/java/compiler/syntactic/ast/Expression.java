package compiler.syntactic.ast;

public class Expression extends Factor {
    private final SimpleExpression simpleExpressionLeft;
    private SimpleExpression simpleExpressionRight;
    private RelationalOperator operator;

    public Expression(SimpleExpression simpleExpressionLeft) {
        this.simpleExpressionLeft = simpleExpressionLeft;
    }

    public SimpleExpression getSimpleExpressionLeft() {
        return simpleExpressionLeft;
    }

    public RelationalOperator getOperator() {
        return operator;
    }

    public void setOperator(RelationalOperator operator) {
        this.operator = operator;
    }

    public SimpleExpression getSimpleExpressionRight() {
        return simpleExpressionRight;
    }

    public void setSimpleExpressionRight(SimpleExpression simpleExpressionRight) {
        this.simpleExpressionRight = simpleExpressionRight;
    }

    @Override
    void visit() {
    }
}
