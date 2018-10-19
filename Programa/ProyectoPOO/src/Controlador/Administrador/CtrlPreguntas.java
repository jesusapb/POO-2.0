/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Administrador;

import Modelo.JavaApplication7;
import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesQuizzes;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
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
        this.vp.btnTodos.addActionListener(this);
    }

    public void iniciar() {
        vp.setTitle("Agregar preguntas.");
        vp.setLocationRelativeTo(null);
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
                        if (JavaApplication7.trueDouble(vp.puntos.getText()) == true) {
                            DecimalFormat form = new DecimalFormat("#.00");
                            double pts1 = Double.parseDouble(vp.puntos.getText());
                            vp.puntos.setText(form.format(pts1));
                            double pts2 = Double.parseDouble(vp.puntos.getText());
                            if (pts2 > 0 && pts2 <= 100) {
                                if (vp.abierto.isSelected() == true) {
                                    var.setQuizz(vp.id.getText());
                                    var.setPregunta(vp.txtPreguntas.getText());
                                    var.setTipo("abierto");
                                    var.setNum_resp("*/null/*");
                                    var.setPuntuacion_total(pts2 + "");
                                    var.setOp1("*/null/*");
                                    var.setP1("*/null/*");
                                    var.setOp2("*/null/*");
                                    var.setP2("*/null/*");
                                    var.setOp3("*/null/*");
                                    var.setP3("*/null/*");
                                    var.setOp4("*/null/*");
                                    var.setP4("*/null/*");

                                    int num = Integer.parseInt(vp.actual.getText()) + 1;

                                    if (con.rPreguntas(var)) {
                                        try {
                                            PreparedStatement ps = null;
                                            ModConexion objCon = new ModConexion();
                                            Connection con = objCon.getConexion();
                                            ps = con.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
                                            ps.setInt(1, num);
                                            ps.execute();

                                            JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
                                            vp.actual.setText(num + "");
                                            vq.actual.setText(num + "");
                                            ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
                                            limpiar();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
                                    }

                                } else if (vp.unico.isSelected() == true) {
                                    if (vp.D1.getText().equals("")) {
                                        if (vp.D2.getText().equals("")) {
                                            if (vp.D3.getText().equals("")) {

                                            } else {

                                            }
                                        } else {

                                        }
                                    } else {

                                    }
                                    if (JavaApplication7.trueDouble(vp.D1.getText()) == true) {
                                        if (JavaApplication7.trueDouble(vp.D2.getText()) == true) {
                                            if (JavaApplication7.trueDouble(vp.D3.getText()) == true) {
                                                double d1 = cambio(vp.D1.getText());
                                                vp.D1.setText(d1 + "");
                                                double d2 = cambio(vp.D2.getText());
                                                vp.D2.setText(d2 + "");
                                                double d3 = cambio(vp.D3.getText());
                                                vp.D3.setText(d3 + "");

                                                if ((pts2 + d1 + d2 + d3) >= 0) { //A partir de aqui va los setters
                                                    //*****************************************************************************************
                                                    JOptionPane.showMessageDialog(null, (pts2 + d1 + d2 + d3));
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Los puntos negativos no deben de ser\n"
                                                            + "mayor a la cantidad total de puntos del Quizz.");
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Valor invalido en la puntuación: " + vp.D3.getText());
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Valor invalido en la puntuación: " + vp.D2.getText());
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Valor invalido en la puntuación: " + vp.D1.getText());
                                    }
                                } else if (vp.multiple.isSelected() == true) {
                                } else {
                                    JOptionPane.showMessageDialog(null, "Debe de seleccionar el tipo de respuesta de la pregunta.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "La puntuación debe de ser mayor\n a cero o menor o igual a cien.");
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
                    }
                }

                if (e.getSource() == vp.btnEliminar) {
                }

                if (e.getSource() == vp.btnTodos) {
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
        vp.D1.setText(null);
        vp.D2.setText(null);
        vp.D3.setText(null);
        vp.txtR1.setText(null);
        vp.txtR2.setText(null);
        vp.txtR3.setText(null);
        vp.txtR4.setText(null);
        vp.txtD1.setText(null);
        vp.txtD2.setText(null);
        vp.txtD3.setText(null);
        inicio(vp.r2, vp.r3, vp.r4, false, vp.txtR1, vp.txtR2, vp.txtR3, vp.txtR4, vp.R1, vp.R2,
                vp.R3, vp.R4, vp.txtD1, vp.txtD2, vp.txtD3, vp.D1, vp.D2, vp.D3);
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
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        r2.setEnabled(a);
        r3.setEnabled(a);
        r4.setEnabled(a);

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
        D1.setEnabled(a);
        D2.setEnabled(a);
        D3.setEnabled(a);
    }

    public static void unico(JCheckBox unico, JCheckBox r2, JCheckBox r3, JCheckBox r4, boolean a, boolean b,
            JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        if (unico.isSelected() == true) {
            r2.setEnabled(a);
            r3.setEnabled(a);
            r4.setEnabled(a);

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
            D1.setEnabled(b);
            D2.setEnabled(b);
            D3.setEnabled(b);
        } else {
            inicio(r2, r3, r4, a, txtR1, txtR2, txtR3, txtR4, R1, R2, R3, R4, txtD1, txtD2, txtD3, D1, D2, D3);
        }
    }

    public static void multiple(JCheckBox multiple, JCheckBox r2, JCheckBox r3, JCheckBox r4, boolean a, boolean b,
            JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        if (multiple.isSelected() == true) {
            r2.setEnabled(b);
            r3.setEnabled(b);
            r4.setEnabled(b);

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
            D1.setEnabled(a);
            D2.setEnabled(a);
            D3.setEnabled(a);
        } else {
            r2.setEnabled(a);
            r3.setEnabled(a);
            r4.setEnabled(a);
        }
    }

    public static void r2(JCheckBox r2, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        if (r2.isSelected() == true) {
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
            txtD3.setEnabled(a);
            D1.setEnabled(b);
            D2.setEnabled(b);
            D3.setEnabled(a);
        } else {
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
            D1.setEnabled(a);
            D2.setEnabled(a);
            D3.setEnabled(a);
        }
    }

    public static void r3(JCheckBox r3, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        if (r3.isSelected() == true) {
            txtR1.setEnabled(b);
            txtR2.setEnabled(b);
            txtR3.setEnabled(b);
            txtR4.setEnabled(a);
            R1.setEnabled(b);
            R2.setEnabled(b);
            R3.setEnabled(b);
            R4.setEnabled(a);

            txtD1.setEnabled(b);
            txtD2.setEnabled(a);
            txtD3.setEnabled(a);
            D1.setEnabled(b);
            D2.setEnabled(a);
            D3.setEnabled(a);
        } else {
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
            D1.setEnabled(a);
            D2.setEnabled(a);
            D3.setEnabled(a);
        }
    }

    public static void r4(JCheckBox r4, boolean a, boolean b, JTextField txtR1, JTextField txtR2, JTextField txtR3, JTextField txtR4,
            JTextField R1, JTextField R2, JTextField R3, JTextField R4,
            JTextField txtD1, JTextField txtD2, JTextField txtD3, JTextField D1, JTextField D2, JTextField D3) {
        if (r4.isSelected() == true) {
            txtR1.setEnabled(b);
            txtR2.setEnabled(b);
            txtR3.setEnabled(b);
            txtR4.setEnabled(b);
            R1.setEnabled(b);
            R2.setEnabled(b);
            R3.setEnabled(b);
            R4.setEnabled(b);

            txtD1.setEnabled(a);
            txtD2.setEnabled(a);
            txtD3.setEnabled(a);
            D1.setEnabled(a);
            D2.setEnabled(a);
            D3.setEnabled(a);
        } else {
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
            D1.setEnabled(a);
            D2.setEnabled(a);
            D3.setEnabled(a);
        }
    }
}
