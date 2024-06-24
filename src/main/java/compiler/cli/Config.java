package compiler.cli;

public class Config {
  /* Configuração Padrão do Compilador */
  private static String inputFile = "example.txt";
  private static String outputFolder = null;
  private static boolean color = true;
  private static Stage breakpoint = Stage.CODE_GENERATION;

  public static String getInputFile() {
    return Config.inputFile;
  }

  public static void setInputFile(String inputFile) {
    Config.inputFile = inputFile;
  }

  public static String getOutputFolder() {
    return Config.outputFolder;
  }

  public static void setOutputFolder(String outputFolder) {
    Config.outputFolder = outputFolder;
  }

  public static boolean isColor() {
    return Config.color;
  }

  public static void setColor(boolean color) {
    Config.color = color;
  }

  public static Stage getBreakpoint() {
    return Config.breakpoint;
  }

  public static void setBreakpoint(Stage breakpoint) {
    Config.breakpoint = breakpoint;
  }

}
