package pkg_telas;

import java.awt.Color;
import java.awt.Desktop;
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
    private DefaultTableModel modeloEngenheiros;
    private DefaultTableModel modeloProjetos;

    public TelaPDF() {
        initialize();
        carregarTabelas();
    }

    private void initialize() {
        telaPdf = new JFrame();
        telaPdf.setTitle("Relatórios e Consultas");
        telaPdf.setBounds(100, 100, 936, 615);
        telaPdf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaPdf.getContentPane().setLayout(null);
        telaPdf.setLocationRelativeTo(null);

        // Painel superior com cor padrão
        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(EstiloGlobal.COR_PRINCIPAL);
        painelSuperior.setBounds(0, 0, 920, 66);
        telaPdf.getContentPane().add(painelSuperior);
        painelSuperior.setLayout(null);

        JLabel lblTitulo = new JLabel("RELATÓRIOS E CONSULTAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(EstiloGlobal.FONTE_TITULO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 11, 900, 44);
        painelSuperior.add(lblTitulo);

        // Tabela de engenheiros
        JScrollPane scrollEngenheiros = new JScrollPane();
        scrollEngenheiros.setBounds(24, 137, 429, 273);
        telaPdf.getContentPane().add(scrollEngenheiros);

        modeloEngenheiros = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "CPF", "E-Mail", "Telefone"});
        tabelaEngenheiros = new JTable(modeloEngenheiros);
        tabelaEngenheiros.setFont(EstiloGlobal.FONTE_PADRAO);
        tabelaEngenheiros.setRowHeight(22);
        scrollEngenheiros.setViewportView(tabelaEngenheiros);

        // Tabela de projetos
        JScrollPane scrollProjetos = new JScrollPane();
        scrollProjetos.setBounds(478, 137, 420, 273);
        telaPdf.getContentPane().add(scrollProjetos);

        modeloProjetos = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Projeto", "Início", "Fim", "Responsável", "Status"});
        tabelaProjetos = new JTable(modeloProjetos);
        tabelaProjetos.setFont(EstiloGlobal.FONTE_PADRAO);
        tabelaProjetos.setRowHeight(22);
        scrollProjetos.setViewportView(tabelaProjetos);

        // Campo e botão buscar engenheiro
        JLabel lblIdEng = EstiloGlobal.criarLabel("ID Engenheiro:");
        lblIdEng.setBounds(24, 423, 119, 14);
        telaPdf.getContentPane().add(lblIdEng);

        caixaEngenheiro = new JTextField();
        caixaEngenheiro.setBounds(24, 447, 119, 33);
        caixaEngenheiro.setFont(EstiloGlobal.FONTE_PADRAO);
        telaPdf.getContentPane().add(caixaEngenheiro);
        caixaEngenheiro.setColumns(10);

        JButton btnBuscaEng = EstiloGlobal.criarBotao("Buscar Engenheiro (ID)");
        btnBuscaEng.setBounds(153, 447, 200, 33);
        btnBuscaEng.addActionListener(e -> buscarEGerarPdfEngenheiro());
        telaPdf.getContentPane().add(btnBuscaEng);

        // Botão relatório geral
        JButton btnImprimirTodos = EstiloGlobal.criarBotao("Imprimir Relatório Geral");
        btnImprimirTodos.setBounds(345, 513, 230, 41);
        btnImprimirTodos.setFont(EstiloGlobal.FONTE_PADRAO.deriveFont(Font.BOLD));
        btnImprimirTodos.addActionListener(e -> gerarPdfGeral());
        telaPdf.getContentPane().add(btnImprimirTodos);

        // Botão Voltar (padronizado)
        JButton btnVoltar = EstiloGlobal.criarBotao("Voltar");
        btnVoltar.setBounds(800, 513, 100, 41);
        btnVoltar.addActionListener(e -> telaPdf.dispose());
        telaPdf.getContentPane().add(btnVoltar);
    }

    private void carregarTabelas() {
        modeloEngenheiros.setRowCount(0);
        modeloProjetos.setRowCount(0);

        try {
            EngenheiroDAO engDao = new EngenheiroDAO();
            List<Engenheiro> engenheiros = engDao.listarTodos();
            for (Engenheiro eng : engenheiros) {
                modeloEngenheiros.addRow(new Object[]{eng.getId(), eng.getNomeCompleto(), eng.getCpf(), eng.getEmail(), eng.getTelefone()});
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaPdf, "Erro ao carregar engenheiros:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            ProjetoDAO projDao = new ProjetoDAO();
            List<Projeto> projetos = projDao.listarTodos();
            for (Projeto p : projetos) {
                modeloProjetos.addRow(new Object[]{p.getId(), p.getNome(), p.getDataInicial(), p.getDataFinal(), p.getEngenheiroResponsavel(), p.getStatus()});
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(telaPdf, "Erro ao carregar projetos:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEGerarPdfEngenheiro() {
        String idStr = caixaEngenheiro.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(telaPdf, "Digite o ID do engenheiro!");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(telaPdf, "ID deve ser um número inteiro!");
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

                JOptionPane.showMessageDialog(telaPdf, "PDF Gerado com sucesso!\nArquivo: " + nomeDocumento);
                Desktop.getDesktop().open(new File(nomeDocumento));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(telaPdf, "Falha ao criar o PDF: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(telaPdf, "Engenheiro não encontrado.");
        }
    }

    private void gerarPdfGeral() {
        EngenheiroDAO dao = new EngenheiroDAO();
        List<Engenheiro> engenheiros = dao.listarTodos();

        if (engenheiros.isEmpty()) {
            JOptionPane.showMessageDialog(telaPdf, "Nenhum dado encontrado para gerar o relatório.");
            return;
        }

        try {
            Document arquivo = new Document();
            PdfWriter.getInstance(arquivo, new FileOutputStream("relatorio_geral.pdf"));
            arquivo.open();
            arquivo.add(new Paragraph("RELATÓRIO GERAL DE ENGENHEIROS"));
            arquivo.add(new Paragraph("---------------------------------------------"));

            for (Engenheiro eng : engenheiros) {
                arquivo.add(new Paragraph("ID: " + eng.getId()));
                arquivo.add(new Paragraph("Engenheiro: " + eng.getNomeCompleto()));
                arquivo.add(new Paragraph("CPF: " + eng.getCpf()));
                arquivo.add(new Paragraph("E-Mail: " + eng.getEmail()));
                arquivo.add(new Paragraph("Telefone: " + eng.getTelefone()));
                arquivo.add(new Paragraph("---------------------------------------------"));
            }
            arquivo.close();

            JOptionPane.showMessageDialog(telaPdf, "Relatório Geral emitido com sucesso!");
            Desktop.getDesktop().open(new File("relatorio_geral.pdf"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(telaPdf, "Erro ao gerar o PDF Geral: " + ex.getMessage());
        }
    }
}