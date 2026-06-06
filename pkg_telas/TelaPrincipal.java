package pkg_telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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

	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;

	public TelaPrincipal() {
		setTitle("Painel Principal - Construtora Eco");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		setLocationRelativeTo(null); 

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);

		JMenu menuCadastros = new JMenu("Cadastros");
		menuCadastros.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuBar.add(menuCadastros);

		JMenuItem itemUsuarios = new JMenuItem("Gerenciar Usuários");
		itemUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemUsuarios.addActionListener(e -> abrirGerenciamentoUsuarios());
		menuCadastros.add(itemUsuarios);

		JMenuItem itemClientes = new JMenuItem("Gerenciar Clientes");
		itemClientes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemClientes.addActionListener(e -> abrirGerenciamentoClientes());
		menuCadastros.add(itemClientes);

		JMenuItem itemProjetos = new JMenuItem("Projetos / Obras");
		itemProjetos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemProjetos.addActionListener(e -> abrirGerenciamentoProjetos());
		menuCadastros.add(itemProjetos);

		JMenu menuSistema = new JMenu("Sistema");
		menuSistema.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuBar.add(menuSistema);

		JMenuItem itemLogout = new JMenuItem("Fazer Logout");
		itemLogout.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemLogout.addActionListener(e -> executarLogout());
		menuSistema.add(itemLogout);

		JMenuItem itemSair = new JMenuItem("Sair");
		itemSair.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemSair.addActionListener(e -> System.exit(0));
		menuSistema.add(itemSair);

		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(40, 60, 40, 60)); 
		contentPane.setLayout(new BorderLayout(0, 30));
		setContentPane(contentPane);

		JLabel lblBoasVindas = new JLabel("Sistema de Gestão - Construtora Eco");
		lblBoasVindas.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblBoasVindas.setForeground(COR_PRINCIPAL);
		lblBoasVindas.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBoasVindas, BorderLayout.NORTH);

		JPanel painelDashboard = new JPanel(new GridLayout(2, 2, 30, 30));
		painelDashboard.setBackground(COR_FUNDO);

		JButton btnUsuarios = criarBotaoDashboard("Gerenciar Usuários");
		JButton btnProjetos = criarBotaoDashboard("Projetos / Obras");
		JButton btnClientes = criarBotaoDashboard("Clientes"); 
		JButton btnLogout = criarBotaoDashboard("Fazer Logout");

		painelDashboard.add(btnUsuarios);
		painelDashboard.add(btnProjetos);
		painelDashboard.add(btnClientes);
		painelDashboard.add(btnLogout);

		contentPane.add(painelDashboard, BorderLayout.CENTER);

		btnUsuarios.addActionListener(e -> abrirGerenciamentoUsuarios());
		btnProjetos.addActionListener(e -> abrirGerenciamentoProjetos());
		btnClientes.addActionListener(e -> abrirGerenciamentoClientes());
		btnLogout.addActionListener(e -> executarLogout());

		JLabel lblRodape = new JLabel("Módulo de Controle Interno | Versão 1.0 ");
		lblRodape.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRodape.setForeground(Color.GRAY);
		lblRodape.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblRodape, BorderLayout.SOUTH);
	}


	private JButton criarBotaoDashboard(String texto) {
		JButton botao = new JButton(texto);
		botao.setFont(new Font("Tahoma", Font.BOLD, 16));
		botao.setBackground(COR_PRINCIPAL);
		botao.setForeground(Color.WHITE);
		botao.setFocusPainted(false);
		botao.setBorder(BorderFactory.createLineBorder(COR_PRINCIPAL.darker(), 1));
		return botao;
	}

	
	private void abrirGerenciamentoUsuarios() {
		TelaGerenciarUsuarios telaUsuarios = new TelaGerenciarUsuarios();
		telaUsuarios.setVisible(true);
	}

	private void abrirGerenciamentoProjetos() {
		TelaProjetos telaProjetos = new TelaProjetos();
		telaProjetos.setVisible(true);
	}

	private void abrirGerenciamentoClientes() {
		TelaClientes telaClientes = new TelaClientes();
		telaClientes.setVisible(true);
	}

	private void executarLogout() { 
		TelaDeLogin login = new TelaDeLogin();
		login.setVisible(true);
		this.dispose();
	}
}