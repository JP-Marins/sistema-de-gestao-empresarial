package pkg_telas;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class EstiloGlobal {
    
    public static final Color COR_PRINCIPAL = new Color(0, 146, 69);
    public static final Color COR_FUNDO = Color.WHITE;
    public static final Color COR_PAINEIS = Color.decode("#F8F9FA");
    
    public static final Font FONTE_PADRAO = new Font("Tahoma", Font.PLAIN, 14);
    public static final Font FONTE_TITULO = new Font("Tahoma", Font.BOLD, 22);

    public static JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Tahoma", Font.BOLD, 13));
        botao.setBackground(COR_PRINCIPAL);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return botao;
    }

    public static JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_PADRAO);
        label.setForeground(Color.BLACK);
        return label;
    }
}