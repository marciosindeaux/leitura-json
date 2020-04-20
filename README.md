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

