package compiler.cli;

public class CLI {
    public static void parse(String[] args) {
        if (args.length > 7) {
            System.err.println("\033[91mErro: Excesso de argumentos\033[0m");
            System.exit(1);
        }

        /* Muda as configurações padrões de acordo com os argumentos */
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--no-color":
                    Config.setColor(false);
                    break;
                case "--input":
                    Config.setInputFile(args[++i]);
                    break;
                case "--output":
                    Config.setOutputFolder(args[++i]);
                    Config.setColor(false);
                    break;
                case "--breakpoint":
                    switch (args[++i]) {
                        case "lexical":
                            Config.setBreakpoint(Stage.LEXICAL);
                            break;
                        case "syntactic":
                            Config.setBreakpoint(Stage.SYNTACTIC);
                            break;
                        case "context":
                            Config.setBreakpoint(Stage.CONTEXT);
                            break;
                        case "code-gen":
                            Config.setBreakpoint(Stage.CODE_GENERATION);
                            break;
                        default:
                            System.err.println("\033[91mErro: breakpoint invalido\033[0m");
                            System.exit(1);
                            break;
                    }
                    break;
                default:
                    System.err.println("\033[91mErro: O argumento \"" + args[i] + "\" é invalido\033[0m");
                    System.exit(1);
            }
        }
    }
}
