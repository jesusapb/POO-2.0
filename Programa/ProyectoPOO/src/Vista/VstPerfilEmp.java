
package Vista;

/**
 * Esta es la interfaz del perfil del empleado, el cual podemos visualizar una
 * tabla de los quizzes realizados y una tabla de sus calificaciones. Tambien se
 * tiene la función de cambiar la contraseña.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañaiento
 */
public class VstPerfilEmp extends javax.swing.JFrame {

    /**
     * Creando forma de la vista PerfilEmpleado
     */
    public VstPerfilEmp() {
        initComponents();
        nomQuizz.setVisible(false);
        txtIntento.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ver = new javax.swing.JPanel();
        txtRespuesta = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtPregunta = new javax.swing.JTextField();
        txtComentario = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCalificacion = new javax.swing.JTextField();
        txtPuntuacion = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        nomQuizz = new javax.swing.JTextField();
        txtIntento = new javax.swing.JTextField();
        cambio = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        contraConf = new javax.swing.JPasswordField();
        contra = new javax.swing.JPasswordField();
        btncancelar = new javax.swing.JButton();
        contraConfN = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        contraN = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        btnvalidar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nombre = new javax.swing.JTextField();
        anuncio = new javax.swing.JLabel();
        apPat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        apMat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        matricula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        correo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegistro = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btncambioContr = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        btncambioTab = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ver.setBackground(new java.awt.Color(255, 255, 255));
        ver.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtRespuesta.setEditable(false);
        txtRespuesta.setBackground(new java.awt.Color(255, 255, 255));
        ver.add(txtRespuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 300, 60));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Respuesta:");
        ver.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Pregunta:");
        ver.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtPregunta.setEditable(false);
        txtPregunta.setBackground(new java.awt.Color(255, 255, 255));
        ver.add(txtPregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 300, 60));

        txtComentario.setEditable(false);
        txtComentario.setBackground(new java.awt.Color(255, 255, 255));
        ver.add(txtComentario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 380, 60));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Comentario o retroalimentación:");
        ver.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 290, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Califición asignada:");
        ver.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, -1));

        txtCalificacion.setEditable(false);
        txtCalificacion.setBackground(new java.awt.Color(255, 255, 255));
        ver.add(txtCalificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 230, 30));

        txtPuntuacion.setEditable(false);
        txtPuntuacion.setBackground(new java.awt.Color(255, 255, 255));
        ver.add(txtPuntuacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 230, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("<html>Puntuación de la pregunta:");
        ver.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, -1));

        btnCerrar.setText("Cerrar");
        ver.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, -1, -1));

        btnAtras.setText("Atras");
        ver.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        btnSiguiente.setText("Siguiente");
        ver.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, -1, -1));

        nomQuizz.setEditable(false);
        nomQuizz.setBackground(new java.awt.Color(255, 255, 255));
        nomQuizz.setBorder(null);
        ver.add(nomQuizz, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 70, 20));

        txtIntento.setEditable(false);
        txtIntento.setBackground(new java.awt.Color(255, 255, 255));
        txtIntento.setBorder(null);
        ver.add(txtIntento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 70, 20));

        getContentPane().add(ver, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 370));

        cambio.setBackground(new java.awt.Color(255, 255, 255));
        cambio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setText("Introduzca su contraseña actual:");
        cambio.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setText("Confirme su contraseña actual:");
        cambio.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        contraConf.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cambio.add(contraConf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 400, -1));

        contra.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cambio.add(contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 400, -1));

        btncancelar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btncancelar.setText("Cancelar");
        cambio.add(btncancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, -1));

        contraConfN.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cambio.add(contraConfN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 400, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setText("Confirme su nueva contraseña:");
        cambio.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        contraN.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cambio.add(contraN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 400, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel10.setText("Introduzca su nueva contraseña:");
        cambio.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        btnvalidar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnvalidar.setText("Validar");
        cambio.add(btnvalidar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        btnguardar.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnguardar.setText("Guardar");
        cambio.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, -1, -1));

        getContentPane().add(cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 440, 370));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombre.setEditable(false);
        nombre.setBackground(new java.awt.Color(255, 255, 255));
        nombre.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 380, -1));

        anuncio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        anuncio.setText("Registro de Quizzes presentados:");
        jPanel1.add(anuncio, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        apPat.setEditable(false);
        apPat.setBackground(new java.awt.Color(255, 255, 255));
        apPat.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(apPat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 380, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setText("Apellido paterno:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        apMat.setEditable(false);
        apMat.setBackground(new java.awt.Color(255, 255, 255));
        apMat.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(apMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 380, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("Apellido materno:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        matricula.setEditable(false);
        matricula.setBackground(new java.awt.Color(255, 255, 255));
        matricula.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(matricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 380, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setText("Matrícula:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        correo.setEditable(false);
        correo.setBackground(new java.awt.Color(255, 255, 255));
        correo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jPanel1.add(correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 380, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setText("Correo electrónico:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 40, 370));

        tablaRegistro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Quizzes", "Intentos", "Calificaciónes", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRegistroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRegistro);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 430, 320));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setText("Nombre:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btncambioContr.setText("Cambio de contraseña");
        btncambioContr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btncambioContr.setContentAreaFilled(false);
        btncambioContr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btncambioContr, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        btnVer.setText("Ver");
        btnVer.setContentAreaFilled(false);
        btnVer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, -1, -1));

        btncambioTab.setText("Tabla de calificaciones");
        btncambioTab.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btncambioTab.setContentAreaFilled(false);
        btncambioTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btncambioTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que extrae los datos mostrados en la tabla.
     * @param evt variable que se mantiene a la espera de una interacciòn con la
     * tabla. Ayuda a extraer los datos mostrados en la tabla para su uso.
     */
    private void tablaRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRegistroMouseClicked
        if (btncambioTab.getText().equals("Tabla de calificaciones")) {
            int row = evt.getY() / tablaRegistro.getRowHeight();
            String quizz = "" + tablaRegistro.getValueAt(row, 0);
            String intento = "" + tablaRegistro.getValueAt(row, 1);
            String p_abiertas = "" + tablaRegistro.getValueAt(row, 4);
            String tipo = "" + tablaRegistro.getValueAt(row, 3);
            if (tipo.equals("Por calificar")); else {
                int m = Integer.parseInt(p_abiertas);
                if (m > 0) {
                    nomQuizz.setText(quizz);
                    txtIntento.setText(intento);
                    btnVer.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_tablaRegistroMouseClicked

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
            java.util.logging.Logger.getLogger(VstPerfilEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstPerfilEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstPerfilEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstPerfilEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstPerfilEmp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel anuncio;
    public javax.swing.JTextField apMat;
    public javax.swing.JTextField apPat;
    public javax.swing.JButton btnAtras;
    public javax.swing.JButton btnCerrar;
    public javax.swing.JButton btnSiguiente;
    public javax.swing.JButton btnVer;
    public javax.swing.JButton btncambioContr;
    public javax.swing.JButton btncambioTab;
    public javax.swing.JButton btncancelar;
    public javax.swing.JButton btnguardar;
    public javax.swing.JButton btnvalidar;
    public javax.swing.JPanel cambio;
    public javax.swing.JPasswordField contra;
    public javax.swing.JPasswordField contraConf;
    public javax.swing.JPasswordField contraConfN;
    public javax.swing.JPasswordField contraN;
    public javax.swing.JTextField correo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTextField matricula;
    public javax.swing.JTextField nomQuizz;
    public javax.swing.JTextField nombre;
    public javax.swing.JTable tablaRegistro;
    public javax.swing.JTextField txtCalificacion;
    public javax.swing.JTextField txtComentario;
    public javax.swing.JTextField txtIntento;
    public javax.swing.JTextField txtPregunta;
    public javax.swing.JTextField txtPuntuacion;
    public javax.swing.JTextField txtRespuesta;
    public javax.swing.JPanel ver;
    // End of variables declaration//GEN-END:variables
}
