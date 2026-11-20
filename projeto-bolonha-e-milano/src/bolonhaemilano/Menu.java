package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
        this.governo = new Governo();
        this.construcoes = new Construcoes();
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

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO");
        System.out.println(" Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
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
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida! Digite 1, 2, 3, 4, 5, 6, 7 ou 0.");
        }
    }

    private void iniciarNovoJogo() {
        String nomeJogador = lerTexto("Digite o nome do jogador: ");
        if (nomeJogador == null) {
            emExecucao = false;
            return;
        }
        if (nomeJogador.isEmpty()) {
            nomeJogador = "Jogador 1";
        }

        String nomeCidade = lerTexto("Digite o nome da cidade: ");
        if (nomeCidade == null) {
            emExecucao = false;
            return;
        }
        if (nomeCidade.isEmpty()) {
            nomeCidade = "Bolonha";
        }

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);

        System.out.println();
        System.out.println("Novo jogo criado! Que comece o governo de " + cidade.getNome() + ".");
        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (!jogoIniciado()) {
            return;
        }
        cidade.exibirStatus();
    }

    private void abrirMercado() {
        if (!jogoIniciado()) {
            return;
        }

        boolean noMercado = true;

        while (noMercado) {
            System.out.println();
            System.out.println("------------------------------------------");
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
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();
        if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("Venda realizada! Florins recebidos: "
                    + (quantidade * mercado.getPrecoGrao()) + ".");
        } else {
            System.out.println("Nao foi possivel vender graos. Verifique quantidade e estoque.");
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();
        if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("Compra realizada! Terras adicionadas: "
                    + quantidade + " hectares.");
        } else {
            System.out.println("Nao foi possivel comprar terras. Verifique quantidade e florins.");
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();
        if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("Venda realizada! Florins recebidos: "
                    + (quantidade * mercado.getPrecoTerra()) + ".");
        } else {
            System.out.println("Nao foi possivel vender terras. Verifique quantidade e estoque.");
        }
    }

    private int lerQuantidade() {
        while (true) {
            System.out.print("Digite a quantidade: ");

            if (!scanner.hasNextLine()) {
                return 0;
            }

            String linha = scanner.nextLine().trim();

            try {
                int quantidade = Integer.parseInt(linha);
                if (quantidade > 0) {
                    return quantidade;
                }
            } catch (NumberFormatException e) {
            }

            System.out.println("Digite um numero inteiro maior que zero.");
        }
    }

    private void abrirAgricultura() {
        if (!jogoIniciado()) {
            return;
        }

        boolean naAgricultura = true;

        while (naAgricultura) {
            System.out.println();
            System.out.println("------------------------------------------");
            System.out.println(" AGRICULTURA");
            System.out.println("------------------------------------------");
            System.out.println("Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos : " + cidade.getGraos());
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
            }
        }
    }

    private void colher() {
        int graosAntes = cidade.getGraos();
        int hectares = agricultura.processarColheita(cidade);
        int graosDepois = cidade.getGraos();

        System.out.println("Colheita processada.");
        System.out.println("Hectares cultivados: " + hectares);
        System.out.println("Graos antes: " + graosAntes);
        System.out.println("Graos depois: " + graosDepois);
    }

    private void distribuirAlimentos() {
        int percentual = lerPercentualAlimentacao();

        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos com sucesso.");
            System.out.println("Percentual utilizado: " + percentual + "%.");
            System.out.println("Servos atuais: " + cidade.getServos());
            System.out.println("Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Percentual invalido. Use um valor entre 20% e 80%.");
        }
    }

    private int lerPercentualAlimentacao() {
        while (true) {
            System.out.print("Digite o percentual de alimentos (20 a 80): ");

            if (!scanner.hasNextLine()) {
                return 20;
            }

            String linha = scanner.nextLine().trim();

            try {
                int percentual = Integer.parseInt(linha);
                if (percentual >= 20 && percentual <= 80) {
                    return percentual;
                }
            } catch (NumberFormatException e) {
                // Entrada invalida: repete a leitura.
            }

            System.out.println("Digite um percentual inteiro entre 20 e 80.");
        }
    }

    private void abrirGoverno() {
        if (!jogoIniciado()) {
            return;
        }

        boolean noGoverno = true;

        while (noGoverno) {
            System.out.println();
            System.out.println("------------------------------------------");
            System.out.println(" GOVERNO");
            System.out.println("------------------------------------------");
            System.out.println("Florins disponiveis: " + cidade.getFlorins());
            System.out.println("Taxa de imposto    : " + cidade.getTaxaImposto() + "%");
            System.out.println("Nivel de justica   : " + cidade.getNivelJustica());
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
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        System.out.println("Impostos arrecadados: " + arrecadado + " florins.");
        System.out.println("Florins atuais: " + cidade.getFlorins());
    }

    private void definirTaxaImposto() {
        System.out.print("Digite a nova taxa de imposto (0 a 50): ");

        if (!scanner.hasNextLine()) {
            return;
        }

        String linha = scanner.nextLine().trim();
        int taxa;

        try {
            taxa = Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            System.out.println("Taxa invalida. Digite um numero inteiro entre 0 e 50.");
            return;
        }

        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto definida para " + cidade.getTaxaImposto() + "%.");
        } else {
            System.out.println("Taxa invalida. Use um valor entre 0 e 50.");
        }
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
                return;
        }

        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido para " + cidade.getNivelJustica() + ".");
        } else {
            System.out.println("Nivel de justica invalido.");
        }
    }

    private void abrirConstrucoes() {
        if (!jogoIniciado()) {
            return;
        }

        boolean naConstrucoes = true;

        while (naConstrucoes) {
            System.out.println();
            System.out.println("------------------------------------------");
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
    }

    private void construir() {
        int quantidade = lerQuantidade();
        if (construcoes.construir(cidade, quantidade)) {
            System.out.println("Construcoes erguidas: " + quantidade + ".");
            System.out.println("Custo: " + (quantidade * construcoes.getPrecoConstrucao()) + " florins.");
        } else {
            System.out.println("Nao foi possivel construir. Verifique a quantidade e os florins.");
        }
    }

    private boolean jogoIniciado() {
        if (cidade == null) {
            System.out.println("Nenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    private String lerTexto(String pergunta) {
        System.out.print(pergunta);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println(" SOBRE O JOGO");
        System.out.println("------------------------------------------");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e");
        System.out.println("politica na Italia medieval. Cada jogador governa uma");
        System.out.println("cidade-Estado a partir do ano de 1400, decidindo como");
        System.out.println("distribuir recursos entre agricultura, impostos,");
        System.out.println("comercio, obras e defesa.");
        System.out.println();
        System.out.println("Etapas implementadas: economia, agricultura, governo e defesa.");
        System.out.println("Recursos atuais: florins, terras, graos, servos, soldados");
        System.out.println("e construcoes. Configuracoes: taxa de imposto e justica.");
        System.out.println();
        System.out.println("Objetivo futuro: subir na nobreza ate se tornar Rei ou Rainha:");
        System.out.println("Cavaleiro > Barao > Conde > Marques > Duque >");
        System.out.println("Grao-Duque > Principe > Rei/Rainha.");
        System.out.println("------------------------------------------");
    }
}