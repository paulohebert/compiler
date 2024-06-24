package compiler.lexical;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import compiler.cli.Config;

public class Scanner {
    private BufferedReader reader;
    private OutputStream writer = System.out; // Saída Padrão

    private char currentChar = '\0';
    private Token currentToken;
    private StringBuffer currentSpelling = new StringBuffer();

    private int line = 0;
    private int column = 0;

    public Scanner() {
        String filePath = Config.getInputFile();

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

        /* Cria um arquivo de saída caso necessário */
        String outputFolder = Config.getOutputFolder();
        if (outputFolder != null) {
            try {
                File file = new File(outputFolder, "lexical.txt");

                /* Cria o diretório caso não exista */
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }

                this.writer = new BufferedOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                throw new Error("Erro - Não foi possível criar o arquivo de saida para o analisador lexico");
            }
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

        /* Limpa o Buffer para o próximo Token */
        reset();

        /* Obtém o Token */
        currentToken = new Token(scanToken(), currentSpelling.toString(), line, column - currentSpelling.length());

        /* Imprime o Token */
        writer.write(currentToken.toString().getBytes());

        return currentToken;
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

                    if (writer != System.out)
                        writer.close();

                    reset();
                    currentSpelling.append("<eof>");
                    column += currentSpelling.length();

                    return Token.Kind.EOF;
                default:
                    takeIt();
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
