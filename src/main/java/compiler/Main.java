package compiler;

import java.io.IOException;

import compiler.lexical.Scanner;
import compiler.lexical.Token;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner("example.txt");
        Token token;

        while ((token = scanner.scan()) != Token.EOF) {
            System.out.println(token);
        }
    }
}