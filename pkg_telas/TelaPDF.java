package pkg_telas;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import pkg_database.Engenheiro;
import pkg_database.EngenheiroDAO;
import pkg_database.Projeto;
import pkg_database.ProjetoDAO;

public class TelaPDF {

    public JFrame telaPdf;
    private JTable tabelaEngenheiros;
    private JTable tabelaProjetos;
    private JTextField caixaEngenheiro;
    private JTextField caixaProjeto;
    private DefaultTableModel modeloEngenheiros;
    private DefaultTableModel modeloProjetos;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaPDF window = new TelaPDF();
                window.telaPdf.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaPDF() {
        initialize();
        carregarTabelas();
    }

    private void initialize() {
        telaPdf = new JFrame();
        telaPdf.setBounds(100, 100, 936, 615);
        telaPdf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaPdf.getContentPane().setLayout(null);
        telaPdf.setLocationRelativeTo(null);

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(new Color(0, 146, 69));
        painelSuperior.setBounds(0, 0, 920, 66);
        telaPdf.getContentPane().add(painelSuperior);
        painelSuperior.setLayout(null);

        JLabel lblTitulo = new JLabel("RELATÓRIOS E CONSULTAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 11, 900, 44);
        painelSuperior.add(lblTitulo);

        JScrollPane scrollEngenheiros = new JScrollPane();
        scrollEngenheiros.setBounds(24, 137, 429, 273);
        telaPdf.getContentPane().add(scrollEngenheiros);

        modeloEngenheiros = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "CPF", "E-Mail", "Telefone"});
        tabelaEngenheiros = new JTable(modeloEngenheiros);
        scrollEngenheiros.setViewportView(tabelaEngenheiros);

        JScrollPane scrollProjetos = new JScrollPane();
        scrollProjetos.setBounds(478, 137, 420, 273);
        telaPdf.getContentPane().add(scrollProjetos);

        modeloProjetos = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Projeto", "Início", "Fim", "Responsável", "Status"});
        tabelaProjetos = new JTable(modeloProjetos);
        scrollProjetos.setViewportView(tabelaProjetos);

        caixaEngenheiro = new JTextField();
        caixaEngenheiro.setBounds(24, 447, 119, 33);
        telaPdf.getContentPane().add(caixaEngenheiro);
        caixaEngenheiro.setColumns(10);

        JButton btnBuscaEng = new JButton("Buscar Engenheiro (ID)");
        btnBuscaEng.setBounds(153, 447, 169, 33);
        btnBuscaEng.addActionListener(e -> buscarEGerarPdfEngenheiro());
        telaPdf.getContentPane().add(btnBuscaEng);

        JButton btnImprimirTodos = new JButton("Imprimir Relatório Geral");
        btnImprimirTodos.setBounds(345, 513, 230, 41);
        btnImprimirTodos.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnImprimirTodos.addActionListener(e -> gerarPdfGeral());
        telaPdf.getContentPane().add(btnImprimirTodos);
        
        JLabel lblIdEng = new JLabel("ID Engenheiro:");
        lblIdEng.setBounds(24, 423, 119, 14);
        telaPdf.getContentPane().add(lblIdEng);
    }

    private void carregarTabelas() {
        // Limpa linhas antigas
        modeloEngenheiros.setRowCount(0);
        modeloProjetos.setRowCount(0);

        // Alimenta tabela de Engenheiros usando o DAO
        EngenheiroDAO engDao = new EngenheiroDAO();
        List<Engenheiro> engenheiros = engDao.listarTodos();
        for (Engenheiro eng : engenheiros) {
            modeloEngenheiros.addRow(new Object[]{eng.getId(), eng.getNomeCompleto(), eng.getCpf(), eng.getEmail(), eng.getTelefone()});
        }

        // Alimenta tabela de Projetos usando o DAO
        ProjetoDAO projDao = new ProjetoDAO();
        List<Projeto> projetos = projDao.listarTodos();
        for (Projeto p : projetos) {
            modeloProjetos.addRow(new Object[]{p.getId(), p.getNome(), p.getDataInicial(), p.getDataFinal(), p.getEngenheiroResponsavel(), p.getStatus()});
        }
    }

    private void buscarEGerarPdfEngenheiro() {
        String id = caixaEngenheiro.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o ID do engenheiro!");
            return;
        }

        EngenheiroDAO dao = new EngenheiroDAO();
        Engenheiro eng = dao.buscarPorId(id);

        if (eng != null) {
            try {
                Document arquivo = new Document();
                String nomeDocumento = "relatorio_engenheiro_" + id + ".pdf";
                PdfWriter.getInstance(arquivo, new FileOutputStream(nomeDocumento));
                arquivo.open();
                arquivo.add(new Paragraph("RELATÓRIO DO ENGENHEIRO"));
                arquivo.add(new Paragraph("---------------------------------------------"));
                arquivo.add(new Paragraph("ID: " + eng.getId()));
                arquivo.add(new Paragraph("Engenheiro: " + eng.getNomeCompleto()));
                arquivo.add(new Paragraph("CPF: " + eng.getCpf()));
                arquivo.add(new Paragraph("E-Mail: " + eng.getEmail()));
                arquivo.add(new Paragraph("Telefone: " + eng.getTelefone()));
                arquivo.add(new Paragraph("---------------------------------------------"));
                arquivo.close();

                JOptionPane.showMessageDialog(null, "PDF Gerado com sucesso!");
                Desktop.getDesktop().open(new File(nomeDocumento));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Falha ao criar o PDF: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Engenheiro não encontrado.");
        }
    }

    private void gerarPdfGeral() {
        EngenheiroDAO dao = new EngenheiroDAO();
        List<Engenheiro> engenheiros = dao.listarTodos();

        if (engenheiros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum dado encontrado para gerar o relatório.");
            return;
        }

        try {
            Document arquivo = new Document();
            PdfWriter.getInstance(arquivo, new FileOutputStream("relatorio_geral.pdf"));
            arquivo.open();
            arquivo.add(new Paragraph("RELATÓRIO GERAL DE ENGENHEIROS"));
            arquivo.add(new Paragraph("---------------------------------------------"));

            for (Engenheiro eng : engenheiros) {
                arquivo.add(new Paragraph("Engenheiro: " + eng.getNomeCompleto()));
                arquivo.add(new Paragraph("CPF: " + eng.getCpf()));
                arquivo.add(new Paragraph("E-Mail: " + eng.getEmail()));
                arquivo.add(new Paragraph("Telefone: " + eng.getTelefone()));
                arquivo.add(new Paragraph("---------------------------------------------"));
            }
            arquivo.close();

            JOptionPane.showMessageDialog(null, "Relatório Geral emitido com sucesso!");
            Desktop.getDesktop().open(new File("relatorio_geral.pdf"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o PDF Geral: " + ex.getMessage());
        }
    }
}