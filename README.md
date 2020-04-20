# Lendo arquivos JSON no Java de forma simplificada

Em algum momento da nossa vida, teremos que ler um aqrquivo estatico e trata-lo em nosso sistema. Diversas  APIs no mundo disponibilizam diversos tipos de arquivos estaticos: CSVs, TXTs, JSONs e afins, para que o usuário analise os dados gerados e possa fazer algum tipo de "ajuste" ou análise dos dados, e é jsutamente essa parte que voce foi escolhido para automatizar.


## Antes de tudo: Um alerta
Primeiramente tenho que alertar: ler arquivos estaticos no sistema não é uma boa prática, faça isso apenas quando não ha nenhuma outra opção. Mas se porventura isso for realmente necessário, ai, bem, não é o ideal mas acontece

<img src="./staticMd/nao_ideal.jpg">
<br>

## Iniciando projeto
Para seguir com esse projeto, é importante ter cconhecimento sobre a liguagem Java e colocar as dependencias do Gson e do Junit ( afinal, como saberemos se deu certo sem testes, não é ?) no seu POM. Sem essas
bibliotecas não será possivel seguir com esse artigo.
```xml
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.5</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.6.2</version>
        <scope>test</scope>
    </dependency>
```
## Antes de Continuar: Um pouco de Java
Bem... essa é a parte chata que os mais experientes vão poder pular, mas é sempre bom revisar. A bibliooteca GSON tém muito do seu poder de simplificação porque ele trata o JSON como uam string.

Se voce é mais experiente, provavelmente já teve que ler algum arquivo estatico e agora na sua cabeça, já deve estar bolando algumas coisas para isso. De cara alguns já pensam em usar FileReader ou ImputStreamReader, mas calma, não vamos precisar de tantas coisas assim.

<img src="./staticMd/Vale-a-pena.jpg">
<br>


Para voce que é mais novo nesse mundo, eu vou simplificar e explicar o que cada método de cada classe faz, voce que já é mais experiente pode pular e ir direto pro código, mas é sempre bom revisar.

 * ``` Path.get(String dirPath) ``` 
  O método estatico `get()` recebe uma String, ele tenta se orientar a partir da pasta raiz do seu projeto (pasta onde esta o pom.xml), então digamos que seu pom.xml esteja em `C:\Users\user\Documents\projeto` e voce passsa uma string com `./pasta`, o resultado disso será `C:\Users\user\Documents\projeto\pasta`. Ele retorna um Objeto `Path` que representa ou um diretório, ou um arquivo (caso passe um arquivo).
<br>

 * ``` Files.readAllLines(Path path, Charset charset) ```
  O método estatico `readAllLines()` recebe 2 Parametros, o `Path` do arquivo no qual esta buscando ( agora fica claro o porque usamos a o método explicado acima), e o `Charset` do arquivo. Ele retorna uma Lista de `String`, cada item da nossa lista é uma linha do nosso arquivo. Bom, agora tudo já parece bem mais facil, mas espere, tem mais.
<br>

 * ``` Strings.join(String delimitador, String ... items) ```
  O método estatico `join()` recebe o primeiro parametro como um "delimitador". O outro campo é um spread operator, que significa que podemos receber uma ou mais `String`, que seão juntadas. Se voce já usou a função `split(String separator)`, saiba que ela faz o processo reverso, ao invez de separar, ela junta os items  pelo delimitador.Sigamos o exemplo abaixo:
    ```java
    List<String> lista = Arrays.asList("Meu", "Nome", "é", "Marcio");
    String resultado = String.join(" ", lista);
    System.out.println(resultado);
    ```
    A saida do codigo acima no console é `Meu Nome é Marcio`.
    Ele também pode ser escrito dessa Forma:
    ```java
    String resultado = String.join(", ","Set", "Map", "List")
    System.out.println(resultado);
    ```
    A saida do codigo acima no console é `Set, Map, List`