package compiler.syntactic.ast.node;

abstract public class Literal<T> extends Factor {
    protected T value;

    public Literal(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
