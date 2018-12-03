package proyectopoo;

import Controlador.CtrlAdministrador;
import Controlador.CtrlEmpleado;
import Controlador.CtrlEmpleados;
import Controlador.CtrlLogin;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModListas;
import Modelo.ModVariablesUsr;
import Vista.VstAdministrador;
import Vista.VstEmpleado;
import Vista.VstLogin;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase principal de todo el programa, aquí ocurren distintios tipos
 * de validaciones.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @vesion 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ProyectoPOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ModVariablesUsr var = new ModVariablesUsr();
            ModConsultasSQL cons = new ModConsultasSQL();
            VstLogin vl = new VstLogin();
            
            ModListas mens = new ModListas();
            /**
             * Función de la clase ModListas, este es un arreglo con los datos
             * correspondientes del usuario que tiene una sesión activa en la
             * computadora donde se está ejecutando el programa.
             *
             * @return datos del usuario que tiene sesión activa en la máquina
             * correspondiente.
             * @param String equipo (InetAddress.getLocalHost().getHostName()), le
             * pide a la maquina el nombre con el cuál está registrada, asi comparar
             * en la base de datos si hay alguna con el nombre y si lo hay saber si
             * esta activo o no.
             */
            ArrayList<ModVariablesUsr> list = mens.listaUsr(InetAddress.getLocalHost().getHostName());
            
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    var = list.get(i);
                    
                    /**
                     * Si se detecta que el equipo tiene alguna sesión activa le
                     * pregunta al usuario si es el usuario que tiene esa sesión
                     * activa.
                     *
                     * @return YES_OPTION, NO_OPTION
                     */
                    int conf = JOptionPane.showConfirmDialog(null, "¿Desea continuar en la sesión " + var.getMatricula() + "/ " + var.getNombre_completo() + "?", "Detección de sesión abierta.", JOptionPane.YES_NO_OPTION);
                    
                    if (conf == JOptionPane.YES_OPTION) {
                        /**
                         * Si dice que si es ese usuario le pide la contraseña de
                         * dicha sesión.
                         *
                         * @return pass, la contraseña de la sesión.
                         */
                        String pass = JOptionPane.showInputDialog(null, "Introduzca la contraseña.", "Validar contraseña.", JOptionPane.WARNING_MESSAGE);
                        if (pass == null) {
                            System.exit(0);
                        }
                        if (pass.equals(var.getContraseña())) {
                            
                            /**
                             * Si es la contraseña, verifica el tipo de usuario que
                             * es la sesión a la cual se desea ingresar, con esto se
                             * le asignará la ventana correspondiente.
                             */
                            if ("Administrador".equals(var.getTipo())) {
                                JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                                VstAdministrador va = new VstAdministrador();
                                CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                                ctrlA.iniciar();
                                va.setVisible(true);
                                vl.setVisible(false);
                                
                            } else if ("Empleado".equals(var.getTipo())) {
                                JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");
                                VstEmpleado ve = new VstEmpleado();
                                CtrlEmpleado ctrlE = new CtrlEmpleado(cons, var, ve);
                                ctrlE.iniciar();
                                ve.setVisible(true);
                                vl.setVisible(false);
                            }
                            
                        } else {
                            boolean band = true;
                            /**
                             * Si la contraseña es erronea, se le pregunta al
                             * usuario si desea ingresar de nuevo la contraseña.
                             *
                             * @return YES_OPTION, NO_OPTION
                             */
                            int intento = JOptionPane.showConfirmDialog(null, "La contraseña es incorrecta.\n ¿Desea volver intentar?", "Contraseña incorrecta.", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                            
                            while (intento == JOptionPane.YES_OPTION) {
                                /**
                                 * Si dice que si, se le pide de nuevo al usuario la
                                 * contraseña de dicha sesión.
                                 */
                                pass = JOptionPane.showInputDialog(null, "Introduzca la contraseña.", "Validar contraseña.", JOptionPane.WARNING_MESSAGE);
                                if (pass == null) {
                                    System.exit(0);
                                }
                                if (pass.equals(var.getContraseña())) {
                                    intento = JOptionPane.NO_OPTION;
                                    band = false;
                                    
                                    /**
                                     * Si la contraseña es correcta, se le asigna la
                                     * ventana a la sesión correspondiente.
                                     */
                                    if ("Administrador".equals(var.getTipo())) {
                                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                                        VstAdministrador va = new VstAdministrador();
                                        CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                                        ctrlA.iniciar();
                                        va.setVisible(true);
                                        vl.setVisible(false);
                                        
                                    } else if ("Empleado".equals(var.getTipo())) {
                                        JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");
                                        VstEmpleado ve = new VstEmpleado();
                                        CtrlEmpleado ctrlE = new CtrlEmpleado(cons, var, ve);
                                        ctrlE.iniciar();
                                        ve.setVisible(true);
                                        vl.setVisible(false);
                                    }
                                    
                                } else {
                                    intento = JOptionPane.showConfirmDialog(null, "La contraseña es incorrecta.\n ¿Desea volver intentar?", "Contraseña incorrecta.", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            
                            /**
                             * Si el usuario dice que no desea seguir intentando
                             * poner la contraseña, el programa se cierra.
                             */
                            if (intento == JOptionPane.NO_OPTION && band == true) {
                                System.exit(0);
                            }
                        }
                    } else {
                        /**
                         * Si el usuarió desde el principio niega ser el usuario con
                         * la cuenta activa, a ese usuario con la cuenta activa se
                         * le desconecta de la maquina para darle opción al nuevo
                         * usuario de ingresar con su cuenta (se despliega la
                         * ventana de logeo).
                         */
                        PreparedStatement ps = null;
                        try {
                            ModConexion objCon = new ModConexion();
                            Connection con = objCon.getConexion();
                            ps = con.prepareStatement("UPDATE usuarios SET status = ? WHERE ip = '" + var.getIp() + "'");
                            
                            ps.setString(1, "Desconectado");
                            
                            ps.execute();
                            
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "No se pudo cerrar la sesión.");
                            Logger.getLogger(CtrlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        JOptionPane.showMessageDialog(null, "Ya puede hacer un nuevo inicio de sesión.");
                        CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                        ctrlL.iniciar();
                        vl.setVisible(true);
                    }
                }
            } else {
                /**
                 * Si no hay nunguna cuenta activa en la maquina, se le da acceso al
                 * usuario de acceder con su cuenta (se despliega la ventana de
                 * logeo).
                 */
                CtrlLogin ctrlL = new CtrlLogin(cons, var, vl);
                ctrlL.iniciar();
                vl.setVisible(true);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(ProyectoPOO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
