package compiler.contextAnalysis;

import compiler.syntactic.ast.Visitor;
import compiler.syntactic.ast.node.*;

public class Checker implements Visitor {
    private IdentificationTable idTable;

    public Checker() {
        idTable = new IdentificationTable();
    }

    public void check(Program program) {
        System.out.println("");
        program.visit(this);
    }

    @Override
    public void visitProgram(Program program) {
        idTable.openScope();
        program.getIdentifier().visit(this);
        program.getBody().visit(this);
        idTable.closeScope();
    }

    @Override
    public void visitAssignment(Assignment assignment) {
        Identifier id = assignment.getIdentifier();
        id.visit(this);
        Expression expr = assignment.getExpression();
        expr.visit(this);

        IdentificationTable.IdEntry entry = idTable.retrieve(id.getIdName());
        if (entry == null) {
            System.err.println("Error: Variavel " + id.getIdName() + " não declarada.");
        }
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
        String id = variableDeclaration.getIdentifier().getIdName();
        String type = variableDeclaration.getType().getType().getSpelling();

        if (idTable.retrieve(id) != null) {
            System.err.println("Error: Variable " + id + " already declared.");
        } else {
            idTable.enter(id, new IdentificationTable.IdEntry(id, type));
        }
    }

    @Override
    public void visitIdentifier(Identifier identifier) {
        // if (idTable.retrieve(identifier.getIdName()) == null) {
        //     System.err.println("Error: Variable " + identifier.getIdName() + " not declared.");
        // }
    }

    @Override
    public void visitBody(Body body) {
        body.getDeclarations().visit(this);
        body.getCompoundCommand().visit(this);
    }

    @Override
    public void visitAddOperator(AddOperator addOperator) {
        // Análise do operador de adição
    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral booleanLiteral) {
        // Análise do literal booleano
    }

    @Override
    public void visitCommandList(CommandList commandList) {
        for (Command command : commandList.getCommandList()) {
            command.visit(this);
        }
    }

    @Override
    public void visitCompoundCommand(CompoundCommand compoundCommand) {
        compoundCommand.getCommandList().visit(this);
    }

    @Override
    public void visitConditional(Conditional conditional) {
        conditional.getExpression().visit(this);
        conditional.getCommandIf().visit(this);
        if (conditional.getCommandElse() != null) {
            conditional.getCommandElse().visit(this);
        }
    }

    @Override
    public void visitDeclarations(Declarations declarations) {
        for (VariableDeclaration declaration : declarations.getDeclarations()) {
            declaration.visit(this);
        }
    }

    @Override
    public void visitExpression(Expression expression) {
        expression.getSimpleExpressionLeft().visit(this);
        if (expression.getOperator() != null) {
            expression.getOperator().visit(this);
            expression.getSimpleExpressionRight().visit(this);
        }
    }

    @Override
    public void visitIntegerLiteral(IntegerLiteral integerLiteral) {
        // Análise do literal inteiro
    }

    @Override
    public void visitIterative(Iterative iterative) {
        iterative.getExpression().visit(this);
        iterative.getCommand().visit(this);
    }

    @Override
    public void visitMultiplyOperator(MultiplyOperator multiplyOperator) {
        // Análise do operador de multiplicação
    }

    @Override
    public void visitRelationalOperator(RelationalOperator relationalOperator) {
        // Análise do operador relacional
    }

    @Override
    public void visitSimpleExpression(SimpleExpression simpleExpression) {
        simpleExpression.getTerm().visit(this);
        if (simpleExpression.getOperator() != null) {
            simpleExpression.getOperator().visit(this);
            simpleExpression.getSimpleExpression().visit(this);
        }
    }

    @Override
    public void visitTerm(Term term) {
        term.getFactor().visit(this);
        if (term.getOperator() != null) {
            term.getOperator().visit(this);
            term.getTerm().visit(this);
        }
    }

    @Override
    public void visitType(Type type) {
        // Análise do tipo
    }
}
