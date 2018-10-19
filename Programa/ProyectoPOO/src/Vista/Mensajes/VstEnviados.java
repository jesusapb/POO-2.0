/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Mensajes;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesUsr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class VstEnviados extends javax.swing.JFrame {

    /**
     * Creates new form VstEnviados
     */
    public VstEnviados() {
        initComponents();
        checkEditar.setVisible(false);
        t = new Timer(10, acciones);
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEnviados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMensaje = new javax.swing.JTextPane();
        btnReenviar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPara = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAsunto = new javax.swing.JTextPane();
        checkEditar = new javax.swing.JCheckBox();
        matricula = new javax.swing.JTextField();
        cronometro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaEnviados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Para:", "Asunto:"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEnviados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEnviadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEnviados);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 380));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Asunto:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));

        txtMensaje.setEditable(false);
        jScrollPane2.setViewportView(txtMensaje);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 200, 110));

        btnReenviar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReenviar.setText("Reenviar mensaje");
        jPanel1.add(btnReenviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, -1, -1));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar mensaje");
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, -1, -1));

        id.setEditable(false);
        id.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 90, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mensaje:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, -1, -1));

        txtPara.setEditable(false);
        jScrollPane3.setViewportView(txtPara);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 200, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Para:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        txtAsunto.setEditable(false);
        jScrollPane4.setViewportView(txtAsunto);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 200, 50));

        checkEditar.setText("Editar mensaje");
        checkEditar.setContentAreaFilled(false);
        checkEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEditarActionPerformed(evt);
            }
        });
        jPanel1.add(checkEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        matricula.setEditable(false);
        matricula.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(matricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 130, -1));

        cronometro.setText("00:00:00");
        jPanel1.add(cronometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 60, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaEnviadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEnviadosMouseClicked
        int row = evt.getY() / tablaEnviados.getRowHeight();
        String ident = "" + tablaEnviados.getValueAt(row, 0);
        id.setText(ident);
        String para = "" + tablaEnviados.getValueAt(row, 1);
        txtPara.setText(para);
        String asunto = "" + tablaEnviados.getValueAt(row, 2);
        txtAsunto.setText(asunto);
        ModVariablesMensaje var = new ModVariablesMensaje();
        ModConsultasSQL.mensaje(ident, this, var);
        btnReenviar.setVisible(true);
        //btnEliminar.setVisible(true);
        checkEditar.setVisible(true);
    }//GEN-LAST:event_tablaEnviadosMouseClicked

    private void checkEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEditarActionPerformed
        if (checkEditar.isSelected() == false) {
            txtMensaje.setEditable(false);
        } else {
            txtMensaje.setEditable(true);
        }
    }//GEN-LAST:event_checkEditarActionPerformed

    /**
     * @param args the command line arguments
     */
    private Timer t;
    private int h, m, s, cs;

    private ActionListener acciones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 5 == 0)) {
                ModVariablesMensaje varM = new ModVariablesMensaje();
                ModVariablesUsr var = new ModVariablesUsr();
                var.setMatricula(matricula.getText());
                ModConsultasSQL.enviados(tablaEnviados, varM, var);
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
            actualizarLabel();
        }
    };

    private void actualizarLabel() {
        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s + ":" + (cs <= 9 ? "0" : "") + cs;
        cronometro.setText(tiempo);
    }

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
            java.util.logging.Logger.getLogger(VstEnviados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstEnviados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstEnviados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstEnviados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstEnviados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnReenviar;
    private javax.swing.JCheckBox checkEditar;
    private javax.swing.JLabel cronometro;
    public javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTextField matricula;
    public javax.swing.JTable tablaEnviados;
    public javax.swing.JTextPane txtAsunto;
    public javax.swing.JTextPane txtMensaje;
    public javax.swing.JTextPane txtPara;
    // End of variables declaration//GEN-END:variables
}
