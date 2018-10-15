/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstAdministrador;
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
 *
 * @author Antonio
 */
public class CtrlLogin implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstLogin vl;

    public CtrlLogin(ModConsultasSQL cons, ModVariablesUsr var, VstLogin vl) {
        this.cons = cons;
        this.var = var;
        this.vl = vl;

        vl.btnAcceder.addActionListener(this);
        vl.btnOlvido.addActionListener(this);
        vl.btnValidar.addActionListener(this);
    }

    public void iniciar() throws UnknownHostException {
        vl.setTitle("Inicio de sesión.");
        vl.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vl.btnAcceder) {
            Date date = new Date();
            DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
            DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

            String mat = vl.txtMat.getText();
            String pass = new String(vl.txtPass.getPassword());

            if (mat.equals("") || pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Completa todos los campos para iniciar sesión.");
            } else {
                var.setMatricula(mat);
                var.setCorreo(mat);
                var.setContraseña(pass);
                var.setDia(fechaDate.format(date));
                var.setHora(horaDate.format(date));

                if (cons.loginMat(var)) {
                    if ("Administrador".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstAdministrador va = new VstAdministrador();
                        CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                        ctrlA.iniciar();
                        va.setVisible(true);

                    } else if ("Empleado".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");

                    }
                } else if (cons.loginCor(var)) {
                    if ("Administrador".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador:\n " + var.getNombre_completo() + ".");
                        vl.setVisible(false);
                        VstAdministrador va = new VstAdministrador();
                        CtrlAdministrador ctrlA = new CtrlAdministrador(cons, var, va);
                        ctrlA.iniciar();
                        va.setVisible(true);

                    } else if ("Empleado".equals(var.getTipo())) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Empleado:\n " + var.getNombre_completo() + ".");

                    }
                } else if ("Detección de cambio de contraseña.".equals(var.getMensaje()) || "Detección de nuevo usuario.".equals(var.getMensaje())); else if ("Permanente".equals(var.getStatus()) || "Conectado".equals(var.getStatus())); else {
                    JOptionPane.showMessageDialog(null, "Matrícula/Correo o contraseña incorrecta.");
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
            Date date = new Date();
            DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
            DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

            String mat = vl.txtMat.getText();
            String pass = new String(vl.txtPass.getPassword());

            if (pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Ingresa la nueva contraseña.");
            } else {
                var.setMatricula(mat);
                var.setCorreo(mat);
                var.setContraseña(pass);
                var.setDia(fechaDate.format(date));
                var.setHora(horaDate.format(date));
                var.setAsunto("MATRICULA");

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

                    }
                } else {
                    var.setAsunto("CORREO");
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

                        }
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
