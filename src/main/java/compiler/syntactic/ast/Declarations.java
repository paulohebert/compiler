package compiler.syntactic.ast;

import java.util.ArrayList;

public class Declarations extends AST {
    private final ArrayList<VariableDeclaration> declarations = new ArrayList<>();

    public void add(VariableDeclaration variableDeclaration) {
        declarations.add(variableDeclaration);
    }

    public ArrayList<VariableDeclaration> getDeclarations() {
        return declarations;
    }

    @Override
    void visit() {

    }
}
