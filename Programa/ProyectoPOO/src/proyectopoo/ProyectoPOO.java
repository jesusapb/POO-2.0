/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopoo;

import Controlador.Administrador.CtrlEmpleados;
import Controlador.CtrlAdministrador;
import Controlador.CtrlLogin;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstAdministrador;
import Vista.VstLogin;
import java.net.InetAddress;
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
public class ProyectoPOO {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        // TODO code application logic here
        ModVariablesUsr var = new ModVariablesUsr();
        ModConsultasSQL cons = new ModConsultasSQL();
        VstLogin vl = new VstLogin();

        var.setIp(InetAddress.getLocalHost().getHostAddress());
        var.setEquipo(InetAddress.getLocalHost().getHostName());
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));

        if (cons.sesion(var)) {
            int conf = JOptionPane.showConfirmDialog(null, "¿Desea continuar en la sesión " + var.getNombre_completo() + "?", "Detección de sesión abierta.", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if ("Administrador".equals(var.getTipo())) {
                    JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                    VstAdministrador va = new VstAdministrador();
                    CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                    ctrlA.iniciar();
                    va.setVisible(true);
                    vl.setVisible(false);

                } else if ("Empleado".equals(var.getTipo())) {
                    JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");

                }
            } else {
                ////////////////////////////////////////////////////////////////
                PreparedStatement ps = null;
                try {
                    ModConexion objCon = new ModConexion();
                    Connection con = objCon.getConexion();
                    ps = con.prepareStatement("UPDATE usuarios SET status = ? WHERE ip = '" + var.getIp() + "'");

                    ps.setString(1, "Desconectado");

                    ps.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo cerrar la sesión.");
                    Logger.getLogger(CtrlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                }
                ////////////////////////////////////////////////////////////////
                JOptionPane.showMessageDialog(null, "Ya puede hacer un nuevo inisio de sesión.");
                CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                ctrlL.iniciar();
                vl.setVisible(true);
            }
        } else {
            CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
            ctrlL.iniciar();
            vl.setVisible(true);
        }
    }

}
