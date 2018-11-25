/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.Administrador.CtrlDocumentos;
import Controlador.Administrador.CtrlEmpleados;
import Controlador.Administrador.CtrlQuizzes;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesDoc;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.Administrador.VstDocumentos;
import Vista.Administrador.VstEmpleados;
import Vista.Administrador.VstQuizzes;
import Vista.VstBuscador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlBuscador implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr var;
    private VstBuscador vb;

    public CtrlBuscador(ModConsultasSQL cons, ModVariablesUsr var, VstBuscador vb) {
        this.cons = cons;
        this.var = var;
        this.vb = vb;

        this.vb.btnEfectuar.addActionListener(this);
    }

    public void iniciar() {
        vb.setTitle("Buscador.");
        vb.setLocationRelativeTo(null);
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
                vb.setVisible(false);
            } else {
                if (e.getSource() == vb.btnEfectuar) {
                    if (vb.TEMP.getText().equals("Administrador")) {
                        JOptionPane.showMessageDialog(null, "No se puede acceder al registro de un administrador.");
                    } else {
                        if (vb.txtFormat.getText().toUpperCase().equals("ABRIR") && vb.tipo.getSelectedItem().equals("Empleados")) {
                            boolean band = true;
                            ModVariablesReg varR = new ModVariablesReg();
                            VstEmpleados ve = new VstEmpleados();
                            CtrlEmpleados ctrlE = new CtrlEmpleados(cons, varR, var, ve);
                            ctrlE.iniciar();
                            //**************************************************
                            try {
                                PreparedStatement ps = null;
                                ResultSet rs = null;

                                ModConexion objCon = new ModConexion();
                                Connection con = objCon.getConexion();

                                ps = con.prepareStatement("SELECT matricula, nombre, ap_pat, ap_mat, tipo, correo, status FROM usuarios WHERE matricula = ?");
                                ps.setString(1, vb.txtNombre.getText());
                                rs = ps.executeQuery();

                                while (rs.next()) {
                                    ve.txtNombre.setText(rs.getString("nombre"));
                                    ve.txtApPat.setText(rs.getString("ap_pat"));
                                    ve.txtApMat.setText(rs.getString("ap_mat"));
                                    ve.txtMatricula.setText(rs.getString("matricula"));
                                    ve.txtMatricula.setEditable(false);

                                    ve.btnModificar.setVisible(true);
                                    ve.btnEliminar.setVisible(true);
                                    ve.checkCorreo.setVisible(true);
                                    ve.checkCorreo.setSelected(false);
                                    ve.txtCorreo.setEnabled(false);

                                    ve.btnAvances.setVisible(true);
                                    ve.btnAgregar.setVisible(false);
                                    ve.btnGenerar.setVisible(false);
                                    ve.btnReestablecer.setVisible(true);

                                    String string = rs.getString("correo");
                                    String[] parts = string.split("@");
                                    String part1 = parts[0];

                                    ve.txtCorreo.setText(part1);
                                    ve.email.setText(part1);
                                    ve.setVisible(true);
                                    band = false;
                                }
                                if (band == true) {
                                    ve.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "No se encontró.");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(VstEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                                ve.setVisible(false);
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
                            }
                            //**************************************************
                        }
                        if (vb.txtFormat.getText().toUpperCase().equals("ABRIR") && vb.tipo.getSelectedItem().equals("Documentos")) {
                            ModVariablesDoc varD = new ModVariablesDoc();
                            VstDocumentos vd = new VstDocumentos();
                            CtrlDocumentos ctrlD = new CtrlDocumentos(cons, varD, var, vd);
                            ctrlD.iniciar();
                            vd.setVisible(true);
                            //**************************************************
                            cons.visualizarMod(vd.tablaDocumentos, vb.txtNombre.getText());
                            //**************************************************
                        }
                        if (vb.txtFormat.getText().toUpperCase().equals("ABRIR") && vb.tipo.getSelectedItem().equals("Quizzes")) {
                            boolean band = true;
                            ModVariablesQuizzes varQ = new ModVariablesQuizzes();
                            VstQuizzes vq = new VstQuizzes();
                            CtrlQuizzes ctrlQ = new CtrlQuizzes(cons, varQ, var, vq);
                            ctrlQ.iniciar();
                            //**************************************************
                            try {
                                PreparedStatement ps = null;
                                ResultSet rs = null;

                                ModConexion objCon = new ModConexion();
                                Connection con = objCon.getConexion();

                                ps = con.prepareStatement("SELECT id, nombre, descripcion, p_totales, p_actuales, status, intentos, mod_calif, tiempo, f_registro, f_activacion FROM quizzes WHERE nombre = ?");
                                ps.setString(1, vb.txtNombre.getText());
                                rs = ps.executeQuery();

                                while (rs.next()) {
                                    varQ.setId(rs.getInt("id"));
                                    vq.id.setText(rs.getString("id"));
                                    vq.txtNombre.setText(rs.getString("nombre"));
                                    varQ.setNombre(rs.getString("nombre"));
                                    vq.txtDescripcion.setText(rs.getString("descripcion"));
                                    varQ.setDescripcion(rs.getString("descripcion"));
                                    vq.comboNumPreg.setSelectedItem(rs.getString("p_totales"));

                                    varQ.setP_totales(rs.getString("p_totales"));
                                    varQ.setP_actuales(rs.getString("p_actuales"));
                                    int valor = Integer.parseInt(rs.getString("p_totales")) - Integer.parseInt(rs.getString("p_actuales"));
                                    vq.p_totales.setText(valor + "");
                                    vq.actual.setText(rs.getString("p_actuales"));
                                    varQ.setActuales(rs.getInt("p_actuales"));
                                    varQ.setTotales(rs.getInt("p_totales"));
                                    varQ.setStatus(rs.getString("status"));

                                    vq.comboIntentos.setSelectedItem(rs.getString("intentos"));
                                    varQ.setIntentos(rs.getString("intentos"));
                                    vq.comboModCalf.setSelectedItem(rs.getString("mod_calif"));
                                    varQ.setMod_calif(rs.getString("mod_calif"));
                                    varQ.setTiempo(rs.getString("tiempo"));
                                    varQ.setF_registro(rs.getString("f_registro"));
                                    varQ.setF_activacion(rs.getString("f_activacion"));

                                    String stringf = varQ.getTiempo();
                                    String[] partF = stringf.split(":");
                                    String part1 = partF[0];
                                    String part2 = partF[1];

                                    vq.comboLimHoras.setSelectedItem(part1);
                                    vq.comboLimMinutos.setSelectedItem(part2);

                                    if ("Deshabilitado".equals(varQ.getStatus())) {
                                        vq.checkActivar.setVisible(true);
                                        vq.checkActivar.setSelected(false);
                                    } else if ("Habilitado".equals(varQ.getStatus())) {
                                        vq.checkActivar.setVisible(true);
                                        vq.checkActivar.setSelected(true);
                                    }

                                    vq.checkCamName.setVisible(true);
                                    vq.checkCamName.setSelected(false);
                                    vq.txtNombre.setEditable(false);
                                    vq.btnModificar.setVisible(true);
                                    vq.btnEliminar.setVisible(true);
                                    vq.btnGuardar.setVisible(false);
                                    vq.btnAgrPreg.setVisible(true);
                                    vq.btnNuevo.setVisible(true);

                                    vq.setVisible(true);
                                    band = false;
                                }
                                if (band == true) {
                                    vq.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "No se encontró.");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(VstEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                                vq.setVisible(false);
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
                            }
                            //**************************************************
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vb.setVisible(false);
        }
    }
}
