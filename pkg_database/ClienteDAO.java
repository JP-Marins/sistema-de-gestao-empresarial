package pkg_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pkg_database.Cliente;

public class ClienteDAO {

    // 1. MÉTODO PARA SALVAR/INSERIR CLIENTE
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO tb_clientes (nome_cliente, cpf_cnpj, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 2. MÉTODO PARA LISTAR TODOS OS CLIENTES (Para preencher a JTable)
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM tb_clientes ORDER BY nome_cliente ASC";
        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_cliente"));
                c.setNome(rs.getString("nome_cliente"));
                c.setCpfCnpj(rs.getString("cpf_cnpj"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    // 3. MÉTODO PARA EXCLUIR CLIENTE
    public void excluir(int id) {
        String sql = "DELETE FROM tb_clientes WHERE id_cliente = ?";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}