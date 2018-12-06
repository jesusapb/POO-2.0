
package Vista;

import Modelo.ModConexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Esta es la interfaz de las funcionalidades principales del administrador, donde debemos ver 
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class VstAdministrador extends javax.swing.JFrame {

    /**
     * Creates new form VstAdministrador
     */
    public VstAdministrador() {
        initComponents();
        setDefaultCloseOperation(0);
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
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        txtMatricula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnCerrarSesion = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnDocumentos = new javax.swing.JButton();
        btnQuizz = new javax.swing.JButton();
        btnPerfil = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConectados = new javax.swing.JTable();
        btnMensajes = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAQuizzes = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaADocumentos = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnDDesactivar = new javax.swing.JButton();
        btnQDesactivar = new javax.swing.JButton();
        docs = new javax.swing.JTextField();
        quizz = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaAvisos = new javax.swing.JTable();
        btnLTodo = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnMini = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAviso = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Avisos:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, -1, -1));

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.setBorder(null);
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 300, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        txtTipo.setEditable(false);
        txtTipo.setBackground(new java.awt.Color(255, 255, 255));
        txtTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTipo.setBorder(null);
        jPanel1.add(txtTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, 20));

        txtMatricula.setEditable(false);
        txtMatricula.setBackground(new java.awt.Color(255, 255, 255));
        txtMatricula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMatricula.setBorder(null);
        jPanel1.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 170, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Matrícula:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 900, 10));

        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Import_20px_1.png"))); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión.");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setContentAreaFilled(false);
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 40, -1, -1));

        btnEmpleados.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_People_64px_1.png"))); // NOI18N
        btnEmpleados.setText("Empleados");
        btnEmpleados.setBorder(null);
        btnEmpleados.setContentAreaFilled(false);
        btnEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        btnDocumentos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_PDF_64px.png"))); // NOI18N
        btnDocumentos.setText("Documentos");
        btnDocumentos.setBorder(null);
        btnDocumentos.setContentAreaFilled(false);
        btnDocumentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnDocumentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        btnQuizz.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnQuizz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Questionnaire_64px_1.png"))); // NOI18N
        btnQuizz.setText("Quizzes");
        btnQuizz.setBorder(null);
        btnQuizz.setContentAreaFilled(false);
        btnQuizz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnQuizz, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, -1, -1));

        btnPerfil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_User_64px.png"))); // NOI18N
        btnPerfil.setText("Perfil");
        btnPerfil.setBorder(null);
        btnPerfil.setContentAreaFilled(false);
        btnPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Quizzes activos:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, -1, -1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tablaConectados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tipo de usuario.", "Matrícula.", "Nombre."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaConectados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tablaConectados);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 670, 140));

        btnMensajes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Envelope_20px.png"))); // NOI18N
        btnMensajes.setText("Mensajes.");
        btnMensajes.setBorder(null);
        btnMensajes.setContentAreaFilled(false);
        btnMensajes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnMensajes, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, -1, -1));

        tablaAQuizzes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre:", "FAcvtivo"
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
        tablaAQuizzes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAQuizzesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaAQuizzes);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, 280, 200));

        tablaADocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Nombre:"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaADocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaADocumentosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaADocumentos);
        if (tablaADocumentos.getColumnModel().getColumnCount() > 0) {
            tablaADocumentos.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 250, 200));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 900, 10));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 30, 550));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Usuarios conectados:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Documentos activos:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, -1, -1));

        btnDDesactivar.setText("Desactivar Documento");
        jPanel1.add(btnDDesactivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        btnQDesactivar.setText("Desactivar Quizz");
        jPanel1.add(btnQDesactivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, -1, -1));

        docs.setEditable(false);
        docs.setBackground(new java.awt.Color(255, 255, 255));
        docs.setBorder(null);
        jPanel1.add(docs, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 140, -1));

        quizz.setEditable(false);
        quizz.setBackground(new java.awt.Color(255, 255, 255));
        quizz.setBorder(null);
        jPanel1.add(quizz, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 330, 140, -1));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 30, 270));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tipo de usuario:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        tablaAvisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAvisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAvisosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaAvisos);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 33, 230, 330));

        btnLTodo.setText("Leer todo");
        btnLTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnLTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 510, -1, -1));

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Close_Window_20px.png"))); // NOI18N
        btnCerrar.setToolTipText("Cerrar");
        btnCerrar.setActionCommand("Cerrar");
        btnCerrar.setBorder(null);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, -1, -1));
        btnCerrar.getAccessibleContext().setAccessibleName("Cerrar");

        btnMini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_Minimize_Window_20px.png"))); // NOI18N
        btnMini.setToolTipText("Minimizar");
        btnMini.setActionCommand("Minimizar");
        btnMini.setBorder(null);
        btnMini.setContentAreaFilled(false);
        btnMini.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, -1, -1));

        btnBuscar.setText("Buscar");
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        txtAviso.setEditable(false);
        jScrollPane5.setViewportView(txtAviso);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 380, 230, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Método que obtiene los datos del documento seleccionado. Hace visible el botón de Desactivar.
     * @param evt se mantiene en espera para la posible desactivación del documento.
     */
    private void tablaADocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaADocumentosMouseClicked
        int row = evt.getY() / tablaADocumentos.getRowHeight();
        String ident = "" + tablaADocumentos.getValueAt(row, 0);
        docs.setText(ident);
        btnDDesactivar.setVisible(true);
    }//GEN-LAST:event_tablaADocumentosMouseClicked

    /**
     * Método que obtiene los datos del quiz seleccionado. Hace visible el botón de Desactivar.
     * @param evt se mantiene en espera para la posible desactivación del quiz.
     */
    private void tablaAQuizzesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAQuizzesMouseClicked
        int row = evt.getY() / tablaAQuizzes.getRowHeight();
        String ident = "" + tablaAQuizzes.getValueAt(row, 0);
        quizz.setText(ident);
        btnQDesactivar.setVisible(true);
    }//GEN-LAST:event_tablaAQuizzesMouseClicked

    /**
     * Método que obtiene todos avisos, modificaciones, que haya hecho algún administrador.
     * @param evt se mantiene a la espera de la interacción del usuario para obtener los datos. Hace visible el botón de Leer todos los avisos.
     */
    private void tablaAvisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAvisosMouseClicked
        int row = evt.getY() / tablaAvisos.getRowHeight();
        String aviso = "" + tablaAvisos.getValueAt(row, 0);
        String[] part = aviso.split("/");
        String id = part[0];
        txtAviso.setText(aviso);
        
        ModConexion con = new ModConexion();
        String sql = "UPDATE avisos SET status = ? WHERE id = '" + id + "'";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, "visto");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }//GEN-LAST:event_tablaAvisosMouseClicked


 /*   private void actualizarLabel() {
        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s + ":" + (cs <= 9 ? "0" : "") + cs;
        cronometro.setText(tiempo);
    }*/

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
            java.util.logging.Logger.getLogger(VstAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnCerrar;
    public javax.swing.JButton btnCerrarSesion;
    public javax.swing.JButton btnDDesactivar;
    public javax.swing.JButton btnDocumentos;
    public javax.swing.JButton btnEmpleados;
    public javax.swing.JButton btnLTodo;
    public javax.swing.JButton btnMensajes;
    public javax.swing.JButton btnMini;
    public javax.swing.JButton btnPerfil;
    public javax.swing.JButton btnQDesactivar;
    public javax.swing.JButton btnQuizz;
    public javax.swing.JTextField docs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    public javax.swing.JTextField quizz;
    public javax.swing.JTable tablaADocumentos;
    public javax.swing.JTable tablaAQuizzes;
    public javax.swing.JTable tablaAvisos;
    public javax.swing.JTable tablaConectados;
    public javax.swing.JTextPane txtAviso;
    public javax.swing.JTextField txtMatricula;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
