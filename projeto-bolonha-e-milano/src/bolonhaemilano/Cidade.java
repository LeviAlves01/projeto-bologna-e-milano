package bolonhaemilano;

public class Cidade {

    public static final int ANO_INICIAL = 1400;

    private String nome;
    private int anoAtual;
    private Jogador dono;

    private int florins;
    private int terras;
    private int graos;
    private int servos;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = ANO_INICIAL;

        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
        this.servos = 200;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void adicionarFlorins(int quantidade) {
        if (quantidade > 0) {
            florins += quantidade;
        }
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade <= 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    public void adicionarTerras(int quantidade) {
        if (quantidade > 0) {
            terras += quantidade;
        }
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade <= 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    public void adicionarGraos(int quantidade) {
        if (quantidade > 0) {
            graos += quantidade;
        }
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade <= 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    public void adicionarServos(int quantidade) {
        if (quantidade > 0) {
            servos += quantidade;
        }
    }

    public boolean removerServos(int quantidade) {
        if (quantidade <= 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    public void exibirStatus() {
        System.out.println("------------------------------------------");
        System.out.println(" STATUS DA CIDADE");
        System.out.println("------------------------------------------");
        System.out.println(" Cidade     : " + nome);
        System.out.println(" Governante : " + dono.getTitulo() + " " + dono.getNome());
        System.out.println(" Ano atual  : " + anoAtual);
        System.out.println(" Florins    : " + florins);
        System.out.println(" Terras     : " + terras + " hectares");
        System.out.println(" Graos      : " + graos);
        System.out.println(" Servos     : " + servos);
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
    public int getFlorins() {
        return florins;
    }

    public int getTerras() {
        return terras;
    }

    public int getGraos() {
        return graos;
    }

    public int getServos() {
        return servos;
    }
}
