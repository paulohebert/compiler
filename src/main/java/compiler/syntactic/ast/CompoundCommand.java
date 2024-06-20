package compiler.syntactic.ast;

public class CompoundCommand extends Command {
    private final CommandList commandList;

    public CompoundCommand(CommandList commandList) {
        this.commandList = commandList;
    }

    public CommandList getCommandList() {
        return commandList;
    }

    @Override
    void visit() {
    }
}
