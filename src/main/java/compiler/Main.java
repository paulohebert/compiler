package compiler;

import java.io.IOException;

import compiler.cli.CLI;
import compiler.cli.Config;
import compiler.cli.Stage;
import compiler.codeGenerator.Coder;
import compiler.contextAnalysis.Checker;
import compiler.lexical.Scanner;
import compiler.syntactic.Parser;
import compiler.syntactic.ast.Printer;
import compiler.syntactic.ast.node.Program;

public class Main {
    public static void main(String[] args) throws IOException {
        /* Analisa a Linha de Comando */
        CLI.parse(args);

        /* Analisador Léxico */
        System.out.println("(1) - Iniciando Leitura do Programa Fonte");
        Scanner scanner = new Scanner();
        System.out.println("(2) - Analise Léxica Concluída");

        /* Analisador Sintático */
        Program program = new Parser().parse(scanner);

        if (Config.getBreakpoint() == Stage.LEXICAL) {
            System.exit(0);
        }

        System.out.println("(3) - Analise Sintática Concluída");

        if (program != null) {
            /* Visualização da AST */
            System.out.println("(4) - Imprimindo AST");
            Printer printer = new Printer();
            printer.print(program);

            if (Config.getBreakpoint() == Stage.SYNTACTIC) {
                System.exit(0);
            }

            /* Analisador de Contexto */

            if (Config.getBreakpoint() == Stage.CONTEXT) {
                System.exit(0);
            }

            /* Gerador de Código */
            Coder coder = new Coder();
            System.out.println("(5) - Geração de Código Concluída");
            coder.encode(program);

            Checker checker = new Checker();

            checker.check(program);
        }
    }
}