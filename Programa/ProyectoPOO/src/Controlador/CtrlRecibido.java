/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesUsr;
import Vista.VstEnviar;
import Vista.VstRecibido;
import Vista.VstBandejadEntrada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class CtrlRecibido implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private ModVariablesMensaje varM;
    private VstRecibido vr;
    private VstBandejadEntrada vbe;

    public CtrlRecibido(ModConsultasSQL con, ModVariablesUsr var, ModVariablesMensaje varM, VstRecibido vr, VstBandejadEntrada vbe) {
        this.con = con;
        this.var = var;
        this.varM = varM;
        this.vr = vr;
        this.vbe = vbe;

        this.vr.btnContestar.addActionListener(this);
        this.vr.btnEliminar.addActionListener(this);
    }

    public void iniciar() {
        vr.setTitle("Mensaje.");
        vr.setLocationRelativeTo(null);
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

        if (con.existeUsr(var.getMatricula()) == 1) {
            if (e.getSource() == vr.btnContestar) {
                VstEnviar ve = new VstEnviar();
                CtrlEnviar ctrlE = new CtrlEnviar(con, var, varM, ve);
                ctrlE.iniciar();
                ve.setVisible(true);
                ve.txtPara.setText(vr.ident.getText());
            }

            if (e.getSource() == vr.btnEliminar) {
                try {
                    PreparedStatement ps = null;

                    ModConexion objCon = new ModConexion();
                    Connection con = objCon.getConexion();

                    ps = con.prepareStatement("DELETE FROM mensajes WHERE id = ?");
                    ps.setString(1, vr.id.getText());
                    ps.execute();

                    JOptionPane.showMessageDialog(null, "Mensaje eliminado.");
                    vr.setVisible(false);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "El mensaje no fué eliminado.");
                    Logger.getLogger(CtrlEnviados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vr.setVisible(false);
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
