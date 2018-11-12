/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstOlvido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlOlvido implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstOlvido vfc;

    public CtrlOlvido(ModConsultasSQL cons, ModVariablesUsr var, VstOlvido vfc) {
        this.cons = cons;
        this.var = var;
        this.vfc = vfc;

        this.vfc.botonValidar.addActionListener(this);
        //this.vfc.btnCerrar.addActionListener(this);
    }

    public void iniciar() {
        vfc.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == vfc.btnCerrar) {
//            vfc.setVisible(false);
//        }
        
        if (e.getSource() == vfc.botonValidar) {
            String busm = "";
            String busc = "";
            if (vfc.txtMatriculaCorreo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese su matricula o correo electrónico.");
            } else {
                String mat = vfc.txtMatriculaCorreo.getText();
                var.setMatricula(mat);

                if (cons.emailMatricula(var)) {
                    vfc.txtMatriculaCorreo.setText(null);
                } else {
                    if ("Conectado".equals(var.getStatus()) || "Permanente".equals(var.getStatus())); else if ("Nuevo".equals(var.getMensaje())); else {
                        busm = "null";
                    }
                }

                if (cons.emailCorreo(var)) {
                    vfc.txtMatriculaCorreo.setText(null);
                } else {
                    if ("Conectado".equals(var.getStatus()) || "Permanente".equals(var.getStatus())); else if ("Nuevo".equals(var.getMensaje())); else {
                        busc = "null";
                    }
                }

                if ("null".equals(busm) && "null".equals(busc)) {
                    JOptionPane.showMessageDialog(null, "No se encontro la matrícula.");
                }
            }
        }
    }
}
