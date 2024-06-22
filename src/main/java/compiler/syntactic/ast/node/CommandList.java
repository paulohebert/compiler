package compiler.syntactic.ast.node;

import java.util.ArrayList;

import compiler.syntactic.ast.Visitor;

public class CommandList extends AST {
    private final ArrayList<Command> commandList = new ArrayList<>();

    public void add(Command command) {
        commandList.add(command);
    }

    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    @Override
    public void visit(Visitor v) {
        v.visitCommandList(this);
    }
}
