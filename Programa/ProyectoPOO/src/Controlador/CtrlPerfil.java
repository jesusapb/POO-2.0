
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstPerfil;
import Vista.VstAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase controlador de perfil, aqui ocurre la gestion de la ventana
 * perfil
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlPerfil implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstPerfil vp;
    private VstAdministrador va;
/**     
 * 
 * constructor de la clase controlador perfil
 * @param cons parametro que contiene el resultado de las consultas sql
 * @param var parametro con las variables del usuario
 * @param vp  parametro que funciona como conexion con la vista perfil
 * @param va  parametro que funciona como conexion con la vista administrador
 */
    public CtrlPerfil(ModConsultasSQL cons, ModVariablesUsr var, VstPerfil vp, VstAdministrador va) {
        this.cons = cons;
        this.var = var;
        this.vp = vp;
        this.va = va;

        this.vp.btnGuardar.addActionListener(this);
        this.vp.btnValidar.addActionListener(this);
        this.vp.btnInfo.addActionListener(this);
    }

    public void iniciar() {
        vp.setTitle("Perfil");
        vp.setLocationRelativeTo(null);
        vp.nombre.setText(var.getNombre());
        vp.ap_pat.setText(var.getAp_pat());
        vp.ap_mat.setText(var.getAp_mat());
        String correo = var.getCorreo();
        String[] part = correo.split("@");
        String part1 = part[0];
        vp.correo.setText(part1);

        vp.nombre.setEditable(false);
        vp.ap_pat.setEditable(false);
        vp.ap_mat.setEditable(false);
        vp.correo.setEditable(false);
        vp.matricula.setEditable(false);
        vp.contraseña.setEditable(false);
        vp.btnGuardar.setEnabled(false);
        vp.btnValidar.setEnabled(false);
    }
    
    /**
     * Es el constructor encargado en recibir y ejecutar la acciones
     * correspondiente a lo que va ocurriendo en la vista de mensjes.
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

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if (e.getSource() == vp.btnInfo) {
                JOptionPane.showMessageDialog(null, "Para poder modificar la información:\n"
                        + "1.- Debe de seleccionar 'Editar' en el\n"
                        + "campo correspondiente donde desea modificar.\n"
                        + "2.- Luego de modificar, debe de ingeresa\n"
                        + "su matrícula y su contraseña y seleccionar\n"
                        + "'Validar', si los datos esta bien, deberá\n"
                        + "de seleccionar 'Guardar' y asi concluir.\n"
                        + "\nPar hacer el cambio de contraseña:\n"
                        + "1.- Debe de seleccionar 'Cambiar de contraseña',\n"
                        + "con esto se abirán los campos correspondientes.\n"
                        + "2.- Debe de ingresar su matrícula y la contraseña\n"
                        + "actual y posterior a esto seleccionar 'Validar',\n"
                        + "si los datos son correctos, deberás de ingresar\n"
                        + "la nueva contraseña en el campo correspondiente\n"
                        + "a la contraseña, luego, deberá de seleccionar\n"
                        + "'Guardar' para concluir, si la contraseña nueva\n"
                        + "no es válida, el sistema pedira el cambio a otra.");
            }

            if (e.getSource() == vp.btnValidar) {
                String nom = vp.nombre.getText();
                String[] pnom = nom.split(" ");
                String npart = pnom[0].toUpperCase();
                vp.nombre.setText(npart);

                String ap_pat = vp.ap_pat.getText();
                String[] pap = ap_pat.split(" ");
                String appart = pap[0].toUpperCase();
                vp.ap_pat.setText(appart);

                String ap_mat = vp.ap_mat.getText();
                String[] pam = ap_mat.split(" ");
                String ampart = pam[0].toUpperCase();
                vp.ap_mat.setText(ampart);

                String correo = vp.correo.getText();
                String[] pcor = correo.split("@");
                String cpart = pcor[0];
                vp.correo.setText(cpart);

                String mat = vp.matricula.getText();
                String pass = new String(vp.contraseña.getPassword());

                if (nom.equals("") || ap_pat.equals("") || ap_mat.equals("")
                        || correo.equals("") || mat.equals("") || pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe de completar todos los campos que estén vacíos.");
                } else {
                    if (mat.equals(var.getMatricula()) && pass.equals(var.getContraseña())) {
                        if (vp.ed_nombre.isSelected() == true) {
                            try {
                                PreparedStatement ps = null;

                                ModConexion objCon = new ModConexion();
                                Connection con = objCon.getConexion();
                                ps = con.prepareStatement("UPDATE usuarios SET nombre = ?, ap_pat = ?, ap_mat = ? WHERE matricula = '" + mat + "'");

                                ps.setString(1, npart);
                                ps.setString(2, appart);
                                ps.setString(3, ampart);
                                ps.execute();

                                var.setNombre(npart);
                                var.setNombre_completo(npart + " " + appart + " " + ampart);
                                va.txtNombre.setText(var.getNombre_completo());

                                JOptionPane.showMessageDialog(null, "Modificación completa.");
                                limpiar();

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                Logger.getLogger(CtrlPerfil.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (vp.ed_correo.isSelected() == true) {
                            if (cons.existeCorreo(cpart + "@gmail.com") == 0) {
                                try {
                                    PreparedStatement ps = null;

                                    ModConexion objCon = new ModConexion();
                                    Connection con = objCon.getConexion();
                                    ps = con.prepareStatement("UPDATE usuarios SET correo = ? WHERE matricula = '" + mat + "'");

                                    ps.setString(1, cpart + "@gmail.com");
                                    ps.execute();

                                    var.setCorreo(cpart + "@gmail.com");

                                    JOptionPane.showMessageDialog(null, "Modificación completa.");
                                    limpiar();

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                    Logger.getLogger(CtrlPerfil.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El correo electrónico ya existe,\n"
                                        + "por favor ingrese unos distinto.");
                            }
                        } else if (vp.cambio.isSelected() == true) {
                            vp.ed_nombre.setEnabled(false);
                            vp.ed_correo.setEnabled(false);
                            vp.cambio.setEnabled(false);
                            vp.matricula.setEditable(false);
                            vp.btnValidar.setEnabled(false);
                            vp.btnGuardar.setEnabled(true);
                            JOptionPane.showMessageDialog(null, "Ahora puede introducir su nueva contraseña.");
                            vp.contraseña.setText(null);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña y/o matrícula incorrecta.");
                    }
                }
            }

            if (e.getSource() == vp.btnGuardar) {
                String mat = vp.matricula.getText();
                String pass = new String(vp.contraseña.getPassword());
                int cont = pass.length();
                String comp = "";
                if (pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe de ingresar la nueva contraseña.");
                } else {
                    if (cont >= 6) {
                        comp = pass.substring(0, 6).toUpperCase();

                        if (pass.equals(var.getContraseña())) {
                            JOptionPane.showMessageDialog(null, "Contraseña no válida.");
                        } else {
                            if ("@NUEVO".equals(comp)) {
                                JOptionPane.showMessageDialog(null, "Contraseña no válida.");
                            } else {
                                try {
                                    PreparedStatement ps = null;

                                    ModConexion objCon = new ModConexion();
                                    Connection con = objCon.getConexion();
                                    ps = con.prepareStatement("UPDATE usuarios SET contraseña = ? WHERE matricula = '" + mat + "'");

                                    ps.setString(1, pass);
                                    ps.execute();

                                    var.setContraseña(pass);

                                    JOptionPane.showMessageDialog(null, "Modificación completa.");
                                    limpiar();

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                    Logger.getLogger(CtrlPerfil.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La contraseña tiene que tener un\n"
                                + "mínimo de 6 caracteres alfanuméricos.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vp.setVisible(false);
            variables();
        }
    }
/**
 * metodo encargado de establescer los atributos de la clase correspondientes
 * al usuario en null
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
        vp.matricula.setText(null);
        vp.contraseña.setText(null);
        vp.ed_nombre.setSelected(false);
        vp.ed_correo.setSelected(false);
        vp.cambio.setSelected(false);
        vp.nombre.setEditable(false);
        vp.ap_pat.setEditable(false);
        vp.ap_mat.setEditable(false);
        vp.correo.setEditable(false);
        vp.matricula.setEditable(false);
        vp.contraseña.setEditable(false);
        vp.btnGuardar.setEnabled(false);
        vp.btnValidar.setEnabled(false);

        vp.ed_nombre.setEnabled(true);
        vp.ed_correo.setEnabled(true);
        vp.cambio.setEnabled(true);
        vp.btnGuardar.setEnabled(false);
    }
}
