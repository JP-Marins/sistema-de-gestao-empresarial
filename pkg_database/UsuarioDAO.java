package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

	// C - CREATE: Inserir um novo utilizador
	public void inserir(Usuario usuario) {
		String sql = "INSERT INTO tb_usuarios (usuario, senha, perfil) VALUES (?, ?, ?)";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, usuario.getUsuario());
			preparar.setString(2, usuario.getSenha());
			preparar.setString(3, usuario.getPerfil());
			
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao inserir utilizador: " + erro.getMessage());
		}
	}

	// R - READ: Listar todos os utilizadores cadastrados
	public List<Usuario> listarTodos() {
		List<Usuario> lista = new ArrayList<>();
		String sql = "SELECT * FROM tb_usuarios";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql);
			 ResultSet resultado = preparar.executeQuery()) {
			
			while (resultado.next()) {
				Usuario u = new Usuario(
					resultado.getString("id_usuario"),
					resultado.getString("usuario"),
					resultado.getString("senha"),
					resultado.getString("perfil")
				);
				lista.add(u);
			}
		} catch (SQLException erro) {
			System.err.println("Erro ao listar utilizadores: " + erro.getMessage());
		}
		return lista;
	}

	// U - UPDATE: Alterar dados de um utilizador existente
	public void atualizar(Usuario usuario) {
		String sql = "UPDATE tb_usuarios SET usuario = ?, senha = ?, perfil = ? WHERE id_usuario = ?";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, usuario.getUsuario());
			preparar.setString(2, usuario.getSenha());
			preparar.setString(3, usuario.getPerfil());
			preparar.setString(4, usuario.getId());
			
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao atualizar utilizador: " + erro.getMessage());
		}
	}

	// D - DELETE: Remover um utilizador do banco de dados pelo ID
	public void excluir(String id) {
		String sql = "DELETE FROM tb_usuarios WHERE id_usuario = ?";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, id);
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao excluir utilizador: " + erro.getMessage());
		}
	}

	/**
	 * CORREÇÃO: Método para autenticação na Tela de Login.
	 * Procura no banco de dados se existe a combinação exata de usuário e senha.
	 */
	public boolean validarLogin(String login, String senha) {
		String sql = "SELECT * FROM tb_usuarios WHERE usuario = ? AND senha = ?";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, login);
			preparar.setString(2, senha);
			
			try (ResultSet resultado = preparar.executeQuery()) {
				if (resultado.next()) {
					return true; // Credenciais corretas, utilizador encontrado!
				}
			}
		} catch (SQLException erro) {
			System.err.println("Erro ao validar login: " + erro.getMessage());
		}
		return false; // Retorna falso se não encontrar ou se ocorrer um erro
	}
}