package bolonhaemilano;

public class Cidade {

    public static final int ANO_INICIAL = 1400;

    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = ANO_INICIAL;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void exibirStatus() {
        System.out.println("------------------------------------------");
        System.out.println(" STATUS DA CIDADE");
        System.out.println("------------------------------------------");
        System.out.println(" Cidade     : " + nome);
        System.out.println(" Governante : " + dono.getTitulo() + " " + dono.getNome());
        System.out.println(" Ano atual  : " + anoAtual);
        System.out.println("------------------------------------------");
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }
}
