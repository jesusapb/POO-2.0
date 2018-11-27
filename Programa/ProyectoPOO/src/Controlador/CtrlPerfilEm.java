/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesAbierto;
import Modelo.ModVariablesCalif;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesUsr;
import Vista.VstPerfilEmp;
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
public class CtrlPerfilEm implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private ModVariablesPresentados varP;
    private VstPerfilEmp vp;
    private VstEmpleado ve;
    private Timer t;
    private String[] preg = {};
    private String temp[] = new String[10];
    private int cont = 0;
    private int acum = 0;

    public CtrlPerfilEm(ModConsultasSQL cons, ModVariablesUsr var, VstPerfilEmp vp, VstEmpleado ve, ModVariablesPresentados varP) {
        this.cons = cons;
        this.var = var;
        this.vp = vp;
        this.ve = ve;
        this.varP = varP;

        this.vp.btncambioContr.addActionListener(this);
        this.vp.btnvalidar.addActionListener(this);
        this.vp.btncancelar.addActionListener(this);
        this.vp.btnguardar.addActionListener(this);

        this.vp.btnVer.addActionListener(this);
        this.vp.btnSiguiente.addActionListener(this);
        this.vp.btnAtras.addActionListener(this);
        this.vp.btnCerrar.addActionListener(this);
        this.vp.btncambioTab.addActionListener(this);
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
        vp.ver.setVisible(false);
        vp.btnVer.setVisible(false);
        vp.btnAtras.setVisible(false);
        vp.btnguardar.setEnabled(false);
        vp.contraN.setEnabled(false);
        vp.contraConfN.setEnabled(false);
        ModConsultasSQL.tablaRegistro(vp.tablaRegistro, varP, var.getMatricula(), "Empleado");

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
        ModConsultasSQL.obtenerResp(varP, var.getMatricula(), vp.txtIntento.getText(), vp.nomQuizz.getText());

        if (cons.existeUsr(var.getMatricula()) == 1) {
            if (e.getSource() == vp.btncambioTab) {
                if (vp.btncambioTab.getText().equals("Tabla de calificaciones")) {
                    vp.btnVer.setVisible(false);
                    vp.anuncio.setText("Calificaciones:");
                    vp.btncambioTab.setText("Tabla de registro de Quizzes");
                    ModVariablesCalif varC = new ModVariablesCalif();
                    ModConsultasSQL.tablaCalif(vp.tablaRegistro, varC, var.getMatricula());
                    t.stop();

                } else if (vp.btncambioTab.getText().equals("Tabla de registro de Quizzes")) {
                    vp.anuncio.setText("Registro de Quizzes presentados:");
                    vp.btncambioTab.setText("Tabla de calificaciones");
                    ModConsultasSQL.tablaRegistro(vp.tablaRegistro, varP, var.getMatricula(), "Empleado");
                    t.start();
                }
            }
            if (e.getSource() == vp.btnVer) {
                preg = varP.getAbrt().split("~");
                vp.btnVer.setVisible(false);
                t.stop();
                cont = 0;
                acum = 0;
                ModVariablesAbierto varA = new ModVariablesAbierto();
                ModConsultasSQL.llenarRespMod(vp, varA, var.getMatricula(), vp.nomQuizz.getText(), preg[cont]);
                cont = cont + 1;
                acum = acum + 1;
                vp.ver.setVisible(true);
            }
            if (e.getSource() == vp.btnCerrar) {
                t.start();
                vp.ver.setVisible(false);
                vp.txtPregunta.setText(null);
                vp.txtRespuesta.setText(null);
                vp.txtCalificacion.setText(null);
                vp.txtPuntuacion.setText(null);
                vp.txtComentario.setText(null);
            }
            if (e.getSource() == vp.btnAtras) {
                int retro = - 1;
                int arr = acum + retro;
                String[] partir = temp[arr].split("~");
                vp.txtPregunta.setText(partir[0]);
                vp.txtRespuesta.setText(partir[1]);
                vp.txtPuntuacion.setText(partir[2]);
                vp.txtCalificacion.setText(partir[3]);
                if (partir[4].equals("/*null*/")) {
                    vp.txtComentario.setText("SIN COMENTARIOS O RETROALIMENTACIÓN.");
                } else {
                    vp.txtComentario.setText(partir[4]);
                }
                acum = acum - 1;
                cont = cont - 1;
                if (acum == 1) {
                    vp.btnAtras.setVisible(false);
                }
            }
            if (e.getSource() == vp.btnSiguiente) {
                String todo = vp.txtPregunta.getText() + "~" + vp.txtRespuesta.getText() + "~" + vp.txtPuntuacion.getText() + "~" + vp.txtCalificacion.getText() + "~" + vp.txtComentario.getText();
                temp[acum] = todo;
                ModVariablesAbierto varA = new ModVariablesAbierto();
                if (cont == Integer.parseInt(varP.getTotales()) || acum == Integer.parseInt(varP.getTotales())); else {
                    ModConsultasSQL.llenarRespMod(vp, varA, var.getMatricula(), vp.nomQuizz.getText(), preg[cont]);
                }
                if (cont + 1 == Integer.parseInt(varP.getTotales())) {
                    vp.btnSiguiente.setText("Terminar");
                    cont = cont + 1;
                    acum = acum + 1;
                } else {
                    vp.btnSiguiente.setText("Siguiente");
                    cont = cont + 1;
                    acum = acum + 1;
                }
                if (cont > Integer.parseInt(varP.getTotales())) {
                    t.start();
                    vp.ver.setVisible(false);
                    vp.txtPregunta.setText(null);
                    vp.txtRespuesta.setText(null);
                    vp.txtCalificacion.setText(null);
                    vp.txtPuntuacion.setText(null);
                    vp.txtComentario.setText(null);
                }
                if (acum > 1) {
                    vp.btnAtras.setVisible(true);
                }
            }
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
                                        Logger.getLogger(Controlador.CtrlPerfil.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Las contraseñas con coinciden.");
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
        vp.btnvalidar.setEnabled(true);
        vp.btnguardar.setEnabled(false);
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
                ModConsultasSQL.tablaRegistro(vp.tablaRegistro, varP, var.getMatricula(), "Empleado");
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
