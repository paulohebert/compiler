package compiler.lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    private BufferedReader reader;

    private char currentChar = '\0';
    private StringBuffer currentSpelling = new StringBuffer();
    private Token currentToken;

    private int line = 0;
    private int column = 0;

    private void take(char expectedChar) throws IOException, Exception {
        if (this.currentChar == expectedChar) {
            takeIt();
        } else {
            throw new Exception(
                    "ERRO: Era esperado um \"" + expectedChar + "\", mas foi encontrado um \"" + currentChar + "\".");
        }
    }

    private void takeIt() throws IOException {
        this.currentSpelling.append(this.currentChar);
        this.currentChar = (char) reader.read();
        this.column++;
    }

    private void reset() {
        this.currentSpelling.setLength(0);
    }

    public Scanner(String filePath) throws IOException {
        this.reader = new BufferedReader(new FileReader(filePath));
        this.currentChar = (char) this.reader.read();
    }

    public Token scan() throws IOException {
        while (scanSeparator())
            ;
        reset();
        int startColumn = column;
        currentToken = scanToken();
        currentToken.setPosition(line, startColumn);
        return currentToken;
    }

    private Token scanToken() throws IOException {
        if (Character.isLetter(currentChar)) {
            do {
                takeIt();
            } while (Character.isLetterOrDigit(currentChar));
            return Token.fromString(currentSpelling.toString());
        } else if (Character.isDigit(currentChar)) {
            do {
                takeIt();
            } while (Character.isDigit(currentChar));
            if (currentChar == '.') {
                do {
                    takeIt();
                } while (Character.isDigit(currentChar));
                return Token.FLOAT_LITERAL.value(currentSpelling.toString());
            }
            return Token.INTEGER_LITERAL.value(currentSpelling.toString());
        } else {
            switch (currentChar) {
                case '+':
                    takeIt();
                    return Token.PLUS;
                case '-':
                    takeIt();
                    return Token.MINUS;
                case '*':
                    takeIt();
                    return Token.ASTERISK;
                case '/':
                    takeIt();
                    return Token.SLASH;
                case '=':
                    takeIt();
                    return Token.EQUAL;
                case '>':
                    takeIt();
                    return Token.GREATER;
                case '<':
                    takeIt();
                    return Token.LESS;
                case ';':
                    takeIt();
                    return Token.SEMICOLON;
                case ':':
                    takeIt();
                    if (currentChar == '=') {
                        takeIt();
                        return Token.BECOMES;
                    }
                    return Token.COLON;
                case ',':
                    takeIt();
                    return Token.COMMA;
                case '.':
                    takeIt();
                    return Token.DOT;
                case '(':
                    takeIt();
                    return Token.LEFT_PARENTHESIS;
                case ')':
                    takeIt();
                    return Token.RIGHT_PARENTHESIS;
                case Character.MAX_VALUE: // FIM DE ARQUIVO
                    reader.close();
                    return Token.EOF;
                default:
                    return Token.ERROR;
            }
        }
    }

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
