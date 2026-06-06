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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pkg_database.Projeto;
import pkg_database.ProjetoDAO;

public class TelaGerenciarProjetos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaProjetos;
	private DefaultTableModel modeloTabela;
	
	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;
	private final Color COR_PAINEIS = Color.decode("#F8F9FA");
	private final Font FONTE_PADRAO = new Font("Tahoma", Font.PLAIN, 14);
	private final Font FONTE_TITULO = new Font("Tahoma", Font.BOLD, 22);

	private JTextField txtId, txtNome, txtDataInicio, txtDataFim, txtEngenheiro;
	private JComboBox<String> comboStatus;
	
	private ProjetoDAO projetoDAO = new ProjetoDAO();

	public TelaGerenciarProjetos() {
		setTitle("Gerenciamento de Projetos - Construtora Eco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);

		JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTitulo.setBackground(COR_FUNDO);
		JLabel lblTitulo = new JLabel("Controle de Projetos de Engenharia");
		lblTitulo.setFont(FONTE_TITULO);
		lblTitulo.setForeground(COR_PRINCIPAL);
		painelTitulo.add(lblTitulo);
		contentPane.add(painelTitulo, BorderLayout.NORTH);

		String[] colunas = {"ID", "Nome do Projeto", "Data Início", "Data Fim", "Engenheiro Resp.", "Status"};
		modeloTabela = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabelaProjetos = new JTable(modeloTabela);
		tabelaProjetos.setFont(FONTE_PADRAO);
		tabelaProjetos.setRowHeight(22);
		
		JScrollPane scrollTabela = new JScrollPane(tabelaProjetos);
		contentPane.add(scrollTabela, BorderLayout.CENTER);

		JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
		painelInferior.setBackground(COR_FUNDO);

		JPanel painelFormulario = new JPanel(new GridBagLayout());
		painelFormulario.setBackground(COR_PAINEIS);
		painelFormulario.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COR_PRINCIPAL), "Dados do Projeto", 0, 0, FONTE_PADRAO, COR_PRINCIPAL));

		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(10, 10, 5, 5);
		gbc_lblId.gridx = 0; gbc_lblId.gridy = 0;
		gbc_lblId.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("ID:"), gbc_lblId);

		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.insets = new Insets(10, 5, 5, 15);
		gbc_txtId.gridx = 1; gbc_txtId.gridy = 0;
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		txtId = new JTextField(5);
		txtId.setEditable(false);
		painelFormulario.add(txtId, gbc_txtId);

		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.insets = new Insets(10, 10, 5, 5);
		gbc_lblNome.gridx = 2; gbc_lblNome.gridy = 0;
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Nome do Projeto:"), gbc_lblNome);

		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(10, 5, 5, 10);
		gbc_txtNome.gridx = 3; gbc_txtNome.gridy = 0;
		gbc_txtNome.weightx = 1.0;
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		txtNome = new JTextField(20);
		painelFormulario.add(txtNome, gbc_txtNome);

		GridBagConstraints gbc_lblDataInicio = new GridBagConstraints();
		gbc_lblDataInicio.insets = new Insets(5, 10, 5, 5);
		gbc_lblDataInicio.gridx = 0; gbc_lblDataInicio.gridy = 1;
		gbc_lblDataInicio.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Data Início:"), gbc_lblDataInicio);

		GridBagConstraints gbc_txtDataInicio = new GridBagConstraints();
		gbc_txtDataInicio.insets = new Insets(5, 5, 5, 15);
		gbc_txtDataInicio.gridx = 1; gbc_txtDataInicio.gridy = 1;
		gbc_txtDataInicio.fill = GridBagConstraints.HORIZONTAL;
		txtDataInicio = new JTextField(10);
		painelFormulario.add(txtDataInicio, gbc_txtDataInicio);

		GridBagConstraints gbc_lblDataFim = new GridBagConstraints();
		gbc_lblDataFim.insets = new Insets(5, 10, 5, 5);
		gbc_lblDataFim.gridx = 2; gbc_lblDataFim.gridy = 1;
		gbc_lblDataFim.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Data Término:"), gbc_lblDataFim);

		GridBagConstraints gbc_txtDataFim = new GridBagConstraints();
		gbc_txtDataFim.insets = new Insets(5, 5, 5, 10);
		gbc_txtDataFim.gridx = 3; gbc_txtDataFim.gridy = 1;
		gbc_txtDataFim.fill = GridBagConstraints.HORIZONTAL;
		txtDataFim = new JTextField(10);
		painelFormulario.add(txtDataFim, gbc_txtDataFim);

		GridBagConstraints gbc_lblEngenheiro = new GridBagConstraints();
		gbc_lblEngenheiro.insets = new Insets(5, 10, 10, 5);
		gbc_lblEngenheiro.gridx = 0; gbc_lblEngenheiro.gridy = 2;
		gbc_lblEngenheiro.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Eng. Resp.:"), gbc_lblEngenheiro);

		GridBagConstraints gbc_txtEngenheiro = new GridBagConstraints();
		gbc_txtEngenheiro.insets = new Insets(5, 5, 10, 15);
		gbc_txtEngenheiro.gridx = 1; gbc_txtEngenheiro.gridy = 2;
		gbc_txtEngenheiro.fill = GridBagConstraints.HORIZONTAL;
		txtEngenheiro = new JTextField(12);
		painelFormulario.add(txtEngenheiro, gbc_txtEngenheiro);

		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(5, 10, 10, 5);
		gbc_lblStatus.gridx = 2; gbc_lblStatus.gridy = 2;
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		painelFormulario.add(criarLabel("Status do Projeto:"), gbc_lblStatus);

		GridBagConstraints gbc_comboStatus = new GridBagConstraints();
		gbc_comboStatus.insets = new Insets(5, 5, 10, 10);
		gbc_comboStatus.gridx = 3; gbc_comboStatus.gridy = 2;
		gbc_comboStatus.fill = GridBagConstraints.HORIZONTAL;
		comboStatus = new JComboBox<>(new String[] {"Planejamento", "Em Execução", "Pausado", "Concluído"});
		comboStatus.setFont(FONTE_PADRAO);
		painelFormulario.add(comboStatus, gbc_comboStatus);

		painelInferior.add(painelFormulario, BorderLayout.CENTER);

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

		// --- LOGIC INTERACTION (AÇÕES DO CRUD REAL) ---

		// AÇÃO: CADASTRAR
		btnSalvar.addActionListener(e -> {
			if(txtNome.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha o nome do projeto!");
				return;
			}
			Projeto p = new Projeto(
				null, // O MySQL vai gerar o ID automaticamente via Auto_Increment
				txtNome.getText(),
				txtDataInicio.getText(),
				txtDataFim.getText(),
				txtEngenheiro.getText(),
				comboStatus.getSelectedItem().toString()
			);
			projetoDAO.inserir(p);
			JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso!");
			limparCampos();
			atualizarTabelaReal();
		});

		// AÇÃO: ALTERAR
		btnAlterar.addActionListener(e -> {
			if(txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela para alterar!");
				return;
			}
			Projeto p = new Projeto(
				txtId.getText(),
				txtNome.getText(),
				txtDataInicio.getText(),
				txtDataFim.getText(),
				txtEngenheiro.getText(),
				comboStatus.getSelectedItem().toString()
			);
			projetoDAO.atualizar(p);
			JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
			limparCampos();
			atualizarTabelaReal();
		});

		// AÇÃO: EXCLUIR
		btnExcluir.addActionListener(e -> {
			if(txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela para excluir!");
				return;
			}
			int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este projeto?", "Confirmação", JOptionPane.YES_NO_OPTION);
			if(confirmacao == JOptionPane.YES_OPTION) {
				projetoDAO.excluir(txtId.getText());
				JOptionPane.showMessageDialog(this, "Projeto removido do banco.");
				limparCampos();
				atualizarTabelaReal();
			}
		});

		// AÇÃO: CLIQUE NA TABELA (Preenche os campos de texto automaticamente)
		tabelaProjetos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabelaProjetos.getSelectedRow() != -1) {
				int linha = tabelaProjetos.getSelectedRow();
				txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
				txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
				txtDataInicio.setText(modeloTabela.getValueAt(linha, 2).toString());
				txtDataFim.setText(modeloTabela.getValueAt(linha, 3).toString());
				txtEngenheiro.setText(modeloTabela.getValueAt(linha, 4).toString());
				comboStatus.setSelectedItem(modeloTabela.getValueAt(linha, 5).toString());
			}
		});

		// Inicializa a tabela buscando os dados direto do banco MySQL
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
		txtNome.setText("");
		txtDataInicio.setText("");
		txtDataFim.setText("");
		txtEngenheiro.setText("");
		comboStatus.setSelectedIndex(0);
		tabelaProjetos.clearSelection();
	}

	private void atualizarTabelaReal() {
		modeloTabela.setRowCount(0);
		List<Projeto> lista = projetoDAO.listarTodos();
		for (Projeto p : lista) {
			modeloTabela.addRow(new Object[] {
				p.getId(),
				p.getNome(),
				p.getDataInicial(),
				p.getDataFinal(),
				p.getEngenheiroResponsavel(),
				p.getStatus()
			});
		}
	}
}