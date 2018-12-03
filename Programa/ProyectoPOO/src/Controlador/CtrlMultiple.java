/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.VstMultiple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Esta es la clase de controlador multiple, aqui ocurre la gestion 
 * de la ventana que se presenta para la vista multiple
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @vesion 29/11/2018/ProyectoPoo_Acompañamiento
 */

public class CtrlMultiple implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private ModVariablesMensaje varM;
    private VstMultiple vm;
    private Timer t;

 /**
 * constructor de la clase multiples
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    public CtrlMultiple(ModConsultasSQL con, ModVariablesUsr var, ModVariablesMensaje varM, VstMultiple vm) {
        this.con = con;
        this.var = var;
        this.varM = varM;
        this.vm = vm;

        this.vm.btnEnviar.addActionListener(this);
        this.vm.btnAgregar.addActionListener(this);
        this.vm.btnVaciar.addActionListener(this);
    }

    
    
    public void iniciar() {
        vm.setTitle("Mensaje múltiple.");
        vm.setLocationRelativeTo(null);
        t = new Timer(10, acciones);
        t.start();
        ModVariablesReg varR = new ModVariablesReg();
        ModConsultasSQL.tablaTEmp(vm.tablaTUsuarios, varR, var.getMatricula());
        vm.btnAgregar.setVisible(false);
    }

     /**
     * Es el constructor encargado en recibir y ejecutar la acciones
     * correspondiente a lo que va ocurriendo en la vista de multiples.
     *
     * @param e variable encargada de recibir cada acción de los botónes de la
     * interfaz gráfica.
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
            if (e.getSource() == vm.btnEnviar) {
                if (vm.txtAsunto.getText().equals("") || vm.txtMensaje.getText().equals("") || vm.txtPara.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos de escritura.");
                } else {
                    varM.setDe_mat(var.getMatricula());
                    varM.setDe_nom(var.getNombre_completo());
                    int cantidad = Integer.parseInt(vm.txtCantidad.getText());
                    String[] partir = vm.txtPara.getText().split("~");
                    for (int i = 0; i < cantidad; i++) {
                        String[] datos = partir[i].split("/");
                        varM.setPara_mat(datos[0]);
                        varM.setPara_nom(datos[1]);

                        varM.setFecha(fechaDate.format(date) + " " + horaDate.format(date));
                        varM.setAsunto(vm.txtAsunto.getText());
                        varM.setMensaje(vm.txtMensaje.getText());
                        varM.setStatus("NO VISTO");

                        if (con.enviar(varM)) {
                            JOptionPane.showMessageDialog(null, "Se ha enviado un mensaje a: " + varM.getPara_nom());
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo enviado un mensaje a: " + varM.getPara_nom());
                        }
                    }
                    limpiar();
                }
            }
            if (e.getSource() == vm.btnAgregar) {
                vm.btnAgregar.setVisible(false);
                vm.txtPara.setText(vm.txtPara.getText() + vm.txtMatricula.getText() + "/" + vm.txtNombre.getText() + "~");
                int cantidad = Integer.parseInt(vm.txtCantidad.getText()) + 1;
                vm.txtCantidad.setText(cantidad + "");
            }
            if (e.getSource() == vm.btnVaciar) {
                vm.txtPara.setText(null);
                vm.txtCantidad.setText("0");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vm.setVisible(false);
        }
    }
/**
 * metodo encargado de limpiar los atributos de la clase, dejandolos en null
 * segun se requiera
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    public void limpiar() {
        vm.txtAsunto.setText(null);
        vm.txtMensaje.setText(null);
    }

    private int h, m, s, cs;

/**
* Es el constructor encargado en recibir y ejecutar la acciones
* correspondiente a lo que va ocurriendo en la vista de multiples.
*
* @param e variable encargada de recibir cada acción de los botónes de la
* interfaz gráfica.
* @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
* @version 29/11/2018/ProyectoPoo_Acompañamiento 
*/
    private ActionListener acciones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 5 == 0)) {
                ModVariablesReg varR = new ModVariablesReg();
                ModConsultasSQL.tablaTEmp(vm.tablaTUsuarios, varR, var.getMatricula());
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
        }
    };
}
