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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TelaProjetos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeProjeto, txtEngenheiro, txtOrcamento;
	private JComboBox<String> cbStatus;
	private JTable tabelaProjetos;
	private DefaultTableModel modeloTabela;

	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;

	public TelaProjetos() {
		setTitle("Gerenciamento de Projetos e Obras - Construtora Eco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setLayout(new BorderLayout(0, 20));
		setContentPane(contentPane);

		// --- TÍTULO ---
		JLabel lblTitulo = new JLabel("Controle de Projetos e Engenharia");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setForeground(COR_PRINCIPAL);
		contentPane.add(lblTitulo, BorderLayout.NORTH);

		// --- PAINEL CENTRAL (Formulário + Tabela) ---
		JPanel painelCentral = new JPanel(new BorderLayout(0, 20));
		painelCentral.setBackground(COR_FUNDO);

		// Formulário GridBagLayout
		JPanel formulario = new JPanel(new GridBagLayout());
		formulario.setBackground(COR_FUNDO);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Linha 0: Nome do Projeto
		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
		formulario.add(new JLabel("Nome do Projeto/Obra:"), gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		txtNomeProjeto = new JTextField();
		formulario.add(txtNomeProjeto, gbc);

		// Linha 0 (Coluna 2): Status
		gbc.gridx = 2; gbc.weightx = 0;
		formulario.add(new JLabel("Status:"), gbc);
		gbc.gridx = 3; gbc.weightx = 0.4;
		cbStatus = new JComboBox<>(new String[] {"Planejamento", "Em Execução", "Embargado", "Finalizado"});
		formulario.add(cbStatus, gbc);

		// Linha 1: Engenheiro
		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
		formulario.add(new JLabel("Engenheiro Resp.:"), gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		txtEngenheiro = new JTextField();
		formulario.add(txtEngenheiro, gbc);

		// Linha 1 (Coluna 2): Orçamento
		gbc.gridx = 2; gbc.weightx = 0;
		formulario.add(new JLabel("Orçamento (R$):"), gbc);
		gbc.gridx = 3; gbc.weightx = 0.4;
		txtOrcamento = new JTextField();
		formulario.add(txtOrcamento, gbc);

		painelCentral.add(formulario, BorderLayout.NORTH);

		// Tabela de Dados
		String[] colunas = {"Código", "Descrição do Projeto", "Responsável", "Custo Estimado", "Status"};
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaProjetos = new JTable(modeloTabela);
		JScrollPane scrollTabela = new JScrollPane(tabelaProjetos);
		painelCentral.add(scrollTabela, BorderLayout.CENTER);

		contentPane.add(painelCentral, BorderLayout.CENTER);

		// --- PAINEL INFERIOR (Ações) ---
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		painelBotoes.setBackground(COR_FUNDO);

		JButton btnSalvar = criarBotao("Salvar Obra");
		JButton btnExcluir = criarBotao("Remover");
		JButton btnVoltar = criarBotao("Voltar");
		btnVoltar.setBackground(Color.GRAY);

		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnVoltar);
		contentPane.add(painelBotoes, BorderLayout.SOUTH);

		// Eventos básicos
		btnVoltar.addActionListener(e -> this.dispose());
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
}