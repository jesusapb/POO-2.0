/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Administrador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesDoc;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.Administrador.VstDocumentos;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Antonio
 */
public class CtrlDocumentos implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesDoc varD;
    private ModVariablesUsr varU;
    private VstDocumentos vd;

    public CtrlDocumentos(ModConsultasSQL con, ModVariablesDoc varD, ModVariablesUsr varU, VstDocumentos vd) {
        this.con = con;
        this.varD = varD;
        this.varU = varU;
        this.vd = vd;

        this.vd.btnNuevo.addActionListener(this);
        this.vd.btnGuardar.addActionListener(this);
        this.vd.btnModificar.addActionListener(this);
        this.vd.btnEliminar.addActionListener(this);
        this.vd.btnCancelar.addActionListener(this);
        this.vd.btnSeleccionar.addActionListener(this);
        this.vd.btnSi.addActionListener(this);
        this.vd.btnNo.addActionListener(this);
    }

    //tablaPDF tpdf = new tablaPDF();
    String ruta_archivo = "";

    public void iniciar() {
        vd.setTitle("Documentos");
        vd.setLocationRelativeTo(null);
        vd.matricula.setText(varU.getMatricula());

        con.visualizar(vd.tablaDocumentos);
        activar_boton(false, false, false, false);
        vd.txtNombre.setEnabled(false);
        vd.txtDescripcion.setEnabled(false);
        vd.btnSi.setVisible(false);
        vd.btnNo.setVisible(false);
        vd.cambio.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        varU.setMatricula(varU.getMatricula());
        varU.setDia(fechaDate.format(date));
        varU.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(varU);
        ModConsultasSQL.status(varU);

        if (con.existeUsr(varU.getMatricula()) == 1) {
            if ("Permanente".equals(varU.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vd.setVisible(false);
            } else {
                if (e.getSource() == vd.btnSeleccionar) {
                    seleccionarPDF();
                }

                if (e.getSource() == vd.btnNuevo) {
                    activar_boton(true, false, false, true);
                    vd.txtNombre.setEnabled(true);
                    vd.txtDescripcion.setEnabled(true);
                    vd.btnSi.setVisible(false);
                    vd.btnNo.setVisible(false);
                    vd.cambio.setVisible(false);
                    ruta_archivo = "";
                }

                if (e.getSource() == vd.btnGuardar) {
                    String nombre = vd.txtNombre.getText();
                    String descripcion = vd.txtDescripcion.getText();
                    //sql s = new sql();
                    int codigo = con.auto_incremento("SELECT MAX(id) FROM documentos");
                    File ruta = new File(ruta_archivo);
                    if (nombre.trim().length() != 0 && ruta_archivo.trim().length() != 0) {
                        guardarPDF(codigo, nombre, "Habilitado", descripcion, ruta);

                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que = "Se agregó un nuevo Documento y está habilidato: " + vd.txtNombre.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = varU.getMatricula();
                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                        String tipo2 = "Empleado";
                        String quien2 = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que2 = "Se activo un Documento: " + vd.txtNombre.getText();
                        String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp2 = varU.getMatricula();
                        if (con.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                        con.visualizar(vd.tablaDocumentos);
                        ruta_archivo = "";
                        activar_boton(false, false, false, false);
                        vd.txtNombre.setEnabled(false);
                        vd.txtDescripcion.setEnabled(false);
                        vd.btnSi.setVisible(false);
                        vd.btnNo.setVisible(false);
                        vd.cambio.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Completa etodos los campos.");
                    }
                }

                if (e.getSource() == vd.btnModificar) {
                    String nombre = vd.txtNombre.getText();
                    String descripcion = vd.txtDescripcion.getText();
                    File ruta = new File(ruta_archivo);
                    if (vd.stat.getText().equals("Habilitado")) {
                        JOptionPane.showMessageDialog(null, "Para modificar el documento debe de desactivarlo.");
                    } else {
                        if (vd.cambio.isSelected() == false) {
                            if (vd.txtNombre.getText().equals("") || vd.txtDescripcion.getText().equals("")) {
                                JOptionPane.showMessageDialog(null, "Debe de ingresar el nombre y la descripción");
                            } else {
                                int id = Integer.parseInt(vd.ident.getText());
                                modificarPDF(id, nombre, descripcion);

                                ModVariablesReg varR = new ModVariablesReg();
                                String tipo = "Administrador";
                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                String que = "Se modificoó el Documento: " + vd.txtNombre.getText();
                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                String comp = varU.getMatricula();
                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                con.visualizar(vd.tablaDocumentos);
                                ruta_archivo = "";
                                activar_boton(false, false, false, false);
                                vd.txtNombre.setEnabled(false);
                                vd.txtDescripcion.setEnabled(false);
                                vd.btnSi.setVisible(false);
                                vd.btnNo.setVisible(false);
                                vd.cambio.setVisible(false);
                            }
                        } else {
                            if (vd.txtNombre.getText().equals("") || vd.btnSeleccionar.getText().equals("Seleccionar...")
                                    || vd.btnSeleccionar.getText().equals("") || vd.txtDescripcion.getText().equals("")) {
                                JOptionPane.showMessageDialog(null, "Debe de ingresar el nombre, la descripción y el archivo.");
                            } else {
                                int id = Integer.parseInt(vd.ident.getText());
                                modificarPDF(id, nombre, descripcion, ruta);
                                con.visualizar(vd.tablaDocumentos);

                                ruta_archivo = "";
                                activar_boton(false, false, false, false);
                                vd.txtNombre.setEnabled(false);
                                vd.txtDescripcion.setEnabled(false);
                                vd.btnSi.setVisible(false);
                                vd.btnNo.setVisible(false);
                                vd.cambio.setVisible(false);
                            }
                        }
                    }
                }

                if (e.getSource() == vd.btnEliminar) {
                    int id = Integer.parseInt(vd.ident.getText());
                    eliminarPDF(id);
                    con.visualizar(vd.tablaDocumentos);

                    ModVariablesReg varR = new ModVariablesReg();
                    String tipo = "Administrador";
                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                    String que = "Se eliminó el Documento: " + vd.txtNombre.getText();
                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                    String comp = varU.getMatricula();
                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                    activar_boton(false, false, false, false);
                    vd.txtNombre.setEnabled(false);
                    vd.txtDescripcion.setEnabled(false);
                    vd.btnSi.setVisible(false);
                    vd.btnNo.setVisible(false);
                    vd.cambio.setVisible(false);
                    ruta_archivo = "";
                }

                if (e.getSource() == vd.btnCancelar) {
                    activar_boton(false, false, false, false);
                    ruta_archivo = "";
                    vd.txtNombre.setEnabled(false);
                    vd.txtDescripcion.setEnabled(false);
                    vd.btnSi.setVisible(false);
                    vd.btnNo.setVisible(false);
                    vd.cambio.setVisible(false);
                }

                if (e.getSource() == vd.btnSi) {
                    ModConexion conn = new ModConexion();
                    String sql = "UPDATE documentos SET status = ? WHERE id = '" + vd.ident.getText() + "'";
                    PreparedStatement ps = null;

                    try {
                        ps = conn.getConexion().prepareStatement(sql);
                        ps.setString(1, "Habilitado");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Documento activado.");
                        
                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que = "Se activó un Documento " + vd.txtNombre.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = varU.getMatricula();
                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                        String tipo2 = "Empleado";
                        String quien2 = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que2 = "Se activo un Documento: " + vd.txtNombre.getText();
                        String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp2 = varU.getMatricula();
                        if (con.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                        con.visualizar(vd.tablaDocumentos);
                        activar_boton(false, false, false, false);
                        ruta_archivo = "";
                        vd.txtNombre.setEnabled(false);
                        vd.txtDescripcion.setEnabled(false);
                        vd.btnSi.setVisible(false);
                        vd.btnNo.setVisible(false);
                        vd.cambio.setVisible(false);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "El documento no pudo activarse.");
                        con.visualizar(vd.tablaDocumentos);
                        System.out.println(ex.getMessage());
                    } catch (HeadlessException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                if (e.getSource() == vd.btnNo) {
                    ModConexion conn = new ModConexion();
                    String sql = "UPDATE documentos SET status = ? WHERE id = '" + vd.ident.getText() + "'";
                    PreparedStatement ps = null;

                    try {
                        ps = conn.getConexion().prepareStatement(sql);
                        ps.setString(1, "Deshabilitado");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Documento desactivado.");

                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que = "Se desactivó un Documento: " + vd.txtNombre.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = varU.getMatricula();
                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                        String tipo2 = "Empleado";
                        String quien2 = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que2 = "Se desactivó un Documento: " + vd.txtNombre.getText();
                        String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp2 = varU.getMatricula();
                        if (con.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                        con.visualizar(vd.tablaDocumentos);
                        activar_boton(false, false, false, false);
                        ruta_archivo = "";
                        vd.txtNombre.setEnabled(false);
                        vd.txtDescripcion.setEnabled(false);
                        vd.btnSi.setVisible(false);
                        vd.btnNo.setVisible(false);
                        vd.cambio.setVisible(false);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "El documento no pudo desactivarse.");
                        con.visualizar(vd.tablaDocumentos);
                        System.out.println(ex.getMessage());
                    } catch (HeadlessException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vd.setVisible(false);
        }
    }

    public void guardarPDF(int codigo, String nombre, String status, String descripcion, File archivo) {
        //PDF pdf = new PDF();
        varD.setId(codigo);
        varD.setNombre(nombre);
        varD.setStatus(status);
        varD.setDescripcion(descripcion);
        try {
            byte[] doc = new byte[(int) archivo.length()];
            InputStream input = new FileInputStream(archivo);
            input.read(doc);
            varD.setArchivo(doc);
        } catch (IOException ex) {
            varD.setArchivo(null);
        }
        con.agregarD(varD);
    }

    public void modificarPDF(int codigo, String nombre, String descripcion, File archivo) {
        //PDF pdf = new PDF();
        varD.setId(codigo);
        varD.setNombre(nombre);
        varD.setDescripcion(descripcion);
        try {
            byte[] doc = new byte[(int) archivo.length()];
            InputStream input = new FileInputStream(archivo);
            input.read(doc);
            varD.setArchivo(doc);
            JOptionPane.showMessageDialog(null, "Modificación exitosa.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se puedo realizar la modificación.");
            varD.setArchivo(null);
        }
        con.modificarD(varD);
    }

    public void modificarPDF(int codigo, String nombre, String descripcion) {
        //PDF pdf = new PDF();
        varD.setId(codigo);
        varD.setNombre(nombre);
        varD.setDescripcion(descripcion);
        con.modificarNomD(varD);
    }

    public void eliminarPDF(int codigo) {
        //PDF pdf = new PDF();
        varD.setId(codigo);
        con.eliminarD(varD);
    }

    public void seleccionarPDF() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(vd);
        if (se == 0) {
            vd.btnSeleccionar.setText("" + j.getSelectedFile().getName());
            ruta_archivo = j.getSelectedFile().getAbsolutePath();
        } else {
        }
    }

    public void activar_boton(boolean a, boolean b, boolean c, boolean d) {
        vd.btnGuardar.setEnabled(a);
        vd.btnModificar.setEnabled(b);
        vd.btnEliminar.setEnabled(c);
        vd.btnSeleccionar.setEnabled(d);
        vd.txtNombre.setText("");
        vd.txtDescripcion.setText("");
        vd.btnSeleccionar.setText("Seleccionar...");
    }
}
