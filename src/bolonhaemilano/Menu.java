package bolonhaemilano;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Partida partida;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;
    private Nobreza nobreza;
    private Eventos eventos;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
        this.governo = new Governo();
        this.construcoes = new Construcoes();
        this.nobreza = new Nobreza();
        this.eventos = new Eventos();
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        System.out.println("Ate a proxima, governante!");
        scanner.close();
    }

    private void limparTela() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J\033[3J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Sem suporte: segue sem limpar.
        }
        if (System.console() == null) {
            // Consoles de IDE nao interpretam "cls"/ANSI: empurra o conteudo antigo para cima.
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
        exibirCabecalho();
    }

    private void exibirCabecalho() {
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO");
        if (partida == null) {
            System.out.println(" Nenhum jogo em andamento");
        } else if (partida.terminou()) {
            System.out.println(" Partida encerrada");
        } else {
            System.out.println(" Ano " + partida.getAnoAtual() + " (fim em " + partida.getAnoFinal() + ")");
            System.out.println(" Vez de: " + cidade.getDono().getTitulo() + " "
                    + cidade.getDono().getNome() + " (" + cidade.getNome() + ")");
        }
        System.out.println("==========================================");
    }

    private void pausar() {
        System.out.println();
        System.out.print("Pressione ENTER para continuar...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private void exibirOpcoes() {
        limparTela();
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        return lerInteiro();
    }

    private int lerInteiro() {
        if (!scanner.hasNextLine()) {
            return 0;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int lerQuantidade() {
        System.out.print("Digite a quantidade: ");
        return lerInteiro();
    }

    private String lerTexto(String pergunta) {
        System.out.print(pergunta);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    private boolean exigirJogoEmAndamento() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            pausar();
            return false;
        }
        return true;
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
                abrirGoverno();
                break;
            case 6:
                abrirConstrucoes();
                break;
            case 7:
                encerrarTurno();
                break;
            case 8:
                exibirClassificacao();
                break;
            case 9:
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida! Digite de 0 a 9.");
                pausar();
        }
    }

    private void iniciarNovoJogo() {
        String[] cidadesPadrao = {"Bolonha", "Milano", "Florenca", "Veneza"};

        System.out.print("Quantos jogadores (1 a " + Partida.MAXIMO_JOGADORES + ")? ");
        int quantidade = lerInteiro();
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            quantidade = 2;
        }

        Partida novaPartida = new Partida();

        for (int i = 0; i < quantidade; i++) {
            System.out.println();
            System.out.println("--- Jogador " + (i + 1) + " ---");

            String nomeJogador = lerTexto("Digite o nome do jogador: ");
            if (nomeJogador == null) {
                emExecucao = false;
                return;
            }
            if (nomeJogador.isEmpty()) {
                nomeJogador = "Jogador " + (i + 1);
            }

            String nomeCidade = lerTexto("Digite o nome da cidade: ");
            if (nomeCidade == null) {
                emExecucao = false;
                return;
            }
            if (nomeCidade.isEmpty()) {
                nomeCidade = cidadesPadrao[i];
            }

            novaPartida.adicionarCidade(new Cidade(nomeCidade, new Jogador(nomeJogador)));
        }

        partida = novaPartida;
        cidade = partida.getCidadeAtual();

        System.out.println();
        System.out.println("Novo jogo criado com " + quantidade + " jogador(es)!");
        System.out.println("Comeca o ano de " + partida.getAnoAtual() + ". A primeira vez e de "
                + cidade.getDono().getNome() + " (" + cidade.getNome() + ").");
        cidade.exibirStatus();
        pausar();
    }

    private void exibirStatus() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        cidade.exibirStatus();
        pausar();
    }

    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noMercado = true;

        while (noMercado) {
            limparTela();
            System.out.println(" MERCADO");
            System.out.println("------------------------------------------");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Preco do grao      : " + mercado.getPrecoGrao() + " florins/un.");
            System.out.println("Preco da terra     : " + mercado.getPrecoTerra() + " florins/hectare");
            System.out.println();
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    pausar();
            }
        }
    }

    private void comprarGraos() {
        int quantidade = lerQuantidade();
        if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("Compra realizada! Graos adicionados: " + quantidade + ".");
        } else {
            System.out.println("Nao foi possivel comprar graos. Verifique quantidade e florins.");
        }
        pausar();
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();
        if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("Venda realizada! Florins recebidos: "
                    + (quantidade * mercado.getPrecoGrao()) + ".");
        } else {
            System.out.println("Nao foi possivel vender graos. Verifique quantidade e estoque.");
        }
        pausar();
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();
        if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("Compra realizada! Terras adicionadas: "
                    + quantidade + " hectares.");
        } else {
            System.out.println("Nao foi possivel comprar terras. Verifique quantidade e florins.");
        }
        pausar();
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();
        if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("Venda realizada! Florins recebidos: "
                    + (quantidade * mercado.getPrecoTerra()) + ".");
        } else {
            System.out.println("Nao foi possivel vender terras. Verifique quantidade e estoque.");
        }
        pausar();
    }

    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean naAgricultura = true;

        while (naAgricultura) {
            limparTela();
            System.out.println(" AGRICULTURA");
            System.out.println("------------------------------------------");
            System.out.println("Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos : " + cidade.getGraos());
            System.out.println("Colheita: " + (cidade.isColheitaFeita() ? "ja feita neste ano" : "pendente"));
            System.out.println();
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    pausar();
            }
        }
    }

    private void colher() {
        int graosAntes = cidade.getGraos();
        int hectares = agricultura.processarColheita(cidade);

        if (hectares < 0) {
            System.out.println("Esta cidade ja colheu neste ano. Encerre o turno para chegar ao proximo ano.");
        } else {
            System.out.println("Colheita processada.");
            System.out.println("Hectares cultivados: " + hectares);
            System.out.println("Graos antes: " + graosAntes);
            System.out.println("Graos depois: " + cidade.getGraos());
        }
        pausar();
    }

    private void distribuirAlimentos() {
        System.out.print("Digite o percentual de alimentos (20 a 80): ");
        int percentual = lerInteiro();

        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos com sucesso.");
            System.out.println("Percentual utilizado: " + percentual + "%.");
            System.out.println("Servos atuais: " + cidade.getServos());
            System.out.println("Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Percentual invalido. Use um valor entre 20% e 80%.");
        }
        pausar();
    }

    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noGoverno = true;

        while (noGoverno) {
            limparTela();
            System.out.println(" GOVERNO");
            System.out.println("------------------------------------------");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Taxa de imposto    : " + cidade.getTaxaImposto() + "%");
            System.out.println("Nivel de justica   : " + cidade.getNivelJustica());
            System.out.println("Impostos           : "
                    + (cidade.isImpostosArrecadados() ? "ja arrecadados neste ano" : "pendentes"));
            System.out.println();
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    pausar();
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);

        if (arrecadado < 0) {
            System.out.println("Os impostos ja foram arrecadados neste ano. Encerre o turno para chegar ao proximo ano.");
        } else {
            System.out.println("Impostos arrecadados: " + arrecadado + " florins.");
            System.out.println("Florins atuais: " + cidade.getFlorins());
        }
        pausar();
    }

    private void definirTaxaImposto() {
        System.out.print("Digite a nova taxa de imposto (0 a 50): ");
        int taxa = lerInteiro();

        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto definida para " + cidade.getTaxaImposto() + "%.");
        } else {
            System.out.println("Taxa invalida. Use um numero inteiro entre 0 e 50.");
        }
        pausar();
    }

    private void definirJustica() {
        System.out.println("1 - Justa");
        System.out.println("2 - Moderada");
        System.out.println("3 - Severa");
        System.out.print("Escolha o nivel de justica: ");

        String nivel;

        switch (lerOpcao()) {
            case 1:
                nivel = "Justa";
                break;
            case 2:
                nivel = "Moderada";
                break;
            case 3:
                nivel = "Severa";
                break;
            default:
                System.out.println("Opcao invalida.");
                pausar();
                return;
        }

        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido para " + cidade.getNivelJustica() + ".");
        } else {
            System.out.println("Nivel de justica invalido.");
        }
        pausar();
    }

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean naConstrucoes = true;

        while (naConstrucoes) {
            limparTela();
            System.out.println(" CONSTRUCOES");
            System.out.println("------------------------------------------");
            System.out.println("Florins    : " + cidade.getFlorins());
            System.out.println("Servos     : " + cidade.getServos());
            System.out.println("Soldados   : " + cidade.getSoldados());
            System.out.println("Construcoes: " + cidade.getConstrucoes());
            System.out.println("Custo do soldado   : " + construcoes.getPrecoSoldado() + " florins + 1 servo");
            System.out.println("Custo da construcao: " + construcoes.getPrecoConstrucao() + " florins");
            System.out.println();
            System.out.println("1 - Recrutar soldados");
            System.out.println("2 - Construir");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    naConstrucoes = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    pausar();
            }
        }
    }

    private void recrutarSoldados() {
        int quantidade = lerQuantidade();
        if (construcoes.recrutarSoldados(cidade, quantidade)) {
            System.out.println("Soldados recrutados: " + quantidade + ".");
            System.out.println("Custo: " + (quantidade * construcoes.getPrecoSoldado())
                    + " florins e " + quantidade + " servos.");
        } else {
            System.out.println("Nao foi possivel recrutar soldados. Verifique florins e servos.");
        }
        pausar();
    }

    private void construir() {
        int quantidade = lerQuantidade();
        if (construcoes.construir(cidade, quantidade)) {
            System.out.println("Construcoes erguidas: " + quantidade + ".");
            System.out.println("Custo: " + (quantidade * construcoes.getPrecoConstrucao()) + " florins.");
        } else {
            System.out.println("Nao foi possivel construir. Verifique a quantidade e os florins.");
        }
        pausar();
    }

    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean fimDoAno = partida.passarTurno();

        if (fimDoAno) {
            int anoEncerrado = partida.getAnoAtual();
            List<Cidade> promovidas = partida.encerrarAno(nobreza);

            System.out.println("O ano de " + anoEncerrado + " terminou.");
            if (promovidas.isEmpty()) {
                System.out.println("Ninguem foi promovido neste ano.");
            }
            for (Cidade promovida : promovidas) {
                System.out.println("PROMOCAO: " + promovida.getDono().getNome() + " (" + promovida.getNome()
                        + ") agora e " + promovida.getDono().getTitulo() + "!");
            }
            System.out.println();
            exibirClassificacao();

            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
            System.out.println("Comeca o ano de " + partida.getAnoAtual() + ".");
            sortearEventosDoAno();
        }

        cidade = partida.getCidadeAtual();
        System.out.println("Agora e a vez de " + cidade.getDono().getNome() + " (" + cidade.getNome() + ").");
        pausar();
    }

    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        System.out.println("------------------------------------------");
        System.out.println(" CLASSIFICACAO - ano " + partida.getAnoAtual());
        System.out.println("------------------------------------------");
        for (Cidade c : partida.getCidades()) {
            Jogador dono = c.getDono();
            int prestigio = nobreza.calcularPrestigio(c);
            int proximo = nobreza.prestigioParaProximoTitulo(dono);

            System.out.println(c.getNome() + " - " + dono.getTitulo() + " " + dono.getNome());
            System.out.println("   Prestigio: " + prestigio);
            if (proximo == -1) {
                System.out.println("   Ja e Rei/Rainha!");
            } else {
                int falta = Math.max(0, proximo - prestigio);
                System.out.println("   Faltam " + falta + " pontos para o proximo titulo (" + proximo + ").");
            }
        }
        System.out.println("------------------------------------------");
        pausar();
    }

    private void sortearEventosDoAno() {
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println(" EVENTOS DO ANO " + partida.getAnoAtual());
        System.out.println("------------------------------------------");

        boolean houveEvento = false;
        for (Cidade c : partida.getCidades()) {
            int graosAntes = c.getGraos();
            int servosAntes = c.getServos();
            int tipo = eventos.sortearEvento(c);

            if (tipo == Eventos.TIPO_SECA) {
                houveEvento = true;
                System.out.println("SECA em " + c.getNome() + " (" + c.getDono().getNome() + "): "
                        + (graosAntes - c.getGraos()) + " graos se perderam.");
            } else if (tipo == Eventos.TIPO_PESTE) {
                houveEvento = true;
                System.out.println("PESTE em " + c.getNome() + " (" + c.getDono().getNome() + "): "
                        + (servosAntes - c.getServos()) + " servos morreram.");
            }
        }
        if (!houveEvento) {
            System.out.println("Nenhum evento especial neste ano.");
        }
        System.out.println("------------------------------------------");
    }

    private void encerrarPartida() {
        Cidade vencedor = partida.determinarVencedor(nobreza);

        limparTela();
        System.out.println(" FIM DE JOGO");
        System.out.println("------------------------------------------");
        if (vencedor.getDono().ehMonarca()) {
            System.out.println("Alguem alcancou o trono antes de " + partida.getAnoFinal() + "!");
        } else {
            System.out.println("O ano de " + partida.getAnoFinal() + " chegou ao fim.");
        }
        System.out.println("VENCEDOR: " + vencedor.getDono().getTitulo() + " "
                + vencedor.getDono().getNome() + ", governante de " + vencedor.getNome() + ".");
        System.out.println("Prestigio final: " + nobreza.calcularPrestigio(vencedor));
        System.out.println("------------------------------------------");
        System.out.println("Escolha 'Novo jogo' para jogar de novo.");

        partida = null;
        cidade = null;
        pausar();
    }

    private void exibirSobre() {
        limparTela();
        System.out.println(" SOBRE O JOGO");
        System.out.println("------------------------------------------");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e");
        System.out.println("politica na Italia medieval. De 1 a 4 jogadores governam");
        System.out.println("cidades-Estado a partir de 1400, decidindo como distribuir");
        System.out.println("recursos entre agricultura, impostos, comercio, obras e defesa.");
        System.out.println();
        System.out.println("Cada jogador joga o seu turno; quando o ultimo encerra, o");
        System.out.println("ano fecha. Colher e arrecadar impostos valem uma vez por ano.");
        System.out.println();
        System.out.println("Objetivo: subir na nobreza ate se tornar Rei ou Rainha:");
        System.out.println("Cavaleiro > Barao > Conde > Marques > Duque > Rei/Rainha.");
        System.out.println("Vence quem chegar primeiro ao trono, ou o melhor colocado");
        System.out.println("ao fim de 1410.");
        System.out.println("------------------------------------------");
        pausar();
    }
}