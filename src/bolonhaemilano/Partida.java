package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;

    private List<Cidade> cidades;
    private int indiceAtual;

    public Partida() {
        this.cidades = new ArrayList<>();
        this.indiceAtual = 0;
    }

    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public Cidade getCidadeAtual() {
        return cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        if (cidades.isEmpty()) {
            return 0;
        }
        return cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    public boolean passarTurno() {
        indiceAtual++;
        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true; // o ano acabou
        }
        return false;
    }

    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    public boolean terminou() {
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }
        return getAnoAtual() > ANO_FINAL;
    }

    public Cidade determinarVencedor(Nobreza nobreza) {
        Cidade vencedor = null;
        for (Cidade cidade : cidades) {
            if (vencedor == null || ehMelhor(cidade, vencedor, nobreza)) {
                vencedor = cidade;
            }
        }
        return vencedor;
    }

    private boolean ehMelhor(Cidade candidata, Cidade atual, Nobreza nobreza) {
        int nivelCandidata = candidata.getDono().getNivelTitulo();
        int nivelAtual = atual.getDono().getNivelTitulo();

        if (nivelCandidata != nivelAtual) {
            return nivelCandidata > nivelAtual;
        }
        return nobreza.calcularPrestigio(candidata) > nobreza.calcularPrestigio(atual);
    }
}