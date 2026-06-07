package pkg_telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pkg_database.UsuarioDAO;

public class TelaDeLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaDeLogin() {
        setTitle("Login - Sistema de Gestão Empresarial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(EstiloGlobal.COR_FUNDO);
        contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
        contentPane.setLayout(new BorderLayout(0, 20));
        setContentPane(contentPane);

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.setBackground(EstiloGlobal.COR_FUNDO);
        JLabel lblTitulo = new JLabel("Acesso ao Sistema");
        lblTitulo.setFont(EstiloGlobal.FONTE_TITULO);
        lblTitulo.setForeground(EstiloGlobal.COR_PRINCIPAL);
        painelTitulo.add(lblTitulo);
        contentPane.add(painelTitulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(EstiloGlobal.COR_FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painelFormulario.add(EstiloGlobal.criarLabel("Usuário:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(EstiloGlobal.FONTE_PADRAO);
        painelFormulario.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painelFormulario.add(EstiloGlobal.criarLabel("Senha:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(EstiloGlobal.FONTE_PADRAO);
        painelFormulario.add(txtSenha, gbc);

        contentPane.add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        painelBotoes.setBackground(EstiloGlobal.COR_FUNDO);

        JButton btnEntrar = EstiloGlobal.criarBotao("Entrar");
        JButton btnSair = EstiloGlobal.criarBotao("Sair");

        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnSair);
        contentPane.add(painelBotoes, BorderLayout.SOUTH);

        btnEntrar.addActionListener(e -> executarAcaoLogin());
        txtSenha.addActionListener(e -> executarAcaoLogin());
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void executarAcaoLogin() {
        String login = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o usuário e a senha!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            if (usuarioDAO.validarLogin(login, senha)) {
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
                txtUsuario.requestFocus();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados:\n" + ex.getMessage(), "Falha no Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}