# Bolonha e Milano

Jogo de estrategia economica medieval em Java (console), desenvolvido de forma incremental.

## Estado atual

**Etapas 1 a 6 implementadas.**

As Etapas 3 a 6 foram implementadas a partir dos enunciados fornecidos:

- **Etapa 3 — Economia:** florins, terras, graos e classe Mercado.
- **Etapa 4 — Agricultura e populacao:** servos e classe Agricultura.
- **Etapa 5 — Impostos e governo:** taxa de imposto, nivel de justica e classe Governo.
- **Etapa 6 — Construcoes e soldados:** soldados, construcoes e classe Construcoes.

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
| 5 | Governo |
| 6 | Construcoes |
| 7 | Sobre o jogo |
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

### Governo

1. Arrecadar impostos
2. Definir taxa de imposto
3. Definir nivel de justica
0. Voltar

### Construcoes

1. Recrutar soldados
2. Construir
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

Conforme o manual do jogo: 1 grao = 2 florins e 1 hectare de terra = 1 florim (compra e venda ao mesmo preco).

```java
private static final int PRECO_GRAO = 2;
private static final int PRECO_TERRA = 1;
```

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

## Etapa 5 — Impostos e governo

A `Cidade` recebe `taxaImposto` (10% inicial) e `nivelJustica` ("Moderada" inicial). Sao configuracoes, nao recursos acumulaveis: possuem apenas getter e setter simples.

A classe `Governo` (sem referencia permanente a `Cidade`):

```text
base = servos * taxa / 100
arrecadado = base * multiplicador     (Justa 0,8 / Moderada 1,0 / Severa 1,3)
```

- `definirTaxaImposto`: aceita apenas 0 a 50;
- `definirJustica`: aceita apenas "Justa", "Moderada" ou "Severa" (no menu, escolhida por submenu numerado).

## Etapa 6 — Construcoes e soldados

A `Cidade` recebe `soldados` e `construcoes` (0 iniciais), com o padrao seguro `adicionar*/remover*`.

A classe `Construcoes`:

- `recrutarSoldados`: cada soldado custa 5 florins + 1 servo; florins e servos sao validados **antes** de alterar qualquer um;
- `construir`: cada construcao custa 20 florins.

"Ver status da cidade" exibe tambem taxa de imposto, justica, soldados e construcoes.

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
│       ├── Agricultura.java
│       ├── Governo.java
│       └── Construcoes.java
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

A regra de negocio nao fica concentrada no menu: compras/vendas ficam em `Mercado`, colheita/alimentacao em `Agricultura`, impostos/justica em `Governo` e soldados/obras em `Construcoes`.

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
- servos;
- taxa de imposto e nivel de justica;
- soldados e construcoes.

### `Mercado`

Concentra as regras de comercio.

### `Governo`

Concentra impostos e configuracao de taxa/justica.

### `Construcoes`

Concentra recrutamento de soldados e construcao de edificios.

### `Agricultura`

Concentra as regras de colheita e distribuicao de alimentos.

## Diagramas

Os diagramas cumulativos (Etapas 1 a 6) estao em:

```text
uml/diagrama-de-classes.puml
casos-de-uso/diagrama-de-casos-de-uso.puml
```

## Proximas etapas

- Etapa 7: rodadas (avancar o ano), multiplos jogadores e vitoria;
- Etapa 8: integracao, testes e entrega final.