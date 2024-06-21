package compiler.syntactic.ast;

public class Iterative extends Command {
    private final Expression expression;
    private final Command command;

    public Iterative(Expression expression, Command command) {
        this.expression = expression;
        this.command = command;
    }

    public Expression getExpression() {
        return expression;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public void visit(Visitor v) {
        v.visitIterative(this);
    }
}
