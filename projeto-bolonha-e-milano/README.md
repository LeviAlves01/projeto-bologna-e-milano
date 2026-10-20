# Bolonha e Milano

Jogo de estrategia economica medieval em Java (console), construido em 8 etapas
incrementais. Cada jogador governa uma cidade-Estado a partir de 1400 e tenta
subir de Cavaleiro ate Rei/Rainha.

**Estado atual: Etapa 2** (Main, Menu, Jogador e Cidade).

## Como compilar e executar

Requer Java 17 ou superior, sem ferramenta de build externa. A partir da pasta do projeto:

```
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

(No Windows, use `src\bolonhaemilano\*.java` se necessario.)

## Menu

| Opcao | Acao |
|-------|------|
| 1 | Novo jogo (pede nome do jogador e da cidade) |
| 2 | Ver status da cidade (avisa se nao ha jogo iniciado) |
| 3 | Sobre o jogo |
| 0 | Sair |

Entradas invalidas (letras, vazio, numeros fora do menu) nao travam o programa.

## Estrutura do repositorio

```
src/bolonhaemilano/   codigo Java (pacote bolonhaemilano)
  Main.java           ponto de entrada
  Menu.java           laco principal, leitura e validacao de opcoes
  Jogador.java        nome e titulo de nobreza (inicia "Cavaleiro")
  Cidade.java         nome, ano atual (1400) e jogador dono
uml/                  diagrama de classes (PlantUML, cumulativo)
casos-de-uso/         diagrama de casos de uso (PlantUML, cumulativo)
bin/                  classes compiladas (ignorado pelo Git)
```

Os arquivos `.puml` podem ser abertos em https://www.plantuml.com/plantuml
ou pela extensao PlantUML do VS Code/IntelliJ, e exportados como imagem.

## Regras do jogo (referencia para as proximas etapas)

- Rodadas anuais, comecando em 1400.
- Recursos: florins, graos, terras, servos, soldados, mercadores, clero,
  nobres, mercados, moinhos de la, palacios, catedrais.
- Quatro fases por jogada: (1) colheita, compra/venda de graos e terras,
  alimentacao; (2) impostos e justica; (3) consulta ao territorio;
  (4) obras e defesa.
- Eventos automaticos: clima, ratos, safra, nascimentos/mortes, imigracao,
  fome, comercio, invasoes, juros.
- Titulos: Cavaleiro, Barao, Conde, Marques, Duque, Grao-Duque, Principe,
  Rei/Rainha.
- Parametros a confirmar: distribuicao de alimentos entre 20% e 80% dos
  graos; cultivar 10 hectares exige 2 servos e 5 unidades de grao.

## Roteiro das etapas

1. Estrutura inicial, Main e menu  (feito)
2. Classes Jogador e Cidade  (feito)
3. Economia: florins, terras e graos
4. Agricultura e populacao
5. Impostos e governo
6. Construcoes e soldados
7. Rodadas, multiplos jogadores e vitoria
8. Integracao, testes e entrega final

## Dicas para evoluir o codigo

- Novos atributos da cidade (florins, terras, graos, servos...) entram em
  `Cidade`; considere criar classes separadas por regra (agricultura,
  populacao, economia, construcao, defesa, eventos, titulos).
- Novas opcoes: adicione a constante em `Menu`, a linha em `exibirOpcoes()` e
  um `case` em `processarOpcao()`.
- Para varios jogadores (Etapa 7), troque os campos `jogador`/`cidade` do
  `Menu` por listas.
- Atualize os dois diagramas a cada etapa e commite com "Etapa N: ...".
