package pkg_database;

public class Projeto {
    private String id;
    private String nome;
    private String dataInicial;
    private String dataFinal;
    private String engenheiroResponsavel;
    private String status;

    public Projeto(String id, String nome, String dataInicial, String dataFinal, String engenheiroResponsavel, String status) {
        this.id = id;
        this.nome = nome;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.engenheiroResponsavel = engenheiroResponsavel;
        this.status = status;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDataInicial() { return dataInicial; }
    public String getDataFinal() { return dataFinal; }
    public String getEngenheiroResponsavel() { return engenheiroResponsavel; }
    public String getStatus() { return status; }
}