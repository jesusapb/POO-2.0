/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesUsr;
import Vista.VstEnviados;
import Vista.VstBandejadEntrada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Es la clase encargada de ser el controlador de la clase vista enviados
 *
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlEnviados implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private ModVariablesMensaje varM;
    private VstEnviados ve;
    private VstBandejadEntrada vbe;

 /**
 * constructor de la clase enviados
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    
    public CtrlEnviados(ModConsultasSQL con, ModVariablesUsr var, ModVariablesMensaje varM, VstEnviados ve, VstBandejadEntrada vbe) {
        this.con = con;
        this.var = var;
        this.varM = varM;
        this.ve = ve;
        this.vbe = vbe;

        this.ve.btnReenviar.addActionListener(this);
    }
 /**
 * metodo encargado de iniciar 
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    
    public void iniciar() {
        ve.setTitle("Mensajes enviados.");
        ve.setLocationRelativeTo(null);

        ve.matricula.setText(vbe.matricula.getText());
        ModConsultasSQL.enviados(ve.tablaEnviados, varM, var);
        ve.btnReenviar.setVisible(false);
    }

 /**
  * Es el constructor encargado en recibir y ejecutar la acciones
  * correspondiente a lo que va ocurriendo en la vista enviados
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
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
            if (e.getSource() == ve.btnReenviar) {
                if (ve.txtMensaje.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No ha escrito el mensaje.");
                } else {
                    ModConsultasSQL.enviados(ve.tablaEnviados, varM, var);
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
                        JOptionPane.showMessageDialog(null, "Se ha reenviado el mensaje a: " + varM.getPara_nom());
                        ModConsultasSQL.enviados(ve.tablaEnviados, varM, var);
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo reenviado el mensaje a: " + varM.getPara_nom());
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
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
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
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    public void limpiar() {
        ve.txtAsunto.setText(null);
        ve.txtMensaje.setText(null);
        ve.txtPara.setText(null);
        ve.btnReenviar.setVisible(false);
    }
}
