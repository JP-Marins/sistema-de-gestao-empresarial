package pkg_telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class TelaSplash extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JProgressBar barraProgresso;
	private Timer timer;

	// Identidade Visual (Verde Eco)
	private final Color COR_PRINCIPAL = new Color(0, 146, 69);
	private final Color COR_FUNDO = Color.WHITE;

	public TelaSplash() {
		// Remove as bordas e botões de fechar padrão para parecer um Splash real
		setUndecorated(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(COR_FUNDO);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);

		// --- PAINEL CENTRAL: TEXTOS ---
		JPanel painelCentral = new JPanel();
		painelCentral.setBackground(COR_FUNDO);
		painelCentral.setLayout(null); // Layout absoluto para posicionar os textos livremente
		contentPane.add(painelCentral, BorderLayout.CENTER);

		JLabel lblTitulo = new JLabel("Construtora Eco");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitulo.setForeground(COR_PRINCIPAL);
		lblTitulo.setBounds(115, 70, 300, 45);
		painelCentral.add(lblTitulo);

		JLabel lblSubtitulo = new JLabel("Sistema de Engenharia e Controle");
		lblSubtitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubtitulo.setForeground(Color.GRAY);
		lblSubtitulo.setBounds(135, 115, 250, 20);
		painelCentral.add(lblSubtitulo);

		JLabel lblStatus = new JLabel("Carregando módulos...");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setForeground(Color.DARK_GRAY);
		lblStatus.setBounds(5, 195, 200, 20);
		painelCentral.add(lblStatus);

		// --- PAINEL INFERIOR: BARRA DE PROGRESSO ---
		barraProgresso = new JProgressBar();
		barraProgresso.setStringPainted(true);
		barraProgresso.setForeground(COR_PRINCIPAL);
		barraProgresso.setBackground(Color.decode("#E9ECEF"));
		barraProgresso.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(barraProgresso, BorderLayout.SOUTH);

		// Inicializa o carregamento do sistema
		initializeTimer();
	}

	/**
	 * Configura o Timer que simula o carregamento e faz a transição de telas
	 */
	private void initializeTimer() {
		// O Timer vai disparar a cada 30 milissegundos
		timer = new Timer(30, new ActionListener() {
			private int progresso = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				progresso += 2; // Incrementa a barra de 2 em 2
				barraProgresso.setValue(progresso);

				// Quando chegar a 100%, para o Timer e abre a próxima tela
				if (progresso >= 100) {
					timer.stop();

					// CORREÇÃO: Instancia e exibe a tela de login dinamicamente
					TelaDeLogin telaLogin = new TelaDeLogin();
					telaLogin.setVisible(true);

					// Fecha a tela de Splash atual
					dispose(); 
				}
			}
		});
		timer.start();
	}

	/**
	 * Método principal para executar o sistema a partir do Splash
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {
			try {
				TelaSplash frame = new TelaSplash();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}