/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author Antonio
 */
public class VstPerfil extends javax.swing.JFrame {

    /**
     * Creates new form VstPerfil
     */
    public VstPerfil() {
        initComponents();
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
        nombre = new javax.swing.JTextField();
        ap_pat = new javax.swing.JTextField();
        correo = new javax.swing.JTextField();
        ed_correo = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        btnInfo = new javax.swing.JButton();
        ed_nombre = new javax.swing.JCheckBox();
        matricula = new javax.swing.JTextField();
        btnValidar = new javax.swing.JButton();
        contraseña = new javax.swing.JPasswordField();
        btnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ap_mat = new javax.swing.JTextField();
        cambio = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 380, -1));
        jPanel1.add(ap_pat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 380, -1));
        jPanel1.add(correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 310, -1));

        buttonGroup1.add(ed_correo);
        ed_correo.setActionCommand("Editar");
        ed_correo.setContentAreaFilled(false);
        ed_correo.setLabel("Editar");
        ed_correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ed_correoActionPerformed(evt);
            }
        });
        jPanel1.add(ed_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, -1, -1));

        jLabel1.setText("@gmail.com");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, -1, -1));

        btnInfo.setText("?");
        btnInfo.setBorder(null);
        btnInfo.setContentAreaFilled(false);
        jPanel1.add(btnInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 50, -1));

        buttonGroup1.add(ed_nombre);
        ed_nombre.setActionCommand("Editar");
        ed_nombre.setContentAreaFilled(false);
        ed_nombre.setLabel("Editar");
        ed_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ed_nombreActionPerformed(evt);
            }
        });
        jPanel1.add(ed_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));
        jPanel1.add(matricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 280, -1));

        btnValidar.setText("Validar");
        jPanel1.add(btnValidar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 150, -1));
        jPanel1.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 280, -1));

        btnGuardar.setText("Guardar");
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 150, -1));

        jLabel2.setText("Contraseña:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel3.setText("Matrícula:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));
        jPanel1.add(ap_mat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 380, -1));

        buttonGroup1.add(cambio);
        cambio.setText("Cambiar contraseña");
        cambio.setContentAreaFilled(false);
        cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioActionPerformed(evt);
            }
        });
        jPanel1.add(cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioActionPerformed
        if (cambio.isSelected() == true) {
            btnValidar.setEnabled(true);
            matricula.setEditable(true);
            contraseña.setEditable(true);
            nombre.setEditable(false);
            ap_pat.setEditable(false);
            ap_mat.setEditable(false);
            correo.setEditable(false);
        } else {
            btnValidar.setEnabled(false);
            matricula.setEditable(false);
            contraseña.setEditable(false);
        }
    }//GEN-LAST:event_cambioActionPerformed

    private void ed_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ed_nombreActionPerformed
        if (ed_nombre.isSelected() == true) {
            btnValidar.setEnabled(true);
            matricula.setEditable(true);
            contraseña.setEditable(true);
            nombre.setEditable(true);
            ap_pat.setEditable(true);
            ap_mat.setEditable(true);
            correo.setEditable(false);
        }
    }//GEN-LAST:event_ed_nombreActionPerformed

    private void ed_correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ed_correoActionPerformed
        if (ed_correo.isSelected() == true) {
            btnValidar.setEnabled(true);
            matricula.setEditable(true);
            contraseña.setEditable(true);
            nombre.setEditable(false);
            ap_pat.setEditable(false);
            ap_mat.setEditable(false);
            correo.setEditable(true);
        }
    }//GEN-LAST:event_ed_correoActionPerformed

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
            java.util.logging.Logger.getLogger(VstPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstPerfil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField ap_mat;
    public javax.swing.JTextField ap_pat;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnInfo;
    public javax.swing.JButton btnValidar;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JCheckBox cambio;
    public javax.swing.JPasswordField contraseña;
    public javax.swing.JTextField correo;
    public javax.swing.JCheckBox ed_correo;
    public javax.swing.JCheckBox ed_nombre;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JTextField matricula;
    public javax.swing.JTextField nombre;
    // End of variables declaration//GEN-END:variables
}