# Bolonha e Milano

Jogo de estrategia economica e politica na Italia medieval, em Java (console), desenvolvido de forma incremental em 8 etapas. De 1 a 4 jogadores governam cidades-Estado e disputam o titulo de Rei/Rainha.

## Estado atual

**Etapas 1 a 8 implementadas.**

- **Etapa 3 — Economia:** florins, terras, graos e classe `Mercado`.
- **Etapa 4 — Agricultura e populacao:** servos e classe `Agricultura`.
- **Etapa 5 — Impostos e governo:** taxa de imposto, nivel de justica e classe `Governo`.
- **Etapa 6 — Construcoes e soldados:** soldados, construcoes e classe `Construcoes`.
- **Etapa 7 — Rodadas, multiplos jogadores e vitoria:** classes `Partida` e `Nobreza`.
- **Etapa 8 — Integracao, testes e entrega final:** classe `Testes`, refatoracao do `Menu` e diagramas finais.
- **Extra — Eventos aleatorios:** classe `Eventos` (seca e peste), sorteados ao comecar cada novo ano.

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

## Como rodar os testes

Os testes ficam em `Testes.java` (sem JUnit ou biblioteca externa) e compilam junto com o jogo:

```text
java -cp bin bolonhaemilano.Testes
```

Saida esperada no final:

```text
Aprovados: 46 | Reprovados: 0
```

Se algum teste falhar, aparece `[FALHA]` com a descricao da regra quebrada e o programa termina com `System.exit(1)`.

## Como jogar

Ao escolher **Novo jogo**, informe quantos jogadores (1 a 4; valor invalido vira 2), o nome de cada jogador e o nome de cada cidade. Nomes em branco usam os padroes "Jogador N" e Bolonha, Milano, Florenca e Veneza.

A cada volta do menu a tela e limpa e o cabecalho mostra o **ano** e a **vez do jogador**. Depois de cada resultado, o jogo espera **ENTER** para continuar (para a mensagem nao sumir antes de ser lida).

Em cada turno o jogador pode usar mercado, agricultura, governo e construcoes quantas vezes quiser, com duas regras: **colher** e **arrecadar impostos** valem **uma vez por ano** por cidade. Ao terminar, escolhe **Encerrar turno**. Quando o ultimo jogador encerra, o ano fecha (todas as cidades avancam um ano e os governantes que merecem sao promovidos).

### Menu principal

| Opcao | Acao |
|---|---|
| 1 | Novo jogo |
| 2 | Ver status da cidade |
| 3 | Mercado |
| 4 | Agricultura |
| 5 | Governo |
| 6 | Construcoes |
| 7 | Encerrar turno |
| 8 | Ver classificacao |
| 9 | Sobre o jogo |
| 0 | Sair |

Antes de "Novo jogo", as opcoes 2 a 8 avisam que nao ha jogo em andamento.

### Mercado

1. Comprar graos
2. Vender graos
3. Comprar terras
4. Vender terras
0. Voltar

### Agricultura

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

## Regras do jogo

### Valores iniciais de uma cidade

| Recurso | Valor |
|---|---|
| Ano | 1400 |
| Florins | 1000 |
| Terras | 10000 hectares |
| Graos | 500 |
| Servos | 200 |
| Taxa de imposto | 10% |
| Nivel de justica | Moderada |
| Soldados | 0 |
| Construcoes | 0 |

A `Cidade` possui pares seguros `adicionar*/remover*`. Os metodos `remover*` retornam `false` quando a quantidade e invalida ou maior que o estoque, evitando recursos negativos. Taxa de imposto e nivel de justica sao configuracoes (getter/setter simples).

### Mercado (Etapa 3)

Precos fixos, iguais para compra e venda: **1 grao = 2 florins** e **1 hectare de terra = 1 florim**. Quantidade zero ou negativa e recusada.

### Agricultura (Etapa 4)

```text
hectares = min(terras, servos * 5)
sementes = hectares / 2
producao = hectares * 2
```

Se faltarem graos para as sementes, a area cultivada e reduzida. `processarColheita()` devolve os hectares cultivados, ou **-1** se a cidade ja colheu neste ano.

Distribuir alimentos aceita 20% a 80% do estoque:

```text
necessidade = servos / 5
```

Se o alimento distribuido for suficiente, a populacao cresce 5%. Senao, perde o deficit (sem ficar negativa).

### Governo (Etapa 5)

```text
base = servos * taxa / 100
arrecadado = base * multiplicador     (Justa 0,8 / Moderada 1,0 / Severa 1,3)
```

A taxa so aceita 0 a 50. A justica so aceita "Justa", "Moderada" ou "Severa". `arrecadarImpostos()` devolve **-1** se ja arrecadou neste ano.

### Construcoes (Etapa 6)

- `recrutarSoldados`: cada soldado custa 5 florins + 1 servo; florins e servos sao validados **antes** de alterar qualquer um;
- `construir`: cada construcao custa 20 florins.

### Nobreza e vitoria (Etapa 7)

```text
prestigio = construcoes * 10 + soldados * 2 + servos + terras / 100
```

Florins no cofre nao contam. Uma cidade nova comeca com 300 de prestigio.

| Titulo | Prestigio minimo |
|---|---|
| Cavaleiro | inicio |
| Barao | 400 |
| Conde | 550 |
| Marques | 700 |
| Duque | 850 |
| Rei/Rainha | 1000 |

A promocao acontece ao fechar o ano, no maximo **um titulo por ano**. Vence quem virar Rei/Rainha primeiro ou, se ninguem chegar la, o melhor colocado ao fim de 1410 (titulo mais alto; empate, maior prestigio).

### Eventos aleatorios (extra)

Ao comecar cada novo ano (depois da promocao de titulos e da classificacao), cada cidade sorteia **no maximo um** evento. Nao ha evento ao comecar o jogo (1400) nem depois do fim da partida.

| Sorteio (0 a 99) | Evento | Efeito |
|---|---|---|
| 0 a 14 (15%) | Seca | a cidade perde 40% dos graos em estoque |
| 15 a 24 (10%) | Peste | a cidade perde 20% dos servos |
| 25 a 99 (75%) | Nenhum | nada acontece |

Os efeitos usam `removerGraos`/`removerServos`, entao nenhum recurso fica negativo. Florins, terras, soldados e construcoes nao sao afetados. Como o prestigio conta os servos, uma peste pode atrasar a promocao de titulo.

## Estrutura do projeto

```text
projeto-bolonha-e-milano/
├── src/
│   └── bolonhaemilano/
│       ├── Main.java
│       ├── Menu.java
│       ├── Jogador.java
│       ├── Cidade.java
│       ├── Partida.java
│       ├── Mercado.java
│       ├── Agricultura.java
│       ├── Governo.java
│       ├── Construcoes.java
│       ├── Nobreza.java
│       ├── Eventos.java
│       └── Testes.java
├── uml/
│   ├── diagrama-de-classes.puml
│   └── diagrama-de-sequencia.puml
├── casos-de-uso/
│   └── diagrama-de-casos-de-uso.puml
└── README.md
```

## Responsabilidade das classes

- **`Main`**: ponto de entrada; cria o `Menu` e inicia a execucao.
- **`Menu`**: interface de console (limpar tela, cabecalho, pausa e leitura das escolhas); delega as regras para as classes de servico. Na Etapa 8 foi refatorado com `lerInteiro()` e `exigirJogoEmAndamento()`, sem mudar o comportamento.
- **`Partida`**: guarda de 1 a 4 cidades, controla de quem e a vez, fecha o ano, detecta o fim de jogo e define o vencedor.
- **`Jogador`**: nome e nivel de titulo (`promover()`, `ehMonarca()`).
- **`Cidade`**: estado da cidade, recursos, configuracoes e o que ja foi feito no ano (colheita e impostos).
- **`Mercado`**, **`Agricultura`**, **`Governo`**, **`Construcoes`**, **`Nobreza`**, **`Eventos`**: classes de servico; nao guardam a cidade, recebem-na como parametro. `Eventos` guarda apenas o gerador de numeros aleatorios.
- **`Testes`**: segundo `main` com 46 verificacoes das regras; nao faz parte do jogo.

## Diagramas

Os diagramas finais (Etapas 1 a 8) estao em:

```text
uml/diagrama-de-classes.puml
uml/diagrama-de-sequencia.puml
casos-de-uso/diagrama-de-casos-de-uso.puml
```

Podem ser abertos com PlantUML ou com uma extensao PlantUML no VS Code/IntelliJ.