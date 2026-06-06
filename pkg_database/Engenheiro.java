package pkg_database;

public class Engenheiro {
    private String id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;

    public Engenheiro(String id, String nomeCompleto, String cpf, String email, String telefone) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public String getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
}