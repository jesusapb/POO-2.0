
package Vista;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesDoc;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta es la interfaz de los documentos, las funcionalidades de esto que le tocan al administrador.
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class VstDocumentos extends javax.swing.JFrame {

    /**
     * Creates new form VstDocumentos
     */
    ModConsultasSQL tpdf = new ModConsultasSQL();
    String ruta_archivo = "";
    int id = -1;
    
    /**
     * Ayuda a la visualización de ciertos componentes en la interfaz.
     */
    public VstDocumentos() {
        initComponents();
        t = new Timer(10, acciones);
        t.start();
        stat.setVisible(false); 
        ident.setVisible(false); 
        matricula.setVisible(false); 
    }
    
    /**
     * Método que guarda un documento en PDF de tu computadora y manda lo datos para su actualización en la base de datos.
     * @param codigo id del registro del documento en la base de datos.
     * @param nombre es el nombre que se le haya puesto al documento.
     * @param status para saber si es visible para los empleados o no.
     * @param descripcion es una breve explicación de cómo o qué es el documento.
     * @param archivo es nuestro documento es sí que vamos a guardar.
     */
    public void guardarPDF(int codigo, String nombre, String status, String descripcion, File archivo) {
        ModConsultasSQL pdf = new ModConsultasSQL();
        ModVariablesDoc var = new ModVariablesDoc();
        var.setId(codigo);
        var.setNombre(nombre);
        var.setStatus(status);
        var.setDescripcion(descripcion);
        try {
            byte[] doc = new byte[(int) archivo.length()];
            InputStream input = new FileInputStream(archivo);
            input.read(doc);
            var.setArchivo(doc);
        } catch (IOException ex) {
            var.setArchivo(null);
        }
        pdf.agregarD(var);
    }
    
     /**
     * Método que modifica un documento en PDF y manda lo datos para su actualización en la base de datos.
     * @param codigo id del registro del documento en la base de datos.
     * @param nombre es el nombre que se le haya puesto al documento.
     * @param status para saber si es visible para los empleados o no.
     * @param descripcion es una breve explicación de cómo o qué es el documento.
     * @param archivo es nuestro documento es sí que vamos a guardar.
     */
    public void modificarPDF(int codigo, String nombre, String status, String descripcion, File archivo) {
        ModConsultasSQL pdf = new ModConsultasSQL();
        ModVariablesDoc var = new ModVariablesDoc();
        var.setId(codigo);
        var.setNombre(nombre);
        var.setStatus(status);
        var.setDescripcion(descripcion);
        try {
            byte[] doc = new byte[(int) archivo.length()];
            InputStream input = new FileInputStream(archivo);
            input.read(doc);
            var.setArchivo(doc);
        } catch (IOException ex) {
            var.setArchivo(null);
        }
        pdf.modificarD(var);
    }

    /**
     * Método que hace el cambio del nombre del archivo.
     * @param codigo id del registro del documento en la base de datos.
     * @param nombre es el nombre que se le haya puesto al documento.
     */
    public void modificarPDF(int codigo, String nombre) {
        ModConsultasSQL pdf = new ModConsultasSQL();
        ModVariablesDoc var = new ModVariablesDoc();
        var.setId(codigo);
        var.setNombre(nombre);
        pdf.modificarNomD(var);
    }

    /**
     * Método que hace la eliminación de un documento en la base de datos sólo con su id.
     * @param codigo id del registro del documento en la base de datos.
     */
    public void eliminarPDF(int codigo) {
        ModConsultasSQL pdf = new ModConsultasSQL();
        ModVariablesDoc var = new ModVariablesDoc();
        var.setId(codigo);
        pdf.eliminarD(var);
    }
    
    /**
     * Método que abre una interfaz y te deja seleccionar el archivo PDF que se guardará en la base de datos.
     */
    public void seleccionarPDF() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(this);
        if (se == 0) {
            this.btnSeleccionar.setText("" + j.getSelectedFile().getName());
            ruta_archivo = j.getSelectedFile().getAbsolutePath();
        } else {
        }
    }

    /**
     * Método que ayuda a la visibilidad de ciertos botones, dependiendo de donde este el usuario.
     * @param a es para la visualización del botón de guardar.
     * @param b es para la visualización del botón de modificar.
     * @param c es para la visualización del botón de eliminar.
     * @param d es para la visualización del botón de seleccionar.
     */
    public void activar_boton(boolean a, boolean b, boolean c, boolean d) {
        btnGuardar.setEnabled(a);
        btnModificar.setEnabled(b);
        btnEliminar.setEnabled(c);
        btnSeleccionar.setEnabled(d);
        txtNombre.setText("");
        txtDescripcion.setText("");
        btnSeleccionar.setText("Seleccionar...");
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
        btnSeleccionar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDocumentos = new javax.swing.JTable();
        btnSi = new javax.swing.JButton();
        btnNo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        matricula = new javax.swing.JTextField();
        cambio = new javax.swing.JCheckBox();
        ident = new javax.swing.JTextField();
        stat = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Seleccionar archivo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 310, -1));

        btnSeleccionar.setText("Seleccionar...");
        btnSeleccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 310, -1));

        jLabel2.setText("Nombre del archivo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        btnNuevo.setText("Nuevo");
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        btnModificar.setText("Modificar");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 740, 10));

        tablaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Status", "Descipcion", "Archivo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Byte.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDocumentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDocumentos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 720, 180));

        btnSi.setText("Visible para el empleado");
        btnSi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        btnNo.setText("No visible para el empleado");
        btnNo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, -1, -1));

        jLabel3.setText("Descripcion:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));

        matricula.setEditable(false);
        matricula.setBackground(new java.awt.Color(255, 255, 255));
        matricula.setBorder(null);
        jPanel1.add(matricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 100, -1));

        cambio.setText("Cambiar archivo");
        cambio.setContentAreaFilled(false);
        cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioActionPerformed(evt);
            }
        });
        jPanel1.add(cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        ident.setEditable(false);
        ident.setBackground(new java.awt.Color(255, 255, 255));
        ident.setBorder(null);
        jPanel1.add(ident, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 40, -1));

        stat.setEditable(false);
        stat.setBackground(new java.awt.Color(255, 255, 255));
        stat.setBorder(null);
        jPanel1.add(stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 40, -1));

        jScrollPane3.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 240, 110));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
//        seleccionarPDF();
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
//        activar_boton(true, false, false, true);
//        txtNombre.setEnabled(true);
//        txtDescripcion.setEnabled(true);
//        btnSi.setVisible(false);
//        btnNo.setVisible(false);
//        ruta_archivo = "";
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        String nombre = txtNombre.getText();
//        String descripcion = txtDescripcion.getText();
//        sql s = new sql();
//        int codigo = s.auto_incremento("SELECT MAX(id) FROM documentos");
//        File ruta = new File(ruta_archivo);
//        if (nombre.trim().length() != 0 && ruta_archivo.trim().length() != 0) {
//            guardarPDF(codigo, nombre, "Habilitado", descripcion, ruta);
//            tpdf.visualizar(tablaDocumentos);
//            ruta_archivo = "";
//            activar_boton(false, false, false, false);
//            txtNombre.setEnabled(false);
//            txtDescripcion.setEnabled(false);
//            btnSi.setVisible(false);
//            btnNo.setVisible(false);
//        } else {
//            JOptionPane.showMessageDialog(null, "Completa etodos los campos.");
//        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
//        String nombre = txtNombre.getText();
//        String descripcion = txtDescripcion.getText();
//        File ruta = new File(ruta_archivo);
//        if (nombre.trim().length() != 0 && ruta_archivo.trim().length() != 0 && descripcion.equals("")) {
//            modificarPDF(id, nombre, "Habilitado", descripcion, ruta);
//            tpdf.visualizar(tablaDocumentos);
//        } else if (ruta_archivo.trim().length() == 0) {
//            modificarPDF(id, nombre);
//            tpdf.visualizar(tablaDocumentos);
//        }
//        ruta_archivo = "";
//        activar_boton(false, false, false, false);
//        txtNombre.setEnabled(false);
//        txtDescripcion.setEnabled(false);
//        btnSi.setVisible(false);
//        btnNo.setVisible(false);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
//        eliminarPDF(id);
//        tpdf.visualizar(tablaDocumentos);
//        activar_boton(false, false, false, false);
//        txtNombre.setEnabled(false);
//        txtDescripcion.setEnabled(false);
//        btnSi.setVisible(false);
//        btnNo.setVisible(false);
//        ruta_archivo = "";
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
//        activar_boton(false, false, false, false);
//        ruta_archivo = "";
//        txtNombre.setEnabled(false);
//        txtDescripcion.setEnabled(false);
//        btnSi.setVisible(false);
//        btnNo.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que extrae los datos mostrados en la tabla. 
     * @param evt variable  que mantiene a la espera de una interacción con la tabla. Ayuda a extraer los datos mostrados en la tabla.
     */
    private void tablaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseClicked

        int column = tablaDocumentos.getColumnModel().getColumnIndexAtX(evt.getX());//Posicion donde se da "click" el la columna
        int row = evt.getY() / tablaDocumentos.getRowHeight();
        activar_boton(false, true, true, true);
        txtNombre.setEnabled(true);
        txtDescripcion.setEnabled(true);
        cambio.setVisible(true);
        btnSeleccionar.setEnabled(false);
        if (row < tablaDocumentos.getRowCount() && row >= 0 && column < tablaDocumentos.getColumnCount() && column >= 0) {

            id = (int) tablaDocumentos.getValueAt(row, 0);
            ident.setText(tablaDocumentos.getValueAt(row, 0) + "");
            Object value = tablaDocumentos.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                if (boton.getText().equals("Vacio")) {
                    JOptionPane.showMessageDialog(null, "No hay archivo");
                } else {
                    ModConsultasSQL pdf = new ModConsultasSQL();
                    pdf.abrirD(id);
                    try {
                        Desktop.getDesktop().open(new File("new.pdf"));
                    } catch (Exception ex) {
                    }
                }
            } else {
                String name = "" + tablaDocumentos.getValueAt(row, 1);
                txtNombre.setText(name);
                String desc = "" + tablaDocumentos.getValueAt(row, 3);
                txtDescripcion.setText(desc);
                String status = "" + tablaDocumentos.getValueAt(row, 2);
                stat.setText(status);

                if ("Habilitado".equals(status)) {
                    btnNo.setVisible(true);
                    btnSi.setVisible(false);
                } else if ("Deshabilitado".equals(status)) {
                    btnNo.setVisible(false);
                    btnSi.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_tablaDocumentosMouseClicked

    /**
     * Método que hace visible el botón seleccionar si se ha seleccionado el de cambiar.
     * @param evt es el evento de seleccionar el botón de cambiar.
     */
    private void cambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioActionPerformed
        if (cambio.isSelected() == false) {
            btnSeleccionar.setEnabled(false);
        } else {
            btnSeleccionar.setEnabled(true);
        }
    }//GEN-LAST:event_cambioActionPerformed

    
    private Timer t;
    private int h, m, s, cs;
    /**
     *metodo encargado funcionar como contador ante los eventos
     */
    private ActionListener acciones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 5 == 0)) {
                tpdf.visualizar(tablaDocumentos);
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
            java.util.logging.Logger.getLogger(VstDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstDocumentos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnNo;
    public javax.swing.JButton btnNuevo;
    public javax.swing.JButton btnSeleccionar;
    public javax.swing.JButton btnSi;
    public javax.swing.JCheckBox cambio;
    public javax.swing.JTextField ident;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTextField matricula;
    public javax.swing.JTextField stat;
    public javax.swing.JTable tablaDocumentos;
    public javax.swing.JTextPane txtDescripcion;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
