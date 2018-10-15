/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.CtrlMensajes;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.Mensajes.VstBandejadEntrada;
import Vista.Mensajes.VstEnviados;
import Vista.Mensajes.VstEnviar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlBandejadEntrada implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstBandejadEntrada vbe;

    public CtrlBandejadEntrada(ModConsultasSQL cons, ModVariablesUsr var, VstBandejadEntrada vbe) {
        this.cons = cons;
        this.var = var;
        this.vbe = vbe;

        this.vbe.btnEnviados.addActionListener(this);
        this.vbe.btnNuevo.addActionListener(this);
    }

    public void iniciar() {
        vbe.setTitle("Mensajes recibidos.");
        vbe.setLocationRelativeTo(null);

        vbe.matricula.setText(var.getMatricula());
        ModVariablesReg varR = new ModVariablesReg();
        ModConsultasSQL.tablaTEmp(vbe.tablaTUsuarios, varR);
        ModVariablesMensaje varM = new ModVariablesMensaje();
        ModConsultasSQL.recibidos(vbe.tablaBandejaEntrada, varM, var);
        ModConsultasSQL.todos(vbe.tablaTodos, varM, var);
        vbe.btnNuevo.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(var.getMatricula());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(var);
        ModConsultasSQL.status(var);

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if ("Permanente".equals(var.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vbe.setVisible(false);
                variables();
            } else {
                if (e.getSource() == vbe.btnNuevo) {
                    VstEnviar ve = new VstEnviar();
                    ModVariablesMensaje varM = new ModVariablesMensaje();
                    CtrlEnviar ctrlE = new CtrlEnviar(cons, var, varM, ve);
                    ctrlE.iniciar();
                    ve.setVisible(true);
                    ve.txtPara.setText(vbe.txtMatricula.getText() + "/" + vbe.txtNombre.getText());
                }

                if (e.getSource() == vbe.btnEnviados) {
                    VstEnviados ve = new VstEnviados();
                    ModVariablesMensaje varM = new ModVariablesMensaje();
                    CtrlEnviados ctrlE = new CtrlEnviados(cons, var, varM, ve);
                    ctrlE.iniciar();
                    ve.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vbe.setVisible(false);
            variables();
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
