
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.VstEnviados;
import Vista.VstEnviar;
import Vista.VstMultiple;
import Vista.VstBandejadEntrada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Esta es la clase de los mensajes recibidos y enviados por el usuarios.
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlBandejadEntrada implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstBandejadEntrada vbe;
    private Timer t;

    /**
     * Constructor de la clase.
     * @param cons es la clase donde estan almacenadas las funciones de consulta.
     * @param var es la clase que contiene las variables utilizadas para el usuario y para que sus datos sean almacenados.
     * @param vbe es la interfaz grafica de la bandeja de entrada.
     */
    public CtrlBandejadEntrada(ModConsultasSQL cons, ModVariablesUsr var, VstBandejadEntrada vbe) {
        this.cons = cons;
        this.var = var;
        this.vbe = vbe;

        this.vbe.btnEnviados.addActionListener(this);
        this.vbe.btnNuevo.addActionListener(this);
        this.vbe.btnMensajeMul.addActionListener(this);
    }

    /**
     * Método para visualizar la pantalla de Bandeja de entrada.
     */
    public void iniciar() {
        vbe.setTitle("Mensajes recibidos.");
        vbe.setLocationRelativeTo(null);
        t = new Timer(10, acciones);
        t.start();
        vbe.matricula.setText(var.getMatricula());
        ModVariablesReg varR = new ModVariablesReg();
        ModConsultasSQL.tablaTEmp(vbe.tablaTUsuarios, varR, vbe.matricula.getText());
        ModVariablesMensaje varM = new ModVariablesMensaje();
        ModConsultasSQL.recibidos(vbe.tablaBandejaEntrada, varM, var);
        vbe.btnNuevo.setVisible(false);
        vbe.btnMensajeMul.setVisible(false);

        vbe.LMat.setVisible(false);
        vbe.LNom.setVisible(false);
        vbe.txtMatricula.setVisible(false);
        vbe.txtNombre.setVisible(false);
    }
    
    /**
     * Es el contructor encargado en recibir y ejecutar las acciones correspondientes a lo que va ocurriendo en la vista de la bandeja de entrada.
     * @param e es la variable encargada de recibir cada acciones de los botones de la interfaz gráfica.
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

        if (cons.existeUsr(var.getMatricula()) == 1) {

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
                CtrlEnviados ctrlE = new CtrlEnviados(cons, var, varM, ve, vbe);
                ctrlE.iniciar();
                ve.setVisible(true);
            }
            if (e.getSource() == vbe.btnMensajeMul) {
                VstMultiple vm = new VstMultiple();
                ModVariablesMensaje varM = new ModVariablesMensaje();
                CtrlMultiple ctrlM = new CtrlMultiple(cons, var, varM, vm);
                ctrlM.iniciar();
                vm.setVisible(true);
                vm.txtCantidad.setText("1");
                vm.txtPara.setText(vbe.txtMatricula.getText() + "/" + vbe.txtNombre.getText() + "~");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vbe.setVisible(false);
            variables();
        }

    }

    /**
     * Limpia las variables para ver una nueva interfaz.    
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
     * Método para el uso del cronómetro
     * @param h variable para hora
     * @param m variable para minutos
     * @param s variable para segundos
     * @param cs variale para centésima de segundo
     */
    private int h, m, s, cs;

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
                ModConsultasSQL.tablaTEmp(vbe.tablaTUsuarios, varR, vbe.matricula.getText());
                ModVariablesMensaje varM = new ModVariablesMensaje();
                ModConsultasSQL.recibidos(vbe.tablaBandejaEntrada, varM, var);
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
