package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstAdministrador;
import Vista.VstEmpleado;
import Vista.VstLogin;
import Vista.VstOlvido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Esta es la clase de logeo, aqui ocurre la gestion de la ventana que se
 * presenta para logear.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlLogin implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstLogin vl;

    /**
     * Constructor de la clase
     *
     * @param cons es la clase donde están almacenadas las funciones de
     * consulta.
     * @param var es la clase que contiene las variables utilizadas para el
     * usuario que inicia la sesión y para que sus datos sean almacenados.
     * @param vl es la interfaz grafica del logeo.
     */
    public CtrlLogin(ModConsultasSQL cons, ModVariablesUsr var, VstLogin vl) {
        this.cons = cons;
        this.var = var;
        this.vl = vl;

        vl.btnAcceder.addActionListener(this);
        vl.btnOlvido.addActionListener(this);
        vl.btnValidar.addActionListener(this);
    }

    /**
     * Constructor encargado en dar el nombre a la ventana y asignarle la
     * posición donde se va a generar al ser visible.
     *
     * @throws UnknownHostException
     */
    public void iniciar() throws UnknownHostException {
        vl.setTitle("Inicio de sesión.");
        vl.setLocationRelativeTo(null);
    }

    /**
     * Es el constructor encargado en recibir y ejecutar la acciones
     * correspondiente a lo que va ocurriendo en la vista de logeo.
     *
     * @param e variable encargada de recibir cada acción de los botónes de la
     * interfaz gráfica.
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

        String mat = vl.txtMat.getText();
        String pass = new String(vl.txtPass.getPassword());

        if (e.getSource() == vl.btnAcceder) {
            if (mat.equals("") || pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Completa todos los campos para iniciar sesión.");
            } else {
                var.setMatricula(mat);
                var.setCorreo(mat);
                var.setContraseña(pass);
                var.setDia(fechaDate.format(date));
                var.setHora(horaDate.format(date));

                if (cons.login(var)) {
                    if ("Administrador".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstAdministrador va = new VstAdministrador();
                        CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                        ctrlA.iniciar();
                        va.setVisible(true);

                    } else if ("Empleado".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstEmpleado ve = new VstEmpleado();
                        CtrlEmpleado ctrlE = new CtrlEmpleado(cons, var, ve);
                        ctrlE.iniciar();
                        ve.setVisible(true);
                    }
                }
                if ("Detección de cambio de contraseña.".equals(var.getMensaje())) {
                    vl.txtMat.setEditable(false);
                    vl.txtPass.setText(null);
                    vl.btnAcceder.setVisible(false);
                    vl.btnValidar.setVisible(true);

                } else if ("Detección de nuevo usuario.".equals(var.getMensaje())) {
                    vl.txtMat.setEditable(false);
                    vl.txtPass.setText(null);
                    vl.btnAcceder.setVisible(false);
                    vl.btnValidar.setVisible(true);

                }
            }
        }

        if (e.getSource() == vl.btnValidar) {
            if (pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Ingresa la nueva contraseña.");
            } else {
                var.setMatricula(mat);
                var.setCorreo(mat);
                var.setContraseña(pass);
                var.setDia(fechaDate.format(date));
                var.setHora(horaDate.format(date));

                if (cons.loginNuevo(var)) {
                    if ("Administrador".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstAdministrador va = new VstAdministrador();
                        CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                        ctrlA.iniciar();
                        va.setVisible(true);

                    } else if ("Empleado".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstEmpleado ve = new VstEmpleado();
                        CtrlEmpleado ctrlE = new CtrlEmpleado(cons, var, ve);
                        ctrlE.iniciar();
                        ve.setVisible(true);

                    }
                }
            }
        }

        if (e.getSource() == vl.btnOlvido) {
            VstOlvido vo = new VstOlvido();
            CtrlOlvido ctrlO = new CtrlOlvido(cons, var, vo);
            ctrlO.iniciar();
            vo.setVisible(true);
        }

    }
}
