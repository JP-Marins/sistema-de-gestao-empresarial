package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EngenheiroDAO {

    public void inserir(Engenheiro engenheiro) {
        String sql = "INSERT INTO tb_engenheiros (nome_completo, cpf, email, telefone) VALUES (?, ?, ?, ?)";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            preparar.setString(1, engenheiro.getNomeCompleto());
            preparar.setString(2, engenheiro.getCpf());
            preparar.setString(3, engenheiro.getEmail());
            preparar.setString(4, engenheiro.getTelefone());
            
            preparar.executeUpdate();
            
            try (ResultSet rs = preparar.getGeneratedKeys()) {
                if (rs.next()) {
                    engenheiro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir engenheiro: " + e.getMessage(), e);
        }
    }

    public List<Engenheiro> listarTodos() {
        List<Engenheiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_engenheiros ORDER BY nome_completo";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql);
             ResultSet resultado = preparar.executeQuery()) {
            
            while (resultado.next()) {
                lista.add(new Engenheiro(
                    resultado.getInt("id_engenheiro"),
                    resultado.getString("nome_completo"),
                    resultado.getString("cpf"),
                    resultado.getString("email"),
                    resultado.getString("telefone")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar engenheiros: " + e.getMessage(), e);
        }
        return lista;
    }

    public Engenheiro buscarPorId(int id) {
        String sql = "SELECT * FROM tb_engenheiros WHERE id_engenheiro = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            try (ResultSet resultado = preparar.executeQuery()) {
                if (resultado.next()) {
                    return new Engenheiro(
                        resultado.getInt("id_engenheiro"),
                        resultado.getString("nome_completo"),
                        resultado.getString("cpf"),
                        resultado.getString("email"),
                        resultado.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar engenheiro por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Engenheiro engenheiro) {
        String sql = "UPDATE tb_engenheiros SET nome_completo = ?, cpf = ?, email = ?, telefone = ? WHERE id_engenheiro = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setString(1, engenheiro.getNomeCompleto());
            preparar.setString(2, engenheiro.getCpf());
            preparar.setString(3, engenheiro.getEmail());
            preparar.setString(4, engenheiro.getTelefone());
            preparar.setInt(5, engenheiro.getId());
            
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar engenheiro: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_engenheiros WHERE id_engenheiro = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir engenheiro: " + e.getMessage(), e);
        }
    }
}