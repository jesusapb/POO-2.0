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
 *Es la clase encargada de recibir los mensajes de otros usuarios 
 * y poder visualizarlos 
 * @author Karina Carmona, Antonio Cetzal, Jessica González Bautista y Jesús Pacheco
 * @version 01/12/2018/Proyecto_Poo_Acompañamiento
 */
public class CtrlRecibido implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private ModVariablesMensaje varM;
    private VstRecibido vr;
    private VstBandejadEntrada vbe;
    
    /**
    * Método de la clase 
    * @param con es la clase donde esan almacenadas las funciones de consulta
    * @param var es la clase que contiene las variables utilizadas para el usuario que inicia
    * sesion y para que sus datos sean almacenados 
    * @param varM es la clase que contiene las variables utilizadas para los mensajes y para
    * que sus datos sean almacenados. 
    * @param vr es la interfaz gráfica del mensaje recibido. 
    * @param vbe es la interfaz gráfica de la bandeja de entrada del usuario.  
    */

    public CtrlRecibido(ModConsultasSQL con, ModVariablesUsr var, ModVariablesMensaje varM, VstRecibido vr, VstBandejadEntrada vbe) {
        this.con = con;
        this.var = var;
        this.varM = varM;
        this.vr = vr;
        this.vbe = vbe;

        this.vr.btnContestar.addActionListener(this);
        this.vr.btnEliminar.addActionListener(this);
    }

    /**
    *Constructor en mostrar la interfaz
    */

    public void iniciar() {
        vr.setTitle("Mensaje.");
        vr.setLocationRelativeTo(null);
    }

    /**
    *Constructor encargado en recibir y ejecutar las acciones correspondientes
    * a lo que va ocurriendo en la vista de Recibido
    * @param e es la variable encargada de recibir cada acción de los botones de la vista.
    */

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
