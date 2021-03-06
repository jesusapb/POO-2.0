
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.VstAvances;
import Vista.VstEmpleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase de las funciones principales de los empleados.
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlEmpleados implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesReg var;
    private ModVariablesUsr varU;
    private VstEmpleados ve;

    /**
     * Constructor de la clase.
     * @param cons es la clase donde estan almacenadas las funciones de consulta.
     * @param var es la clase que contiene los datos de los otros usuarios.
     * @param varU es la clase que contiene las variables utilizadas para el usuario y para que sus datos sean almacenados.
     * @param ve es la interfaz  principal del Empleado.
     */
    public CtrlEmpleados(ModConsultasSQL cons, ModVariablesReg var, ModVariablesUsr varU, VstEmpleados ve) {
        this.cons = cons;
        this.var = var;
        this.varU = varU;
        this.ve = ve;

        this.ve.btnAgregar.addActionListener(this);
        this.ve.btnModificar.addActionListener(this);
        this.ve.btnEliminar.addActionListener(this);
        this.ve.btnGenerar.addActionListener(this);
        this.ve.btnReestablecer.addActionListener(this);
        this.ve.btnAvances.addActionListener(this);
    }

    /**
     * Método para visualizar la pantalla de Bandeja de entrada.
     */
    public void iniciar() {
        ve.setTitle("Empleados.");
        ModConsultasSQL.tablaEmp(ve.tablaRegistrados);
        ve.setLocationRelativeTo(null);

    }

    /**
     * Es el contructor encargado en recibir y ejecutar las acciones correspondientes a lo que va ocurriendo en la vista de Empleados.
     * @param e es la variable encargada de recibir cada acciones de los botones de la interfaz gráfica.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(varU.getMatricula());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(varU);
        ModConsultasSQL.status(varU);

        if (cons.existeUsr(varU.getMatricula()) == 1 || cons.existeCorreo(varU.getCorreo()) == 1) {
            if (e.getSource() == ve.btnAgregar) {
                if (ve.txtNombre.getText().equals("") || ve.txtApPat.getText().equals("") || ve.txtApMat.getText().equals("")
                        || ve.txtMatricula.getText().equals("") || ve.txtCorreo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese todos los datos del empleado.");
                } else {
                    int num = (int) (Math.random() * 99999999 + 1);
                    int pass = (int) (Math.random() * 9999 + 1000);
                    String ident = ve.txtCorreo.getText();
                    String[] partese = ident.split("@");
                    String parte = partese[0];

                    if (cons.existeUsr(ve.txtMatricula.getText()) == 0) {
                        if (cons.existeCorreo(parte + "@gmail.com") == 0) {
                            String nombre = ve.txtNombre.getText().toUpperCase();
                            String[] partesn = nombre.split(" ");
                            String partn = partesn[0];
                            var.setNombre(partn);

                            String app = ve.txtApPat.getText().toUpperCase();
                            String[] partesp = app.split(" ");
                            String partp = partesp[0];
                            var.setAp_pat(partp);

                            String apm = ve.txtApMat.getText().toUpperCase();
                            String[] partesm = apm.split(" ");
                            String partm = partesm[0];
                            var.setAp_mat(partm);

                            var.setTipo("Empleado");
                            var.setMatricula(ve.txtMatricula.getText().toUpperCase());
                            var.setContraseña("@NUEVO" + pass);

                            var.setCorreo(parte + "@gmail.com");

                            var.setDia("Nuevo");
                            var.setHora("Nuevo");
                            var.setStatus("Desconectado");
                            var.setIp("Nuevo");
                            var.setEquipo("Nuevo");
                            var.setComando("Deshabilitado");
                            var.setCodigo("Nuevo" + num);
                            var.setNombre_completo(partn + " " + partp + " " + partm);

                            if (cons.registrar(var)) {
                                varU.setAsunto("INFORMACIÓN DE SU REGISTRO.");
                                varU.setMensaje("Se le informa que se ha llevado a cabo el registro exitoso de sus datos, se le proporcionará"
                                        + "la siguiente informacion:\n"
                                        + "Su nombre de usuario es: " + var.getNombre_completo() + ".\n"
                                        + "Su matrícula es: " + var.getMatricula() + ".\n"
                                        + "Su contraseña es: " + var.getContraseña() + ".\n"
                                        + "A partir de este momento usten ya puede acceder a su cuentas.");

                                JOptionPane.showMessageDialog(null, "Registro exitoso.");
                                ModConsultasSQL.tablaEmp(ve.tablaRegistrados);

                                String tipo = "Administrador";
                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                String que = "Se agregó un nuevo empleado: " + var.getMatricula() + "/ " + var.getNombre_completo();
                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                String comp = varU.getMatricula();
                                if (cons.avisoAA(var, tipo, quien, que, cuando, comp));

                                limpiar();
                                if (cons.email(varU, var)) {
                                    JOptionPane.showMessageDialog(null, "Se le ha enviado un mensaje al correo electrónico:\n" + var.getCorreo()
                                            + ".\n Con la informacion de su cuenta.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "El mensaje al correo electrónico " + var.getCorreo() + "\nno se pudo llevar a cabo.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo realizar el registro del empleado.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El correo electrónico ya existe,\n"
                                    + "por favor ingrese unos distinto.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR.\nLa matricula ya se encuentra registrada.\nPor favor introduzca otra.");
                    }
                }
            }

            if (e.getSource() == ve.btnModificar) {
                if (ve.checkCorreo.isSelected() == false) {
                    if (ve.txtNombre.getText().equals("") || ve.txtApPat.getText().equals("") || ve.txtApMat.getText().equals("")
                            || ve.txtMatricula.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Ingrese todos los datos del empleado.");
                    } else {
                        String ident = ve.txtCorreo.getText();
                        String[] partese = ident.split("@");
                        String parte = partese[0];

                        PreparedStatement ps = null;
                        try {
                            ModConexion objCon = new ModConexion();
                            Connection con = objCon.getConexion();
                            ps = con.prepareStatement("UPDATE usuarios SET nombre = ?, ap_pat = ?, ap_mat = ? WHERE matricula = '" + ve.txtMatricula.getText() + "'");

                            String nombre = ve.txtNombre.getText().toUpperCase();
                            String[] partesn = nombre.split(" ");
                            String partn = partesn[0];

                            ps.setString(1, partn);

                            String app = ve.txtApPat.getText().toUpperCase();
                            String[] partesp = app.split(" ");
                            String partp = partesp[0];

                            ps.setString(2, partp);

                            String apm = ve.txtApMat.getText().toUpperCase();
                            String[] partesm = apm.split(" ");
                            String partm = partesm[0];

                            ps.setString(3, partm);

                            var.setCorreo(parte + "@gmail.com");

                            ps.execute();

                            var.setNombre_completo(ve.txtNombre.getText().toUpperCase() + " " + ve.txtApPat.getText().toUpperCase() + " " + ve.txtApMat.getText().toUpperCase());

                            varU.setAsunto("INFORMACIÓN DE LA ACTUALIZACION DE SUS DATOS.");
                            varU.setMensaje("Se le informa que se ha llevado a cabo la actualización exitoso de sus datos, se le proporcionará"
                                    + "la siguiente informacion:\n"
                                    + "Su nombre de usuario es: " + var.getNombre_completo() + ".\n"
                                    + "Su matrícula es: " + ve.txtMatricula.getText() + ".\n"
                                    + "A partir de este momento su cuenta ya ha sido actualizada.");

                            JOptionPane.showMessageDialog(null, "Modificación completa.");
                            ModConsultasSQL.tablaEmp(ve.tablaRegistrados);

                            String tipo = "Administrador";
                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                            String que = "Se modificó a un empleado: " + var.getMatricula() + "/ " + var.getNombre_completo();
                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                            String comp = varU.getMatricula();
                            if (cons.avisoAA(var, tipo, quien, que, cuando, comp));

                            limpiar();
                            if (cons.email(varU, var)) {
                                JOptionPane.showMessageDialog(null, "Se le ha enviado un mensaje al correo electrónico:\n" + var.getCorreo()
                                        + ".\n Con la nueva informacion de su cuenta.");
                            } else {
                                JOptionPane.showMessageDialog(null, "El mensaje al correo electrónico " + var.getCorreo() + "\nno se pudo llevar a cabo.");
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                            Logger.getLogger(CtrlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        String mat = ve.txtMatricula.getText();
                        var.setMatricula(mat);
                    }
                } else {
                    if (ve.txtNombre.getText().equals("") || ve.txtApPat.getText().equals("") || ve.txtApMat.getText().equals("")
                            || ve.txtMatricula.getText().equals("") || ve.txtCorreo.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Ingrese todos los datos del empleado.");
                    } else {

                        String string = ve.txtCorreo.getText();
                        String[] parts = string.split("@");
                        String part1 = parts[0];
                        if (cons.existeCorreo(part1 + "@gmail.com") == 0) {
                            PreparedStatement ps = null;

                            try {
                                ModConexion objCon = new ModConexion();
                                Connection con = objCon.getConexion();
                                ps = con.prepareStatement("UPDATE usuarios SET nombre = ?, ap_pat = ?, ap_mat = ?, correo = ? WHERE matricula = '" + ve.txtMatricula.getText() + "'");

                                String nombre = ve.txtNombre.getText().toUpperCase();
                                String[] partesn = nombre.split(" ");
                                String partn = partesn[0];

                                ps.setString(1, partn);

                                String app = ve.txtApPat.getText().toUpperCase();
                                String[] partesp = app.split(" ");
                                String partp = partesp[0];

                                ps.setString(2, partp);

                                String apm = ve.txtApMat.getText().toUpperCase();
                                String[] partesm = apm.split(" ");
                                String partm = partesm[0];

                                ps.setString(3, partm);

                                ps.setString(4, part1 + "@gmail.com");
                                var.setCorreo(part1 + "@gmail.com");

                                ps.execute();

                                var.setNombre_completo(ve.txtNombre.getText().toUpperCase() + " " + ve.txtApPat.getText().toUpperCase() + " " + ve.txtApMat.getText().toUpperCase());

                                varU.setAsunto("INFORMACIÓN DE LA ACTUALIZACION DE SUS DATOS CON CAMBIO DE CORREO ELECTRÓNICO.");
                                varU.setMensaje("Se le informa que se ha llevado a cabo la actualización exitoso de sus datos, se le proporcionará"
                                        + "la siguiente informacion:\n"
                                        + "Su nombre de usuario es: " + var.getNombre_completo() + ".\n"
                                        + "Su matrícula es: " + ve.txtMatricula.getText() + ".\n"
                                        + "A partir de este momento su cuenta ya ha sido actualizada.");

                                JOptionPane.showMessageDialog(null, "Modificación completa.");
                                ModConsultasSQL.tablaEmp(ve.tablaRegistrados);

                                String tipo = "Administrador";
                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                String que = "Se modificó a un empleado: " + var.getMatricula() + "/ " + var.getNombre_completo();
                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                String comp = varU.getMatricula();
                                if (cons.avisoAA(var, tipo, quien, que, cuando, comp));

                                if (cons.email(varU, var)) {
                                    JOptionPane.showMessageDialog(null, "Se le ha enviado un mensaje al correo electrónico:\n" + var.getCorreo()
                                            + ".\n Con la nueva informacion de su cuenta.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "El mensaje al correo electrónico " + var.getCorreo() + "\nno se pudo llevar a cabo.");
                                }

                                var.setCorreo(ve.email.getText() + "@gmail.com");
                                varU.setAsunto("CAMBIO DE CORREO ELECTRÓNICO.");
                                varU.setMensaje("Se le informa que se ha llevado a cabo el cambio de correo electrónico.\n"
                                        + "Si usted sigue siendo el titutal de la cuenta: " + var.getNombre_completo() + ", se le recomienda"
                                        + "ponerse en contacto con el administrador a travez del correo electrónico: "
                                        + "poo.acompanamiento@gmail.com o puede acudir a las oficinas correspondientes para poder "
                                        + "llevar a cabo el cambio correspondiente.\n"
                                        + "El nuevo correo electrónico registrado es: " + part1 + "@gmail.com");

                                if (cons.email(varU, var)) {
                                    System.out.println("Correo electrónico enviado a " + ve.email.getText() + "@gmail.com");
                                }
                                limpiar();

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                Logger.getLogger(CtrlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            String mat = ve.txtMatricula.getText();
                            var.setMatricula(mat);
                        } else {
                            JOptionPane.showMessageDialog(null, "El correo electrónico ya existe,\n"
                                    + "por favor ingrese unos distinto.");
                        }
                    }
                }
            }

            if (e.getSource() == ve.btnEliminar) {
                PreparedStatement ps = null;
                try {
                    ModConexion objCon = new ModConexion();
                    Connection con = objCon.getConexion();

                    ps = con.prepareStatement("DELETE FROM usuarios WHERE matricula = ?");
                    ps.setString(1, ve.txtMatricula.getText());
                    ps.execute();

                    JOptionPane.showMessageDialog(null, "Registro del empleado eliminado.");
                    ModConsultasSQL.tablaEmp(ve.tablaRegistrados);

                    String tipo = "Administrador";
                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                    String que = "Se eliminó a un empleado: " + var.getMatricula() + "/ " + var.getNombre_completo();
                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                    String comp = varU.getMatricula();
                    if (cons.avisoAA(var, tipo, quien, que, cuando, comp));

                    var.setCorreo(ve.email.getText() + "@gmail.com");
                    varU.setAsunto("ELIMINACION DE CUENTA.");
                    varU.setMensaje("Se le informa que su cuenta con el nombre de usuario: " + ve.txtNombre.getText() + " " + ve.txtApPat.getText() + " " + ve.txtApMat.getText() + " y con la "
                            + "matrícula: " + ve.txtMatricula.getText() + " ha sido eliminada, por lo cual, usted no podrá acceder a su sesión.\n"
                            + "Para mayor información, contacte al administrador a travez de poo.acompanamiento@gmail.com, o también "
                            + "podría acudir a la oficina correspondiente.");

                    if (cons.email(varU, var)) {
                        System.out.println("Correo electrónico enviado a " + ve.email.getText() + "@gmail.com");
                    }

                    limpiar();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "El registro del empleado no pudo ser eliminado.");
                    Logger.getLogger(CtrlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                }

                String mat = ve.txtMatricula.getText();
                var.setMatricula(mat);
            }

            if (e.getSource() == ve.btnGenerar) {
                if (ve.txtNombre.getText().equals("") || ve.txtApPat.getText().equals("") || ve.txtApMat.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Verifique si ingreso el nombre y apellidos del usuario\n antes de realizar esta acción.");
                } else {
                    String nombre = ve.txtNombre.getText();
                    String paterno = ve.txtApPat.getText();
                    String materno = ve.txtApMat.getText();
                    String n = nombre.substring(0, 1);
                    String p = paterno.substring(0, 1);
                    String m = materno.substring(0, 1);

                    int num = (int) (Math.random() * 999999 + 1000);
                    String cadena = (n + p + m).toUpperCase() + num;

                    while (cons.existeUsr(cadena) != 0) {
                        num = (int) (Math.random() * 999999 + 1000);
                        cadena = (n + p + m).toUpperCase() + num;
                    }

                    ve.txtMatricula.setText(cadena);
                }
            }

            if (e.getSource() == ve.btnAvances) {
                VstAvances va = new VstAvances();
                ModVariablesPresentados varP = new ModVariablesPresentados();
                CtrlAvances ctrlA = new CtrlAvances(cons, var, varU, varP, ve, va);
                va.txtNombre.setText(ve.txtMatricula.getText() + "/" + ve.txtNombre.getText() + " " + ve.txtApPat.getText() + " " + ve.txtApMat.getText());
                ctrlA.iniciar();
                va.setVisible(true);
            }

            if (e.getSource() == ve.btnReestablecer) {
                limpiar();
            }

        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            ve.setVisible(false);
            variables();
        }
    }

    /**
     * Método que limpia la interfaz gráfica.
     */
    public void limpiar() {
        ve.txtNombre.setText(null);
        ve.txtApPat.setText(null);
        ve.txtApMat.setText(null);
        ve.txtMatricula.setText(null);
        ve.txtCorreo.setText(null);
        ve.txtCorreo.setEnabled(true);
        ve.txtMatricula.setEditable(true);

        ve.btnAgregar.setVisible(true);
        ve.btnGenerar.setVisible(true);
        ve.btnAvances.setVisible(false);
        ve.btnReestablecer.setVisible(false);
        ve.btnModificar.setVisible(false);
        ve.btnEliminar.setVisible(false);
        ve.checkCorreo.setVisible(false);
    }

    /**
     * Limpia las variables de la interfaz.
     */
    public void variables() {
        varU.setNombre(null);
        varU.setAp_pat(null);
        varU.setAp_mat(null);
        varU.setTipo(null);
        varU.setMatricula(null);
        varU.setContraseña(null);
        varU.setStatus(null);
        varU.setCorreo(null);
    }
}
