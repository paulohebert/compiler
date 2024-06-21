package compiler.syntactic.ast;

public class Conditional extends Command {
    private final Expression expression;
    private final Command commandIf, commandElse;

    public Conditional(Expression expression, Command commandIf, Command commandElse) {
        this.expression = expression;
        this.commandIf = commandIf;
        this.commandElse = commandElse;
    }

    public Expression getExpression() {
        return expression;
    }

    public Command getCommandIf() {
        return commandIf;
    }

    public Command getCommandElse() {
        return commandElse;
    }

    @Override
    public void visit(Visitor v) {
        v.visitConditional(this);
    }
}
