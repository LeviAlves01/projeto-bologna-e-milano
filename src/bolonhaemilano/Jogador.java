package bolonhaemilano;

public class Jogador {

    private static final String[] TITULOS = {
            "Cavaleiro", "Barao", "Conde", "Marques", "Duque", "Rei/Rainha"
    };

    private String nome;
    private int nivelTitulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.nivelTitulo = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return TITULOS[nivelTitulo];
    }

    public int getNivelTitulo() {
        return nivelTitulo;
    }

    public boolean promover() {
        if (ehMonarca()) {
            return false;
        }
        nivelTitulo++;
        return true;
    }

    public boolean ehMonarca() {
        return nivelTitulo == TITULOS.length - 1;
    }
}