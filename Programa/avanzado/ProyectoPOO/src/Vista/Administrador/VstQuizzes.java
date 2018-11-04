/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador;

import Controlador.Administrador.CtrlQuizzes;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class VstQuizzes extends javax.swing.JFrame {

    /**
     * Creates new form VstQuizzes
     */
    public VstQuizzes() {
        initComponents();
        CtrlQuizzes.tiempo(comboLimHoras, comboLimMinutos);
        CtrlQuizzes.intentos(comboIntentos);
        comboModCalf.setEnabled(false);
        checkActivar.setVisible(false);
        checkCamName.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        btnAgrPreg.setVisible(false);
        //id.setVisible(false);

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
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaQuizzes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboModCalf = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboLimHoras = new javax.swing.JComboBox<>();
        comboLimMinutos = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        checkActivar = new javax.swing.JCheckBox();
        txtNombre = new javax.swing.JTextField();
        comboIntentos = new javax.swing.JComboBox<>();
        comboNumPreg = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();
        cronometro = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        checkCamName = new javax.swing.JCheckBox();
        btnNuevo = new javax.swing.JButton();
        btnAgrPreg = new javax.swing.JButton();
        actual = new javax.swing.JTextField();
        matricula = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jCalendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 340, 220));

        tablaQuizzes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Descripción", "Status", "#PregTot", "#PregAct", "FRegistro", "FActivado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaQuizzes.setGridColor(new java.awt.Color(240, 240, 240));
        tablaQuizzes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaQuizzesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaQuizzes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 780, 220));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Modo de calificar:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripción:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        comboModCalf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboModCalf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(comboModCalf, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 270, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nombre:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Límite de tiempo:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("<html>Intentos para presentar:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 90, -1));

        comboLimHoras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboLimHoras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(comboLimHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, -1));

        comboLimMinutos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboLimMinutos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(comboLimMinutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 130, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("<html>Preguntas del Quizz:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 70, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, -1, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 530, -1, -1));

        btnModificar.setText("Modificar");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, -1));

        checkActivar.setText("Activar Quizz");
        checkActivar.setContentAreaFilled(false);
        checkActivar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActivarActionPerformed(evt);
            }
        });
        jPanel1.add(checkActivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 110, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 310, -1));

        comboIntentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboIntentosActionPerformed(evt);
            }
        });
        jPanel1.add(comboIntentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 380, 90, -1));

        comboNumPreg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jPanel1.add(comboNumPreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 80, -1));

        jScrollPane2.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 310, 70));

        cronometro.setText("00:00:00");
        jPanel1.add(cronometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 90, 20));

        id.setEditable(false);
        id.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 70, -1));

        checkCamName.setText("Cambiar nombre");
        checkCamName.setContentAreaFilled(false);
        checkCamName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCamNameActionPerformed(evt);
            }
        });
        jPanel1.add(checkCamName, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, -1, -1));

        btnNuevo.setText("Nuevo Quizz");
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 120, -1));

        btnAgrPreg.setText("Agregar preguntas");
        jPanel1.add(btnAgrPreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 290, 170, -1));

        actual.setEditable(false);
        actual.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(actual, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 70, -1));

        matricula.setEditable(false);
        matricula.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(matricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboIntentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIntentosActionPerformed
        CtrlQuizzes.modo(comboModCalf, comboIntentos);
    }//GEN-LAST:event_comboIntentosActionPerformed

    ModVariablesQuizzes varQ = new ModVariablesQuizzes();

    private void tablaQuizzesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaQuizzesMouseClicked
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            int Fila = tablaQuizzes.getSelectedRow();
            String codigo = tablaQuizzes.getValueAt(Fila, 0).toString();

            ps = con.prepareStatement("SELECT id, nombre, descripcion, p_totales, p_actuales, status, intentos, mod_calif, tiempo, f_registro, f_activacion FROM quizzes WHERE nombre = ?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            while (rs.next()) {
                varQ.setId(rs.getInt("id"));
                id.setText(rs.getString("id"));
                txtNombre.setText(rs.getString("nombre"));
                varQ.setNombre(rs.getString("nombre"));
                txtDescripcion.setText(rs.getString("descripcion"));
                varQ.setDescripcion(rs.getString("descripcion"));
                comboNumPreg.setSelectedItem(rs.getString("p_totales"));

                varQ.setP_totales(rs.getString("p_totales"));
                varQ.setP_actuales(rs.getString("p_actuales"));
                actual.setText(rs.getString("p_actuales"));
                varQ.setActuales(rs.getInt("p_actuales"));
                varQ.setTotales(rs.getInt("p_totales"));
                varQ.setStatus(rs.getString("status"));

                comboIntentos.setSelectedItem(rs.getString("intentos"));
                varQ.setIntentos(rs.getString("intentos"));
                comboModCalf.setSelectedItem(rs.getString("mod_calif"));
                varQ.setMod_calif(rs.getString("mod_calif"));
                varQ.setTiempo(rs.getString("tiempo"));
                varQ.setF_registro(rs.getString("f_registro"));
                varQ.setF_activacion(rs.getString("f_activacion"));

                String stringf = varQ.getTiempo();
                String[] partF = stringf.split(":");
                String part1 = partF[0];
                String part2 = partF[1];

                comboLimHoras.setSelectedItem(part1);
                comboLimMinutos.setSelectedItem(part2);

                if ("Deshabilitado".equals(varQ.getStatus())) {
                    checkActivar.setVisible(true);
                    checkActivar.setSelected(false);
                } else if ("Habilitado".equals(varQ.getStatus())) {
                    checkActivar.setVisible(true);
                    checkActivar.setSelected(true);
                }

                checkCamName.setVisible(true);
                checkCamName.setSelected(false);
                txtNombre.setEditable(false);
                btnModificar.setVisible(true);
                btnEliminar.setVisible(true);
                btnGuardar.setVisible(false);
                btnAgrPreg.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VstEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablaQuizzesMouseClicked

    private void checkActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActivarActionPerformed
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");
        ModVariablesUsr varU = new ModVariablesUsr();
        ModConsultasSQL con = new ModConsultasSQL();

        varU.setMatricula(matricula.getText());
        varU.setDia(fechaDate.format(date));
        varU.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(varU);
        ModConsultasSQL.status(varU);

        if (checkActivar.isSelected() == true) {
            if (Integer.parseInt(actual.getText()) < varQ.getTotales()) {
                JOptionPane.showMessageDialog(null, "No se puede activar este Quizz debido \na que no tiene las preguntas minimas.");
                checkActivar.setSelected(false);
            } else {
                PreparedStatement ps = null, ps1 = null;
                try {
                    ModConexion objCon = new ModConexion();
                    Connection conn = objCon.getConexion();
                    ps = conn.prepareStatement("UPDATE quizzes SET status = ? WHERE id = '" + id.getText() + "'");
                    ps.setString(1, "Habilitado");
                    ps.execute();

                    ps1 = conn.prepareStatement("UPDATE quizzes SET f_activacion = ? WHERE id = '" + id.getText() + "'");
                    ps1.setString(1, fechaDate.format(date));
                    ps1.execute();

                    ModConsultasSQL.tablaQuizz(tablaQuizzes);
                    JOptionPane.showMessageDialog(null, "Activacion del Quizz completa.");

                    ModVariablesReg varR = new ModVariablesReg();
                    String tipo = "Administrador";
                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                    String que = "Se activó el Quizz: " + txtNombre.getText();
                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                    String comp = varU.getMatricula();
                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                    String tipo2 = "Empleado";
                    String quien2 = varU.getMatricula() + "/ " + varU.getNombre_completo();
                    String que2 = "Se activó el Quizz: " + txtNombre.getText();
                    String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                    String comp2 = varU.getMatricula();
                    if (con.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                } catch (SQLException ex) {
                    Logger.getLogger(VstQuizzes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            PreparedStatement ps = null, ps1 = null;
            try {
                ModConexion objCon = new ModConexion();
                Connection conn = objCon.getConexion();
                ps = conn.prepareStatement("UPDATE quizzes SET status = ? WHERE nombre = '" + txtNombre.getText() + "'");
                ps.setString(1, "Deshabilitado");
                ps.execute();

                ps1 = conn.prepareStatement("UPDATE quizzes SET f_activacion = ? WHERE id = '" + id.getText() + "'");
                ps1.setString(1, "no activo");
                ps1.execute();

                ModConsultasSQL.tablaQuizz(tablaQuizzes);
                JOptionPane.showMessageDialog(null, "Desactivacion del Quizz completa.");

                ModVariablesReg varR = new ModVariablesReg();
                String tipo = "Administrador";
                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                String que = "Se desactivó el Quizz: " + txtNombre.getText();
                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                String comp = varU.getMatricula();
                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                String tipo2 = "Empleado";
                String quien2 = varU.getMatricula() + "/ " + varU.getNombre_completo();
                String que2 = "Se desactivó el Quizz: " + txtNombre.getText();
                String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                String comp2 = varU.getMatricula();
                if (con.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

            } catch (SQLException ex) {
                Logger.getLogger(VstQuizzes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_checkActivarActionPerformed

    private void checkCamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCamNameActionPerformed
        if (checkCamName.isSelected() == false) {
            txtNombre.setEditable(false);
        } else {
            txtNombre.setEditable(true);
        }
    }//GEN-LAST:event_checkCamNameActionPerformed

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
                ModConsultasSQL.tablaQuizz(tablaQuizzes);
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
            java.util.logging.Logger.getLogger(VstQuizzes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstQuizzes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstQuizzes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstQuizzes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstQuizzes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField actual;
    public javax.swing.JButton btnAgrPreg;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnNuevo;
    public javax.swing.JCheckBox checkActivar;
    public javax.swing.JCheckBox checkCamName;
    public javax.swing.JComboBox<String> comboIntentos;
    public javax.swing.JComboBox<String> comboLimHoras;
    public javax.swing.JComboBox<String> comboLimMinutos;
    public javax.swing.JComboBox<String> comboModCalf;
    public javax.swing.JComboBox<String> comboNumPreg;
    private javax.swing.JLabel cronometro;
    public javax.swing.JTextField id;
    private com.toedter.calendar.JCalendar jCalendar1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextField matricula;
    public javax.swing.JTable tablaQuizzes;
    public javax.swing.JTextPane txtDescripcion;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
