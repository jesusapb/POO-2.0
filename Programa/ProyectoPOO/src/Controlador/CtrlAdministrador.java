/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.Administrador.CtrlEmpleados;
import Controlador.Administrador.CtrlQuizzes;
import Controlador.CtrlMensajes.CtrlBandejadEntrada;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesDoc;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.Administrador.VstDocumentos;
import Vista.Administrador.VstEmpleados;
import Vista.Administrador.VstQuizzes;
import Vista.Mensajes.VstBandejadEntrada;
import Vista.VstAdministrador;
import Vista.VstLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlAdministrador implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstAdministrador va;

    public CtrlAdministrador(ModConsultasSQL cons, ModVariablesUsr var, VstAdministrador va) {
        this.cons = cons;
        this.var = var;
        this.va = va;

        this.va.btnCerrarSesion.addActionListener(this);
        this.va.btnMensajes.addActionListener(this);
        this.va.btnEmpleados.addActionListener(this);
        this.va.btnDocumentos.addActionListener(this);
        this.va.btnQuizz.addActionListener(this);
        this.va.btnPerfil.addActionListener(this);
        this.va.btnDDesactivar.addActionListener(this);
        this.va.btnQDesactivar.addActionListener(this);
    }

    public void iniciar() {
        va.setTitle("Administrador.");
        va.txtNombre.setText(var.getNombre_completo());
        va.txtMatricula.setText(var.getMatricula());
        va.txtTipo.setText(var.getTipo());
        va.setLocationRelativeTo(null);
        ModConsultasSQL.tablaConectados(va.tablaConectados);
        ModVariablesQuizzes varQ = new ModVariablesQuizzes();
        ModVariablesDoc varD = new ModVariablesDoc();
        ModConsultasSQL.DocsAct(va.tablaADocumentos, varD);
        ModConsultasSQL.QuizzAct(va.tablaAQuizzes, varQ);
        va.btnQDesactivar.setVisible(false);
        va.btnDDesactivar.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(va.txtMatricula.getText());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(var);
        ModConsultasSQL.status(var);

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if (e.getSource() == va.btnCerrarSesion) {//Cerrar pantalla de administrador.
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    var.setStatus("Desconectado");
                    if (cons.cerrarM(var)); else if (cons.cerrarC(var));
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                }
            }//Funcionamiento completo.

            if (e.getSource() == va.btnMensajes) { //Desplega ventana con una tabla de todos los usuarios.
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    VstBandejadEntrada vbe = new VstBandejadEntrada();
                    CtrlBandejadEntrada ctrlBE = new CtrlBandejadEntrada(cons, var, vbe);
                    ctrlBE.iniciar();
                    vbe.setVisible(true);
                }
            }

            if (e.getSource() == va.btnEmpleados) {//Ventana con tabla y opciones.
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    ModVariablesReg varR = new ModVariablesReg();
                    VstEmpleados ve = new VstEmpleados();
                    CtrlEmpleados ctrlE = new CtrlEmpleados(cons, varR, var, ve);
                    ctrlE.iniciar();
                    ve.setVisible(true);
                }
            }

            if (e.getSource() == va.btnDocumentos) {
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    VstDocumentos vd = new VstDocumentos();
                    vd.setVisible(true);
                }
            }

            if (e.getSource() == va.btnQuizz) {
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                    VstQuizzes vq = new VstQuizzes();
                    CtrlQuizzes ctrlQ = new CtrlQuizzes(cons, varQ, var, vq);
                    ctrlQ.iniciar();
                    vq.setVisible(true);
                }
            }

            if (e.getSource() == va.btnPerfil) {
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {

                }
            }

            if (e.getSource() == va.btnDDesactivar) {
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    try {
                        PreparedStatement ps = null;
                        ModConexion objCon = new ModConexion();
                        Connection con = objCon.getConexion();
                        ps = con.prepareStatement("UPDATE documentos SET status = ? WHERE nombre = '" + va.docs.getText() + "'");

                        ps.setString(1, "Deshabilitado");
                        ps.execute();

                        ModVariablesDoc varD = new ModVariablesDoc();
                        ModConsultasSQL.DocsAct(va.tablaADocumentos, varD);
                        va.btnDDesactivar.setVisible(false);

                        JOptionPane.showMessageDialog(null, "El documento ya no es visible para los empleados.");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "No pudo ser cambiado el estado del documento.");
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (e.getSource() == va.btnQDesactivar) {
                if ("Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                            + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                            + "\npoo.acompanamiento@gmail.com");
                    va.setVisible(false);
                    variables();
                    VstLogin vl = new VstLogin();
                    CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                    try {
                        ctrlL.iniciar();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vl.setVisible(true);
                } else {
                    try {
                        PreparedStatement ps = null;
                        ModConexion objCon = new ModConexion();
                        Connection con = objCon.getConexion();
                        ps = con.prepareStatement("UPDATE quizzes SET status = ? WHERE nombre = '" + va.quizz.getText() + "'");

                        ps.setString(1, "Deshabilitado");
                        ps.execute();

                        ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                        ModConsultasSQL.QuizzAct(va.tablaAQuizzes, varQ);
                        va.btnQDesactivar.setVisible(false);

                        JOptionPane.showMessageDialog(null, "El Quizz ya no es visible para los empleados.");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "No pudo ser cambiado el estado del Quizz.");
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            va.setVisible(false);
            variables();
            VstLogin vl = new VstLogin();
            CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
            try {
                ctrlL.iniciar();
            } catch (UnknownHostException ex) {
                Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
            vl.setVisible(true);
        }
    }

    public void variables() {
        var.setNombre(null);
        var.setAp_pat(null);
        var.setAp_mat(null);
        var.setTipo(null);
        var.setMatricula(null);
        var.setContraseña(null);
        var.setStatus(null);
        var.setCorreo(null);
    }
}
