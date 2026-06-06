package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EngenheiroDAO {

    public Engenheiro buscarPorId(String id) {
        String sql = "SELECT * FROM tb_engenheiros WHERE id_engenheiro = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setString(1, id);
            
            try (ResultSet resultado = preparar.executeQuery()) {
                if (resultado.next()) {
                    return new Engenheiro(
                        resultado.getString("id_engenheiro"),
                        resultado.getString("nome_completo"),
                        resultado.getString("cpf"),
                        resultado.getString("email"),
                        resultado.getString("telefone")
                    );
                }
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao buscar engenheiro: " + erro.getMessage());
        }
        return null;
    }

    public List<Engenheiro> listarTodos() {
        List<Engenheiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_engenheiros";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql);
             ResultSet resultado = preparar.executeQuery()) {
            
            while (resultado.next()) {
                lista.add(new Engenheiro(
                    resultado.getString("id_engenheiro"),
                    resultado.getString("nome_completo"),
                    resultado.getString("cpf"),
                    resultado.getString("email"),
                    resultado.getString("telefone")
                ));
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao listar engenheiros: " + erro.getMessage());
        }
        return lista;
    }
}