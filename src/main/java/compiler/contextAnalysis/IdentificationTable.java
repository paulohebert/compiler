package compiler.contextAnalysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class IdentificationTable {
    private Stack<Map<String, IdEntry>> scopeStack;

    public IdentificationTable() {
        scopeStack = new Stack<>();
        openScope();  // Inicializa com um escopo global
    }

    public void openScope() {
        scopeStack.push(new HashMap<>());
    }

    public void closeScope() {
        scopeStack.pop();
    }

    public void enter(String id, IdEntry entry) {
        scopeStack.peek().put(id, entry);
    }

    public IdEntry retrieve(String id) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Map<String, IdEntry> scope = scopeStack.get(i);
            if (scope.containsKey(id)) {
                return scope.get(id);
            }
        }
        return null;  // Identificador não encontrado
    }

    public static class IdEntry {
        public final String id;
        public final String type;

        public IdEntry(String id, String type) {
            this.id = id;
            this.type = type;
        }
    }
}
