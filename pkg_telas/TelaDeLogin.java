package pkg_telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
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
	
	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;
	private final Font FONTE_PADRAO = new Font("Tahoma", Font.PLAIN, 14);
	private final Font FONTE_TITULO = new Font("Tahoma", Font.BOLD, 24);

	public TelaDeLogin() {
		setTitle("Login - Construtora Eco");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		contentPane.setLayout(new BorderLayout(0, 20));
		setContentPane(contentPane);

		JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTitulo.setBackground(COR_FUNDO);
		JLabel lblTitulo = new JLabel("Acesso ao Sistema");
		lblTitulo.setFont(FONTE_TITULO);
		lblTitulo.setForeground(COR_PRINCIPAL);
		painelTitulo.add(lblTitulo);
		contentPane.add(painelTitulo, BorderLayout.NORTH);

		JPanel painelFormulario = new JPanel(new GridBagLayout());
		painelFormulario.setBackground(COR_FUNDO);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 0;
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setFont(FONTE_PADRAO);
		painelFormulario.add(lblUsuario, gbc);

		gbc.gridx = 1; gbc.gridy = 0;
		gbc.weightx = 1.0;
		txtUsuario = new JTextField(15);
		txtUsuario.setFont(FONTE_PADRAO);
		painelFormulario.add(txtUsuario, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.weightx = 0;
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(FONTE_PADRAO);
		painelFormulario.add(lblSenha, gbc);

		gbc.gridx = 1; gbc.gridy = 1;
		gbc.weightx = 1.0;
		txtSenha = new JPasswordField(15);
		txtSenha.setFont(FONTE_PADRAO);
		painelFormulario.add(txtSenha, gbc);

		contentPane.add(painelFormulario, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		painelBotoes.setBackground(COR_FUNDO);

		JButton btnEntrar = criarBotao("Entrar");
		JButton btnSair = criarBotao("Sair");

		painelBotoes.add(btnEntrar);
		painelBotoes.add(btnSair);
		contentPane.add(painelBotoes, BorderLayout.SOUTH);

		// Eventos corrigidos com X
		btnEntrar.addActionListener(e -> executarAcaoLogin());
		txtSenha.addActionListener(e -> executarAcaoLogin());
		btnSair.addActionListener(e -> System.exit(0));
	}

	// Método corrigido para "executarAcaoLogin" (com X)
	private void executarAcaoLogin() {
		String login = txtUsuario.getText().trim();
		String senha = new String(txtSenha.getPassword()).trim();

		if (login.isEmpty() || senha.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, insira o usuário e a senha!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		if (usuarioDAO.validarLogin(login, senha)) {
			JOptionPane.showMessageDialog(this, "Autenticação realizada com sucesso!", "Bem-vindo", JOptionPane.INFORMATION_MESSAGE);
			
			TelaPrincipal telaPrincipal = new TelaPrincipal();
			telaPrincipal.setVisible(true);
			
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
			txtSenha.setText("");
			txtUsuario.requestFocus();
		}
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setFont(new Font("Tahoma", Font.BOLD, 13));
		botao.setBackground(COR_PRINCIPAL);
		botao.setForeground(Color.WHITE);
		botao.setFocusPainted(false);
		botao.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
		return botao;
	}
}