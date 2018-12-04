/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesUsr;
import Vista.VstEnviar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Es la clase encargada de ser el controlador de la clase vista enviar
 *de los mensajes
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */

public class CtrlEnviar implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private ModVariablesMensaje varM;
    private VstEnviar ve;
/** 
 *  constructor de la clase enviar
 * 
 * @param con parametro con los resultados de la consulta sql
 * @param var parametro que contiene las variables del usuario
 * @param varM parametro con las variables de los mensajes
 * @param ve paramtro que conecta con la vista
 */
    public CtrlEnviar(ModConsultasSQL con, ModVariablesUsr var, ModVariablesMensaje varM, VstEnviar ve) {
        this.con = con;
        this.var = var;
        this.varM = varM;
        this.ve = ve;

        this.ve.btnEnviar.addActionListener(this);
    }

    
    /**
     * metodo para iniciar la vista enviar
     */
    public void iniciar() {
        ve.setTitle("Nuevo mensaje.");
        ve.setLocationRelativeTo(null);
    }
    
    
    /**     
     * metodo encargado de interacutuar con el usuario en la vista enviar
     * @param e parametro que esta escuchando los eventos de la vista enviar
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
            if (e.getSource() == ve.btnEnviar) {
                if (ve.txtAsunto.getText().equals("") || ve.txtMensaje.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos de escritura.");
                } else {
                    varM.setDe_mat(var.getMatricula());
                    varM.setDe_nom(var.getNombre_completo());

                    String mat = ve.txtPara.getText();
                    String[] part = mat.split("/");
                    String part1 = part[0];
                    varM.setPara_mat(part1);
                    String par2 = part[1];
                    varM.setPara_nom(par2);

                    varM.setFecha(fechaDate.format(date) + " " + horaDate.format(date));
                    varM.setAsunto(ve.txtAsunto.getText());
                    varM.setMensaje(ve.txtMensaje.getText());
                    varM.setStatus("NO VISTO");

                    if (con.enviar(varM)) {
                        JOptionPane.showMessageDialog(null, "Se ha enviado un mensaje a: " + varM.getPara_nom());
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo enviado un mensaje a: " + varM.getPara_nom());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            ve.setVisible(false);
            variables();
        }
    }
/**
 * metodo encargado de contener los atributos del empleado
 */
    
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

  /**
 * metodo encargado de limpiar los atributos de la clase, dejandolos en null
 * segun se requiera
 */
    public void limpiar() {
        ve.txtAsunto.setText(null);
        ve.txtMensaje.setText(null);
    }
}
