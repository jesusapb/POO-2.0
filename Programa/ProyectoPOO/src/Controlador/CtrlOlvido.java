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
 * Es la clase encargada de ser el controlador de la vista olvido
 *donde el usuario proporciana su correo o matricula para 
 * la recuperacion de la contraseña y con ello de la cuenta
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */

public class CtrlOlvido implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstOlvido vfc;

    
 /**
 * constructor de la clase olvido
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    public CtrlOlvido(ModConsultasSQL cons, ModVariablesUsr var, VstOlvido vfc) {
        this.cons = cons;
        this.var = var;
        this.vfc = vfc;

        this.vfc.botonValidar.addActionListener(this);
    }

    public void iniciar() {
        vfc.setLocationRelativeTo(null);
    }
/**
     * Es el constructor encargado en recibir y ejecutar la acciones
     * correspondiente a lo que va ocurriendo en la vista de olvido
     * aceptando el correo electronico o la matricula
     *
     * @param e variable encargada de recibir cada acción de los botónes de la
     * interfaz gráfica.
     * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
     * @version 29/11/2018/ProyectoPoo_Acompañamiento 
     */
        
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
