package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private static final int OPCAO_SAIR = 0;
    private static final int OPCAO_NOVO_JOGO = 1;
    private static final int OPCAO_STATUS = 2;
    private static final int OPCAO_SOBRE = 3;
    private static final int OPCAO_INVALIDA = -1;

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
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
        System.out.println("3 - Sobre o jogo");
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
            case OPCAO_SOBRE:
                exibirSobre();
                break;
            case OPCAO_SAIR:
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida! Digite 1, 2, 3 ou 0.");
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
        if (cidade == null) {
            System.out.println("Nenhum jogo iniciado. Escolha 'Novo jogo' primeiro.");
            return;
        }
        cidade.exibirStatus();
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
        System.out.println("A cada rodada anual voce compra e vende graos e terras,");
        System.out.println("define a alimentacao do povo, os impostos e a justica,");
        System.out.println("e investe em mercados, moinhos, palacios, catedrais e");
        System.out.println("soldados.");
        System.out.println();
        System.out.println("Objetivo: subir na nobreza ate se tornar Rei ou Rainha:");
        System.out.println("Cavaleiro > Barao > Conde > Marques > Duque >");
        System.out.println("Grao-Duque > Principe > Rei/Rainha.");
        System.out.println("------------------------------------------");
    }
}
