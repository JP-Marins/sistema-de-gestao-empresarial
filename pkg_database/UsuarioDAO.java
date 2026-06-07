package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO tb_usuarios (usuario, senha, perfil) VALUES (?, ?, ?)";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            preparar.setString(1, usuario.getUsuario());
            preparar.setString(2, usuario.getSenha());
            preparar.setString(3, usuario.getPerfil());
            
            preparar.executeUpdate();
            
            try (ResultSet rs = preparar.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_usuarios ORDER BY usuario";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql);
             ResultSet resultado = preparar.executeQuery()) {
            
            while (resultado.next()) {
                Usuario u = new Usuario(
                    resultado.getInt("id_usuario"),
                    resultado.getString("usuario"),
                    resultado.getString("senha"),
                    resultado.getString("perfil")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return lista;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM tb_usuarios WHERE id_usuario = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            try (ResultSet resultado = preparar.executeQuery()) {
                if (resultado.next()) {
                    return new Usuario(
                        resultado.getInt("id_usuario"),
                        resultado.getString("usuario"),
                        resultado.getString("senha"),
                        resultado.getString("perfil")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE tb_usuarios SET usuario = ?, senha = ?, perfil = ? WHERE id_usuario = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setString(1, usuario.getUsuario());
            preparar.setString(2, usuario.getSenha());
            preparar.setString(3, usuario.getPerfil());
            preparar.setInt(4, usuario.getId());
            
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_usuarios WHERE id_usuario = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setInt(1, id);
            preparar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage(), e);
        }
    }

    public boolean validarLogin(String login, String senha) {
        String sql = "SELECT * FROM tb_usuarios WHERE usuario = ? AND senha = ?";
        
        try (Connection conectar = Conexao.getConexao();
             PreparedStatement preparar = conectar.prepareStatement(sql)) {
            
            preparar.setString(1, login);
            preparar.setString(2, senha);
            
            try (ResultSet resultado = preparar.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar login: " + e.getMessage(), e);
        }
    }
}