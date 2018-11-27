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
    }

    public void iniciar() {
        vfc.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vfc.botonValidar) {
            if (vfc.txtMatriculaCorreo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese su matricula o correo electrónico.");
            } else {
                String mat = vfc.txtMatriculaCorreo.getText();
                var.setMatricula(mat);

                if (cons.emailVal(var)) {
                    vfc.txtMatriculaCorreo.setText(null);
                    vfc.setVisible(false);
                }
            }
        }
    }
}
