/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class VstEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form VstEmpleados
     */
    public VstEmpleados() {
        initComponents();
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        //btnQuitar.setVisible(false);
        //btnDar.setVisible(false);
        btnReestablecer.setVisible(false);
        btnAvances.setVisible(false);
        checkCorreo.setVisible(false);
        email.setVisible(false);
        //setDefaultCloseOperation(0);

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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegistrados = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApPat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApMat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnReestablecer = new javax.swing.JButton();
        btnAvances = new javax.swing.JButton();
        checkCorreo = new javax.swing.JCheckBox();
        email = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Usuarios Registrados:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tablaRegistrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Matrícula.", "Nombre."
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
        tablaRegistrados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaRegistrados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRegistradosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRegistrados);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 120));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Registration_30px.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setBorder(null);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Denied_30px.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 140, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("<html>Apellido paterno:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 60, -1));

        txtApPat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtApPat, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("<html>Apellido materno:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 60, -1));

        txtApMat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtApMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 140, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Matrícula:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        txtMatricula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 140, -1));

        btnGenerar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Available_Updates_20px.png"))); // NOI18N
        btnGenerar.setText("Auto-Generar");
        btnGenerar.setBorder(null);
        btnGenerar.setContentAreaFilled(false);
        btnGenerar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("<html>Correo electrónico:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 80, -1));

        txtCorreo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 140, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("@gmail.com");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, -1, -1));

        btnReestablecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Change_User_30px.png"))); // NOI18N
        btnReestablecer.setText("<html>Atrás");
        btnReestablecer.setBorder(null);
        btnReestablecer.setContentAreaFilled(false);
        btnReestablecer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnReestablecer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 150, -1));

        btnAvances.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Advance_30px.png"))); // NOI18N
        btnAvances.setText("Info de Quizzes presentados");
        btnAvances.setBorder(null);
        btnAvances.setContentAreaFilled(false);
        btnAvances.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnAvances, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, -1, -1));

        checkCorreo.setText("Modificar correo electrónico");
        checkCorreo.setContentAreaFilled(false);
        checkCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(checkCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        email.setEditable(false);
        email.setBackground(new java.awt.Color(255, 255, 255));
        email.setBorder(null);
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 150, -1));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Add_User_Male_30px.png"))); // NOI18N
        btnAgregar.setText("Agregar nuevo empleado");
        btnAgregar.setBorder(null);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaRegistradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRegistradosMouseClicked
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            int Fila = tablaRegistrados.getSelectedRow();
            String codigo = tablaRegistrados.getValueAt(Fila, 0).toString();

            ps = con.prepareStatement("SELECT matricula, nombre, ap_pat, ap_mat, tipo, correo, status FROM usuarios WHERE matricula = ?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            while (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtApPat.setText(rs.getString("ap_pat"));
                txtApMat.setText(rs.getString("ap_mat"));
                txtMatricula.setText(rs.getString("matricula"));
                txtMatricula.setEditable(false);

                btnModificar.setVisible(true);
                btnEliminar.setVisible(true);
                checkCorreo.setVisible(true);
                checkCorreo.setSelected(false);
                txtCorreo.setEnabled(false);

                btnAvances.setVisible(true);
                btnAgregar.setVisible(false);
                btnGenerar.setVisible(false);
                btnReestablecer.setVisible(true);

                String string = rs.getString("correo");
                String[] parts = string.split("@");
                String part1 = parts[0];

                txtCorreo.setText(part1);
                email.setText(part1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VstEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablaRegistradosMouseClicked

    private void checkCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCorreoActionPerformed
        if (checkCorreo.isSelected() == false){
            txtCorreo.setEnabled(false);
        } else {
            txtCorreo.setEnabled(true);
        }
    }//GEN-LAST:event_checkCorreoActionPerformed

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
                ModConsultasSQL.tablaEmp(tablaRegistrados);
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
           // actualizarLabel();
        }
    };

//    private void actualizarLabel() {
//        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s + ":" + (cs <= 9 ? "0" : "") + cs;
//        cronometro.setText(tiempo);
//    }

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
            java.util.logging.Logger.getLogger(VstEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgregar;
    public javax.swing.JButton btnAvances;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGenerar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnReestablecer;
    public javax.swing.JCheckBox checkCorreo;
    public javax.swing.JTextField email;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaRegistrados;
    public javax.swing.JTextField txtApMat;
    public javax.swing.JTextField txtApPat;
    public javax.swing.JTextField txtCorreo;
    public javax.swing.JTextField txtMatricula;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
