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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlQuizzes implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesQuizzes var;
    private ModVariablesUsr varU;
    private VstQuizzes vq;

    public CtrlQuizzes(ModConsultasSQL con, ModVariablesQuizzes var, ModVariablesUsr varU, VstQuizzes vq) {
        this.con = con;
        this.var = var;
        this.varU = varU;
        this.vq = vq;

        this.vq.btnGuardar.addActionListener(this);
        this.vq.btnModificar.addActionListener(this);
        this.vq.btnEliminar.addActionListener(this);
        this.vq.btnNuevo.addActionListener(this);
        this.vq.btnAgrPreg.addActionListener(this);
    }

    public void iniciar() {
        vq.setTitle("Quizzes");
        ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
        vq.setLocationRelativeTo(null);
        vq.matricula.setText(varU.getMatricula());
        limpiar();
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
        //vq.matricula.setText(varU.getMatricula());

        if (con.existeUsr(varU.getMatricula()) == 1) {
            if (e.getSource() == vq.btnGuardar) {
                if ("Permanente".equals(varU.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Acceso denegado.");
                    vq.setVisible(false);
                    variables();
                } else {
                    if (vq.txtNombre.getText().equals("") || vq.txtDescripcion.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Completa los campos de escritura.");
                    } else {
                        if (vq.comboLimHoras.getSelectedItem().toString().equals("Horas:") || vq.comboLimMinutos.getSelectedItem().toString().equals("Minutos:")) {
                            JOptionPane.showMessageDialog(null, "Aún hay un campo sin seleccionar.");
                        } else {
                            if (con.existequizz(vq.txtNombre.getText()) == 0) {
                                var.setNombre(vq.txtNombre.getText());
                                var.setDescripcion(vq.txtDescripcion.getText());
                                var.setP_totales(vq.comboNumPreg.getSelectedItem().toString());
                                var.setP_actuales("0");
                                var.setStatus("Deshabilitado");
                                var.setIntentos(vq.comboIntentos.getSelectedItem().toString());

                                if (vq.comboIntentos.getSelectedItem().toString().equals("1")) {
                                    var.setMod_calif("Primer intento");
                                } else {
                                    var.setMod_calif(vq.comboModCalf.getSelectedItem().toString());
                                }

                                var.setTiempo(vq.comboLimHoras.getSelectedItem().toString() + ":" + vq.comboLimMinutos.getSelectedItem().toString() + ":00");

                                var.setF_registro(fechaDate.format(date));
                                var.setF_activacion("nuevo");

                                if (con.Rquizzes(var)) {
                                    JOptionPane.showMessageDialog(null, "Quizz guardado exitosamente.");
                                    ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);

                                    ModVariablesReg varR = new ModVariablesReg();
                                    String tipo = "Administrador";
                                    String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                    String que = "Se agregó un nuevo Quizz: " + vq.txtNombre.getText();
                                    String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                    String comp = varU.getMatricula();
                                    if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                    limpiar();
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se pudo guardar el Quizz.");
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Ya existe un Quizz con ese nombre.");
                            }
                        }
                    }
                }
            }

            if (e.getSource() == vq.btnModificar) {
                if ("Permanente".equals(varU.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Acceso denegado.");
                    vq.setVisible(false);
                    variables();
                } else {
                    if (vq.txtNombre.getText().equals("") || vq.txtDescripcion.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Completa los campos de escritura.");
                    } else {
                        if (vq.comboLimHoras.getSelectedItem().toString().equals("Horas:") || vq.comboLimMinutos.getSelectedItem().toString().equals("Minutos:")) {
                            JOptionPane.showMessageDialog(null, "Aún hay un campo sin seleccionar.");
                        } else {
                            if (vq.checkActivar.isSelected() == true) {
                                JOptionPane.showMessageDialog(null, "Debe de desactivar el Quizz para la modificación.");
                            } else {
                                if (vq.checkCamName.isSelected() == false) { //No cambiar nombre, el cambio se puede hacer tomando referencia id o nombre
                                    PreparedStatement ps = null;
                                    try {

                                        ModConexion objCon = new ModConexion();
                                        Connection conn = objCon.getConexion();
                                        ps = conn.prepareStatement("UPDATE quizzes SET nombre = ?, descripcion = ?, p_totales = ?, intentos = ?, "
                                                + "mod_calif = ?, tiempo = ? WHERE id = '" + vq.id.getText() + "'");

                                        ps.setString(1, vq.txtNombre.getText());
                                        ps.setString(2, vq.txtDescripcion.getText());
                                        ps.setString(3, vq.comboNumPreg.getSelectedItem().toString());
                                        ps.setString(4, vq.comboIntentos.getSelectedItem().toString());

                                        if (vq.comboIntentos.getSelectedItem().toString().equals("1")) {
                                            ps.setString(5, "Primer intento");
                                        } else {
                                            ps.setString(5, vq.comboModCalf.getSelectedItem().toString());
                                        }

                                        ps.setString(6, vq.comboLimHoras.getSelectedItem().toString() + ":" + vq.comboLimMinutos.getSelectedItem().toString() + ":00");

                                        ps.execute();

                                        JOptionPane.showMessageDialog(null, "Modificación completa.");
                                        ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);

                                        ModVariablesReg varR = new ModVariablesReg();
                                        String tipo = "Administrador";
                                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                        String que = "Se modificó el Quizz: " + vq.txtNombre.getText();
                                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                        String comp = varU.getMatricula();
                                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                        limpiar();

                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                        Logger.getLogger(CtrlQuizzes.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                } else { //Cambiar nombre, el cambio se puede hacer tomando referencia id
                                    if (con.existequizz(vq.txtNombre.getText()) == 0) {
                                        PreparedStatement ps = null;
                                        try {

                                            ModConexion objCon = new ModConexion();
                                            Connection conn = objCon.getConexion();
                                            ps = conn.prepareStatement("UPDATE quizzes SET nombre = ?, descripcion = ?, p_totales = ?, intentos = ?, "
                                                    + "mod_calif = ?, tiempo = ? WHERE id = '" + vq.id.getText() + "'");

                                            ps.setString(1, vq.txtNombre.getText());
                                            ps.setString(2, vq.txtDescripcion.getText());
                                            ps.setString(3, vq.comboNumPreg.getSelectedItem().toString());
                                            ps.setString(4, vq.comboIntentos.getSelectedItem().toString());
                                            if (vq.comboIntentos.getSelectedItem().toString().equals("1")) {
                                                ps.setString(5, "Primer intento");
                                            } else {
                                                ps.setString(5, vq.comboModCalf.getSelectedItem().toString());
                                            }
                                            ps.setString(6, vq.comboLimHoras.getSelectedItem().toString() + ":" + vq.comboLimMinutos.getSelectedItem().toString() + ":00");

                                            ps.execute();

                                            JOptionPane.showMessageDialog(null, "Modificación completa.");
                                            ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);

                                            ModVariablesReg varR = new ModVariablesReg();
                                            String tipo = "Administrador";
                                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                            String que = "Se modificó el Quizz: " + vq.txtNombre.getText();
                                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                            String comp = varU.getMatricula();
                                            if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                                            limpiar();

                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(null, "No se pudo realizar la modificación.");
                                            Logger.getLogger(CtrlQuizzes.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Ya existe un Quizz con ese nombre.");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (e.getSource() == vq.btnEliminar) {
                if ("Permanente".equals(varU.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Acceso denegado.");
                    vq.setVisible(false);
                    variables();
                } else {
                    PreparedStatement ps = null, ps1 = null;
                    ResultSet rs = null;

                    try {
                        ModConexion objCon = new ModConexion();
                        Connection conn = objCon.getConexion();

                        ps = conn.prepareStatement("DELETE FROM quizzes WHERE id = ?");
                        ps.setString(1, vq.id.getText());
                        ps.execute();

                        ps1 = conn.prepareStatement("SELECT * FROM preguntas");
                        rs = ps1.executeQuery();

                        ResultSetMetaData rsMd = rs.getMetaData();
                        int cantidadColumnas = rsMd.getColumnCount();

                        while (rs.next()) {
                            for (int i = 0; i < cantidadColumnas; i++) {
                                String sql1 = "DELETE FROM preguntas WHERE quizz = ?";

                                ps = conn.prepareStatement(sql1);
                                ps.setString(1, vq.id.getText());
                                ps.execute();
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Quizz eliminado.");
                        ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);

                        ModVariablesReg varR = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String que = "Se eliminó el Quizz: " + vq.txtNombre.getText();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = varU.getMatricula();
                        if (con.avisoAA(varR, tipo, quien, que, cuando, comp));

                        limpiar();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "El Quizz no pudo ser eliminado.");
                        Logger.getLogger(CtrlQuizzes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (e.getSource() == vq.btnNuevo) {
                if ("Permanente".equals(varU.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Acceso denegado.");
                    vq.setVisible(false);
                    variables();
                } else {
                    limpiar();
                }
            }

            if (e.getSource() == vq.btnAgrPreg) {
                if ("Permanente".equals(varU.getStatus())) {
                    JOptionPane.showMessageDialog(null, "Acceso denegado.");
                    vq.setVisible(false);
                    variables();
                } else {
                    if (vq.checkActivar.isSelected() == true) {
                        JOptionPane.showMessageDialog(null, "Para agregar una pregunta, modificarla o eliminarla,\n"
                                + "debe de desactivar el Quizz para los empleados.");
                    } else {
                        VstPreguntas vp = new VstPreguntas();
                        ModvariablesPreguntas varp = new ModvariablesPreguntas();
                        CtrlPreguntas ctrlP = new CtrlPreguntas(con, varp, var, varU, vp, vq);
                        ctrlP.iniciar();
                        vp.setVisible(true);
                        vp.txtNombre.setText(vq.txtNombre.getText());
                        vp.id.setText(vq.id.getText());
                        vp.actual.setText(vq.actual.getText());
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vq.setVisible(false);
            variables();
        }
    }

    public void limpiar() {
        vq.txtNombre.setText(null);
        vq.txtDescripcion.setText(null);
        vq.comboNumPreg.setSelectedItem("1");
        vq.comboIntentos.setSelectedItem("1");
        vq.comboModCalf.setSelectedItem(null);
        vq.comboLimHoras.setSelectedItem("Horas:");
        vq.comboLimMinutos.setSelectedItem("Minutos:");

        vq.txtNombre.setEditable(true);
        vq.checkCamName.setVisible(false);
        vq.checkActivar.setVisible(false);
        vq.btnGuardar.setVisible(true);
        vq.btnModificar.setVisible(false);
        vq.btnEliminar.setVisible(false);
        vq.btnAgrPreg.setVisible(false);

//        vq.txtNombre.setEnabled(false);
//        vq.txtDescripcion.setEnabled(false);
//        vq.comboNumPreg.setEnabled(false);
//        vq.comboIntentos.setEnabled(false);
//        vq.comboModCalf.setEnabled(false);
//        vq.comboLimHoras.setEnabled(false);
//        vq.comboLimMinutos.setEnabled(false);
//        vq.btnGuardar.setEnabled(false);
//        vq.btnModificar.setEnabled(false);
//        vq.checkCamName.setEnabled(false);
//        vq.checkCamName.setVisible(false);
//        vq.checkActivar.setVisible(false);
    }

    public static void tiempo(JComboBox ComboLimTimeAgrQuizz, JComboBox ComboTimeAgrQuizz) {
        ComboLimTimeAgrQuizz.removeAllItems();
        ComboLimTimeAgrQuizz.addItem("Horas:");
        ComboLimTimeAgrQuizz.addItem("01");
        ComboLimTimeAgrQuizz.addItem("02");
        ComboLimTimeAgrQuizz.addItem("03");
        ComboLimTimeAgrQuizz.addItem("04");
        ComboLimTimeAgrQuizz.addItem("05");
        ComboLimTimeAgrQuizz.addItem("06");
        ComboLimTimeAgrQuizz.addItem("07");
        ComboLimTimeAgrQuizz.addItem("08");
        ComboLimTimeAgrQuizz.addItem("09");
        ComboLimTimeAgrQuizz.addItem("10");
        ComboLimTimeAgrQuizz.addItem("11");
        ComboLimTimeAgrQuizz.addItem("12");
        ComboLimTimeAgrQuizz.addItem("13");
        ComboLimTimeAgrQuizz.addItem("14");
        ComboLimTimeAgrQuizz.addItem("15");
        ComboLimTimeAgrQuizz.addItem("16");
        ComboLimTimeAgrQuizz.addItem("17");
        ComboLimTimeAgrQuizz.addItem("18");
        ComboLimTimeAgrQuizz.addItem("19");
        ComboLimTimeAgrQuizz.addItem("20");
        ComboLimTimeAgrQuizz.addItem("21");
        ComboLimTimeAgrQuizz.addItem("22");
        ComboLimTimeAgrQuizz.addItem("23");
        ComboLimTimeAgrQuizz.addItem("24");

        ComboTimeAgrQuizz.addItem("Minutos:");
        ComboTimeAgrQuizz.addItem("00");
        ComboTimeAgrQuizz.addItem("01");
        ComboTimeAgrQuizz.addItem("02");
        ComboTimeAgrQuizz.addItem("03");
        ComboTimeAgrQuizz.addItem("04");
        ComboTimeAgrQuizz.addItem("05");
        ComboTimeAgrQuizz.addItem("06");
        ComboTimeAgrQuizz.addItem("07");
        ComboTimeAgrQuizz.addItem("08");
        ComboTimeAgrQuizz.addItem("09");
        ComboTimeAgrQuizz.addItem("10");
        ComboTimeAgrQuizz.addItem("11");
        ComboTimeAgrQuizz.addItem("12");
        ComboTimeAgrQuizz.addItem("13");
        ComboTimeAgrQuizz.addItem("14");
        ComboTimeAgrQuizz.addItem("15");
        ComboTimeAgrQuizz.addItem("16");
        ComboTimeAgrQuizz.addItem("17");
        ComboTimeAgrQuizz.addItem("18");
        ComboTimeAgrQuizz.addItem("19");
        ComboTimeAgrQuizz.addItem("20");
        ComboTimeAgrQuizz.addItem("21");
        ComboTimeAgrQuizz.addItem("22");
        ComboTimeAgrQuizz.addItem("23");
        ComboTimeAgrQuizz.addItem("24");
        ComboTimeAgrQuizz.addItem("25");
        ComboTimeAgrQuizz.addItem("26");
        ComboTimeAgrQuizz.addItem("27");
        ComboTimeAgrQuizz.addItem("28");
        ComboTimeAgrQuizz.addItem("29");
        ComboTimeAgrQuizz.addItem("30");
        ComboTimeAgrQuizz.addItem("31");
        ComboTimeAgrQuizz.addItem("32");
        ComboTimeAgrQuizz.addItem("33");
        ComboTimeAgrQuizz.addItem("34");
        ComboTimeAgrQuizz.addItem("35");
        ComboTimeAgrQuizz.addItem("36");
        ComboTimeAgrQuizz.addItem("37");
        ComboTimeAgrQuizz.addItem("38");
        ComboTimeAgrQuizz.addItem("39");
        ComboTimeAgrQuizz.addItem("40");
        ComboTimeAgrQuizz.addItem("41");
        ComboTimeAgrQuizz.addItem("42");
        ComboTimeAgrQuizz.addItem("43");
        ComboTimeAgrQuizz.addItem("44");
        ComboTimeAgrQuizz.addItem("45");
        ComboTimeAgrQuizz.addItem("46");
        ComboTimeAgrQuizz.addItem("47");
        ComboTimeAgrQuizz.addItem("48");
        ComboTimeAgrQuizz.addItem("49");
        ComboTimeAgrQuizz.addItem("50");
        ComboTimeAgrQuizz.addItem("51");
        ComboTimeAgrQuizz.addItem("52");
        ComboTimeAgrQuizz.addItem("53");
        ComboTimeAgrQuizz.addItem("54");
        ComboTimeAgrQuizz.addItem("55");
        ComboTimeAgrQuizz.addItem("56");
        ComboTimeAgrQuizz.addItem("57");
        ComboTimeAgrQuizz.addItem("58");
        ComboTimeAgrQuizz.addItem("59");
    }

    public static void Año(JComboBox ComboAñoAgrQuizz) {
        ComboAñoAgrQuizz.addItem("Año:");
        ComboAñoAgrQuizz.addItem("2018");
        ComboAñoAgrQuizz.addItem("2019");
        ComboAñoAgrQuizz.addItem("2020");
        ComboAñoAgrQuizz.addItem("2021");
        ComboAñoAgrQuizz.addItem("2022");
        ComboAñoAgrQuizz.addItem("2023");
        ComboAñoAgrQuizz.addItem("2024");
    }

    public static void Mes(JComboBox ComboMesAgrQuizz) {
        ComboMesAgrQuizz.addItem("Mes:");
        ComboMesAgrQuizz.addItem("01");
        ComboMesAgrQuizz.addItem("02");
        ComboMesAgrQuizz.addItem("03");
        ComboMesAgrQuizz.addItem("04");
        ComboMesAgrQuizz.addItem("05");
        ComboMesAgrQuizz.addItem("06");
        ComboMesAgrQuizz.addItem("07");
        ComboMesAgrQuizz.addItem("08");
        ComboMesAgrQuizz.addItem("09");
        ComboMesAgrQuizz.addItem("10");
        ComboMesAgrQuizz.addItem("11");
        ComboMesAgrQuizz.addItem("12");
    }

    public static void Fecha(JComboBox ComboAñoAgrQuizz, JComboBox ComboMesAgrQuizz, JComboBox ComboDiaAgrQuizz) {
        ComboDiaAgrQuizz.removeAllItems();
        if (ComboAñoAgrQuizz.getSelectedItem().toString().equals("2018") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2019")
                || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2020") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2021")
                || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2022") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2023")
                || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2024")) {
            if (ComboMesAgrQuizz.getSelectedItem().toString().equals("01") || ComboMesAgrQuizz.getSelectedItem().toString().equals("03")
                    || ComboMesAgrQuizz.getSelectedItem().toString().equals("05") || ComboMesAgrQuizz.getSelectedItem().toString().equals("06")
                    || ComboMesAgrQuizz.getSelectedItem().toString().equals("07") || ComboMesAgrQuizz.getSelectedItem().toString().equals("09")
                    || ComboMesAgrQuizz.getSelectedItem().toString().equals("12")) {
                ComboDiaAgrQuizz.addItem("Día:");
                ComboDiaAgrQuizz.addItem("01");
                ComboDiaAgrQuizz.addItem("02");
                ComboDiaAgrQuizz.addItem("03");
                ComboDiaAgrQuizz.addItem("04");
                ComboDiaAgrQuizz.addItem("05");
                ComboDiaAgrQuizz.addItem("06");
                ComboDiaAgrQuizz.addItem("07");
                ComboDiaAgrQuizz.addItem("08");
                ComboDiaAgrQuizz.addItem("09");
                ComboDiaAgrQuizz.addItem("10");
                ComboDiaAgrQuizz.addItem("11");
                ComboDiaAgrQuizz.addItem("12");
                ComboDiaAgrQuizz.addItem("13");
                ComboDiaAgrQuizz.addItem("14");
                ComboDiaAgrQuizz.addItem("15");
                ComboDiaAgrQuizz.addItem("16");
                ComboDiaAgrQuizz.addItem("17");
                ComboDiaAgrQuizz.addItem("18");
                ComboDiaAgrQuizz.addItem("19");
                ComboDiaAgrQuizz.addItem("20");
                ComboDiaAgrQuizz.addItem("21");
                ComboDiaAgrQuizz.addItem("22");
                ComboDiaAgrQuizz.addItem("23");
                ComboDiaAgrQuizz.addItem("24");
                ComboDiaAgrQuizz.addItem("25");
                ComboDiaAgrQuizz.addItem("26");
                ComboDiaAgrQuizz.addItem("27");
                ComboDiaAgrQuizz.addItem("28");
                ComboDiaAgrQuizz.addItem("29");
                ComboDiaAgrQuizz.addItem("30");
                ComboDiaAgrQuizz.addItem("31");

            } else if (ComboMesAgrQuizz.getSelectedItem().toString().equals("04") || ComboMesAgrQuizz.getSelectedItem().toString().equals("06")
                    || ComboMesAgrQuizz.getSelectedItem().toString().equals("09") || ComboMesAgrQuizz.getSelectedItem().toString().equals("11")) {
                ComboDiaAgrQuizz.addItem("Día:");
                ComboDiaAgrQuizz.addItem("01");
                ComboDiaAgrQuizz.addItem("02");
                ComboDiaAgrQuizz.addItem("03");
                ComboDiaAgrQuizz.addItem("04");
                ComboDiaAgrQuizz.addItem("05");
                ComboDiaAgrQuizz.addItem("06");
                ComboDiaAgrQuizz.addItem("07");
                ComboDiaAgrQuizz.addItem("08");
                ComboDiaAgrQuizz.addItem("09");
                ComboDiaAgrQuizz.addItem("10");
                ComboDiaAgrQuizz.addItem("11");
                ComboDiaAgrQuizz.addItem("12");
                ComboDiaAgrQuizz.addItem("13");
                ComboDiaAgrQuizz.addItem("14");
                ComboDiaAgrQuizz.addItem("15");
                ComboDiaAgrQuizz.addItem("16");
                ComboDiaAgrQuizz.addItem("17");
                ComboDiaAgrQuizz.addItem("18");
                ComboDiaAgrQuizz.addItem("19");
                ComboDiaAgrQuizz.addItem("20");
                ComboDiaAgrQuizz.addItem("21");
                ComboDiaAgrQuizz.addItem("22");
                ComboDiaAgrQuizz.addItem("23");
                ComboDiaAgrQuizz.addItem("24");
                ComboDiaAgrQuizz.addItem("25");
                ComboDiaAgrQuizz.addItem("26");
                ComboDiaAgrQuizz.addItem("27");
                ComboDiaAgrQuizz.addItem("28");
                ComboDiaAgrQuizz.addItem("29");
                ComboDiaAgrQuizz.addItem("30");

            }
        }
        if (ComboAñoAgrQuizz.getSelectedItem().toString().equals("2018") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2019")
                || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2021") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2022")
                || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2023")) {
            if (ComboMesAgrQuizz.getSelectedItem().toString().equals("02")) {
                ComboDiaAgrQuizz.addItem("Día:");
                ComboDiaAgrQuizz.addItem("01");
                ComboDiaAgrQuizz.addItem("02");
                ComboDiaAgrQuizz.addItem("03");
                ComboDiaAgrQuizz.addItem("04");
                ComboDiaAgrQuizz.addItem("05");
                ComboDiaAgrQuizz.addItem("06");
                ComboDiaAgrQuizz.addItem("07");
                ComboDiaAgrQuizz.addItem("08");
                ComboDiaAgrQuizz.addItem("09");
                ComboDiaAgrQuizz.addItem("10");
                ComboDiaAgrQuizz.addItem("11");
                ComboDiaAgrQuizz.addItem("12");
                ComboDiaAgrQuizz.addItem("13");
                ComboDiaAgrQuizz.addItem("14");
                ComboDiaAgrQuizz.addItem("15");
                ComboDiaAgrQuizz.addItem("16");
                ComboDiaAgrQuizz.addItem("17");
                ComboDiaAgrQuizz.addItem("18");
                ComboDiaAgrQuizz.addItem("19");
                ComboDiaAgrQuizz.addItem("20");
                ComboDiaAgrQuizz.addItem("21");
                ComboDiaAgrQuizz.addItem("22");
                ComboDiaAgrQuizz.addItem("23");
                ComboDiaAgrQuizz.addItem("24");
                ComboDiaAgrQuizz.addItem("25");
                ComboDiaAgrQuizz.addItem("26");
                ComboDiaAgrQuizz.addItem("27");
                ComboDiaAgrQuizz.addItem("28");

            }
        } else if (ComboAñoAgrQuizz.getSelectedItem().toString().equals("2020") || ComboAñoAgrQuizz.getSelectedItem().toString().equals("2024")) {
            if (ComboMesAgrQuizz.getSelectedItem().toString().equals("02")) {
                ComboDiaAgrQuizz.addItem("Día:");
                ComboDiaAgrQuizz.addItem("01");
                ComboDiaAgrQuizz.addItem("02");
                ComboDiaAgrQuizz.addItem("03");
                ComboDiaAgrQuizz.addItem("04");
                ComboDiaAgrQuizz.addItem("05");
                ComboDiaAgrQuizz.addItem("06");
                ComboDiaAgrQuizz.addItem("07");
                ComboDiaAgrQuizz.addItem("08");
                ComboDiaAgrQuizz.addItem("09");
                ComboDiaAgrQuizz.addItem("10");
                ComboDiaAgrQuizz.addItem("11");
                ComboDiaAgrQuizz.addItem("12");
                ComboDiaAgrQuizz.addItem("13");
                ComboDiaAgrQuizz.addItem("14");
                ComboDiaAgrQuizz.addItem("15");
                ComboDiaAgrQuizz.addItem("16");
                ComboDiaAgrQuizz.addItem("17");
                ComboDiaAgrQuizz.addItem("18");
                ComboDiaAgrQuizz.addItem("19");
                ComboDiaAgrQuizz.addItem("20");
                ComboDiaAgrQuizz.addItem("21");
                ComboDiaAgrQuizz.addItem("22");
                ComboDiaAgrQuizz.addItem("23");
                ComboDiaAgrQuizz.addItem("24");
                ComboDiaAgrQuizz.addItem("25");
                ComboDiaAgrQuizz.addItem("26");
                ComboDiaAgrQuizz.addItem("27");
                ComboDiaAgrQuizz.addItem("28");
                ComboDiaAgrQuizz.addItem("29");

            }
        }
    }

    public static void modo(JComboBox comboModCalf, JComboBox comboIntentos) {
        comboModCalf.removeAllItems();
        if (comboIntentos.getSelectedItem().toString().equals("1")) {
            comboModCalf.setEnabled(false);
        } else {
            comboModCalf.addItem("Calificacion más alta");
            comboModCalf.addItem("Promedio de calificaciones");
            comboModCalf.setEnabled(true);
        }
    }

    public static void intentos(JComboBox comboIntentos) {
        comboIntentos.addItem("1");
        comboIntentos.addItem("2");
        comboIntentos.addItem("3");
        comboIntentos.addItem("4");
        comboIntentos.addItem("5");
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
}
