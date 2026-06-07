package pkg_database;

public class Projeto {
    private int id;
    private String nome;
    private String dataInicial;
    private String dataFinal;
    private String engenheiroResponsavel;
    private String status;

    public Projeto(int id, String nome, String dataInicial, String dataFinal, String engenheiroResponsavel, String status) {
        this.id = id;
        this.nome = nome;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.engenheiroResponsavel = engenheiroResponsavel;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataInicial() { return dataInicial; }
    public void setDataInicial(String dataInicial) { this.dataInicial = dataInicial; }

    public String getDataFinal() { return dataFinal; }
    public void setDataFinal(String dataFinal) { this.dataFinal = dataFinal; }

    public String getEngenheiroResponsavel() { return engenheiroResponsavel; }
    public void setEngenheiroResponsavel(String engenheiroResponsavel) { this.engenheiroResponsavel = engenheiroResponsavel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}