package pkg_database;

public class Usuario {

	private String id;
	private String usuario;
	private String senha;
	private String perfil;

	// Construtor padrão (vazio) - útil para frameworks e manipulações simples
	public Usuario() {
	}

	// Construtor completo - utilizado pelo DAO para instanciar os dados vindos do MySQL
	public Usuario(String id, String usuario, String senha, String perfil) {
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.perfil = perfil;
	}

	// --- GETTERS E SETTERS ---

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}
