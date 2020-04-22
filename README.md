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
Bem... essa é a parte chata que os mais experientes vão poder pular, mas é sempre bom revisar. A biblioteca GSON tém muito do seu poder de simplificação porque ela Trabalha com Strings.

Se voce é mais experiente, provavelmente já teve que ler algum arquivo estatico e agora na sua cabeça, já deve estar bolando algumas coisas para isso. De cara alguns já pensam em usar `FileReader` ou `InputStreamReader`, mas calma, não vamos precisar de tantas coisas assim.

<img src="./staticMd/Vale-a-pena.jpg">
<br>


Para voce que é mais novo nesse mundo, eu vou simplificar e explicar o que cada método de cada classe faz, voce que já é mais experiente pode pular e ir direto pro código, mas é sempre bom revisar.

 * ``` Path.get(String dirPath) ``` 
  O método estatico `get()` recebe uma `String`, ele tenta se orientar a partir da pasta raiz do seu projeto (pasta onde esta o pom.xml), então digamos que seu pom.xml esteja em `C:\Users\user\Documents\projeto` e voce passsa uma string com `./pasta`, o resultado disso será `C:\Users\user\Documents\projeto\pasta`. Ele retorna um Objeto `Path` que representa ou um diretório, ou um arquivo (caso passe um arquivo).
<br>

 * ``` Files.readAllLines(Path path, Charset charset) ```
  O método estatico `readAllLines()` recebe 2 Parametros, o `Path` do arquivo no qual esta buscando ( agora fica claro o porque usamos a o método explicado acima), e o `Charset` do arquivo. Ele retorna uma lista de `String`, cada item da nossa lista é uma linha do nosso arquivo. Bom, agora tudo já parece bem mais facil, mas espere, tem mais.
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

## Lendo o JSON e o transformando em Objeto

Ok, agora chegamos na parte que realmente interessa. Como dito no topico anterior, a biblioteca GSON tém muito do seu poder de simplificação porque ela trabalha com String. 

Ok, agora vamos levar em consideração que voce quer trabalhar com esse JSON:

```json
{
  "id": 17,
  "sigla": "TO",
  "nome": "Tocantins",
  "regiao": {
    "id": 1,
    "sigla": "N",
    "nome": "Norte"
  }
}
```
_(json extraido da api de localidades do IBGE)_

**Leve em consideração que na pasta onde ha o arquivo pom.xml existe uma pasta chama static e dentro dela o arquivo que carrega esse JSON se chama Tocantins.json** 

### Lendo o JSON como String 

Usando todos os método que já foram mostrado acima, fica bem facil deduzir um jeito de ler esse JSON. Mesmo assim vou mostrar a Foma que eu fiz para que possa auxiliar na compreensão do problema:

De certa forma fazer código repetido é bem facil, então eu criei uma classe chamada `AbstractReader`, nela eu criei um método estatico chamado `readJson(String dirPath)`, ele une toda a logica de leitura em um unico método. Ela ficou assim :

```java
public abstract class AbstracrReader {
    public static String readJson(String path) throws IOException {
            String json = String.join(" ",
                    Files.readAllLines(
                            Paths.get(path),
                            StandardCharsets.UTF_8)
            );
            return json;
    }
}
```
_([Esse arquivo pode ser conferido nesse projeto](https://github.com/marciosindeaux/leitura-json))_

### Criando a Classe que representa o JSON 

É importante ressaltar neste tópico, que como o GSON se orienta pela string formada, então **a classe no Java deve conter campos com os nomes identicos aos do JSON e não há problema em ter campos sobressalentes**, eles serão gerados com valor `null`, mas é importante que a classe tenha os nomes iguals aos fornecidos pelo JSON

No caso do JSON acima, Temos 2 classes, logo de cara: A classe `Estado` e a classe `Regiao`. Veja como ficam as Classes no Java :

```java

public class Regiao {
    private Integer id;
    private String sigla;
    private String nome;
    private List<Estado> estados = new ArrayList<>();

    Getters e Setters omitidos
}
```
```java
public class Estado {
    private Integer id;
    private String sigla;
    private String nome;
    private Regiao regiao;

    Getters e Setters omitidos
}
```

### Buscando o Tipo da classe Java

Até agora, nós não usamos nada da biblioteca GSON, mas é aqui que isso muda, vamos usa-la para conseguir fazer essa transformação. 

Agora que já temos o JSON como string, precisamos dizer qual o tipo de classe que esse JSON é, para isso, vamos nos ultilizar de uma classe chamada `TypeToken` dessa biblioteca.

A classe `TypeToken` implementa a classe `Type` ( das reflections ) do java. Segundo a documentação oficial, o Java não provem uma forma de representar tipos genéricos, então essa classe Faz isso. Ela força o programador a criar uma classe anonima interna e assim pegar o Tipo em tempo de Execução

Para o nosso código seria algo como isso :

```java
Type type = new TypeToken<Estado>(){}.getType();
```

 > Agora fica a dúvida: e se tivessimos um JSON com uma lista de estados, ao invez de apenas um estado como isso ficaria ? A resposta é simples:

```java
Type type = new TypeToken<List<Estado>>(){}.getType();
```

### Transformando o Json em Objeto

Bom... Se voce hegou nesse ponto, e provavelmente seguiu os passos anteriores, saiba que a partir daqui a coisa é bem simples. 

Existem uma classe na biblioteca GSON chamada `Gson`, e existe um método nela chamado `fromJson(String jsonText, Type convertType)`. Esse método recebe o JSON como string, o Tipo do JSON e retorna uma instancia do tipo da classe com os dados encontrados na string. 

A implementação dessa Leitura fica assim:

```java
String jsonText = AbstracrReader.readJson("./static/Tocantins.json");
Type type = new TypeToken<Estado>(){}.getType();
Estado estado = new Gson().fromJson(jsonText, type);
```

Bem simples, não é ?

## Sugestão de implementação

Bom. Caso voce vá fazer essa implementação para muitos JSONs diferentes, eu sugiro que para cada classe, voce crie uma classe `Reader`, que herde de `AbstractReader`, para cada entidade que for ler como JSON.

Isso pode ficar grande ? Claro que sim, mas cada códifo de cada classe estara isolado e as responsabilidades estarão divididas, além disso, isso vai evitar códigos repetidos.

Para o caso citado acima, foi criado um reader para a entidade `Estado`:

```java
public class EstadoReader  extends AbstracrReader {
    public static Estado readOneFrom(String path) throws IOException {
        String jsonText = readJson(path);

        Type collectionType = new TypeToken<Estado>(){}.getType();
        return new Gson().fromJson(jsonText,collectionType);
    }

    public static List<Estado> readListFrom(String path) throws IOException {
        String jsonText = readJson(path);
        Type collectionType = new TypeToken<List<Estado>>(){}.getType();
        return new Gson().fromJson(jsonText, collectionType);

    }
}
```


É isso, espero que tenha ajudado. 
Até a proxima