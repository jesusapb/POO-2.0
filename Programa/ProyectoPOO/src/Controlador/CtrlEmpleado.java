/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.CtrlMensajes.CtrlBandejadEntrada;
import Controlador.Empleado.CtrlLeerDocs;
import Controlador.Empleado.CtrlSelectQuizz;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesAvisos;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.Empleado.VstLeerDocs;
import Vista.Empleado.VstSelectQuizz;
import Vista.Mensajes.VstBandejadEntrada;
import Vista.VstEmpleado;
import Vista.VstLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
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
public class CtrlEmpleado implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstEmpleado ve;

    public CtrlEmpleado(ModConsultasSQL cons, ModVariablesUsr var, VstEmpleado ve) {
        this.cons = cons;
        this.var = var;
        this.ve = ve;

        this.ve.btnCerrarSesion.addActionListener(this);
        this.ve.btnMensajes.addActionListener(this);
        this.ve.btnLTodo.addActionListener(this);
        this.ve.btnLeerDocs.addActionListener(this);
        this.ve.btnRealizarQuizz.addActionListener(this);
        this.ve.btnPerfil.addActionListener(this);
    }

    public void iniciar() {
        ve.setTitle("Empleado.");
        ve.setLocationRelativeTo(null);
        ve.txtNombre.setText(var.getNombre_completo());
        ve.txtMatricula.setText(var.getMatricula());
        ve.txtTipo.setText(var.getTipo());
        ModConsultasSQL.tablaConectados(ve.tablaConectados);
        ModVariablesAvisos varA = new ModVariablesAvisos();
        ModConsultasSQL.tablaAvisos(ve.tablaAvisos, varA, var.getMatricula());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(ve.txtMatricula.getText());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(var);
        ModConsultasSQL.status(var);

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if ("Permanente".equals(var.getStatus())) {
                JOptionPane.showMessageDialog(null, "Se le ha negado el acceso a las funciones administrativas."
                        + "\nse le sugiere ponerse en contacto a traves del correo electrónico:"
                        + "\npoo.acompanamiento@gmail.com");
                ve.setVisible(false);
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
                if (e.getSource() == ve.btnCerrarSesion) {
                    var.setStatus("Desconectado");
                    if (cons.cerrarM(var)); else if (cons.cerrarC(var));
                    ve.setVisible(false);
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
                
                if (e.getSource() == ve.btnMensajes) {
                    VstBandejadEntrada vbe = new VstBandejadEntrada();
                    CtrlBandejadEntrada ctrlBE = new CtrlBandejadEntrada(cons, var, vbe);
                    ctrlBE.iniciar();
                    vbe.setVisible(true);
                }
                
                if (e.getSource() == ve.btnLeerDocs) {
                    VstLeerDocs vld = new VstLeerDocs();
                    CtrlLeerDocs ctrlLD = new CtrlLeerDocs(cons, var, vld);
                    ctrlLD.iniciar();
                    vld.setVisible(true);
                }
                
                if (e.getSource() == ve.btnPerfil) {
                    
                }
                
                if (e.getSource() == ve.btnRealizarQuizz) {
                    ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                    ModvariablesPreguntas varP = new ModvariablesPreguntas();
                    VstSelectQuizz vsq = new VstSelectQuizz();
                    CtrlSelectQuizz ctrlP = new CtrlSelectQuizz(cons, var, vsq, varQ, varP);
                    ctrlP.iniciar();
                    vsq.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            ve.setVisible(false);
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
}
