package compiler;

import java.io.IOException;

import compiler.lexical.Scanner;
//import compiler.lexical.Token;
import compiler.syntactic.Parser;
import compiler.syntactic.ast.Printer;
import compiler.syntactic.ast.node.Program;

public class Main {
    public static void main(String[] args) throws IOException {
        /* Analisador Léxico */
        Scanner scanner = new Scanner("example.txt");
        /*
         * Token token;
         * while ((token = scanner.scan()) != Token.EOF) {
         * System.out.println(token);
         * if (token == Token.ERROR)
         * break;
         * }
         */

        /* Analisador Sintático */
        Program program = new Parser().parse(scanner);
        if (program != null) {
            System.out.println("A sintaxe está correta");
            Printer printer = new Printer();
            printer.print(program);
        }

    }
}