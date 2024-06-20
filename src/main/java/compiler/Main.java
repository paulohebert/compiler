package compiler;

import java.io.IOException;

import compiler.lexical.Scanner;
//import compiler.lexical.Token;
import compiler.syntactic.Parser;

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
        Parser parser = new Parser();
        if (parser.parse(scanner)) {
            System.out.println("A sintaxe está correta");
        }

    }
}