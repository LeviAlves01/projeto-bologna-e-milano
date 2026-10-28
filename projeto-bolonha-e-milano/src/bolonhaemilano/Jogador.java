package bolonhaemilano;

public class Jogador {

    public static final String TITULO_INICIAL = "Cavaleiro";

    private String nome;
    private String titulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.titulo = TITULO_INICIAL;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
