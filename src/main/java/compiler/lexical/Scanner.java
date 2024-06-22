package compiler.lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    private BufferedReader reader;

    private char currentChar = '\0';
    private StringBuffer currentSpelling = new StringBuffer();

    private int line = 0;
    private int column = 0;

    public Scanner(String filePath) {
        try {
            /* Abre o Arquivo para leitura */
            this.reader = new BufferedReader(new FileReader(filePath));
            if (this.reader.ready())
                this.currentChar = (char) this.reader.read(); // Ler o primeiro caracter
        } catch (FileNotFoundException e) {
            throw new Error(String.format("Erro - O Arquivo \"%s\" não existe", filePath));
        } catch (IOException e) {
            throw new Error(String.format("Erro - Não foi possível ler o arquivo \"%s\"", filePath));
        }
    }

    /* Ler e Adiciona um caracter ao buffer */
    private void takeIt() throws IOException {
        this.currentSpelling.append(this.currentChar);
        this.currentChar = (char) reader.read();
        this.column++; // Avança para próxima coluna
    }

    /* Limpa o Buffer */
    private void reset() {
        this.currentSpelling.setLength(0);
    }

    /* Obtém o próximo token */
    public Token scan() throws IOException {
        while (scanSeparator())
            ;
        reset();
        return new Token(scanToken(), currentSpelling.toString(), line, column - currentSpelling.length());
    }

    /* Identifica o Tipo do Token */
    private Token.Kind scanToken() throws IOException {
        if (Character.isLetter(currentChar)) {
            /* <letter> ::= a | b | c | ...| z */
            do {
                takeIt();
            } while (Character.isLetterOrDigit(currentChar)); // (<letter> | <digit>)*
            return Token.fromString(currentSpelling.toString());
        } else if (Character.isDigit(currentChar)) {
            /* <digit> ::= 0 | 1| 2 | ... | 9 */
            do {
                takeIt();
            } while (Character.isDigit(currentChar)); // (<digit>)*
            return Token.Kind.INTEGER_LITERAL;
        } else {
            switch (currentChar) {
                case '+':
                    takeIt();
                    return Token.Kind.PLUS;
                case '-':
                    takeIt();
                    return Token.Kind.MINUS;
                case '*':
                    takeIt();
                    return Token.Kind.ASTERISK;
                case '/':
                    takeIt();
                    return Token.Kind.SLASH;
                case '=':
                    takeIt();
                    return Token.Kind.EQUAL;
                case '>':
                    takeIt();
                    return Token.Kind.GREATER;
                case '<':
                    takeIt();
                    return Token.Kind.LESS;
                case ';':
                    takeIt();
                    return Token.Kind.SEMICOLON;
                case ':':
                    takeIt();
                    if (currentChar == '=') {
                        takeIt();
                        return Token.Kind.BECOMES;
                    }
                    return Token.Kind.COLON;
                case ',':
                    takeIt();
                    return Token.Kind.COMMA;
                case '.':
                    takeIt();
                    return Token.Kind.DOT;
                case '(':
                    takeIt();
                    return Token.Kind.LEFT_PARENTHESIS;
                case ')':
                    takeIt();
                    return Token.Kind.RIGHT_PARENTHESIS;
                case Character.MAX_VALUE: // FIM DE ARQUIVO
                    reader.close();
                    return Token.Kind.EOF;
                default:
                    return Token.Kind.ERROR;
            }
        }
    }

    /* Ignora os caracteres que separam os tokens */
    private boolean scanSeparator() throws IOException {
        switch (currentChar) {
            case '\n':
                takeIt();
                this.line++;
                this.column = 0;
                return true;
            case ' ':
            case '\t':
            case '\f':
            case '\r':
                takeIt();
                return true;
            default:
                return false;
        }
    }
}
