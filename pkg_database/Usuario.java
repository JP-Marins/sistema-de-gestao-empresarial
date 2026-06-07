package pkg_database;

public class Usuario {
    private int id;
    private String usuario;
    private String senha;
    private String perfil;

    public Usuario(int id, String usuario, String senha, String perfil) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}