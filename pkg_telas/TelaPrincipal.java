package pkg_telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public TelaPrincipal() {
        setTitle("Painel Principal - Sistema de Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        JMenu menuCadastros = new JMenu("Cadastros");
        menuCadastros.setFont(EstiloGlobal.FONTE_PADRAO);
        menuBar.add(menuCadastros);

        JMenuItem itemUsuarios = new JMenuItem("Gerenciar Usuários");
        itemUsuarios.addActionListener(e -> new TelaGerenciarUsuarios().setVisible(true));
        menuCadastros.add(itemUsuarios);

        JMenuItem itemClientes = new JMenuItem("Gerenciar Clientes");
        itemClientes.addActionListener(e -> new TelaClientes().setVisible(true));
        menuCadastros.add(itemClientes);

        JMenuItem itemProjetos = new JMenuItem("Projetos / Demandas");
        itemProjetos.addActionListener(e -> new TelaGerenciarProjetos().setVisible(true));
        menuCadastros.add(itemProjetos);

        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        menuRelatorios.setFont(EstiloGlobal.FONTE_PADRAO);
        menuBar.add(menuRelatorios);
        
        JMenuItem itemPDF = new JMenuItem("Gerar PDFs");
        itemPDF.addActionListener(e -> new TelaPDF().telaPdf.setVisible(true));
        menuRelatorios.add(itemPDF);

        JMenu menuSistema = new JMenu("Sistema");
        menuSistema.setFont(EstiloGlobal.FONTE_PADRAO);
        menuBar.add(menuSistema);

        JMenuItem itemLogout = new JMenuItem("Fazer Logout");
        itemLogout.addActionListener(e -> executarLogout());
        menuSistema.add(itemLogout);

        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuSistema.add(itemSair);

        contentPane = new JPanel();
        contentPane.setBackground(EstiloGlobal.COR_FUNDO);
        contentPane.setBorder(new EmptyBorder(40, 60, 40, 60));
        contentPane.setLayout(new BorderLayout(0, 30));
        setContentPane(contentPane);

        JLabel lblBoasVindas = new JLabel("Painel de Controle Corporativo");
        lblBoasVindas.setFont(EstiloGlobal.FONTE_TITULO);
        lblBoasVindas.setForeground(EstiloGlobal.COR_PRINCIPAL);
        lblBoasVindas.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblBoasVindas, BorderLayout.NORTH);

        JPanel painelDashboard = new JPanel(new GridLayout(2, 2, 30, 30));
        painelDashboard.setBackground(EstiloGlobal.COR_FUNDO);

        JButton btnUsuarios = criarBotaoDashboard("Usuários do Sistema");
        JButton btnProjetos = criarBotaoDashboard("Projetos e Demandas");
        JButton btnClientes = criarBotaoDashboard("Carteira de Clientes");
        JButton btnLogout = criarBotaoDashboard("Encerrar Sessão");

        painelDashboard.add(btnUsuarios);
        painelDashboard.add(btnProjetos);
        painelDashboard.add(btnClientes);
        painelDashboard.add(btnLogout);

        contentPane.add(painelDashboard, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> new TelaGerenciarUsuarios().setVisible(true));
        btnProjetos.addActionListener(e -> new TelaGerenciarProjetos().setVisible(true));
        btnClientes.addActionListener(e -> new TelaClientes().setVisible(true));
        btnLogout.addActionListener(e -> executarLogout());
    }

    private JButton criarBotaoDashboard(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(EstiloGlobal.FONTE_TITULO);
        botao.setBackground(EstiloGlobal.COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(EstiloGlobal.COR_PRINCIPAL.darker(), 1));
        return botao;
    }

    private void executarLogout() {
        new TelaDeLogin().setVisible(true);
        this.dispose();
    }
}