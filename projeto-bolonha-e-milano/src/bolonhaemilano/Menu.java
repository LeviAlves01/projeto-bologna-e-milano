package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private static final int OPCAO_SAIR = 0;
    private static final int OPCAO_NOVO_JOGO = 1;
    private static final int OPCAO_STATUS = 2;
    private static final int OPCAO_MERCADO = 3;
    private static final int OPCAO_AGRICULTURA = 4;
    private static final int OPCAO_SOBRE = 5;
    private static final int OPCAO_INVALIDA = -1;

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
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
        System.out.println("5 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return OPCAO_SAIR;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return OPCAO_INVALIDA;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case OPCAO_NOVO_JOGO:
                iniciarNovoJogo();
                break;
            case OPCAO_STATUS:
                exibirStatusCidade();
                break;
            case OPCAO_MERCADO:
                abrirMercado();
                break;
            case OPCAO_AGRICULTURA:
                abrirAgricultura();
                break;
            case OPCAO_SOBRE:
                exibirSobre();
                break;
            case OPCAO_SAIR:
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida! Digite 1, 2, 3, 4, 5 ou 0.");
        }
    }

    private void iniciarNovoJogo() {
        String nomeJogador = lerTextoObrigatorio("Digite o nome do jogador: ");
        if (nomeJogador == null) {
            emExecucao = false;
            return;
        }

        String nomeCidade = lerTextoObrigatorio("Digite o nome da cidade: ");
        if (nomeCidade == null) {
            emExecucao = false;
            return;
        }

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);

        System.out.println();
        System.out.println("Novo jogo criado! Que comece o governo de " + cidade.getNome() + ".");
        cidade.exibirStatus();
    }

    private void exibirStatusCidade() {
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

    private boolean jogoIniciado() {
        if (cidade == null) {
            System.out.println("Nenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    private String lerTextoObrigatorio(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            if (!scanner.hasNextLine()) {
                return null;
            }

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("O nome nao pode ficar em branco.");
        }
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
        System.out.println("Etapas implementadas: economia, agricultura e populacao.");
        System.out.println("Recursos atuais: florins, terras, graos e servos.");
        System.out.println();
        System.out.println("Objetivo futuro: subir na nobreza ate se tornar Rei ou Rainha:");
        System.out.println("Cavaleiro > Barao > Conde > Marques > Duque >");
        System.out.println("Grao-Duque > Principe > Rei/Rainha.");
        System.out.println("------------------------------------------");
    }
}
