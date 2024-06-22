package compiler.syntactic.ast.node;

import compiler.syntactic.ast.Visitor;

public class CompoundCommand extends Command {
    private final CommandList commandList;

    public CompoundCommand(CommandList commandList) {
        this.commandList = commandList;
    }

    public CommandList getCommandList() {
        return commandList;
    }

    @Override
    public void visit(Visitor v) {
        v.visitCompoundCommand(this);
    }
}
