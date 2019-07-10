/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.telas;

import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import br.com.os.dal.ModuloConexao;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author bruno.alves
 */
public class Os extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // a linha a baixo  cria uma variavel para armazenar un texto de acordo com o radio button.
    private String tipo;

    /**
     * Creates new form TelacadastroOs
     */
    public Os() {
        initComponents();
        conexao = ModuloConexao.conector();
        rbtOS.setSelected(true);

    }
    // Função para  realizar uma busca nos registros de OS
    private void buscaFiltro() {
        String sql = "select idcli Id,nome Nome,fonecli Telefone from tbclientes where nome like?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBuscaClientes.getText() + "%");
            rs = pst.executeQuery();
            //a linha a baixo usa a biblioteca rs2xml.jar para preecher a tabela 
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setarCampo() {
        int setar = tblClientes.getSelectedRow();
        txtOsCLiente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());

    }
    // Função para cadastrar uma os 
    private void cadastrarOs() {
        String Sql = "insert into tbos(tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli)value(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(Sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            pst.setString(7, txtOsValor.getText());
            pst.setString(8, txtOsCLiente.getText());

            if (txtOsCLiente.getText().isEmpty() || txtOsEquipamento.getText().isEmpty() || txtOsDefeito.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Preecher todos campos com com *");
            } else {
                int adcionado = pst.executeUpdate();
                if (adcionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emtida com Sucesso!");
                    limparCampos();

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    //Função para realizar a pesquisa de OS especifica no Sistema
    private void pesquisar_os() {
        //a linha abaixo cria uma caixa de entrada de tipo  Joption pane.
        int num_os = Integer.parseInt(JOptionPane.showInputDialog("Numero da OS :"));
        String sql = "select * from tbos where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, num_os);
            rs = pst.executeQuery();
            if (rs.next()) {

                txtNUmeroOS.setText(rs.getString(1));
                txtDataOS.setText(rs.getString(2));
                String rbtTipo = rs.getString(3);

                if (rbtTipo.equals("Os")) {
                    rbtOS.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOr.setSelected(true);
                    tipo = "orçamento";
                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEquipamento.setText(rs.getString(5));
                txtOsDefeito.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTecnico.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtOsCLiente.setText(rs.getString(10));

                // desativando botoes
                btnOSAdcionar.setEnabled(false);
                BtnOsImprimir.setEnabled(false);
                txtOsCLiente.setEnabled(false);
                tblClientes.setEnabled(false);
                txtBuscaClientes.setEnabled(false);

            } else {

                JOptionPane.showMessageDialog(null, "Os não cadastrada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //função que seta os campos  da Tela de OS
    private void limparCampos() {
        txtDataOS.setText(null);
        txtNUmeroOS.setText(null);
        txtOsEquipamento.setText(null);
        txtOsDefeito.setText(null);
        txtOsServico.setText(null);
        txtOsTecnico.setText(null);
        txtOsValor.setText(null);
        txtOsCLiente.setText(null);
    }
    // Função que  edita os valores do  OS aberta no Sistema
    private void editar_Os() {
        String sql = "update tbos set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where os=?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            pst.setString(7, txtOsValor.getText());
            pst.setString(8, txtOsCLiente.getText());

            if (txtOsCLiente.getText().isEmpty() || txtOsEquipamento.getText().isEmpty() || txtOsDefeito.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Preecher todos campos com com *");
            } else {
                int adcionado = pst.executeUpdate();
                if (adcionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com Sucesso!");
                    limparCampos();

                    //ativando os objetos
                    txtBuscaClientes.setEnabled(true);
                    btnOSAdcionar.setEnabled(true);
                    BtnOsImprimir.setEnabled(true);
                    tblClientes.setEnabled(true);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    //Função que deleta a OS que esta cadastrada no Sistema.
    public void excluir_Os() {
        int confirma = JOptionPane.showConfirmDialog(null, "você tem certeza?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNUmeroOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS apagada com sucesso!!");

                    limparCampos();

                    //ativando os objetos
                    txtBuscaClientes.setEnabled(true);
                    btnOSAdcionar.setEnabled(true);
                    BtnOsImprimir.setEnabled(true);
                    tblClientes.setEnabled(true);
                }

            } catch (Exception e) {
            }

        } else {

        }
    }
    //Função que  Imprime os  relatorios de uma OS especifica.
    private void imprimirOS() {
        int Confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (Confirma == JOptionPane.YES_OPTION) {
            //imprimindo relatorio com o framework jasperreport

            try {
                //usando o hashmap para criar um filtro de busca no Ireport.
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(txtNUmeroOS.getText()));
                // usando o jasperprint
                //usando a classe para  preparar a impressão do relatorio

                JasperPrint print = JasperFillManager.fillReport("C:\\Users\\bruno.alves\\Documents\\NetBeansProjects\\projeto_sistema_os\\src\\Relatorios\\imprimir.jasper",filtro, conexao);

                JasperViewer.viewReport(print, false);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNUmeroOS = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDataOS = new javax.swing.JTextPane();
        rbtOS = new javax.swing.JRadioButton();
        rbtOr = new javax.swing.JRadioButton();
        cboOsSit = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtBuscaClientes = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtOsCLiente = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtOsDefeito = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtOsServico = new javax.swing.JTextPane();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtOsTecnico = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtOsValor = new javax.swing.JTextPane();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnOSAdcionar = new javax.swing.JButton();
        btnOsBusca = new javax.swing.JButton();
        btnOsEditar = new javax.swing.JButton();
        btnOsExcluir = new javax.swing.JButton();
        BtnOsImprimir = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtOsEquipamento = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro  OS");

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setText("Nº OS :");

        jLabel2.setText("Data :");

        jScrollPane1.setViewportView(txtNUmeroOS);

        jScrollPane2.setViewportView(txtDataOS);

        buttonGroup1.add(rbtOS);
        rbtOS.setText("Orçamento");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOr);
        rbtOr.setText("Ordem de Serviço");
        rbtOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrActionPerformed(evt);
            }
        });

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abondonado pelo cliente", "Retornou" }));
        cboOsSit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOsSitActionPerformed(evt);
            }
        });

        jLabel3.setText("Situação :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbtOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbtOr))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOS)
                    .addComponent(rbtOr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtBuscaClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaClientesKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(txtBuscaClientes);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/bt_busca_menor.png"))); // NOI18N

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblClientes);

        jScrollPane5.setViewportView(txtOsCLiente);

        jLabel6.setText("*ID :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(21, 21, 21)))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("*Equipamento:");

        jScrollPane7.setViewportView(txtOsDefeito);

        jLabel8.setText("Serviço :");

        jScrollPane8.setViewportView(txtOsServico);

        jLabel9.setText("Defeito :");

        jScrollPane9.setViewportView(txtOsTecnico);

        jScrollPane10.setViewportView(txtOsValor);

        jLabel10.setText("Técnico :");

        jLabel11.setText("Valor:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));

        btnOSAdcionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/botao_inserir.png"))); // NOI18N
        btnOSAdcionar.setToolTipText("Cadastrar os");
        btnOSAdcionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSAdcionarActionPerformed(evt);
            }
        });

        btnOsBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/botao_buscar.png"))); // NOI18N
        btnOsBusca.setToolTipText("Buscar OS");
        btnOsBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsBuscaActionPerformed(evt);
            }
        });

        btnOsEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/botao_editar.png"))); // NOI18N
        btnOsEditar.setToolTipText("Editar OS");
        btnOsEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsEditarActionPerformed(evt);
            }
        });

        btnOsExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/botao_remover.png"))); // NOI18N
        btnOsExcluir.setToolTipText("Excluir OS");
        btnOsExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcluirActionPerformed(evt);
            }
        });

        BtnOsImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/botao_imprimir.png"))); // NOI18N
        BtnOsImprimir.setToolTipText("Imprimir OS");
        BtnOsImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOsImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnOSAdcionar)
                .addGap(32, 32, 32)
                .addComponent(btnOsBusca)
                .addGap(40, 40, 40)
                .addComponent(btnOsEditar)
                .addGap(31, 31, 31)
                .addComponent(btnOsExcluir)
                .addGap(37, 37, 37)
                .addComponent(BtnOsImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOSAdcionar)
                        .addComponent(btnOsBusca)
                        .addComponent(btnOsEditar)
                        .addComponent(btnOsExcluir))
                    .addComponent(BtnOsImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane11.setViewportView(txtOsEquipamento);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                            .addComponent(jScrollPane11))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(44, 44, 44)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOsEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsEditarActionPerformed
        // TODO add your handling code here:
        editar_Os();
    }//GEN-LAST:event_btnOsEditarActionPerformed

    private void btnOsExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcluirActionPerformed
        // TODO add your handling code here:
        excluir_Os();
    }//GEN-LAST:event_btnOsExcluirActionPerformed

    private void txtBuscaClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaClientesKeyReleased
        // TODO add your handling code here:
        buscaFiltro();
    }//GEN-LAST:event_txtBuscaClientesKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setarCampo();

    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed
        // atribuido um texto a variavel tipo selecionado
        tipo = "OS";
    }//GEN-LAST:event_rbtOSActionPerformed

    private void rbtOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrActionPerformed
        // TODO add your handling code here:
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrActionPerformed

    private void cboOsSitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOsSitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboOsSitActionPerformed

    private void btnOSAdcionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSAdcionarActionPerformed
        cadastrarOs();
    }//GEN-LAST:event_btnOSAdcionarActionPerformed

    private void btnOsBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsBuscaActionPerformed
        // TODO add your handling code here:
        pesquisar_os();
    }//GEN-LAST:event_btnOsBuscaActionPerformed

    private void BtnOsImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOsImprimirActionPerformed
        // TODO add your handling code here:
        imprimirOS();
    }//GEN-LAST:event_BtnOsImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Os.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Os.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Os.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Os.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Os().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnOsImprimir;
    private javax.swing.JButton btnOSAdcionar;
    private javax.swing.JButton btnOsBusca;
    private javax.swing.JButton btnOsEditar;
    private javax.swing.JButton btnOsExcluir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOr;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextPane txtBuscaClientes;
    private javax.swing.JTextPane txtDataOS;
    private javax.swing.JTextPane txtNUmeroOS;
    private javax.swing.JTextPane txtOsCLiente;
    private javax.swing.JTextPane txtOsDefeito;
    private javax.swing.JTextPane txtOsEquipamento;
    private javax.swing.JTextPane txtOsServico;
    private javax.swing.JTextPane txtOsTecnico;
    private javax.swing.JTextPane txtOsValor;
    // End of variables declaration//GEN-END:variables
}
