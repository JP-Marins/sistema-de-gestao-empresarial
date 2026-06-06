package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

	// C - CREATE: Inserir um novo projeto
	public void inserir(Projeto projeto) {
		String sql = "INSERT INTO tb_projetos (nome_projeto, data_inicial, data_final, engenheiro_responsavel, status_do_projeto) VALUES (?, ?, ?, ?, ?)";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, projeto.getNome());
			preparar.setString(2, projeto.getDataInicial());
			preparar.setString(3, projeto.getDataFinal());
			preparar.setString(4, projeto.getEngenheiroResponsavel());
			preparar.setString(5, projeto.getStatus());
			
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao inserir projeto: " + erro.getMessage());
		}
	}

	// R - READ: Listar todos os projetos cadastrados
	public List<Projeto> listarTodos() {
		List<Projeto> lista = new ArrayList<>();
		String sql = "SELECT * FROM tb_projetos";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql);
			 ResultSet resultado = preparar.executeQuery()) {
			
			while (resultado.next()) {
				Projeto p = new Projeto(
					resultado.getString("id_projeto"),
					resultado.getString("nome_projeto"),
					resultado.getString("data_inicial"),
					resultado.getString("data_final"),
					resultado.getString("engenheiro_responsavel"),
					resultado.getString("status_do_projeto")
				);
				lista.add(p);
			}
		} catch (SQLException erro) {
			System.err.println("Erro ao listar projetos: " + erro.getMessage());
		}
		return lista;
	}

	// U - UPDATE: Alterar dados de um projeto existente
	public void atualizar(Projeto projeto) {
		String sql = "UPDATE tb_projetos SET nome_projeto = ?, data_inicial = ?, data_final = ?, engenheiro_responsavel = ?, status_do_projeto = ? WHERE id_projeto = ?";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, projeto.getNome());
			preparar.setString(2, projeto.getDataInicial());
			preparar.setString(3, projeto.getDataFinal());
			preparar.setString(4, projeto.getEngenheiroResponsavel());
			preparar.setString(5, projeto.getStatus());
			preparar.setString(6, projeto.getId());
			
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao atualizar projeto: " + erro.getMessage());
		}
	}

	// D - DELETE: Remover um projeto pelo ID
	public void excluir(String id) {
		String sql = "DELETE FROM tb_projetos WHERE id_projeto = ?";
		try (Connection conectar = Conexao.getConexao();
			 PreparedStatement preparar = conectar.prepareStatement(sql)) {
			
			preparar.setString(1, id);
			preparar.executeUpdate();
		} catch (SQLException erro) {
			System.err.println("Erro ao excluir projeto: " + erro.getMessage());
		}
	}
}