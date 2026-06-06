
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

public class TelaFinanceiro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaFinanceiro;
	private DefaultTableModel modeloTabela;
	
	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;
	private final Color COR_PAINEIS = Color.decode("#F8F9FA");
	private final Font FONTE_PADRAO = new Font("Tahoma", Font.PLAIN, 14);
	private final Font FONTE_TITULO = new Font("Tahoma", Font.BOLD, 22);

	private JTextField txtId, txtDescricao, txtValor, txtData, txtProjeto;
	private JComboBox<String> comboTipo;

	public TelaFinanceiro() {
		setTitle("Módulo Financeiro - Construtora Eco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);

		JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTitulo.setBackground(COR_FUNDO);
		JLabel lblTitulo = new JLabel("Controle de Fluxo de Caixa / Projetos");
		lblTitulo.setFont(FONTE_TITULO);
		lblTitulo.setForeground(COR_PRINCIPAL);
		painelTitulo.add(lblTitulo);
		contentPane.add(painelTitulo, BorderLayout.NORTH);

		String[] colunas = {"ID", "Descrição do Lançamento", "Valor (R$)", "Data", "Tipo", "Projeto Vinculado"};
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaFinanceiro = new JTable(modeloTabela);
		tabelaFinanceiro.setFont(FONTE_PADRAO);
		tabelaFinanceiro.setRowHeight(22);
		
		JScrollPane scrollTabela = new JScrollPane(tabelaFinanceiro);
		contentPane.add(scrollTabela, BorderLayout.CENTER);

		JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
		painelInferior.setBackground(COR_FUNDO);

		JPanel painelFormulario = new JPanel(new GridBagLayout());
		painelFormulario.setBackground(COR_PAINEIS);
		painelFormulario.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COR_PRINCIPAL), "Movimentação Financeira", 0, 0, FONTE_PADRAO, COR_PRINCIPAL));

		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(8, 10, 5, 10);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		gbc_lblId.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblId = criarLabel("ID Lançamento:");
		painelFormulario.add(lblId, gbc_lblId);

		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.insets = new Insets(8, 10, 5, 10);
		gbc_txtId.gridx = 1;
		gbc_txtId.gridy = 0;
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		txtId = new JTextField(5); 
		txtId.setEditable(false);
		painelFormulario.add(txtId, gbc_txtId);

		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.insets = new Insets(8, 10, 5, 10);
		gbc_lblDesc.gridx = 2;
		gbc_lblDesc.gridy = 0;
		gbc_lblDesc.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblDesc = criarLabel("Descrição:");
		painelFormulario.add(lblDesc, gbc_lblDesc);

		GridBagConstraints gbc_txtDesc = new GridBagConstraints();
		gbc_txtDesc.insets = new Insets(8, 10, 5, 10);
		gbc_txtDesc.gridx = 3;
		gbc_txtDesc.gridy = 0;
		gbc_txtDesc.weightx = 1.0;
		gbc_txtDesc.fill = GridBagConstraints.HORIZONTAL;
		txtDescricao = new JTextField(20);
		painelFormulario.add(txtDescricao, gbc_txtDesc);

		GridBagConstraints gbc_lblValor = new GridBagConstraints();
		gbc_lblValor.insets = new Insets(5, 10, 5, 10);
		gbc_lblValor.gridx = 0;
		gbc_lblValor.gridy = 1;
		gbc_lblValor.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblValor = criarLabel("Valor (R$):");
		painelFormulario.add(lblValor, gbc_lblValor);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.insets = new Insets(5, 10, 5, 10);
		gbc_txtValor.gridx = 1;
		gbc_txtValor.gridy = 1;
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		txtValor = new JTextField(10);
		painelFormulario.add(txtValor, gbc_txtValor);

		GridBagConstraints gbc_lblData = new GridBagConstraints();
		gbc_lblData.insets = new Insets(5, 10, 5, 10);
		gbc_lblData.gridx = 2;
		gbc_lblData.gridy = 1;
		gbc_lblData.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblData = criarLabel("Data:");
		painelFormulario.add(lblData, gbc_lblData);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.insets = new Insets(5, 10, 5, 10);
		gbc_txtData.gridx = 3;
		gbc_txtData.gridy = 1;
		gbc_txtData.weightx = 1.0;
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		txtData = new JTextField(10);
		painelFormulario.add(txtData, gbc_txtData);

		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(5, 10, 8, 10);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 2;
		gbc_lblTipo.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblTipo = criarLabel("Tipo:");
		painelFormulario.add(lblTipo, gbc_lblTipo);

		GridBagConstraints gbc_comboTipo = new GridBagConstraints();
		gbc_comboTipo.insets = new Insets(5, 10, 8, 10);
		gbc_comboTipo.gridx = 1;
		gbc_comboTipo.gridy = 2;
		gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
		comboTipo = new JComboBox<>(new String[] {"Receita", "Despesa"});
		comboTipo.setFont(FONTE_PADRAO);
		painelFormulario.add(comboTipo, gbc_comboTipo);

		GridBagConstraints gbc_lblProj = new GridBagConstraints();
		gbc_lblProj.insets = new Insets(5, 10, 8, 10);
		gbc_lblProj.gridx = 2;
		gbc_lblProj.gridy = 2;
		gbc_lblProj.fill = GridBagConstraints.HORIZONTAL;
		JLabel lblProj = criarLabel("Projeto:");
		painelFormulario.add(lblProj, gbc_lblProj);

		GridBagConstraints gbc_txtProj = new GridBagConstraints();
		gbc_txtProj.insets = new Insets(5, 10, 8, 10);
		gbc_txtProj.gridx = 3;
		gbc_txtProj.gridy = 2;
		gbc_txtProj.weightx = 1.0;
		gbc_txtProj.fill = GridBagConstraints.HORIZONTAL;
		txtProjeto = new JTextField(15);
		painelFormulario.add(txtProjeto, gbc_txtProj);

		painelInferior.add(painelFormulario, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		painelBotoes.setBackground(COR_FUNDO);

		JButton btnLancamento = criarBotao("Inserir Lançamento");
		JButton btnAtualizar = criarBotao("Alterar");
		JButton btnExcluir = criarBotao("Estornar");
		
		painelBotoes.add(btnLancamento);
		painelBotoes.add(btnAtualizar);
		painelBotoes.add(btnExcluir);
		
		painelInferior.add(painelBotoes, BorderLayout.SOUTH);
		contentPane.add(painelInferior, BorderLayout.SOUTH);

		mockTabela();
		
		tabelaFinanceiro.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabelaFinanceiro.getSelectedRow() != -1) {
				int linha = tabelaFinanceiro.getSelectedRow();
				txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
				txtDescricao.setText(modeloTabela.getValueAt(linha, 1).toString());
				txtValor.setText(modeloTabela.getValueAt(linha, 2).toString());
				txtData.setText(modeloTabela.getValueAt(linha, 3).toString());
				comboTipo.setSelectedItem(modeloTabela.getValueAt(linha, 4).toString());
				txtProjeto.setText(modeloTabela.getValueAt(linha, 5).toString());
			}
		});
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

	private void mockTabela() {
		modeloTabela.setRowCount(0);
		modeloTabela.addRow(new Object[] {"1", "Compra de Sacos de Cimento", "4.500,00", "01/06/2026", "Despesa", "Residencial EcoVida"});
		modeloTabela.addRow(new Object[] {"2", "Aporte Inicial Investidor", "150.000,00", "03/06/2026", "Receita", "Edifício GreenTower"});
		modeloTabela.addRow(new Object[] {"3", "Pagamento de Empreiteira - Alvenaria", "22.300,00", "05/06/2026", "Despesa", "Residencial EcoVida"});
	}
}