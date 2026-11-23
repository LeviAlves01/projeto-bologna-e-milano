package bolonhaemilano;

import java.util.Random;

public class Testes {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        System.out.println("===== TESTES BOLONHA E MILANO =====");

        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();
        testarEventos();

        System.out.println();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);

        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarCidade() {
        System.out.println("-- Cidade --");

        Cidade cidade = novaCidade();
        verificar("cidade nasce com 1000 florins", cidade.getFlorins() == 1000);
        verificar("cidade nasce no ano 1400", cidade.getAnoAtual() == 1400);

        boolean removeu = cidade.removerFlorins(5000);
        verificar("removerFlorins acima do saldo devolve false", !removeu);
        verificar("... e nao altera o saldo", cidade.getFlorins() == 1000);

        cidade.marcarColheitaFeita();
        cidade.avancarAno();
        verificar("avancarAno soma 1 ao ano", cidade.getAnoAtual() == 1401);
        verificar("avancarAno libera a colheita de novo", !cidade.isColheitaFeita());
    }

    private static void testarMercado() {
        System.out.println("-- Mercado --");
        Mercado mercado = new Mercado();

        Cidade cidade = novaCidade();
        boolean comprou = mercado.comprarGraos(cidade, 100);
        verificar("comprar 100 graos da certo", comprou);
        verificar("... e custa 200 florins", cidade.getFlorins() == 800);
        verificar("... e soma 100 graos", cidade.getGraos() == 600);

        Cidade outra = novaCidade();
        verificar("comprar quantidade negativa e recusado", !mercado.comprarGraos(outra, -5));

        Cidade pobre = novaCidade();
        pobre.removerFlorins(995);
        verificar("comprar sem florins suficientes e recusado", !mercado.comprarGraos(pobre, 100));
    }

    private static void testarAgricultura() {
        System.out.println("-- Agricultura --");
        Agricultura agricultura = new Agricultura();

        Cidade cidade = novaCidade();
        int hectares = agricultura.processarColheita(cidade);
        verificar("primeira colheita do ano cultiva 1000 hectares", hectares == 1000);
        verificar("... e deixa 2000 graos (500 - 500 sementes + 2000)", cidade.getGraos() == 2000);

        verificar("segunda colheita no mesmo ano devolve -1", agricultura.processarColheita(cidade) == -1);

        Cidade outra = novaCidade();
        verificar("distribuir 10% e recusado (minimo 20%)", !agricultura.distribuirAlimentos(outra, 10));
    }

    private static void testarGoverno() {
        System.out.println("-- Governo --");
        Governo governo = new Governo();

        Cidade cidade = novaCidade();
        verificar("taxa de 60% e recusada (maximo 50%)", !governo.definirTaxaImposto(cidade, 60));
        verificar("justica \"Branda\" e recusada", !governo.definirJustica(cidade, "Branda"));

        governo.definirJustica(cidade, "Severa");
        verificar("200 servos, 10%, Severa: arrecada 26 florins", governo.arrecadarImpostos(cidade) == 26);
        verificar("segunda arrecadacao no mesmo ano devolve -1", governo.arrecadarImpostos(cidade) == -1);
    }

    private static void testarConstrucoes() {
        System.out.println("-- Construcoes --");
        Construcoes construcoes = new Construcoes();

        Cidade cidade = novaCidade();
        cidade.removerServos(150);

        boolean recrutou = construcoes.recrutarSoldados(cidade, 100);

        verificar("recrutar 100 soldados com so 50 servos e recusado", !recrutou);
        verificar("... e NAO debita florins pela metade", cidade.getFlorins() == 1000);

        Cidade outra = novaCidade();
        boolean construiu = construcoes.construir(outra, 10);
        verificar("construir 10 da certo", construiu);
        verificar("... e custa 200 florins", outra.getFlorins() == 800);
    }

    private static void testarJogadorENobreza() {
        System.out.println("-- Jogador e Nobreza --");
        Nobreza nobreza = new Nobreza();

        Cidade cidade = novaCidade();
        verificar("jogador comeca como Cavaleiro", cidade.getDono().getTitulo().equals("Cavaleiro"));
        verificar("prestigio inicial e 300 (200 servos + 10000/100 terras)",
                nobreza.calcularPrestigio(cidade) == 300);
        verificar("com prestigio 300 nao sobe de titulo", !nobreza.verificarPromocao(cidade));

        cidade.adicionarConstrucoes(50);
        boolean subiu = nobreza.verificarPromocao(cidade);
        verificar("com 50 construcoes sobe para Barao", subiu && cidade.getDono().getTitulo().equals("Barao"));
        verificar("sobe no maximo um titulo por chamada", cidade.getDono().getNivelTitulo() == 1);

        Jogador rei = new Jogador("Rei");
        while (rei.promover()) {
        }
        verificar("promover para alem de Rei/Rainha e recusado", !rei.promover());
    }

    private static void testarPartida() {
        System.out.println("-- Partida --");
        Nobreza nobreza = new Nobreza();

        Partida partida = new Partida();
        Cidade bolonha = new Cidade("Bolonha", new Jogador("A"));
        Cidade milano = new Cidade("Milano", new Jogador("B"));
        partida.adicionarCidade(bolonha);
        partida.adicionarCidade(milano);

        verificar("primeiro turno e de Bolonha", partida.getCidadeAtual() == bolonha);
        verificar("passar o turno de Bolonha NAO fecha o ano", !partida.passarTurno());
        verificar("agora e a vez de Milano", partida.getCidadeAtual() == milano);
        verificar("passar o turno de Milano fecha o ano", partida.passarTurno());

        partida.encerrarAno(nobreza);
        verificar("encerrarAno avanca todas as cidades",
                bolonha.getAnoAtual() == 1401 && milano.getAnoAtual() == 1401);
        verificar("partida ainda nao terminou em 1401", !partida.terminou());

        milano.getDono().promover();
        verificar("vencedor parcial e Milano (titulo mais alto)",
                partida.determinarVencedor(nobreza) == milano);

        partida.adicionarCidade(new Cidade("Florenca", new Jogador("C")));
        partida.adicionarCidade(new Cidade("Veneza", new Jogador("D")));
        verificar("quinta cidade e recusada (maximo 4)",
                !partida.adicionarCidade(new Cidade("Roma", new Jogador("E"))));
    }

    private static Eventos eventosComSorteio(int valor) {
        return new Eventos(new Random() {
            @Override
            public int nextInt(int limite) {
                return valor;
            }
        });
    }

    private static void testarEventos() {
        System.out.println("-- Eventos --");
        Eventos eventos = new Eventos();

        Cidade seca = novaCidade();
        int perdaSeca = eventos.aplicarSeca(seca);
        verificar("seca tira 40% dos graos (200 de 500)", perdaSeca == 200);
        verificar("... e deixa 300 graos", seca.getGraos() == 300);

        Cidade semGraos = novaCidade();
        semGraos.removerGraos(500);
        verificar("seca sem graos nao perde nada e nao fica negativa",
                eventos.aplicarSeca(semGraos) == 0 && semGraos.getGraos() == 0);

        Cidade peste = novaCidade();
        int perdaPeste = eventos.aplicarPeste(peste);
        verificar("peste tira 20% dos servos (40 de 200)", perdaPeste == 40);
        verificar("... e deixa 160 servos", peste.getServos() == 160);

        Cidade semServos = novaCidade();
        semServos.removerServos(200);
        verificar("peste sem servos nao perde nada e nao fica negativa",
                eventos.aplicarPeste(semServos) == 0 && semServos.getServos() == 0);

        Cidade sorteioSeca = novaCidade();
        int tipoSeca = eventosComSorteio(0).sortearEvento(sorteioSeca);
        verificar("sorteio 0 e seca e nao mexe nos servos",
                tipoSeca == Eventos.TIPO_SECA && sorteioSeca.getGraos() == 300 && sorteioSeca.getServos() == 200);

        Cidade sorteioPeste = novaCidade();
        int tipoPeste = eventosComSorteio(15).sortearEvento(sorteioPeste);
        verificar("sorteio 15 e peste e nao mexe nos graos",
                tipoPeste == Eventos.TIPO_PESTE && sorteioPeste.getServos() == 160 && sorteioPeste.getGraos() == 500);

        Cidade sorteioNada = novaCidade();
        int tipoNada = eventosComSorteio(99).sortearEvento(sorteioNada);
        verificar("sorteio 99 nao gera evento e nada muda",
                tipoNada == Eventos.TIPO_NENHUM && sorteioNada.getGraos() == 500 && sorteioNada.getServos() == 200);
    }
}