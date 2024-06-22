package compiler.syntactic.ast.node;

import java.util.ArrayList;

import compiler.syntactic.ast.Visitor;

public class Declarations extends AST {
    private final ArrayList<VariableDeclaration> declarations = new ArrayList<>();

    public void add(VariableDeclaration variableDeclaration) {
        declarations.add(variableDeclaration);
    }

    public ArrayList<VariableDeclaration> getDeclarations() {
        return declarations;
    }

    @Override
    public void visit(Visitor v) {
        v.visitDeclarations(this);
    }
}
