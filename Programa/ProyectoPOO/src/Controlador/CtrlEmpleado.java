
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModListas;
import Modelo.ModVariablesAvisos;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.VstLeerDocs;
import Vista.VstPerfilEmp;
import Vista.VstSelectQuizz;
import Vista.VstBandejadEntrada;
import Vista.VstEmpleado;
import Vista.VstLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Esta es la clase de la creación de un empleado.
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlEmpleado implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstEmpleado ve;
    private Timer t;

    /**
     * Constructor de la clase.
     * @param cons es la clase donde estan almacenadas las funciones de consulta.
     * @param var es la clase que contiene las variables utilizadas para el usuario y para que sus datos sean almacenados.
     * @param ve es la interfaz del empleado.
     */
    public CtrlEmpleado(ModConsultasSQL cons, ModVariablesUsr var, VstEmpleado ve) {
        this.cons = cons;
        this.var = var;
        this.ve = ve;

        this.ve.btnCerrarSesion.addActionListener(this);
        this.ve.btnCerrar.addActionListener(this);
        this.ve.btnMini.addActionListener(this);
        this.ve.btnMensajes.addActionListener(this);
        this.ve.btnLTodo.addActionListener(this);
        this.ve.btnLeerDocs.addActionListener(this);
        this.ve.btnRealizarQuizz.addActionListener(this);
        this.ve.btnPerfil.addActionListener(this);
    }

    /**
     * Método para iniciar y visualizar la pantalla de Empleado.
     */
    public void iniciar() {
        ve.setTitle("Empleado.");
        ve.setLocationRelativeTo(null);

        ImageIcon icono = null;
        if (cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png") != null) {
            icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png"));
        }
        ModVariablesMensaje varM = new ModVariablesMensaje();
        if (cons.ENVisto(varM, var.getMatricula()) == 1) {
            ve.btnMensajes.setIcon(icono);
        }

        t = new Timer(10, acciones);
        t.start();
        ve.setLocationRelativeTo(null);
        ve.txtNombre.setText(var.getNombre_completo());
        ve.txtMatricula.setText(var.getMatricula());
        ve.txtTipo.setText(var.getTipo());
        ModConsultasSQL.tablaConectados(var, ve.tablaConectados, ve.txtMatricula.getText());
        ModVariablesAvisos varA = new ModVariablesAvisos();
        ModConsultasSQL.tablaAvisos(ve.tablaAvisos, varA, var.getMatricula());
        ModListas mens = new ModListas();
        ArrayList<ModVariablesAvisos> list = mens.listaAv(var.getMatricula());
        if (list.isEmpty()) {
            ve.btnLTodo.setVisible(false);
        } else {
            ve.btnLTodo.setVisible(true);
        }
    }

    /**
     * Es el contructor encargado en recibir y ejecutar las acciones correspondientes a lo que va ocurriendo en la vista de Empleado.
     * @param e es la variable encargada de recibir cada acciones de los botones de la interfaz gráfica.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(ve.txtMatricula.getText());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(var);
        ModConsultasSQL.status(var);

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if (e.getSource() == ve.btnCerrarSesion) {
                var.setStatus("Desconectado");
                if (cons.cerrar(var));
                ve.setVisible(false);
                variables();
                VstLogin vl = new VstLogin();
                CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                try {
                    ctrlL.iniciar();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }
                vl.setVisible(true);
            }

            if (e.getSource() == ve.btnCerrar) {
                int preg = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (preg == 0) {
                    t.stop();
                    System.exit(0);
                }
            }

            if (e.getSource() == ve.btnMini) {
                ve.setState(VstEmpleado.ICONIFIED);
            }

            if (e.getSource() == ve.btnMensajes) {
                VstBandejadEntrada vbe = new VstBandejadEntrada();
                CtrlBandejadEntrada ctrlBE = new CtrlBandejadEntrada(cons, var, vbe);
                ctrlBE.iniciar();
                vbe.setVisible(true);
            }

            if (e.getSource() == ve.btnLeerDocs) {
                VstLeerDocs vld = new VstLeerDocs();
                CtrlLeerDocs ctrlLD = new CtrlLeerDocs(cons, var, vld);
                ctrlLD.iniciar();
                vld.setVisible(true);
            }

            if (e.getSource() == ve.btnPerfil) {
                VstPerfilEmp vp = new VstPerfilEmp();
                ModVariablesPresentados varP = new ModVariablesPresentados();
                CtrlPerfilEm ctrlP = new CtrlPerfilEm(cons, var, vp, ve, varP);
                ctrlP.iniciar();
                vp.setVisible(true);
            }

            if (e.getSource() == ve.btnRealizarQuizz) {
                ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                ModvariablesPreguntas varP = new ModvariablesPreguntas();
                VstSelectQuizz vsq = new VstSelectQuizz();
                CtrlSelectQuizz ctrlP = new CtrlSelectQuizz(cons, var, vsq, varQ, varP);
                ctrlP.iniciar();
                vsq.setVisible(true);
            }

            if (e.getSource() == ve.btnLTodo) {
                ModVariablesAvisos varA = new ModVariablesAvisos();
                ModConsultasSQL.LeerTodo(varA, var.getMatricula());
                ModConsultasSQL.tablaAvisos(ve.tablaAvisos, varA, var.getMatricula());
                ModListas mens = new ModListas();
                ArrayList<ModVariablesAvisos> list = mens.listaAv(var.getMatricula());
                if (list.isEmpty()) {
                    ve.btnLTodo.setVisible(false);
                } else {
                    ve.btnLTodo.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            ve.setVisible(false);
            variables();
            VstLogin vl = new VstLogin();
            CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
            try {
                ctrlL.iniciar();
            } catch (UnknownHostException ex) {
                Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
            vl.setVisible(true);
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
        var.setEquipo(null);
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
            ImageIcon icono = null;
            if (cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png") != null) {
                icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px_1.png"));
            }

            Date date = new Date();
            DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
            DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

            var.setDia(fechaDate.format(date));
            var.setHora(horaDate.format(date));

            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 2 == 0)) {
                ModConsultasSQL.tablaConectados(var, ve.tablaConectados, ve.txtMatricula.getText());
                ModConsultasSQL.recarga(var);

                ModVariablesAvisos varA = new ModVariablesAvisos();
                ModConsultasSQL.tablaAvisos(ve.tablaAvisos, varA, var.getMatricula());
                ModListas mens = new ModListas();
                ArrayList<ModVariablesAvisos> list = mens.listaAv(var.getMatricula());
                if (list.isEmpty()) {
                    ve.btnLTodo.setVisible(false);
                } else {
                    ve.btnLTodo.setVisible(true);
                }

                ModVariablesMensaje varM = new ModVariablesMensaje();
                if (cons.ENVisto(varM, var.getMatricula()) == 1) {
                    ve.btnMensajes.setIcon(icono);
                } else {
                    icono = new ImageIcon(cons.get_Image("/Imagenes/icons8_Envelope_20px.png"));
                    ve.btnMensajes.setIcon(icono);
                }
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
