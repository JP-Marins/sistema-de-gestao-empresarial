package pkg_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String URL = "jdbc:mysql://localhost:3306/bd_engenheiros";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL não encontrado: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}