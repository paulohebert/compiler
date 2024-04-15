# Compilador

![Git](https://img.shields.io/badge/Git-%23000?style=for-the-badge&logo=git)
![Maven](https://img.shields.io/badge/Maven-%23000?style=for-the-badge&logo=apachemaven&logoColor=%23C71A36)
![Java](https://img.shields.io/badge/Java-%23000?style=for-the-badge&logo=openjdk)

## Requisitos

- JDK 17

## Executar

### Maven

```bash
mvn compile exec:java
```

### VS Code

`Ctrl` + `F5`

## JAR

### Criar JAR

```bash
mvn clean package
```

### Executar JAR

```bash
java -jar target/compiler.jar
```
