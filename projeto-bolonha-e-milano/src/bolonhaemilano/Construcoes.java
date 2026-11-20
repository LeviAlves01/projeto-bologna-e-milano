package bolonhaemilano;

public class Construcoes {

    private static final int PRECO_SOLDADO = 5;
    private static final int SERVOS_POR_SOLDADO = 1;
    private static final int PRECO_CONSTRUCAO = 20;

    public int getPrecoSoldado() {
        return PRECO_SOLDADO;
    }

    public int getPrecoConstrucao() {
        return PRECO_CONSTRUCAO;
    }

    public boolean recrutarSoldados(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        int custo = quantidade * PRECO_SOLDADO;
        int servosNecessarios = quantidade * SERVOS_POR_SOLDADO;

        if (cidade.getFlorins() < custo || cidade.getServos() < servosNecessarios) {
            return false;
        }

        cidade.removerFlorins(custo);
        cidade.removerServos(servosNecessarios);
        cidade.adicionarSoldados(quantidade);
        return true;
    }

    public boolean construir(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        int custo = quantidade * PRECO_CONSTRUCAO;

        if (!cidade.removerFlorins(custo)) {
            return false;
        }

        cidade.adicionarConstrucoes(quantidade);
        return true;
    }
}