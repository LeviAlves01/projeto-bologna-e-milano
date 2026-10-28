# Bolonha e Milano

Jogo de estrategia economica medieval em Java (console), desenvolvido de forma incremental.

## Estado atual

**Etapas 1 a 4 implementadas.**

As Etapas 3 e 4 foram implementadas a partir dos enunciados fornecidos:

- **Etapa 3 — Economia:** florins, terras, graos e classe Mercado.
- **Etapa 4 — Agricultura e populacao:** servos e classe Agricultura.

A estrutura foi mantida separada por responsabilidade para facilitar a evolucao nas proximas etapas.

## Como compilar e executar

Requer Java 17 ou superior, sem ferramenta de build externa.

A partir da pasta do projeto:

```text
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

No Windows, se necessario:

```text
javac -encoding UTF-8 -d bin src\bolonhaemilano\*.java
java -cp bin bolonhaemilano.Main
```

## Menu atual

| Opcao | Acao |
|---|---|
| 1 | Novo jogo |
| 2 | Ver status da cidade |
| 3 | Mercado |
| 4 | Agricultura |
| 5 | Sobre o jogo |
| 0 | Sair |

### Mercado

O submenu possui:

1. Comprar graos
2. Vender graos
3. Comprar terras
4. Vender terras
0. Voltar

### Agricultura

O submenu possui:

1. Colher (processar safra)
2. Distribuir alimentos
0. Voltar

## Etapa 3 — Economia

Ao criar uma cidade, os recursos iniciais sao:

- **1000 florins**
- **10000 hectares de terras**
- **500 unidades de graos**

A `Cidade` possui pares seguros `adicionar*/remover*`. Os metodos `remover*` retornam `false` quando a quantidade solicitada e invalida ou maior que o estoque, evitando recursos negativos.

A classe `Mercado`:

- nao guarda referencia para uma cidade;
- recebe `Cidade` como parametro;
- compra e vende graos;
- compra e vende terras;
- usa precos fixos.

### Precos

O enunciado da Etapa 3 exige precos fixos, mas nao informa os valores numericos. Por isso, os valores foram centralizados no inicio de `Mercado.java` para que possam ser alterados facilmente:

```java
private static final int PRECO_GRAO = 2;
private static final int PRECO_TERRA = 5;
```

Se o professor fornecer outros valores, basta alterar essas duas constantes.

## Etapa 4 — Agricultura e populacao

A cidade recebe:

- **200 servos iniciais**

A classe `Agricultura` tambem nao guarda referencia permanente para uma cidade.

### Colheita

A implementacao segue as formulas da dinamica:

```text
hectares = min(terras, servos * 5)
sementes = hectares / 2
producao = hectares * 2
```

Quando o estoque de graos nao e suficiente para as sementes, a area cultivada e reduzida para que as sementes caibam no estoque disponivel.

O metodo `processarColheita()` retorna a quantidade de hectares efetivamente cultivados.

### Distribuicao de alimentos

O percentual deve estar entre **20% e 80%**.

```text
alimento = graos * percentual / 100
necessidade = servos / 5
```

Se o alimento distribuido for suficiente:

```text
crescimento = servos * 5 / 100
```

A populacao cresce 5%.

Caso contrario:

```text
deficit = necessidade - alimento
```

O deficit e removido da populacao, sem permitir que os servos fiquem negativos.

## Estrutura do projeto

```text
projeto-bolonha-e-milano/
├── src/
│   └── bolonhaemilano/
│       ├── Main.java
│       ├── Menu.java
│       ├── Jogador.java
│       ├── Cidade.java
│       ├── Mercado.java
│       └── Agricultura.java
├── uml/
│   └── diagrama-de-classes.puml
├── casos-de-uso/
│   └── diagrama-de-casos-de-uso.puml
└── README.md
```

## Responsabilidade das classes

### `Main`

Ponto de entrada do programa. Apenas cria o `Menu` e inicia a execucao.

### `Menu`

Responsavel pela interface de console, leitura das escolhas e delegacao das operacoes.

A regra de negocio nao fica concentrada no menu: compras/vendas ficam em `Mercado`, e colheita/alimentacao ficam em `Agricultura`.

### `Jogador`

Representa o governante, com nome e titulo.

O `setTitulo()` foi mantido para permitir a evolucao futura da progressao de nobreza.

### `Cidade`

Representa o estado da cidade e seus recursos.

Nesta versao possui:

- nome;
- ano atual;
- dono;
- florins;
- terras;
- graos;
- servos.

### `Mercado`

Concentra as regras de comercio.

### `Agricultura`

Concentra as regras de colheita e distribuicao de alimentos.

## Diagramas

Os diagramas cumulativos das Etapas 3 e 4 estao em:

```text
uml/diagrama-de-classes.puml
casos-de-uso/diagrama-de-casos-de-uso.puml
```

Eles podem ser abertos com PlantUML ou com uma extensao PlantUML no VS Code/IntelliJ.

## Preparacao para as proximas etapas

A implementacao foi organizada para que as proximas funcionalidades possam ser adicionadas sem colocar todas as regras dentro de `Menu`.

Uma evolucao natural e:

- `Impostos` para a Etapa 5;
- `Construcoes` e `Defesa` para a Etapa 6;
- listas de jogadores/cidades e processamento anual para a Etapa 7;
- testes e integracao para a Etapa 8.

Novas regras de negocio devem preferencialmente ficar em classes de servico separadas, seguindo o padrao usado por `Mercado` e `Agricultura`.

## Observacao sobre a dinamica completa

O PDF da dinamica completa apresenta uma visao mais ampla do jogo, incluindo impostos, justica, construcoes, soldados, eventos, titulos e multiplos jogadores. Esses elementos sao referencia para as etapas futuras e **nao foram implementados antecipadamente**, pois as Etapas 3 e 4 solicitam especificamente economia, agricultura e populacao.

Isso mantem o projeto incremental e facilita a evolucao conforme os proximos enunciados forem apresentados.
