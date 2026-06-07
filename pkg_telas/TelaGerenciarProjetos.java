package pkg_telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
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

    private JTextField txtId, txtNome, txtDataInicio, txtDataFim, txtEngenheiro;
    private JComboBox<String> comboStatus;

    private ProjetoDAO projetoDAO = new ProjetoDAO();

    public TelaGerenciarProjetos() {
        setTitle("Gerenciamento de Projetos - Construtora Eco");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(EstiloGlobal.COR_FUNDO);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(0, 15));
        setContentPane(contentPane);

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(EstiloGlobal.COR_FUNDO);
        JLabel lblTitulo = new JLabel("Controle de Projetos de Engenharia");
        lblTitulo.setFont(EstiloGlobal.FONTE_TITULO);
        lblTitulo.setForeground(EstiloGlobal.COR_PRINCIPAL);
        painelTitulo.add(lblTitulo);
        contentPane.add(painelTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome do Projeto", "Data Início", "Data Fim", "Engenheiro Resp.", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProjetos = new JTable(modeloTabela);
        tabelaProjetos.setFont(EstiloGlobal.FONTE_PADRAO);
        tabelaProjetos.setRowHeight(22);

        JScrollPane scrollTabela = new JScrollPane(tabelaProjetos);
        contentPane.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
        painelInferior.setBackground(EstiloGlobal.COR_FUNDO);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(EstiloGlobal.COR_PAINEIS);
        painelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(EstiloGlobal.COR_PRINCIPAL), "Dados do Projeto", 0, 0, EstiloGlobal.FONTE_PADRAO, EstiloGlobal.COR_PRINCIPAL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(EstiloGlobal.criarLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(5);
        txtId.setEditable(false);
        painelFormulario.add(txtId, gbc);

        gbc.gridx = 2;
        painelFormulario.add(EstiloGlobal.criarLabel("Nome do Projeto:"), gbc);
        gbc.gridx = 3;
        txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(EstiloGlobal.criarLabel("Data Início:"), gbc);
        gbc.gridx = 1;
        txtDataInicio = new JTextField(10);
        painelFormulario.add(txtDataInicio, gbc);

        gbc.gridx = 2;
        painelFormulario.add(EstiloGlobal.criarLabel("Data Término:"), gbc);
        gbc.gridx = 3;
        txtDataFim = new JTextField(10);
        painelFormulario.add(txtDataFim, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(EstiloGlobal.criarLabel("Eng. Resp.:"), gbc);
        gbc.gridx = 1;
        txtEngenheiro = new JTextField(12);
        painelFormulario.add(txtEngenheiro, gbc);

        gbc.gridx = 2;
        painelFormulario.add(EstiloGlobal.criarLabel("Status do Projeto:"), gbc);
        gbc.gridx = 3;
        comboStatus = new JComboBox<>(new String[] {"Planejamento", "Em Execução", "Pausado", "Concluído"});
        comboStatus.setFont(EstiloGlobal.FONTE_PADRAO);
        painelFormulario.add(comboStatus, gbc);

        painelInferior.add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        painelBotoes.setBackground(EstiloGlobal.COR_FUNDO);

        JButton btnSalvar = EstiloGlobal.criarBotao("Cadastrar");
        JButton btnAlterar = EstiloGlobal.criarBotao("Alterar");
        JButton btnExcluir = EstiloGlobal.criarBotao("Excluir");
        JButton btnVoltar = EstiloGlobal.criarBotao("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        painelInferior.add(painelBotoes, BorderLayout.SOUTH);
        contentPane.add(painelInferior, BorderLayout.SOUTH);

        // Ações
        btnSalvar.addActionListener(e -> cadastrarProjeto());
        btnAlterar.addActionListener(e -> alterarProjeto());
        btnExcluir.addActionListener(e -> excluirProjeto());
        btnVoltar.addActionListener(e -> this.dispose());

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

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Projeto> lista = projetoDAO.listarTodos();
            for (Projeto p : lista) {
                modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getDataInicial(), p.getDataFinal(), p.getEngenheiroResponsavel(), p.getStatus()});
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar projetos:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarProjeto() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do projeto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Projeto p = new Projeto(0,
                txtNome.getText().trim(),
                txtDataInicio.getText().trim(),
                txtDataFim.getText().trim(),
                txtEngenheiro.getText().trim(),
                comboStatus.getSelectedItem().toString());
        try {
            projetoDAO.inserir(p);
            JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar projeto:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarProjeto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela para alterar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        Projeto p = new Projeto(id,
                txtNome.getText().trim(),
                txtDataInicio.getText().trim(),
                txtDataFim.getText().trim(),
                txtEngenheiro.getText().trim(),
                comboStatus.getSelectedItem().toString());
        try {
            projetoDAO.atualizar(p);
            JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar projeto:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProjeto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este projeto?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            try {
                projetoDAO.excluir(id);
                JOptionPane.showMessageDialog(this, "Projeto removido.");
                limparCampos();
                atualizarTabela();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir projeto:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
}