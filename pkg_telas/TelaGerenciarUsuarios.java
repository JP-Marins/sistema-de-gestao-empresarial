package pkg_telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

// Importações das classes de persistência
import pkg_database.Usuario;
import pkg_database.UsuarioDAO;

public class TelaGerenciarUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaUsuarios;
	private DefaultTableModel modeloTabela;
	
	private final Color COR_PRINCIPAL = new Color(0, 146, 69); // Verde Eco
	private final Color COR_FUNDO = Color.WHITE;
	private final Color COR_PAINEIS = Color.decode("#F8F9FA");
	private final Font FONTE_PADRAO = new Font("Tahoma", Font.PLAIN, 14);
	private final Font FONTE_TITULO = new Font("Tahoma", Font.BOLD, 22);

	private JTextField txtId, txtUsuario;
	private JPasswordField txtSenha;
	private JComboBox<String> comboPerfil;
	
	// Instância do DAO para persistência real
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public TelaGerenciarUsuarios() {
		setTitle("Gerenciamento de Usuários - Construtora Eco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);

		// --- PAINEL SUPERIOR: TÍTULO ---
		JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTitulo.setBackground(COR_FUNDO);
		JLabel lblTitulo = new JLabel("Controle de Usuários e Acessos");
		lblTitulo.setFont(FONTE_TITULO);
		lblTitulo.setForeground(COR_PRINCIPAL);
		painelTitulo.add(lblTitulo);
		contentPane.add(painelTitulo, BorderLayout.NORTH);

		// --- PAINEL CENTRAL: TABELA ---
		String[] colunas = {"ID", "Nome de Usuário", "Perfil de Acesso"};
		modeloTabela = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaUsuarios = new JTable(modeloTabela);
		tabelaUsuarios.setFont(FONTE_PADRAO);
		tabelaUsuarios.setRowHeight(22);
		
		JScrollPane scrollTabela = new JScrollPane(tabelaUsuarios);
		contentPane.add(scrollTabela, BorderLayout.CENTER);

		// --- PAINEL INFERIOR: FORMULÁRIO ---
		JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
		painelInferior.setBackground(COR_FUNDO);

		JPanel painelFormulario = new JPanel(new GridBagLayout());
		painelFormulario.setBackground(COR_PAINEIS);
		painelFormulario.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COR_PRINCIPAL), "Dados do Usuário", 0, 0, FONTE_PADRAO, COR_PRINCIPAL));

		// Linha 0: ID
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(10, 10, 5, 5);
		gbc_lblId.gridx = 0; gbc_lblId.gridy = 0;
		gbc_lblId.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("ID:"), gbc_lblId);

		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.insets = new Insets(10, 5, 5, 10);
		gbc_txtId.gridx = 1; gbc_txtId.gridy = 0;
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		txtId = new JTextField(5);
		txtId.setEditable(false);
		painelFormulario.add(txtId, gbc_txtId);

		// Linha 0: Usuário
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(10, 10, 5, 5);
		gbc_lblUsuario.gridx = 2; gbc_lblUsuario.gridy = 0;
		gbc_lblUsuario.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Usuário:"), gbc_lblUsuario);

		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.insets = new Insets(10, 5, 5, 10);
		gbc_txtUsuario.gridx = 3; gbc_txtUsuario.gridy = 0;
		gbc_txtUsuario.weightx = 1.0;
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		txtUsuario = new JTextField(15);
		painelFormulario.add(txtUsuario, gbc_txtUsuario);

		// Linha 1: Senha
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.insets = new Insets(5, 10, 10, 5);
		gbc_lblSenha.gridx = 0; gbc_lblSenha.gridy = 1;
		gbc_lblSenha.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Senha:"), gbc_lblSenha);

		GridBagConstraints gbc_txtSenha = new GridBagConstraints();
		gbc_txtSenha.insets = new Insets(5, 5, 10, 5);
		gbc_txtSenha.gridx = 1; gbc_txtSenha.gridy = 1;
		gbc_txtSenha.fill = GridBagConstraints.HORIZONTAL;
		txtSenha = new JPasswordField(10);
		painelFormulario.add(txtSenha, gbc_txtSenha);

		// Linha 1: Perfil
		GridBagConstraints gbc_lblPerfil = new GridBagConstraints();
		gbc_lblPerfil.insets = new Insets(5, 10, 10, 5);
		gbc_lblPerfil.gridx = 2; gbc_lblPerfil.gridy = 1;
		gbc_lblPerfil.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Perfil:"), gbc_lblPerfil);

		GridBagConstraints gbc_comboPerfil = new GridBagConstraints();
		gbc_comboPerfil.insets = new Insets(5, 5, 10, 10);
		gbc_comboPerfil.gridx = 3; gbc_comboPerfil.gridy = 1;
		gbc_comboPerfil.fill = GridBagConstraints.HORIZONTAL;
		comboPerfil = new JComboBox<>(new String[] {"Administrador", "Engenheiro", "Financeiro", "Operador"});
		comboPerfil.setFont(FONTE_PADRAO);
		painelFormulario.add(comboPerfil, gbc_comboPerfil);

		painelInferior.add(painelFormulario, BorderLayout.CENTER);

		// Painel de Botões
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		painelBotoes.setBackground(COR_FUNDO);

		JButton btnSalvar = criarBotao("Cadastrar");
		JButton btnAlterar = criarBotao("Alterar");
		JButton btnExcluir = criarBotao("Excluir");

		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnAlterar);
		painelBotoes.add(btnExcluir);

		painelInferior.add(painelBotoes, BorderLayout.SOUTH);
		contentPane.add(painelInferior, BorderLayout.SOUTH);

		// --- INTERAÇÕES DO CRUD REAL ---

		// CADASTRAR
		btnSalvar.addActionListener(e -> {
			String senha = new String(txtSenha.getPassword());
			if (txtUsuario.getText().trim().isEmpty() || senha.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha o usuário e a senha!");
				return;
			}
			Usuario u = new Usuario(null, txtUsuario.getText(), senha, comboPerfil.getSelectedItem().toString());
			usuarioDAO.inserir(u);
			JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
			limparCampos();
			atualizarTabelaReal();
		});

		// ALTERAR
		btnAlterar.addActionListener(e -> {
			if (txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para alterar!");
				return;
			}
			String senha = new String(txtSenha.getPassword());
			if (senha.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Digite a senha atual ou uma nova senha para confirmar a alteração.");
				return;
			}
			Usuario u = new Usuario(txtId.getText(), txtUsuario.getText(), senha, comboPerfil.getSelectedItem().toString());
			usuarioDAO.atualizar(u);
			JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
			limparCampos();
			atualizarTabelaReal();
		});

		// EXCLUIR
		btnExcluir.addActionListener(e -> {
			if (txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para excluir!");
				return;
			}
			int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
			if (confirmacao == JOptionPane.YES_OPTION) {
				usuarioDAO.excluir(txtId.getText());
				JOptionPane.showMessageDialog(this, "Usuário removido.");
				limparCampos();
				atualizarTabelaReal();
			}
		});

		// CLIQUE NA TABELA
		tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabelaUsuarios.getSelectedRow() != -1) {
				int linha = tabelaUsuarios.getSelectedRow();
				txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
				txtUsuario.setText(modeloTabela.getValueAt(linha, 1).toString());
				comboPerfil.setSelectedItem(modeloTabela.getValueAt(linha, 2).toString());
				txtSenha.setText(""); // Por segurança, pede para digitar a senha novamente
			}
		});

		// Inicializa buscando do banco
		atualizarTabelaReal();
	}

	private JLabel criarLabel(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(FONTE_PADRAO);
		label.setForeground(Color.BLACK);
		return label;
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setFont(new Font("Tahoma", Font.BOLD, 13));
		botao.setBackground(COR_PRINCIPAL);
		botao.setForeground(Color.WHITE);
		botao.setFocusPainted(false);
		botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		return botao;
	}

	private void limparCampos() {
		txtId.setText("");
		txtUsuario.setText("");
		txtSenha.setText("");
		comboPerfil.setSelectedIndex(0);
		tabelaUsuarios.clearSelection();
	}

	private void atualizarTabelaReal() {
		modeloTabela.setRowCount(0);
		List<Usuario> lista = usuarioDAO.listarTodos();
		for (Usuario u : lista) {
			modeloTabela.addRow(new Object[] {
				u.getId(),
				u.getUsuario(),
				u.getPerfil()
			});
		}
	}
}