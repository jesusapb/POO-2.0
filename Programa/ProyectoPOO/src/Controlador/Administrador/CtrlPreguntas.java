/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Administrador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.Administrador.VstPreguntas;
import Vista.Administrador.VstQuizzes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Antonio
 */
public class CtrlPreguntas implements ActionListener {

    private ModConsultasSQL con;
    private ModvariablesPreguntas var;
    private ModVariablesQuizzes varQ;
    private ModVariablesUsr varU;
    private VstPreguntas vp;
    private VstQuizzes vq;

    public CtrlPreguntas(ModConsultasSQL con, ModvariablesPreguntas var, ModVariablesQuizzes varQ, ModVariablesUsr varU, VstPreguntas vp, VstQuizzes vq) {
        this.con = con;
        this.var = var;
        this.varQ = varQ;
        this.varU = varU;
        this.vp = vp;
        this.vq = vq;

        this.vp.btnGuardar.addActionListener(this);
        this.vp.btnEliminar.addActionListener(this);
        this.vp.btnModificar.addActionListener(this);
        this.vp.btnReestablecer.addActionListener(this);
    }

    public void iniciar() {
        vp.setTitle("Agregar preguntas.");
        vp.setLocationRelativeTo(null);
        String id = vq.id.getText();
        vp.matricula.setText(varU.getMatricula());
        ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

        int acum = Integer.parseInt(vq.p_totales.getText()) - 1;
        if (acum < 1) {
            vp.p_totales.setText("Mínimo de preguntas cumplido.");
            vq.p_totales.setText(acum + "");
        } else {
            vp.p_totales.setText(acum + "");
            vq.p_totales.setText(acum + "");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        varU.setMatricula(varU.getMatricula());
        varU.setDia(fechaDate.format(date));
        varU.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(varU);
        ModConsultasSQL.status(varU);

        if (con.existeUsr(varU.getMatricula()) == 1) {
            if ("Permanente".equals(varU.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vp.setVisible(false);
                variables();
            } else {
                if (e.getSource() == vp.btnGuardar) {
                    if (vp.txtPreguntas.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Debe de ingresar la pregunta.");
                    } else {
                        if (trueDouble(vp.puntos.getText()) == true) {
                            if (con.existePre(vp.txtPreguntas.getText()) == 0) {
                                double pts = cambio(vp.puntos.getText());
                                vp.puntos.setText(pts + "");
                                if (pts > 0 && pts <= 100) {
                                    if (vp.abierto.isSelected() == true) {
                                        var.setQuizz(vp.id.getText());
                                        var.setPregunta(vp.txtPreguntas.getText());
                                        var.setTipo("abierto");
                                        var.setNum_resp("*/null/*");
                                        var.setPuntuacion_total(pts + "");
                                        var.setResp1("*/null/*");
                                        var.setR1("*/null/*");
                                        var.setResp2("*/null/*");
                                        var.setR2("*/null/*");
                                        var.setResp3("*/null/*");
                                        var.setR3("*/null/*");
                                        var.setResp4("*/null/*");
                                        var.setR4("*/null/*");
                                        var.setDis1("*/null/*");
                                        var.setDis2("*/null/*");
                                        var.setDis3("*/null/*");
                                        var.setDis4("*/null/*");

                                        int num = Integer.parseInt(vp.actual.getText()) + 1;

                                        if (con.rPreguntas(var)) {
                                            try {
                                                PreparedStatement ps = null;
                                                ModConexion objCon = new ModConexion();
                                                Connection conn = objCon.getConexion();
                                                ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                                ps.setInt(1, num);
                                                ps.execute();

                                                JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                                int acum = Integer.parseInt(vp.p_totales.getText()) - 1;
                                                if (acum < 1) {
                                                    vp.p_totales.setText("Mínimo de preguntas cumplido.");
                                                    vq.p_totales.setText(acum + "");
                                                } else {
                                                    vp.p_totales.setText(acum + "");
                                                    vq.p_totales.setText(acum + "");
                                                }
                                                vp.actual.setText(num + "");
                                                vq.actual.setText(num + "");
                                                ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                                String id = vq.id.getText();
                                                ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                                                ModVariablesReg varR = new ModVariablesReg();
                                                String tipo = "Administrador";
                                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                String que = "Se agregó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                String comp = varU.getMatricula();
                                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                limpiar();
                                            } catch (SQLException ex) {
                                                Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                        }

                                    } else if (vp.unico.isSelected() == true) {
                                        if (vp.txtR1.getText().equals("") || vp.txtD1.getText().equals("")) {
                                            JOptionPane.showMessageDialog(null, "Debe de ingresar la respuesta y por lo menos,\n"
                                                    + "completar el primer campo de distractores.");
                                        } else {
                                            var.setQuizz(vp.id.getText());
                                            var.setPregunta(vp.txtPreguntas.getText());
                                            var.setTipo("unico");
                                            var.setNum_resp("1");
                                            var.setPuntuacion_total(pts + "");
                                            var.setResp1(vp.txtR1.getText());
                                            var.setR1(pts + "");
                                            var.setResp2("*/null/*");
                                            var.setR2("*/null/*");
                                            var.setResp3("*/null/*");
                                            var.setR3("*/null/*");
                                            var.setResp4("*/null/*");
                                            var.setR4("*/null/*");
                                            var.setDis1(vp.txtD1.getText());
                                            if (vp.txtD2.getText().equals("")) {
                                                var.setDis2("*/null/*");
                                            } else {
                                                var.setDis2(vp.txtD2.getText());
                                            }
                                            if (vp.txtD3.getText().equals("")) {
                                                var.setDis3("*/null/*");
                                            } else {
                                                var.setDis3(vp.txtD3.getText());
                                            }
                                            if (vp.txtD4.getText().equals("")) {
                                                var.setDis4("*/null/*");
                                            } else {
                                                var.setDis4(vp.txtD4.getText());
                                            }

                                            int num = Integer.parseInt(vp.actual.getText()) + 1;

                                            if (con.rPreguntas(var)) {
                                                try {
                                                    PreparedStatement ps = null;
                                                    ModConexion objCon = new ModConexion();
                                                    Connection conn = objCon.getConexion();
                                                    ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                                    ps.setInt(1, num);
                                                    ps.execute();

                                                    JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                                    int acum = Integer.parseInt(vp.p_totales.getText()) - 1;
                                                    if (acum < 1) {
                                                        vp.p_totales.setText("Mínimo de preguntas cumplido.");
                                                        vq.p_totales.setText(acum + "");
                                                    } else {
                                                        vp.p_totales.setText(acum + "");
                                                        vq.p_totales.setText(acum + "");
                                                    }
                                                    vp.actual.setText(num + "");
                                                    vq.actual.setText(num + "");
                                                    ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                                    String id = vq.id.getText();
                                                    ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                                                    ModVariablesReg varR = new ModVariablesReg();
                                                    String tipo = "Administrador";
                                                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                    String que = "Se agregó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                    String comp = varU.getMatricula();
                                                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                    limpiar();
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                            }
                                        }

                                    } else if (vp.multiple.isSelected() == true) {
                                        if (vp.r2.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            double r1 = cambio(vp.R1.getText());
                                                            double r2 = cambio(vp.R2.getText());
                                                            vp.R1.setText(r1 + "");
                                                            vp.R2.setText(r2 + "");
                                                            if (r1 + r2 == pts) {
                                                                var.setQuizz(vp.id.getText());
                                                                var.setPregunta(vp.txtPreguntas.getText());
                                                                var.setTipo("unico");
                                                                var.setNum_resp("1");
                                                                var.setPuntuacion_total(pts + "");
                                                                var.setResp1(vp.txtR1.getText());
                                                                var.setR1(r1 + "");
                                                                var.setResp2(vp.txtR2.getText());
                                                                var.setR2(r2 + "");
                                                                var.setResp3("*/null/*");
                                                                var.setR3("*/null/*");
                                                                var.setResp4("*/null/*");
                                                                var.setR4("*/null/*");
                                                                if (vp.txtD1.getText().equals("")) {
                                                                    var.setDis1("*/null/*");
                                                                } else {
                                                                    var.setDis1(vp.txtD1.getText());
                                                                }
                                                                if (vp.txtD2.getText().equals("")) {
                                                                    var.setDis2("*/null/*");
                                                                } else {
                                                                    var.setDis2(vp.txtD2.getText());
                                                                }
                                                                if (vp.txtD3.getText().equals("")) {
                                                                    var.setDis3("*/null/*");
                                                                } else {
                                                                    var.setDis3(vp.txtD3.getText());
                                                                }
                                                                if (vp.txtD4.getText().equals("")) {
                                                                    var.setDis4("*/null/*");
                                                                } else {
                                                                    var.setDis4(vp.txtD4.getText());
                                                                }

                                                                int num = Integer.parseInt(vp.actual.getText()) + 1;

                                                                if (con.rPreguntas(var)) {
                                                                    try {
                                                                        PreparedStatement ps = null;
                                                                        ModConexion objCon = new ModConexion();
                                                                        Connection conn = objCon.getConexion();
                                                                        ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                                                        ps.setInt(1, num);
                                                                        ps.execute();

                                                                        JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                                                        int acum = Integer.parseInt(vp.p_totales.getText()) - 1;
                                                                        if (acum < 1) {
                                                                            vp.p_totales.setText("Mínimo de preguntas cumplido.");
                                                                            vq.p_totales.setText(acum + "");
                                                                        } else {
                                                                            vp.p_totales.setText(acum + "");
                                                                            vq.p_totales.setText(acum + "");
                                                                        }
                                                                        vp.actual.setText(num + "");
                                                                        vq.actual.setText(num + "");
                                                                        ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                                                        String id = vq.id.getText();
                                                                        ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                                                                        ModVariablesReg varR = new ModVariablesReg();
                                                                        String tipo = "Administrador";
                                                                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                        String que = "Se agregó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                        String comp = varU.getMatricula();
                                                                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                        limpiar();
                                                                    } catch (SQLException ex) {
                                                                        Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                        + "debe de ser igual a los puntos totales de la pregunta.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else if (vp.r3.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            if (trueDouble(vp.R3.getText()) == true) {
                                                                double r1 = cambio(vp.R1.getText());
                                                                double r2 = cambio(vp.R2.getText());
                                                                double r3 = cambio(vp.R3.getText());
                                                                vp.R1.setText(r1 + "");
                                                                vp.R2.setText(r2 + "");
                                                                vp.R3.setText(r3 + "");
                                                                if (r1 + r2 + r3 == pts) {
                                                                    var.setQuizz(vp.id.getText());
                                                                    var.setPregunta(vp.txtPreguntas.getText());
                                                                    var.setTipo("unico");
                                                                    var.setNum_resp("1");
                                                                    var.setPuntuacion_total(pts + "");
                                                                    var.setResp1(vp.txtR1.getText());
                                                                    var.setR1(r1 + "");
                                                                    var.setResp2(vp.txtR2.getText());
                                                                    var.setR2(r2 + "");
                                                                    var.setResp3(vp.txtR3.getText());
                                                                    var.setR3(r3 + "");
                                                                    var.setResp4("*/null/*");
                                                                    var.setR4("*/null/*");
                                                                    if (vp.txtD1.getText().equals("")) {
                                                                        var.setDis1("*/null/*");
                                                                    } else {
                                                                        var.setDis1(vp.txtD1.getText());
                                                                    }
                                                                    if (vp.txtD2.getText().equals("")) {
                                                                        var.setDis2("*/null/*");
                                                                    } else {
                                                                        var.setDis2(vp.txtD2.getText());
                                                                    }
                                                                    if (vp.txtD3.getText().equals("")) {
                                                                        var.setDis3("*/null/*");
                                                                    } else {
                                                                        var.setDis3(vp.txtD3.getText());
                                                                    }
                                                                    if (vp.txtD4.getText().equals("")) {
                                                                        var.setDis4("*/null/*");
                                                                    } else {
                                                                        var.setDis4(vp.txtD4.getText());
                                                                    }

                                                                    int num = Integer.parseInt(vp.actual.getText()) + 1;

                                                                    if (con.rPreguntas(var)) {
                                                                        try {
                                                                            PreparedStatement ps = null;
                                                                            ModConexion objCon = new ModConexion();
                                                                            Connection conn = objCon.getConexion();
                                                                            ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                                                            ps.setInt(1, num);
                                                                            ps.execute();

                                                                            JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                                                            int acum = Integer.parseInt(vp.p_totales.getText()) - 1;
                                                                            if (acum < 1) {
                                                                                vp.p_totales.setText("Mínimo de preguntas cumplido.");
                                                                                vq.p_totales.setText(acum + "");
                                                                            } else {
                                                                                vp.p_totales.setText(acum + "");
                                                                                vq.p_totales.setText(acum + "");
                                                                            }
                                                                            vp.actual.setText(num + "");
                                                                            vq.actual.setText(num + "");
                                                                            ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                                                            String id = vq.id.getText();
                                                                            ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                                                                            ModVariablesReg varR = new ModVariablesReg();
                                                                            String tipo = "Administrador";
                                                                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                            String que = "Se agregó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                            String comp = varU.getMatricula();
                                                                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                            limpiar();
                                                                        } catch (SQLException ex) {
                                                                            Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                                                        }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                            + "debe de ser igual a los puntos totales de la pregunta.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else if (vp.r4.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("") || vp.txtR4.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("") || vp.R4.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            if (trueDouble(vp.R3.getText()) == true) {
                                                                if (trueDouble(vp.R1.getText()) == true) {
                                                                    double r1 = cambio(vp.R1.getText());
                                                                    double r2 = cambio(vp.R2.getText());
                                                                    double r3 = cambio(vp.R3.getText());
                                                                    double r4 = cambio(vp.R4.getText());
                                                                    vp.R1.setText(r1 + "");
                                                                    vp.R2.setText(r2 + "");
                                                                    vp.R3.setText(r3 + "");
                                                                    vp.R4.setText(r4 + "");
                                                                    if (r1 + r2 + r3 + r4 == pts) {
                                                                        var.setQuizz(vp.id.getText());
                                                                        var.setPregunta(vp.txtPreguntas.getText());
                                                                        var.setTipo("unico");
                                                                        var.setNum_resp("1");
                                                                        var.setPuntuacion_total(pts + "");
                                                                        var.setResp1(vp.txtR1.getText());
                                                                        var.setR1(r1 + "");
                                                                        var.setResp2(vp.txtR2.getText());
                                                                        var.setR2(r2 + "");
                                                                        var.setResp3(vp.txtR3.getText());
                                                                        var.setR3(r3 + "");
                                                                        var.setResp4(vp.txtR4.getText());
                                                                        var.setR4(r4 + "");
                                                                        if (vp.txtD1.getText().equals("")) {
                                                                            var.setDis1("*/null/*");
                                                                        } else {
                                                                            var.setDis1(vp.txtD1.getText());
                                                                        }
                                                                        if (vp.txtD2.getText().equals("")) {
                                                                            var.setDis2("*/null/*");
                                                                        } else {
                                                                            var.setDis2(vp.txtD2.getText());
                                                                        }
                                                                        if (vp.txtD3.getText().equals("")) {
                                                                            var.setDis3("*/null/*");
                                                                        } else {
                                                                            var.setDis3(vp.txtD3.getText());
                                                                        }
                                                                        if (vp.txtD4.getText().equals("")) {
                                                                            var.setDis4("*/null/*");
                                                                        } else {
                                                                            var.setDis4(vp.txtD4.getText());
                                                                        }

                                                                        int num = Integer.parseInt(vp.actual.getText()) + 1;

                                                                        if (con.rPreguntas(var)) {
                                                                            try {
                                                                                PreparedStatement ps = null;
                                                                                ModConexion objCon = new ModConexion();
                                                                                Connection conn = objCon.getConexion();
                                                                                ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                                                                ps.setInt(1, num);
                                                                                ps.execute();

                                                                                JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                                                                int acum = Integer.parseInt(vp.p_totales.getText()) - 1;
                                                                                if (acum < 1) {
                                                                                    vp.p_totales.setText("Mínimo de preguntas cumplido.");
                                                                                    vq.p_totales.setText(acum + "");
                                                                                } else {
                                                                                    vp.p_totales.setText(acum + "");
                                                                                    vq.p_totales.setText(acum + "");
                                                                                }
                                                                                vp.actual.setText(num + "");
                                                                                vq.actual.setText(num + "");
                                                                                ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                                                                String id = vq.id.getText();
                                                                                ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                                                                                ModVariablesReg varR = new ModVariablesReg();
                                                                                String tipo = "Administrador";
                                                                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                                String que = "Se agregó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                                String comp = varU.getMatricula();
                                                                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                                limpiar();
                                                                            } catch (SQLException ex) {
                                                                                Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                                                            }
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                                                        }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                                + "debe de ser igual a los puntos totales de la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La puntuación " + vp.R4.getText() + " no es válida.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Debe de seleccionar el numero de respuestas válidas.");
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Debe de seleccionar el tipo de respuesta de la pregunta.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "La puntuación debe de ser mayor\n a cero o menor o igual a cien.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Ya existe esa pregunta.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El valor de la puntuación no es válida.");
                        }
                    }
                }

                if (e.getSource() == vp.btnModificar) {
                    if (vp.txtPreguntas.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Debe de ingresar la pregunta.");
                    } else {
                        if (trueDouble(vp.puntos.getText()) == true) {
                            if (vp.modPre.isSelected() == true) {
                                if (con.existePre(vp.txtPreguntas.getText()) == 0) {
                                    double pts = cambio(vp.puntos.getText());
                                    vp.puntos.setText(pts + "");
                                    if (pts > 0 && pts <= 100) {
                                        if (vp.abierto.isSelected() == true) {
                                            var.setId(Integer.parseInt(vp.idP.getText()));
                                            var.setQuizz(vp.id.getText());
                                            var.setPregunta(vp.txtPreguntas.getText());
                                            var.setTipo("abierto");
                                            var.setNum_resp("*/null/*");
                                            var.setPuntuacion_total(pts + "");
                                            var.setResp1("*/null/*");
                                            var.setR1("*/null/*");
                                            var.setResp2("*/null/*");
                                            var.setR2("*/null/*");
                                            var.setResp3("*/null/*");
                                            var.setR3("*/null/*");
                                            var.setResp4("*/null/*");
                                            var.setR4("*/null/*");
                                            var.setDis1("*/null/*");
                                            var.setDis2("*/null/*");
                                            var.setDis3("*/null/*");
                                            var.setDis4("*/null/*");

                                            if (con.mPreguntas(var)) {
                                                JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                String ident = vp.id.getText();
                                                ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                ModVariablesReg varR = new ModVariablesReg();
                                                String tipo = "Administrador";
                                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                String comp = varU.getMatricula();
                                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                limpiar();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                            }

                                        } else if (vp.unico.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtD1.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de ingresar la respuesta y por lo menos,\n"
                                                        + "completar el primer campo de distractores.");
                                            } else {
                                                var.setId(Integer.parseInt(vp.idP.getText()));
                                                var.setQuizz(vp.id.getText());
                                                var.setPregunta(vp.txtPreguntas.getText());
                                                var.setTipo("unico");
                                                var.setNum_resp("1");
                                                var.setPuntuacion_total(pts + "");
                                                var.setResp1(vp.txtR1.getText());
                                                var.setR1(pts + "");
                                                var.setResp2("*/null/*");
                                                var.setR2("*/null/*");
                                                var.setResp3("*/null/*");
                                                var.setR3("*/null/*");
                                                var.setResp4("*/null/*");
                                                var.setR4("*/null/*");
                                                var.setDis1(vp.txtD1.getText());
                                                if (vp.txtD2.getText().equals("")) {
                                                    var.setDis2("*/null/*");
                                                } else {
                                                    var.setDis2(vp.txtD2.getText());
                                                }
                                                if (vp.txtD3.getText().equals("")) {
                                                    var.setDis3("*/null/*");
                                                } else {
                                                    var.setDis3(vp.txtD3.getText());
                                                }
                                                if (vp.txtD4.getText().equals("")) {
                                                    var.setDis4("*/null/*");
                                                } else {
                                                    var.setDis4(vp.txtD4.getText());
                                                }

                                                if (con.mPreguntas(var)) {
                                                    JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                    String ident = vp.id.getText();
                                                    ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                    ModVariablesReg varR = new ModVariablesReg();
                                                    String tipo = "Administrador";
                                                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                    String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                    String comp = varU.getMatricula();
                                                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                    limpiar();
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                }
                                            }

                                        } else if (vp.multiple.isSelected() == true) {
                                            if (vp.r2.isSelected() == true) {
                                                if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                                } else {
                                                    if (vp.R1.getText().equals("") || vp.R2.getText().equals("")) {
                                                        JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                    } else {
                                                        if (trueDouble(vp.R1.getText()) == true) {
                                                            if (trueDouble(vp.R2.getText()) == true) {
                                                                double r1 = cambio(vp.R1.getText());
                                                                double r2 = cambio(vp.R2.getText());
                                                                vp.R1.setText(r1 + "");
                                                                vp.R2.setText(r2 + "");
                                                                if (r1 + r2 == pts) {
                                                                    var.setId(Integer.parseInt(vp.idP.getText()));
                                                                    var.setQuizz(vp.id.getText());
                                                                    var.setPregunta(vp.txtPreguntas.getText());
                                                                    var.setTipo("unico");
                                                                    var.setNum_resp("1");
                                                                    var.setPuntuacion_total(pts + "");
                                                                    var.setResp1(vp.txtR1.getText());
                                                                    var.setR1(r1 + "");
                                                                    var.setResp2(vp.txtR2.getText());
                                                                    var.setR2(r2 + "");
                                                                    var.setResp3("*/null/*");
                                                                    var.setR3("*/null/*");
                                                                    var.setResp4("*/null/*");
                                                                    var.setR4("*/null/*");
                                                                    if (vp.txtD1.getText().equals("")) {
                                                                        var.setDis1("*/null/*");
                                                                    } else {
                                                                        var.setDis1(vp.txtD1.getText());
                                                                    }
                                                                    if (vp.txtD2.getText().equals("")) {
                                                                        var.setDis2("*/null/*");
                                                                    } else {
                                                                        var.setDis2(vp.txtD2.getText());
                                                                    }
                                                                    if (vp.txtD3.getText().equals("")) {
                                                                        var.setDis3("*/null/*");
                                                                    } else {
                                                                        var.setDis3(vp.txtD3.getText());
                                                                    }
                                                                    if (vp.txtD4.getText().equals("")) {
                                                                        var.setDis4("*/null/*");
                                                                    } else {
                                                                        var.setDis4(vp.txtD4.getText());
                                                                    }

                                                                    if (con.mPreguntas(var)) {
                                                                        JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                        String ident = vp.id.getText();
                                                                        ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                        ModVariablesReg varR = new ModVariablesReg();
                                                                        String tipo = "Administrador";
                                                                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                        String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                        String comp = varU.getMatricula();
                                                                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                        limpiar();
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                            + "debe de ser igual a los puntos totales de la pregunta.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                        }
                                                    }
                                                }

                                            } else if (vp.r3.isSelected() == true) {
                                                if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                                } else {
                                                    if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("")) {
                                                        JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                    } else {
                                                        if (trueDouble(vp.R1.getText()) == true) {
                                                            if (trueDouble(vp.R2.getText()) == true) {
                                                                if (trueDouble(vp.R3.getText()) == true) {
                                                                    double r1 = cambio(vp.R1.getText());
                                                                    double r2 = cambio(vp.R2.getText());
                                                                    double r3 = cambio(vp.R3.getText());
                                                                    vp.R1.setText(r1 + "");
                                                                    vp.R2.setText(r2 + "");
                                                                    vp.R3.setText(r3 + "");
                                                                    if (r1 + r2 + r3 == pts) {
                                                                        var.setId(Integer.parseInt(vp.idP.getText()));
                                                                        var.setQuizz(vp.id.getText());
                                                                        var.setPregunta(vp.txtPreguntas.getText());
                                                                        var.setTipo("unico");
                                                                        var.setNum_resp("1");
                                                                        var.setPuntuacion_total(pts + "");
                                                                        var.setResp1(vp.txtR1.getText());
                                                                        var.setR1(r1 + "");
                                                                        var.setResp2(vp.txtR2.getText());
                                                                        var.setR2(r2 + "");
                                                                        var.setResp3(vp.txtR3.getText());
                                                                        var.setR3(r3 + "");
                                                                        var.setResp4("*/null/*");
                                                                        var.setR4("*/null/*");
                                                                        if (vp.txtD1.getText().equals("")) {
                                                                            var.setDis1("*/null/*");
                                                                        } else {
                                                                            var.setDis1(vp.txtD1.getText());
                                                                        }
                                                                        if (vp.txtD2.getText().equals("")) {
                                                                            var.setDis2("*/null/*");
                                                                        } else {
                                                                            var.setDis2(vp.txtD2.getText());
                                                                        }
                                                                        if (vp.txtD3.getText().equals("")) {
                                                                            var.setDis3("*/null/*");
                                                                        } else {
                                                                            var.setDis3(vp.txtD3.getText());
                                                                        }
                                                                        if (vp.txtD4.getText().equals("")) {
                                                                            var.setDis4("*/null/*");
                                                                        } else {
                                                                            var.setDis4(vp.txtD4.getText());
                                                                        }

                                                                        if (con.mPreguntas(var)) {
                                                                            JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                            String ident = vp.id.getText();
                                                                            ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                            ModVariablesReg varR = new ModVariablesReg();
                                                                            String tipo = "Administrador";
                                                                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                            String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                            String comp = varU.getMatricula();
                                                                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                            limpiar();
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                        }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                                + "debe de ser igual a los puntos totales de la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                        }
                                                    }
                                                }

                                            } else if (vp.r4.isSelected() == true) {
                                                if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("") || vp.txtR4.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                                } else {
                                                    if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("") || vp.R4.getText().equals("")) {
                                                        JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                    } else {
                                                        if (trueDouble(vp.R1.getText()) == true) {
                                                            if (trueDouble(vp.R2.getText()) == true) {
                                                                if (trueDouble(vp.R3.getText()) == true) {
                                                                    if (trueDouble(vp.R4.getText()) == true) {
                                                                        double r1 = cambio(vp.R1.getText());
                                                                        double r2 = cambio(vp.R2.getText());
                                                                        double r3 = cambio(vp.R3.getText());
                                                                        double r4 = cambio(vp.R4.getText());
                                                                        vp.R1.setText(r1 + "");
                                                                        vp.R2.setText(r2 + "");
                                                                        vp.R3.setText(r3 + "");
                                                                        vp.R4.setText(r4 + "");
                                                                        if (r1 + r2 + r3 + r4 == pts) {
                                                                            var.setId(Integer.parseInt(vp.idP.getText()));
                                                                            var.setQuizz(vp.id.getText());
                                                                            var.setPregunta(vp.txtPreguntas.getText());
                                                                            var.setTipo("unico");
                                                                            var.setNum_resp("1");
                                                                            var.setPuntuacion_total(pts + "");
                                                                            var.setResp1(vp.txtR1.getText());
                                                                            var.setR1(r1 + "");
                                                                            var.setResp2(vp.txtR2.getText());
                                                                            var.setR2(r2 + "");
                                                                            var.setResp3(vp.txtR3.getText());
                                                                            var.setR3(r3 + "");
                                                                            var.setResp4(vp.txtR4.getText());
                                                                            var.setR4(r4 + "");
                                                                            if (vp.txtD1.getText().equals("")) {
                                                                                var.setDis1("*/null/*");
                                                                            } else {
                                                                                var.setDis1(vp.txtD1.getText());
                                                                            }
                                                                            if (vp.txtD2.getText().equals("")) {
                                                                                var.setDis2("*/null/*");
                                                                            } else {
                                                                                var.setDis2(vp.txtD2.getText());
                                                                            }
                                                                            if (vp.txtD3.getText().equals("")) {
                                                                                var.setDis3("*/null/*");
                                                                            } else {
                                                                                var.setDis3(vp.txtD3.getText());
                                                                            }
                                                                            if (vp.txtD4.getText().equals("")) {
                                                                                var.setDis4("*/null/*");
                                                                            } else {
                                                                                var.setDis4(vp.txtD4.getText());
                                                                            }

                                                                            if (con.mPreguntas(var)) {
                                                                                JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                                String ident = vp.id.getText();
                                                                                ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                                ModVariablesReg varR = new ModVariablesReg();
                                                                                String tipo = "Administrador";
                                                                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                                String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                                String comp = varU.getMatricula();
                                                                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                                limpiar();
                                                                            } else {
                                                                                JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                            }
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                                    + "debe de ser igual a los puntos totales de la pregunta.");
                                                                        }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R4.getText() + " no es válida.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                        }
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Debe de seleccionar el numero de respuestas válidas.");
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Debe de seleccionar el tipo de respuesta de la pregunta.");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "La puntuación debe de ser mayor\n a cero o menor o igual a cien.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ya existe esa pregunta.");
                                }
                            } else {
                                double pts = cambio(vp.puntos.getText());
                                vp.puntos.setText(pts + "");
                                if (pts > 0 && pts <= 100) {
                                    if (vp.abierto.isSelected() == true) {
                                        var.setId(Integer.parseInt(vp.idP.getText()));
                                        var.setQuizz(vp.id.getText());
                                        var.setPregunta(vp.pregunt.getText());
                                        var.setTipo("abierto");
                                        var.setNum_resp("*/null/*");
                                        var.setPuntuacion_total(pts + "");
                                        var.setResp1("*/null/*");
                                        var.setR1("*/null/*");
                                        var.setResp2("*/null/*");
                                        var.setR2("*/null/*");
                                        var.setResp3("*/null/*");
                                        var.setR3("*/null/*");
                                        var.setResp4("*/null/*");
                                        var.setR4("*/null/*");
                                        var.setDis1("*/null/*");
                                        var.setDis2("*/null/*");
                                        var.setDis3("*/null/*");
                                        var.setDis4("*/null/*");

                                        if (con.mPreguntas(var)) {
                                            JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                            String ident = vp.id.getText();
                                            ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                            ModVariablesReg varR = new ModVariablesReg();
                                            String tipo = "Administrador";
                                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                            String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                            String comp = varU.getMatricula();
                                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                            limpiar();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                        }

                                    } else if (vp.unico.isSelected() == true) {
                                        if (vp.txtR1.getText().equals("") || vp.txtD1.getText().equals("")) {
                                            JOptionPane.showMessageDialog(null, "Debe de ingresar la respuesta y por lo menos,\n"
                                                    + "completar el primer campo de distractores.");
                                        } else {
                                            var.setId(Integer.parseInt(vp.idP.getText()));
                                            var.setQuizz(vp.id.getText());
                                            var.setPregunta(vp.pregunt.getText());
                                            var.setTipo("unico");
                                            var.setNum_resp("1");
                                            var.setPuntuacion_total(pts + "");
                                            var.setResp1(vp.txtR1.getText());
                                            var.setR1(pts + "");
                                            var.setResp2("*/null/*");
                                            var.setR2("*/null/*");
                                            var.setResp3("*/null/*");
                                            var.setR3("*/null/*");
                                            var.setResp4("*/null/*");
                                            var.setR4("*/null/*");
                                            var.setDis1(vp.txtD1.getText());
                                            if (vp.txtD2.getText().equals("")) {
                                                var.setDis2("*/null/*");
                                            } else {
                                                var.setDis2(vp.txtD2.getText());
                                            }
                                            if (vp.txtD3.getText().equals("")) {
                                                var.setDis3("*/null/*");
                                            } else {
                                                var.setDis3(vp.txtD3.getText());
                                            }
                                            if (vp.txtD4.getText().equals("")) {
                                                var.setDis4("*/null/*");
                                            } else {
                                                var.setDis4(vp.txtD4.getText());
                                            }

                                            if (con.mPreguntas(var)) {
                                                JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                String ident = vp.id.getText();
                                                ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                ModVariablesReg varR = new ModVariablesReg();
                                                String tipo = "Administrador";
                                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                String comp = varU.getMatricula();
                                                if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                limpiar();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                            }
                                        }

                                    } else if (vp.multiple.isSelected() == true) {
                                        if (vp.r2.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            double r1 = cambio(vp.R1.getText());
                                                            double r2 = cambio(vp.R2.getText());
                                                            vp.R1.setText(r1 + "");
                                                            vp.R2.setText(r2 + "");
                                                            if (r1 + r2 == pts) {
                                                                var.setId(Integer.parseInt(vp.idP.getText()));
                                                                var.setQuizz(vp.id.getText());
                                                                var.setPregunta(vp.pregunt.getText());
                                                                var.setTipo("unico");
                                                                var.setNum_resp("1");
                                                                var.setPuntuacion_total(pts + "");
                                                                var.setResp1(vp.txtR1.getText());
                                                                var.setR1(r1 + "");
                                                                var.setResp2(vp.txtR2.getText());
                                                                var.setR2(r2 + "");
                                                                var.setResp3("*/null/*");
                                                                var.setR3("*/null/*");
                                                                var.setResp4("*/null/*");
                                                                var.setR4("*/null/*");
                                                                if (vp.txtD1.getText().equals("")) {
                                                                    var.setDis1("*/null/*");
                                                                } else {
                                                                    var.setDis1(vp.txtD1.getText());
                                                                }
                                                                if (vp.txtD2.getText().equals("")) {
                                                                    var.setDis2("*/null/*");
                                                                } else {
                                                                    var.setDis2(vp.txtD2.getText());
                                                                }
                                                                if (vp.txtD3.getText().equals("")) {
                                                                    var.setDis3("*/null/*");
                                                                } else {
                                                                    var.setDis3(vp.txtD3.getText());
                                                                }
                                                                if (vp.txtD4.getText().equals("")) {
                                                                    var.setDis4("*/null/*");
                                                                } else {
                                                                    var.setDis4(vp.txtD4.getText());
                                                                }

                                                                if (con.mPreguntas(var)) {
                                                                    JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                    String ident = vp.id.getText();
                                                                    ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                    ModVariablesReg varR = new ModVariablesReg();
                                                                    String tipo = "Administrador";
                                                                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                    String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                    String comp = varU.getMatricula();
                                                                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                    limpiar();
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                        + "debe de ser igual a los puntos totales de la pregunta.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else if (vp.r3.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            if (trueDouble(vp.R3.getText()) == true) {
                                                                double r1 = cambio(vp.R1.getText());
                                                                double r2 = cambio(vp.R2.getText());
                                                                double r3 = cambio(vp.R3.getText());
                                                                vp.R1.setText(r1 + "");
                                                                vp.R2.setText(r2 + "");
                                                                vp.R3.setText(r3 + "");
                                                                if (r1 + r2 + r3 == pts) {
                                                                    var.setId(Integer.parseInt(vp.idP.getText()));
                                                                    var.setQuizz(vp.id.getText());
                                                                    var.setPregunta(vp.pregunt.getText());
                                                                    var.setTipo("unico");
                                                                    var.setNum_resp("1");
                                                                    var.setPuntuacion_total(pts + "");
                                                                    var.setResp1(vp.txtR1.getText());
                                                                    var.setR1(r1 + "");
                                                                    var.setResp2(vp.txtR2.getText());
                                                                    var.setR2(r2 + "");
                                                                    var.setResp3(vp.txtR3.getText());
                                                                    var.setR3(r3 + "");
                                                                    var.setResp4("*/null/*");
                                                                    var.setR4("*/null/*");
                                                                    if (vp.txtD1.getText().equals("")) {
                                                                        var.setDis1("*/null/*");
                                                                    } else {
                                                                        var.setDis1(vp.txtD1.getText());
                                                                    }
                                                                    if (vp.txtD2.getText().equals("")) {
                                                                        var.setDis2("*/null/*");
                                                                    } else {
                                                                        var.setDis2(vp.txtD2.getText());
                                                                    }
                                                                    if (vp.txtD3.getText().equals("")) {
                                                                        var.setDis3("*/null/*");
                                                                    } else {
                                                                        var.setDis3(vp.txtD3.getText());
                                                                    }
                                                                    if (vp.txtD4.getText().equals("")) {
                                                                        var.setDis4("*/null/*");
                                                                    } else {
                                                                        var.setDis4(vp.txtD4.getText());
                                                                    }

                                                                    if (con.mPreguntas(var)) {
                                                                        JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                        String ident = vp.id.getText();
                                                                        ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                        ModVariablesReg varR = new ModVariablesReg();
                                                                        String tipo = "Administrador";
                                                                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                        String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                        String comp = varU.getMatricula();
                                                                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                        limpiar();
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                            + "debe de ser igual a los puntos totales de la pregunta.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else if (vp.r4.isSelected() == true) {
                                            if (vp.txtR1.getText().equals("") || vp.txtR2.getText().equals("") || vp.txtR3.getText().equals("") || vp.txtR4.getText().equals("")) {
                                                JOptionPane.showMessageDialog(null, "Debe de escribir las respuestas");
                                            } else {
                                                if (vp.R1.getText().equals("") || vp.R2.getText().equals("") || vp.R3.getText().equals("") || vp.R4.getText().equals("")) {
                                                    JOptionPane.showMessageDialog(null, "Debe de asignales puntuación a cada respuesta.");
                                                } else {
                                                    if (trueDouble(vp.R1.getText()) == true) {
                                                        if (trueDouble(vp.R2.getText()) == true) {
                                                            if (trueDouble(vp.R3.getText()) == true) {
                                                                if (trueDouble(vp.R4.getText()) == true) {
                                                                    double r1 = cambio(vp.R1.getText());
                                                                    double r2 = cambio(vp.R2.getText());
                                                                    double r3 = cambio(vp.R3.getText());
                                                                    double r4 = cambio(vp.R4.getText());
                                                                    vp.R1.setText(r1 + "");
                                                                    vp.R2.setText(r2 + "");
                                                                    vp.R3.setText(r3 + "");
                                                                    vp.R4.setText(r4 + "");
                                                                    if (r1 + r2 + r3 + r4 == pts) {
                                                                        var.setId(Integer.parseInt(vp.idP.getText()));
                                                                        var.setQuizz(vp.id.getText());
                                                                        var.setPregunta(vp.pregunt.getText());
                                                                        var.setTipo("unico");
                                                                        var.setNum_resp("1");
                                                                        var.setPuntuacion_total(pts + "");
                                                                        var.setResp1(vp.txtR1.getText());
                                                                        var.setR1(r1 + "");
                                                                        var.setResp2(vp.txtR2.getText());
                                                                        var.setR2(r2 + "");
                                                                        var.setResp3(vp.txtR3.getText());
                                                                        var.setR3(r3 + "");
                                                                        var.setResp4(vp.txtR4.getText());
                                                                        var.setR4(r4 + "");
                                                                        if (vp.txtD1.getText().equals("")) {
                                                                            var.setDis1("*/null/*");
                                                                        } else {
                                                                            var.setDis1(vp.txtD1.getText());
                                                                        }
                                                                        if (vp.txtD2.getText().equals("")) {
                                                                            var.setDis2("*/null/*");
                                                                        } else {
                                                                            var.setDis2(vp.txtD2.getText());
                                                                        }
                                                                        if (vp.txtD3.getText().equals("")) {
                                                                            var.setDis3("*/null/*");
                                                                        } else {
                                                                            var.setDis3(vp.txtD3.getText());
                                                                        }
                                                                        if (vp.txtD4.getText().equals("")) {
                                                                            var.setDis4("*/null/*");
                                                                        } else {
                                                                            var.setDis4(vp.txtD4.getText());
                                                                        }

                                                                        if (con.mPreguntas(var)) {
                                                                            JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
                                                                            String ident = vp.id.getText();
                                                                            ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, ident);

                                                                            ModVariablesReg varR = new ModVariablesReg();
                                                                            String tipo = "Administrador";
                                                                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                                                            String que = "Se modificó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                                                                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                                                            String comp = varU.getMatricula();
                                                                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                                                            limpiar();
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null, "No se pudo modificar la pregunta.");
                                                                        }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "La suma de la puntuacion de cada respuesta debe\n"
                                                                                + "debe de ser igual a los puntos totales de la pregunta.");
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "La puntuación " + vp.R4.getText() + " no es válida.");
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "La puntuación " + vp.R3.getText() + " no es válida.");
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "La puntuación " + vp.R2.getText() + " no es válida.");
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "La puntuación " + vp.R1.getText() + " no es válida.");
                                                    }
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Debe de seleccionar el numero de respuestas válidas.");
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Debe de seleccionar el tipo de respuesta de la pregunta.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "La puntuación debe de ser mayor\n a cero o menor o igual a cien.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El valor de la puntuación no es válida.");
                        }
                    }
                }

                if (e.getSource() == vp.btnEliminar) {
                    int num = Integer.parseInt(vp.actual.getText()) - 1;
                    var.setId(Integer.parseInt(vp.idP.getText()));

                    if (con.ElimPregunta(var)) {
                        try {
                            PreparedStatement ps = null;
                            ModConexion objCon = new ModConexion();
                            Connection conn = objCon.getConexion();
                            ps = conn.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                            ps.setInt(1, num);
                            ps.execute();

                            JOptionPane.showMessageDialog(null, "Se eliminó exitosamente la pregunta.");
                            vp.actual.setText(num + "");
                            vq.actual.setText(num + "");
                            ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                            String id = vq.id.getText();
                            ModConsultasSQL.tablaPreg(vp.tablaPreguntas, var, id);

                            ModVariablesReg varR = new ModVariablesReg();
                            String tipo = "Administrador";
                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                            String que = "Se eliminó una nueva pregunta al Quizz: " + vp.txtNombre.getText();
                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                            String comp = varU.getMatricula();
                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                            limpiar();

                        } catch (SQLException ex) {
                            Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la pregunta.");
                    }
                }

                if (e.getSource() == vp.btnReestablecer) {
                    inicio(vp.r2, vp.r3, vp.r4, false, vp.txtR1, vp.txtR2, vp.txtR3, vp.txtR4, vp.R1, vp.R2, vp.R3, vp.R4, vp.txtD1, vp.txtD2, vp.txtD3, vp.txtD4);
                    vp.btnReestablecer.setVisible(false);
                    vp.btnEliminar.setVisible(false);
                    vp.btnModificar.setVisible(false);
                    vp.btnGuardar.setVisible(true);
                    limpiar();
                }
            }
        }
    }

    public static boolean trueDouble(String cadena) {
        boolean resultado;

        try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException e) {
            return false;
        }
        return resultado;
    }

    public void limpiar() {
        vp.modPre.setVisible(false);
        vp.modPre.setSelected(false);
        vp.txtPreguntas.setText(null);
        vp.abierto.setSelected(false);
        vp.unico.setSelected(false);
        vp.multiple.setSelected(false);
        vp.r2.setSelected(false);
        vp.r3.setSelected(false);
        vp.r4.setSelected(false);
        vp.puntos.setText(null);
        vp.R1.setText(null);
        vp.R2.setText(null);
        vp.R3.setText(null);
        vp.R4.setText(null);
        vp.txtR1.setText(null);
        vp.txtR2.setText(null);
        vp.txtR3.setText(null);
        vp.txtR4.setText(null);
        vp.txtD1.setText(null);
        vp.txtD2.setText(null);
        vp.txtD3.setText(null);
        vp.txtD4.setText(null);
        inicio(vp.r2, vp.r3, vp.r4, false, vp.txtR1, vp.txtR2, vp.txtR3, vp.txtR4, vp.R1, vp.R2,
                vp.R3, vp.R4, vp.txtD1, vp.txtD2, vp.txtD3, vp.txtD4);
    }

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

    public double cambio(String cadena) {
        DecimalFormat op = new DecimalFormat("#.00");
        double db = Double.parseDouble(cadena);
        String bd = op.format(db);
        double fin = Double.parseDouble(bd);

        return fin;
    }

    public static void inicio(JCheckBox r2, JCheckBox r3, JCheckBox r4, boolean a,
            JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        r2.setEnabled(a);
        r3.setEnabled(a);
        r4.setEnabled(a);
        r2.setSelected(a);
        r3.setSelected(a);
        r4.setSelected(a);

        txtR1.setEnabled(a);
        txtR2.setEnabled(a);
        txtR3.setEnabled(a);
        txtR4.setEnabled(a);
        R1.setEnabled(a);
        R2.setEnabled(a);
        R3.setEnabled(a);
        R4.setEnabled(a);
        txtR1.setText(null);
        txtR2.setText(null);
        txtR3.setText(null);
        txtR4.setText(null);
        R1.setText(null);
        R2.setText(null);
        R3.setText(null);
        R4.setText(null);

        txtD1.setEnabled(a);
        txtD2.setEnabled(a);
        txtD3.setEnabled(a);
        txtD4.setEnabled(a);
        txtD1.setText(null);
        txtD2.setText(null);
        txtD3.setText(null);
        txtD4.setText(null);
    }

    public static void unico(JCheckBox unico, JCheckBox r2, JCheckBox r3, JCheckBox r4, boolean a, boolean b,
            JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        if (unico.isSelected() == true) {
            r2.setEnabled(a);
            r3.setEnabled(a);
            r4.setEnabled(a);
            r2.setSelected(a);
            r3.setSelected(a);
            r4.setSelected(a);

            txtR1.setText(null);
            txtR2.setText(null);
            txtR3.setText(null);
            txtR4.setText(null);
            R1.setText(null);
            R2.setText(null);
            R3.setText(null);
            R4.setText(null);
            txtD1.setText(null);
            txtD2.setText(null);
            txtD3.setText(null);
            txtD4.setText(null);

            txtR1.setEnabled(b);
            txtR2.setEnabled(a);
            txtR3.setEnabled(a);
            txtR4.setEnabled(a);
            R1.setEnabled(a);
            R2.setEnabled(a);
            R3.setEnabled(a);
            R4.setEnabled(a);
            txtD1.setEnabled(b);
            txtD2.setEnabled(b);
            txtD3.setEnabled(b);
            txtD4.setEnabled(b);
        }
    }

    public static void multiple(JCheckBox multiple, JCheckBox r2, JCheckBox r3, JCheckBox r4, boolean a, boolean b,
            JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        if (multiple.isSelected() == true) {
            r2.setEnabled(b);
            r3.setEnabled(b);
            r4.setEnabled(b);
            r2.setSelected(a);
            r3.setSelected(a);
            r4.setSelected(a);

            txtR1.setText(null);
            txtR2.setText(null);
            txtR3.setText(null);
            txtR4.setText(null);
            R1.setText(null);
            R2.setText(null);
            R3.setText(null);
            R4.setText(null);
            txtD1.setText(null);
            txtD2.setText(null);
            txtD3.setText(null);
            txtD4.setText(null);

            txtR1.setEnabled(a);
            txtR2.setEnabled(a);
            txtR3.setEnabled(a);
            txtR4.setEnabled(a);
            R1.setEnabled(a);
            R2.setEnabled(a);
            R3.setEnabled(a);
            R4.setEnabled(a);
            txtD1.setEnabled(a);
            txtD2.setEnabled(a);
            txtD3.setEnabled(a);
            txtD4.setEnabled(a);
        } else {
            r2.setEnabled(a);
            r3.setEnabled(a);
            r4.setEnabled(a);
            r2.setSelected(a);
            r3.setSelected(a);
            r4.setSelected(a);
        }
    }

    public static void r2(JCheckBox r2, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        if (r2.isSelected() == true) {
            txtR1.setText(null);
            txtR2.setText(null);
            txtR3.setText(null);
            txtR4.setText(null);
            R1.setText(null);
            R2.setText(null);
            R3.setText(null);
            R4.setText(null);
            txtD1.setText(null);
            txtD2.setText(null);
            txtD3.setText(null);
            txtD4.setText(null);

            txtR1.setEnabled(b);
            txtR2.setEnabled(b);
            txtR3.setEnabled(a);
            txtR4.setEnabled(a);
            R1.setEnabled(b);
            R2.setEnabled(b);
            R3.setEnabled(a);
            R4.setEnabled(a);
            txtD1.setEnabled(b);
            txtD2.setEnabled(b);
            txtD3.setEnabled(b);
            txtD4.setEnabled(b);
        }
    }

    public static void r3(JCheckBox r3, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        if (r3.isSelected() == true) {
            txtR1.setText(null);
            txtR2.setText(null);
            txtR3.setText(null);
            txtR4.setText(null);
            R1.setText(null);
            R2.setText(null);
            R3.setText(null);
            R4.setText(null);
            txtD1.setText(null);
            txtD2.setText(null);
            txtD3.setText(null);
            txtD4.setText(null);

            txtR1.setEnabled(b);
            txtR2.setEnabled(b);
            txtR3.setEnabled(b);
            txtR4.setEnabled(a);
            R1.setEnabled(b);
            R2.setEnabled(b);
            R3.setEnabled(b);
            R4.setEnabled(a);
            txtD1.setEnabled(b);
            txtD2.setEnabled(b);
            txtD3.setEnabled(b);
            txtD4.setEnabled(b);
        }
    }

    public static void r4(JCheckBox r4, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField txtD4) {
        if (r4.isSelected() == true) {
            txtR1.setText(null);
            txtR2.setText(null);
            txtR3.setText(null);
            txtR4.setText(null);
            R1.setText(null);
            R2.setText(null);
            R3.setText(null);
            R4.setText(null);
            txtD1.setText(null);
            txtD2.setText(null);
            txtD3.setText(null);
            txtD4.setText(null);

            txtR1.setEnabled(b);
            txtR2.setEnabled(b);
            txtR3.setEnabled(b);
            txtR4.setEnabled(b);
            R1.setEnabled(b);
            R2.setEnabled(b);
            R3.setEnabled(b);
            R4.setEnabled(b);
            txtD1.setEnabled(b);
            txtD2.setEnabled(b);
            txtD3.setEnabled(b);
            txtD4.setEnabled(b);
        }
    }
}
