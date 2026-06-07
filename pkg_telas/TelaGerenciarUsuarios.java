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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pkg_database.Usuario;
import pkg_database.UsuarioDAO;

public class TelaGerenciarUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    private JTextField txtId, txtUsuario;
    private JPasswordField txtSenha;
    private JComboBox<String> comboPerfil;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public TelaGerenciarUsuarios() {
        setTitle("Gerenciamento de Usuários - Construtora Eco");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(EstiloGlobal.COR_FUNDO);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(0, 15));
        setContentPane(contentPane);

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(EstiloGlobal.COR_FUNDO);
        JLabel lblTitulo = new JLabel("Controle de Usuários e Acessos");
        lblTitulo.setFont(EstiloGlobal.FONTE_TITULO);
        lblTitulo.setForeground(EstiloGlobal.COR_PRINCIPAL);
        painelTitulo.add(lblTitulo);
        contentPane.add(painelTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome de Usuário", "Perfil de Acesso"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.setFont(EstiloGlobal.FONTE_PADRAO);
        tabelaUsuarios.setRowHeight(22);

        JScrollPane scrollTabela = new JScrollPane(tabelaUsuarios);
        contentPane.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
        painelInferior.setBackground(EstiloGlobal.COR_FUNDO);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(EstiloGlobal.COR_PAINEIS);
        painelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(EstiloGlobal.COR_PRINCIPAL), "Dados do Usuário", 0, 0, EstiloGlobal.FONTE_PADRAO, EstiloGlobal.COR_PRINCIPAL));

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
        painelFormulario.add(EstiloGlobal.criarLabel("Usuário:"), gbc);
        gbc.gridx = 3;
        txtUsuario = new JTextField(15);
        painelFormulario.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(EstiloGlobal.criarLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(10);
        painelFormulario.add(txtSenha, gbc);

        gbc.gridx = 2;
        painelFormulario.add(EstiloGlobal.criarLabel("Perfil:"), gbc);
        gbc.gridx = 3;
        comboPerfil = new JComboBox<>(new String[] {"Administrador", "Engenheiro", "Financeiro", "Operador"});
        comboPerfil.setFont(EstiloGlobal.FONTE_PADRAO);
        painelFormulario.add(comboPerfil, gbc);

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
        btnSalvar.addActionListener(e -> cadastrarUsuario());
        btnAlterar.addActionListener(e -> alterarUsuario());
        btnExcluir.addActionListener(e -> excluirUsuario());
        btnVoltar.addActionListener(e -> this.dispose());

        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaUsuarios.getSelectedRow() != -1) {
                int linha = tabelaUsuarios.getSelectedRow();
                txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
                txtUsuario.setText(modeloTabela.getValueAt(linha, 1).toString());
                comboPerfil.setSelectedItem(modeloTabela.getValueAt(linha, 2).toString());
                txtSenha.setText("");
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Usuario> lista = usuarioDAO.listarTodos();
            for (Usuario u : lista) {
                modeloTabela.addRow(new Object[]{u.getId(), u.getUsuario(), u.getPerfil()});
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha usuário e senha!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario u = new Usuario(0, usuario, senha, comboPerfil.getSelectedItem().toString());
        try {
            usuarioDAO.inserir(u);
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para alterar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String senha = new String(txtSenha.getPassword()).trim();
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a senha (atual ou nova) para confirmar a alteração.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        Usuario u = new Usuario(id, txtUsuario.getText().trim(), senha, comboPerfil.getSelectedItem().toString());
        try {
            usuarioDAO.atualizar(u);
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            try {
                usuarioDAO.excluir(id);
                JOptionPane.showMessageDialog(this, "Usuário removido.");
                limparCampos();
                atualizarTabela();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtUsuario.setText("");
        txtSenha.setText("");
        comboPerfil.setSelectedIndex(0);
        tabelaUsuarios.clearSelection();
    }
}