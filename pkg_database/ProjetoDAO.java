package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public void inserir(Projeto projeto) {
        String sql = "INSERT INTO tb_projetos (nome_projeto, data_inicial, data_final, engenheiro_responsavel, status_do_projeto) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            preparar.setString(1, projeto.getNome());
            preparar.setString(2, projeto.getDataInicial());
            preparar.setString(3, projeto.getDataFinal());
            preparar.setString(4, projeto.getEngenheiroResponsavel());
            preparar.setString(5, projeto.getStatus());
            
            preparar.executeUpdate();
            
            try (ResultSet rs = preparar.getGeneratedKeys()) {
                if (rs.next()) {
                    projeto.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir projeto: " + e.getMessage(), e);
        }
    }

    public List<Projeto> listarTodos() {
        List<Projeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_projetos ORDER BY nome_projeto";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql);
             ResultSet resultado = preparar.executeQuery()) {
            
            while (resultado.next()) {
                Projeto p = new Projeto(
                    resultado.getInt("id_projeto"),
                    resultado.getString("nome_projeto"),
                    resultado.getString("data_inicial"),
                    resultado.getString("data_final"),
                    resultado.getString("engenheiro_responsavel"),
                    resultado.getString("status_do_projeto")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar projetos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Projeto buscarPorId(int id) {
        String sql = "SELECT * FROM tb_projetos WHERE id_projeto = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            try (ResultSet resultado = preparar.executeQuery()) {
                if (resultado.next()) {
                    return new Projeto(
                        resultado.getInt("id_projeto"),
                        resultado.getString("nome_projeto"),
                        resultado.getString("data_inicial"),
                        resultado.getString("data_final"),
                        resultado.getString("engenheiro_responsavel"),
                        resultado.getString("status_do_projeto")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar projeto por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Projeto projeto) {
        String sql = "UPDATE tb_projetos SET nome_projeto = ?, data_inicial = ?, data_final = ?, engenheiro_responsavel = ?, status_do_projeto = ? WHERE id_projeto = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setString(1, projeto.getNome());
            preparar.setString(2, projeto.getDataInicial());
            preparar.setString(3, projeto.getDataFinal());
            preparar.setString(4, projeto.getEngenheiroResponsavel());
            preparar.setString(5, projeto.getStatus());
            preparar.setInt(6, projeto.getId());
            
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar projeto: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_projetos WHERE id_projeto = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir projeto: " + e.getMessage(), e);
        }
    }
}