package compiler.syntactic.ast;

import java.util.ArrayList;

public class CommandList extends AST {
    private final ArrayList<Command> commandList = new ArrayList<>();

    public void add(Command command) {
        commandList.add(command);
    }

    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    @Override
    void visit() {
    }
}
