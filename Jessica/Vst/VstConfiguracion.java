/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ModConsultasSQL;
import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *  Esta es la interfaz de Configuración, la cual debemos ingresar el hostname en el
 * el primer campo, seguido de port,  base de datos (nombre donde esta toda la información)
 * usuario y contraseña. De esta forma configuramos tenemo acceso a la base de datos. 
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 02/12/2018/ProyectoPoo_Acompañamiento
 */
public class VstConfiguracion extends javax.swing.JFrame {

    /**
     * Método para mostrar la interfaz de Configuración 
     */
    public VstConfiguracion() {
        initComponents();
        setTitle("Configuración de la base de datos.");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        database = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hostname = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        user = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pass = new javax.swing.JTextField();

        /**
        * Para visualizar la interfaz 
        */ 

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setText("Database:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 90, -1));

        database.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(database, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 300, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setText("Configuración de la base de datos:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("Hostname:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, 20));

        hostname.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(hostname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 300, -1));

        port.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(port, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 300, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setText("Port:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 90, -1));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, -1, -1));

        user.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 300, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setText("Username:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 90, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setText("Password:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 90, -1));

        pass.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 300, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * Método encargado de recibir y ejecutar la acción correspondiente a lo que va ocurriendo en la vista Configuración. 
    * @param evt encargada de recibir cada accion de los botones de la interfaz.
    */ 
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        ModConsultasSQL cons = new ModConsultasSQL();
        if (database.getText().equals("") || hostname.getText().equals("") || port.getText().equals("") || user.getText().equals("") || pass.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos.");
        } else {
            String todo = database.getText() + "~" + hostname.getText() + "~" + port.getText() + "~" + user.getText() + "~" + pass.getText();
            Texto text = new Texto(ModConsultasSQL.Encriptar(todo));
            cons.encriptar(text);
//            System.out.println(todo);
            JOptionPane.showMessageDialog(null, "Configurado.");
            setVisible(false);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
    * Método para asignar a las variables
    * @param palabra es una cadena recibida
    * @return devuelve una cadena 
    */

    public static class Texto implements Serializable {

        private String palabra;

        public Texto() {
        }

        public Texto(String palabra) {
            this.palabra = palabra;
        }

        public String getPalabra() {
            return palabra;
        }

        public void setPalabra(String palabra) {
            this.palabra = palabra;
        }
    }

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
            java.util.logging.Logger.getLogger(VstConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstConfiguracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGuardar;
    public javax.swing.JTextField database;
    public javax.swing.JTextField hostname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JTextField pass;
    public javax.swing.JTextField port;
    public javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables
}
