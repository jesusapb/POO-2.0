/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Empleado;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesUsr;
import Vista.Empleado.VstPerfil;
import Vista.VstEmpleado;
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
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class CtrlPerfil implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private ModVariablesPresentados varP;
    private VstPerfil vp;
    private VstEmpleado ve;
    private Timer t;

    public CtrlPerfil(ModConsultasSQL cons, ModVariablesUsr var, VstPerfil vp, VstEmpleado ve, ModVariablesPresentados varP) {
        this.cons = cons;
        this.var = var;
        this.vp = vp;
        this.ve = ve;
        this.varP = varP;

        this.vp.btncambioContr.addActionListener(this);
        this.vp.btnvalidar.addActionListener(this);
        this.vp.btncancelar.addActionListener(this);
        this.vp.btnguardar.addActionListener(this);
    }

    public void iniciar() {
        vp.setTitle("Perfil");
        vp.setLocationRelativeTo(null);
        vp.nombre.setText(var.getNombre());
        vp.apPat.setText(var.getAp_pat());
        vp.apMat.setText(var.getAp_mat());
        vp.matricula.setText(var.getMatricula());
        vp.correo.setText(var.getCorreo());
        vp.cambio.setVisible(false);
        vp.btnguardar.setEnabled(false);
        vp.contraN.setEnabled(false);
        vp.contraConfN.setEnabled(false);
        ModConsultasSQL.tablaRegistro(vp.tablaRegistro, varP, var.getMatricula());

        t = new Timer(10, acciones);
        t.start();
    }

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
            if ("Permanente".equals(var.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vp.setVisible(false);
            } else {
                if (e.getSource() == vp.btncambioContr) {
                    limpiar();
                    t.stop();
                    vp.cambio.setVisible(true);
                }
                if (e.getSource() == vp.btncancelar) {
                    limpiar();
                }
                if (e.getSource() == vp.btnvalidar) {
                    String contra = new String(vp.contra.getPassword());
                    String contraConf = new String(vp.contraConf.getPassword());
                    if (contra.equals("") || contraConf.equals("")) {
                        JOptionPane.showMessageDialog(null, "Debe de ingresar y confirmar su contraseña.");
                    } else {
                        if (contra.equals(contraConf)) {
                            if (contra.equals(var.getContraseña())) {
                                vp.btnvalidar.setEnabled(false);
                                vp.btnguardar.setEnabled(true);
                                vp.contraN.setEnabled(true);
                                vp.contraConfN.setEnabled(true);
                                vp.contra.setEnabled(false);
                                vp.contraConf.setEnabled(false);
                            } else {
                                JOptionPane.showMessageDialog(null, "La contraseña es incorrecta.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Las contraseñas con coinciden.");
                        }
                    }
                }
                if (e.getSource() == vp.btnguardar) {
                    String contraN = new String(vp.contraN.getPassword());
                    String contraConfN = new String(vp.contraConfN.getPassword());
                    int cont = contraN.length();
                    String comp = "";
                    if (contraN.equals("") || contraConfN.equals("")) {
                        JOptionPane.showMessageDialog(null, "Debe de ingresar y confirmar su nueva contraseña.");
                    } else {
                        if (contraN.equals(contraConfN)) {
                            if (cont >= 6) {
                                comp = contraN.substring(0, 6).toUpperCase();

                                if (contraN.equals(var.getContraseña())) {
                                    JOptionPane.showMessageDialog(null, "Contraseña no válida.");
                                } else {
                                    if ("@NUEVO".equals(comp)) {
                                        JOptionPane.showMessageDialog(null, "Contraseña no válida.");
                                    } else {
                                        try {
                                            PreparedStatement ps = null;

                                            ModConexion objCon = new ModConexion();
                                            Connection con = objCon.getConexion();
                                            ps = con.prepareStatement("UPDATE usuarios SET contraseña = ? WHERE matricula = '" + var.getMatricula() + "'");

                                            ps.setString(1, contraN);
                                            ps.execute();

                                            var.setContraseña(contraN);

                                            JOptionPane.showMessageDialog(null, "Modificación completa.");
                                            limpiar();

                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                            Logger.getLogger(Controlador.Administrador.CtrlPerfil.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Las contraseñas con coinciden.");
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vp.setVisible(false);
        }
    }

    public void limpiar() {
        vp.contra.setText(null);
        vp.contraConf.setText(null);
        vp.contraN.setText(null);
        vp.contraConfN.setText(null);
        vp.cambio.setVisible(false);
        vp.contraN.setEnabled(false);
        vp.contraConfN.setEnabled(false);
        vp.contra.setEnabled(true);
        vp.contraConf.setEnabled(true);
        t.start();
    }

    private int h, m, s, cs;
    private final ActionListener acciones = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent ae) {
            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (cs == 0 && (s % 2 == 0)) {
                vp.nombre.setText(var.getNombre());
                vp.apPat.setText(var.getAp_pat());
                vp.apMat.setText(var.getAp_mat());
                vp.matricula.setText(var.getMatricula());
                vp.correo.setText(var.getCorreo());
                ModConsultasSQL.tablaRegistro(vp.tablaRegistro, varP, var.getMatricula());
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
//            actualizarLabel();

        }
    };
}
