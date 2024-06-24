# Compilador

![Git](https://img.shields.io/badge/Git-%23000?style=for-the-badge&logo=git)
![Maven](https://img.shields.io/badge/Maven-%23000?style=for-the-badge&logo=apachemaven&logoColor=%23C71A36)
![Java](https://img.shields.io/badge/Java-%23000?style=for-the-badge&logo=openjdk)

O compilador aceita como programa fonte uma versão reduzida da linguagem mini-pascal e transforma em intruções assembly para a maquina TAM (Triangle Abstract Machine)

## Requisitos

- JDK 17 (ou superior)

## Argumentos do Compilador

|   Argumento  |        Opções         |     Padrão    |                                   Descrição                                      |
| :----------: |    :------------:     |  :---------:  |                   :-------------------------------------------                   |
|  --no-color  |           -           |      false    | Desativa a exibição de cores no terminal                                         |
|    --input   |  [caminho do arquivo] |  example.txt  | Define o arquivo do programa fonte                                               |
|   --output   |     [nome da pasta]   |       -       | Define a pasta para a saída de cada etapa da compilação                          |
| --breakpoint | lexical <br> syntactic <br> context <br> code-gen |  code-gen  | Define um ponto de interrupção na compilação            |

### Exemplo de Argumento

```bash
[comando de execução] --input example.txt --output saida
```

## Executar

### Maven

```bash
mvn compile exec:java                      # Sem Passar Argumentos
mvn compile exec:java -D"exec.args=[...]"  # Com Argumentos
```

### Maven Wrapper (Caso não possua o Maven instalado localmente)

> [!WARNING]
> :rotating_light: Precisa ter configurado a Variável de Ambiente: `JAVA_HOME`

#### Windows

```bash
.\mvnw.cmd compile exec:java                      # Sem Passar Argumentos
.\mvnw.cmd compile exec:java -D"exec.args=[...]"  # Com Argumentos
```

#### Linux

```bash
./mvnw compile exec:java                      # Sem Passar Argumentos
./mvnw compile exec:java -D"exec.args=[...]"  # Com Argumentos
```

## JAR

### Criar JAR

```bash
mvn clean package         # Maven
.\mvnw.cmd clean package  # Maven Wrapper (Windows)
./mvnw clean package      # Maven Wrapper (Linux)
```

### Executar JAR

```bash
java -jar target/compiler.jar        # Sem Passar Argumentos
java -jar target/compiler.jar [...]  # Com Argumentos
```
