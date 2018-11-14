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
public class VstLogin extends javax.swing.JFrame {

    /**
     * Creates new form VstLogin
     */
    public VstLogin() {
        initComponents();
        btnValidar.setVisible(false);
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
        ImgUsrLg = new javax.swing.JLabel();
        txtMat = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        ImgPswdLg = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        btnAcceder = new javax.swing.JButton();
        btnOlvido = new javax.swing.JButton();
        checkContraseña = new javax.swing.JCheckBox();
        btnValidar = new javax.swing.JButton();
        btnConfiguración = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ImgUsrLg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Customer_30px.png"))); // NOI18N
        jPanel1.add(ImgUsrLg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        txtMat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMat.setBorder(null);
        txtMat.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel1.add(txtMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 176, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 180, 10));

        ImgPswdLg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Key_30px.png"))); // NOI18N
        jPanel1.add(ImgPswdLg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPass.setToolTipText("Contraseña");
        txtPass.setBorder(null);
        jPanel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 176, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 180, 10));

        btnAcceder.setBackground(new java.awt.Color(0, 0, 0));
        btnAcceder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Enter_40px.png"))); // NOI18N
        btnAcceder.setText("Acceder");
        btnAcceder.setToolTipText("Acceder");
        btnAcceder.setBorder(null);
        btnAcceder.setContentAreaFilled(false);
        btnAcceder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnAcceder, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        btnOlvido.setBackground(new java.awt.Color(0, 0, 0));
        btnOlvido.setText("¿Olvidaste tu contraseña?");
        btnOlvido.setToolTipText("Acceder");
        btnOlvido.setBorder(null);
        btnOlvido.setContentAreaFilled(false);
        btnOlvido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnOlvido, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 150, -1));

        checkContraseña.setBackground(new java.awt.Color(255, 255, 255));
        checkContraseña.setText("Hacer visible contraseña.");
        checkContraseña.setContentAreaFilled(false);
        checkContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkContraseñaActionPerformed(evt);
            }
        });
        jPanel1.add(checkContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        btnValidar.setBackground(new java.awt.Color(0, 0, 0));
        btnValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Enter_40px.png"))); // NOI18N
        btnValidar.setText("Validar para acceder");
        btnValidar.setToolTipText("Acceder");
        btnValidar.setBorder(null);
        btnValidar.setContentAreaFilled(false);
        btnValidar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnValidar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        btnConfiguración.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Settings_20px.png"))); // NOI18N
        btnConfiguración.setToolTipText("Configuración");
        btnConfiguración.setActionCommand("Configuración");
        btnConfiguración.setBorder(null);
        btnConfiguración.setContentAreaFilled(false);
        btnConfiguración.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfiguración.setLabel("");
        jPanel1.add(btnConfiguración, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));
        btnConfiguración.getAccessibleContext().setAccessibleName("Configuración");
        btnConfiguración.getAccessibleContext().setAccessibleDescription("Configuración");

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkContraseñaActionPerformed
        if (checkContraseña.isSelected() == true) {
            txtPass.setEchoChar((char)0);
        } else {
            txtPass.setEchoChar('*');
        }
    }//GEN-LAST:event_checkContraseñaActionPerformed

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
            java.util.logging.Logger.getLogger(VstLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel ImgPswdLg;
    public javax.swing.JLabel ImgUsrLg;
    public javax.swing.JButton btnAcceder;
    public javax.swing.JButton btnConfiguración;
    public javax.swing.JButton btnOlvido;
    public javax.swing.JButton btnValidar;
    public javax.swing.JCheckBox checkContraseña;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JTextField txtMat;
    public javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
