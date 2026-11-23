package bolonhaemilano;

import java.util.Random;

public class Eventos {

    public static final int TIPO_NENHUM = 0;
    public static final int TIPO_SECA = 1;
    public static final int TIPO_PESTE = 2;

    private static final int CHANCE_SECA = 15;
    private static final int CHANCE_PESTE = 10;
    private static final int PERCENTUAL_PERDA_SECA = 40;
    private static final int PERCENTUAL_PERDA_PESTE = 20;

    private Random random;

    public Eventos() {
        this.random = new Random();
    }

    public Eventos(Random random) {
        this.random = random;
    }

    public int sortearEvento(Cidade cidade) {
        int sorteio = random.nextInt(100);

        if (sorteio < CHANCE_SECA) {
            aplicarSeca(cidade);
            return TIPO_SECA;
        }
        if (sorteio < CHANCE_SECA + CHANCE_PESTE) {
            aplicarPeste(cidade);
            return TIPO_PESTE;
        }
        return TIPO_NENHUM;
    }

    public int aplicarSeca(Cidade cidade) {
        int perda = cidade.getGraos() * PERCENTUAL_PERDA_SECA / 100;
        cidade.removerGraos(perda);
        return perda;
    }

    public int aplicarPeste(Cidade cidade) {
        int perda = cidade.getServos() * PERCENTUAL_PERDA_PESTE / 100;
        cidade.removerServos(perda);
        return perda;
    }
}