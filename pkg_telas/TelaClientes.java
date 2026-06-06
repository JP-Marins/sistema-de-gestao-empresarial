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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pkg_database.Cliente;
import pkg_database.ClienteDAO;

public class TelaClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome, txtCpfCnpj, txtTelefone, txtEmail;
	private JTable tabelaClientes;
	private DefaultTableModel modeloTabela;
	
	private ClienteDAO clienteDAO = new ClienteDAO();

	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;

	public TelaClientes() {
		setTitle("Gerenciamento de Clientes - Construtora Eco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setLayout(new BorderLayout(0, 20));
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Cadastro e Controle de Clientes");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setForeground(COR_PRINCIPAL);
		contentPane.add(lblTitulo, BorderLayout.NORTH);

		JPanel painelCentral = new JPanel(new BorderLayout(0, 20));
		painelCentral.setBackground(COR_FUNDO);

		JPanel formulario = new JPanel(new GridBagLayout());
		formulario.setBackground(COR_FUNDO);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
		formulario.add(new JLabel("Nome / Razão Social:"), gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		txtNome = new JTextField();
		formulario.add(txtNome, gbc);

		gbc.gridx = 2; gbc.weightx = 0;
		formulario.add(new JLabel("CPF / CNPJ:"), gbc);
		gbc.gridx = 3; gbc.weightx = 0.5;
		txtCpfCnpj = new JTextField(12);
		formulario.add(txtCpfCnpj, gbc);

		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
		formulario.add(new JLabel("Telefone:"), gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		txtTelefone = new JTextField();
		formulario.add(txtTelefone, gbc);

		gbc.gridx = 2; gbc.weightx = 0;
		formulario.add(new JLabel("E-mail:"), gbc);
		gbc.gridx = 3; gbc.weightx = 0.5;
		txtEmail = new JTextField();
		formulario.add(txtEmail, gbc);

		painelCentral.add(formulario, BorderLayout.NORTH);

		String[] colunas = {"ID", "Nome", "CPF/CNPJ", "Telefone", "E-mail"};
		modeloTabela = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tabelaClientes = new JTable(modeloTabela);
		JScrollPane scrollTabela = new JScrollPane(tabelaClientes);
		painelCentral.add(scrollTabela, BorderLayout.CENTER);

		contentPane.add(painelCentral, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		painelBotoes.setBackground(COR_FUNDO);

		JButton btnSalvar = criarBotao("Salvar");
		JButton btnExcluir = criarBotao("Excluir");
		JButton btnVoltar = criarBotao("Voltar");
		btnVoltar.setBackground(Color.GRAY);

		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnVoltar);
		contentPane.add(painelBotoes, BorderLayout.SOUTH);

		// --- CONFIGURAÇÃO DOS EVENTOS ---
		
		// Evento Salvar
		btnSalvar.addActionListener(e -> salvarCliente());

		// Evento Excluir
		btnExcluir.addActionListener(e -> excluirCliente());

		// Evento Voltar
		btnVoltar.addActionListener(e -> this.dispose());
		
		// Inicializa a tabela carregando os dados do banco ao abrir a janela
		atualizarTabela();
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setFont(new Font("Tahoma", Font.BOLD, 13));
		botao.setBackground(COR_PRINCIPAL);
		botao.setForeground(Color.WHITE);
		botao.setFocusPainted(false);
		botao.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		return botao;
	}

	// --- MÉTODOS AUXILIARES DE LÓGICA ---

	private void atualizarTabela() {
		// Limpa todas as linhas atuais da tabela
		modeloTabela.setRowCount(0);
		
		// Busca a lista atualizada do banco
		List<Cliente> clientes = clienteDAO.listarTodos();
		
		// Adiciona as linhas dinamicamente
		for (Cliente c : clientes) {
			Object[] linha = { c.getId(), c.getNome(), c.getCpfCnpj(), c.getTelefone(), c.getEmail() };
			modeloTabela.addRow(linha);
		}
	}

	private void salvarCliente() {
		// Validação básica de campos obrigatórios
		if (txtNome.getText().trim().isEmpty() || txtCpfCnpj.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nome e CPF/CNPJ são campos obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Cria o objeto modelo e popula com os dados da tela
		Cliente c = new Cliente();
		c.setNome(txtNome.getText().trim());
		c.setCpfCnpj(txtCpfCnpj.getText().trim());
		c.setTelefone(txtTelefone.getText().trim());
		c.setEmail(txtEmail.getText().trim());

		// Envia para o banco de dados
		clienteDAO.inserir(c);

		// Limpa os campos da tela
		txtNome.setText("");
		txtCpfCnpj.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");

		// Atualiza a tabela na tela
		atualizarTabela();
	}

	private void excluirCliente() {
		int linhaSelecionada = tabelaClientes.getSelectedRow();
		
		// Verifica se o usuário selecionou alguma linha na tabela antes de clicar
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Pega o ID que está armazenado na coluna 0 da linha selecionada
		int id = (int) tabelaClientes.getValueAt(linhaSelecionada, 0);
		String nome = (String) tabelaClientes.getValueAt(linhaSelecionada, 1);

		int confirmacao = JOptionPane.showConfirmDialog(this, 
				"Tem a certeza que deseja remover o cliente " + nome + "?", 
				"Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

		if (confirmacao == JOptionPane.YES_OPTION) {
			// Remove do banco de dados
			clienteDAO.excluir(id);
			// Atualiza a visualização
			atualizarTabela();
		}
	}
}