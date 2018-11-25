/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.Administrador.CtrlDocumentos;
import Controlador.Administrador.CtrlEmpleados;
import Controlador.Administrador.CtrlPerfil;
import Controlador.Administrador.CtrlQuizzes;
import Controlador.CtrlMensajes.CtrlBandejadEntrada;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesAvisos;
import Modelo.ModVariablesDoc;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.Administrador.VstDocumentos;
import Vista.Administrador.VstEmpleados;
import Vista.Administrador.VstPerfil;
import Vista.Administrador.VstQuizzes;
import Vista.Mensajes.VstBandejadEntrada;
import Vista.VstAdministrador;
import Vista.VstBuscador;
import Vista.VstLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class CtrlAdministrador implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstAdministrador va;
    private Timer t;

    public CtrlAdministrador(ModConsultasSQL cons, ModVariablesUsr var, VstAdministrador va) {
        this.cons = cons;
        this.var = var;
        this.va = va;

        this.va.btnCerrarSesion.addActionListener(this);
        this.va.btnCerrar.addActionListener(this);
        this.va.btnMini.addActionListener(this);
        this.va.btnMensajes.addActionListener(this);
        this.va.btnEmpleados.addActionListener(this);
        this.va.btnDocumentos.addActionListener(this);
        this.va.btnQuizz.addActionListener(this);
        this.va.btnPerfil.addActionListener(this);
        this.va.btnDDesactivar.addActionListener(this);
        this.va.btnQDesactivar.addActionListener(this);
        this.va.btnLTodo.addActionListener(this);
        
        this.va.btnBuscar.addActionListener(this);
    }

    public void iniciar() {
        va.setTitle("Administrador.");
        va.setLocale(null);

        ImageIcon icono = null;
        if (cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png") != null) {
            icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png"));
        }
        ModVariablesMensaje varM = new ModVariablesMensaje();
        if (cons.ENVisto(varM, var.getMatricula()) == 1) {
            va.btnMensajes.setIcon(icono);
        }

        t = new Timer(10, acciones);
        t.start();
        va.txtNombre.setText(var.getNombre_completo());
        va.txtMatricula.setText(var.getMatricula());
        va.txtTipo.setText(var.getTipo());
        va.setLocationRelativeTo(null);
        ModConsultasSQL.tablaConectados(va.tablaConectados, va.txtMatricula.getText());
        ModVariablesQuizzes varQ = new ModVariablesQuizzes();
        ModVariablesDoc varD = new ModVariablesDoc();
        ModConsultasSQL.DocsAct(va.tablaADocumentos, varD);
        ModConsultasSQL.QuizzAct(va.tablaAQuizzes, varQ);
        ModVariablesAvisos varA = new ModVariablesAvisos();
        ModConsultasSQL.tablaAvisos(va.tablaAvisos, varA, var.getMatricula());
        ArrayList<ModVariablesAvisos> list = cons.listaAv(var.getMatricula());
        if (list.isEmpty()) {
            va.btnLTodo.setVisible(false);
        } else {
            va.btnLTodo.setVisible(true);
        }
        va.btnQDesactivar.setVisible(false);
        va.btnDDesactivar.setVisible(false);
        va.docs.setVisible(false);
        va.quizz.setVisible(false);
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
                if (e.getSource() == va.btnCerrarSesion) {
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

                if (e.getSource() == va.btnCerrar) {
                    int preg = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (preg == 0) {
                        t.stop();
                        System.exit(0);
                    }
                }

                if (e.getSource() == va.btnMini) {
                    va.setState(VstAdministrador.ICONIFIED);
                }

                if (e.getSource() == va.btnMensajes) {
                    VstBandejadEntrada vbe = new VstBandejadEntrada();
                    CtrlBandejadEntrada ctrlBE = new CtrlBandejadEntrada(cons, var, vbe);
                    ctrlBE.iniciar();
                    vbe.setVisible(true);
                }

                if (e.getSource() == va.btnEmpleados) {
                    ModVariablesReg varR = new ModVariablesReg();
                    VstEmpleados ve = new VstEmpleados();
                    CtrlEmpleados ctrlE = new CtrlEmpleados(cons, varR, var, ve);
                    ctrlE.iniciar();
                    ve.setVisible(true);
                }

                if (e.getSource() == va.btnDocumentos) {
                    ModVariablesDoc varD = new ModVariablesDoc();
                    VstDocumentos vd = new VstDocumentos();
                    CtrlDocumentos ctrlD = new CtrlDocumentos(cons, varD, var, vd);
                    ctrlD.iniciar();
                    vd.setVisible(true);
                }

                if (e.getSource() == va.btnQuizz) {
                    ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                    VstQuizzes vq = new VstQuizzes();
                    CtrlQuizzes ctrlQ = new CtrlQuizzes(cons, varQ, var, vq);
                    ctrlQ.iniciar();
                    vq.setVisible(true);
                }

                if (e.getSource() == va.btnPerfil) {
                    VstPerfil vp = new VstPerfil();
                    CtrlPerfil ctrlP = new CtrlPerfil(cons, var, vp, va);
                    ctrlP.iniciar();
                    vp.setVisible(true);
                }

                if (e.getSource() == va.btnDDesactivar) {
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

                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = var.getMatricula() + "/ " + var.getNombre_completo();
                        String que = "Desactivó el Documento: " + va.docs.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = var.getMatricula();
                        if (cons.avisoAA(varR, tipo, quien, que, cuando, comp));

                        String tipo2 = "Empleado";
                        String quien2 = var.getMatricula() + "/ " + var.getNombre_completo();
                        String que2 = "Se desactivó el Documento: " + va.docs.getText();
                        String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp2 = var.getMatricula();
                        if (cons.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "No pudo ser cambiado el estado del documento.");
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (e.getSource() == va.btnQDesactivar) {
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

                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = var.getMatricula() + "/ " + var.getNombre_completo();
                        String que = "Desactivó el Quizz: " + va.quizz.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = var.getMatricula();
                        if (cons.avisoAA(varR, tipo, quien, que, cuando, comp));

                        String tipo2 = "Empleado";
                        String quien2 = var.getMatricula() + "/ " + var.getNombre_completo();
                        String que2 = "Se desactivó el Quizz: " + va.quizz.getText();
                        String cuando2 = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp2 = var.getMatricula();
                        if (cons.avisoAA(varR, tipo2, quien2, que2, cuando2, comp2));

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "No pudo ser cambiado el estado del Quizz.");
                        Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (e.getSource() == va.btnLTodo) {
                    ModVariablesAvisos varA = new ModVariablesAvisos();
                    ModConsultasSQL.LeerTodo(varA, var.getMatricula());
                    ModConsultasSQL.tablaAvisos(va.tablaAvisos, varA, var.getMatricula());
                    ArrayList<ModVariablesAvisos> list = cons.listaAv(var.getMatricula());
                    if (list.isEmpty()) {
                        va.btnLTodo.setVisible(false);
                    } else {
                        va.btnLTodo.setVisible(true);
                    }
                }
                if (e.getSource() == va.btnBuscar) {
                    VstBuscador vb = new VstBuscador();
                    CtrlBuscador ctrlB = new CtrlBuscador(cons, var, vb);
                    ctrlB.iniciar();
                    vb.setVisible(true);
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
        var.setEquipo(null);
    }

    private int h, m, s, cs;
    private ActionListener acciones = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent ae) {

            ImageIcon icono = null;
            if (cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png") != null) {
                icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png"));
            }

            ModVariablesQuizzes varQ = new ModVariablesQuizzes();
            ModVariablesDoc varD = new ModVariablesDoc();
            Date date = new Date();
            DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
            DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

            var.setDia(fechaDate.format(date));
            var.setHora(horaDate.format(date));

            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 2 == 0)) {
                ModConsultasSQL.tablaConectados(va.tablaConectados, va.txtMatricula.getText());
                ModConsultasSQL.recarga(var);
                ModConsultasSQL.DocsAct(va.tablaADocumentos, varD);
                ModConsultasSQL.QuizzAct(va.tablaAQuizzes, varQ);

                ModVariablesAvisos varA = new ModVariablesAvisos();
                ModConsultasSQL.tablaAvisos(va.tablaAvisos, varA, var.getMatricula());
                ArrayList<ModVariablesAvisos> list = cons.listaAv(var.getMatricula());
                if (list.isEmpty()) {
                    va.btnLTodo.setVisible(false);
                } else {
                    va.btnLTodo.setVisible(true);
                }

                ModVariablesMensaje varM = new ModVariablesMensaje();
                if (cons.ENVisto(varM, var.getMatricula()) == 1) {
                    va.btnMensajes.setIcon(icono);
                } else {
                    icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px.png"));
                    va.btnMensajes.setIcon(icono);
                }
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
            //actualizarLabel();
        }
    };
}
