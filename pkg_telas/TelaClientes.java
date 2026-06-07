package pkg_telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
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

    public TelaClientes() {
        setTitle("Gerenciamento de Clientes - Sistema de Gestão");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 550);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(EstiloGlobal.COR_FUNDO);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout(0, 20));
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Cadastro e Controle de Clientes");
        lblTitulo.setFont(EstiloGlobal.FONTE_TITULO);
        lblTitulo.setForeground(EstiloGlobal.COR_PRINCIPAL);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 20));
        painelCentral.setBackground(EstiloGlobal.COR_FUNDO);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(EstiloGlobal.COR_FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formulario.add(EstiloGlobal.criarLabel("Nome / Razão Social:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNome = new JTextField();
        formulario.add(txtNome, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formulario.add(EstiloGlobal.criarLabel("CPF / CNPJ:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        txtCpfCnpj = new JTextField(12);
        formulario.add(txtCpfCnpj, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formulario.add(EstiloGlobal.criarLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTelefone = new JTextField();
        formulario.add(txtTelefone, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formulario.add(EstiloGlobal.criarLabel("E-mail:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        txtEmail = new JTextField();
        formulario.add(txtEmail, gbc);

        painelCentral.add(formulario, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF/CNPJ", "Telefone", "E-mail"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollTabela = new JScrollPane(tabelaClientes);
        painelCentral.add(scrollTabela, BorderLayout.CENTER);

        contentPane.add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        painelBotoes.setBackground(EstiloGlobal.COR_FUNDO);

        JButton btnSalvar = EstiloGlobal.criarBotao("Salvar");
        JButton btnAlterar = EstiloGlobal.criarBotao("Alterar");
        JButton btnExcluir = EstiloGlobal.criarBotao("Excluir");
        JButton btnVoltar = EstiloGlobal.criarBotao("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);
        contentPane.add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnSalvar.addActionListener(e -> salvarCliente());
        btnAlterar.addActionListener(e -> alterarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnVoltar.addActionListener(e -> this.dispose());

        // Selecionar linha da tabela -> preencher campos para edição
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                int linha = tabelaClientes.getSelectedRow();
                // Preenche os campos com os dados da linha selecionada
                // Não preenchemos o ID em um campo visível, mas usaremos internamente
                // Para simplificar, vamos armazenar o ID em uma variável ou campo oculto
                // Neste caso, vou deixar o ID em um JTextField invisível (opcional)
                // Mas vou usar o ID diretamente no momento da alteração/exclusão
                txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
                txtCpfCnpj.setText(modeloTabela.getValueAt(linha, 2).toString());
                txtTelefone.setText(modeloTabela.getValueAt(linha, 3).toString());
                txtEmail.setText(modeloTabela.getValueAt(linha, 4).toString());
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            for (Cliente c : clientes) {
                modeloTabela.addRow(new Object[]{c.getId(), c.getNome(), c.getCpfCnpj(), c.getTelefone(), c.getEmail()});
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarCliente() {
        String nome = txtNome.getText().trim();
        String cpfCnpj = txtCpfCnpj.getText().trim();
        if (nome.isEmpty() || cpfCnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF/CNPJ são campos obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = new Cliente(0, nome, cpfCnpj, txtTelefone.getText().trim(), txtEmail.getText().trim());

        try {
            clienteDAO.inserir(c);
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para alterar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = txtNome.getText().trim();
        String cpfCnpj = txtCpfCnpj.getText().trim();

        if (nome.isEmpty() || cpfCnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF/CNPJ são obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = new Cliente(id, nome, cpfCnpj, txtTelefone.getText().trim(), txtEmail.getText().trim());

        try {
            clienteDAO.atualizar(c);
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tabelaClientes.getValueAt(linhaSelecionada, 0);
        String nome = (String) tabelaClientes.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o cliente " + nome + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                clienteDAO.excluir(id);
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
                limparCampos();
                atualizarTabela();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover cliente:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpfCnpj.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        tabelaClientes.clearSelection();
    }
}