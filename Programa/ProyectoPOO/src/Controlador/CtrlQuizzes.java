
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.VstPreguntas;
import Vista.VstQuizzes;
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
 * Esta es la clase de quizzes, aquí ocurre la gestion de la ventana para
 * agregar quizzes, agregar preguntas y sus respuestas, activar o desactivar quizzes
 * entre otras funciones. 
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 29s/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlQuizzes implements ActionListener {

    private ModConsultasSQL con;
    private ModVariablesQuizzes var;
    private ModVariablesUsr varU;
    private VstQuizzes vq;

    /**
    * Es el contructor de la clase.
    * @param con es la clase donde estan almacenadas las funciones de consulta.
    * @param var es la clase donde estan almacenadas las variables de quizzes y para que sus datos sean almacenados.
    * @param varU es la clase que contiene las varibables utilizadas para el usuario que inicia la sesión y para que sus
    * datos sean almacenados. 
    * @param vq es la interfaz gráfica de quizzes. 
    */

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

    /**
    * Es el método que muestra la interfaz de quizzes con su respectiva tabla. 
    */ 

    public void iniciar() {
        vq.setTitle("Quizzes");
        ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
        vq.setLocationRelativeTo(null);
        vq.matricula.setText(varU.getMatricula());
        limpiar();
    }

    /**
    * Constructor encargado de recibir y ejecutar las acciones correspondientes 
    * a lo que va ocurriendo en la vista de Quizzes.
    * @param e es la variable encargada de recibir cada acción de los botones de la
    * interfaz gráfica. 
    */

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
            if (e.getSource() == vq.btnGuardar) {
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

            /**
            * Cuando el administrador selecciona modificar, no debe dejar ningun campo nulo.  
             */

            if (e.getSource() == vq.btnModificar) {
                if (vq.txtNombre.getText().equals("") || vq.txtDescripcion.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Completa los campos de escritura.");
                } else {
                    if (vq.comboLimHoras.getSelectedItem().toString().equals("Horas:") || vq.comboLimMinutos.getSelectedItem().toString().equals("Minutos:")) {
                        JOptionPane.showMessageDialog(null, "Aún hay un campo sin seleccionar.");
                    } else {
                        if (vq.checkActivar.isSelected() == true) {
                            JOptionPane.showMessageDialog(null, "Debe de desactivar el Quizz para la modificación.");
                        } else {
                            if (vq.checkCamName.isSelected() == false) {
                                PreparedStatement ps = null;
                                try {
                                    /**
                                    * Para la actualización de los cambios realizados por el administrador. 
                                    */

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

            /**
            * Cuando se selecciona un quiz de la tabla y posteriormente eliminar. 
            */

            if (e.getSource() == vq.btnEliminar) {
                PreparedStatement ps = null, ps1 = null;
                ResultSet rs = null;

                try {

                    /**
                    * Para eliminar de la base de datos el quiz que se haya seleccionado. 
                    */

                    ModConexion objCon = new ModConexion();
                    Connection conn = objCon.getConexion();

                    ps = conn.prepareStatement("DELETE FROM quizzes WHERE id = ?");
                    ps.setString(1, vq.id.getText());
                    ps.execute();

                    ps1 = conn.prepareStatement("SELECT * FROM preguntas");
                    rs = ps1.executeQuery();

                    ResultSetMetaData rsMd = rs.getMetaData();
                    int cantidadColumnas = rsMd.getColumnCount();

                    /**
                    * Para eliminar todas las preguntas almacenadas en el quiz. 
                    */
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

            /**
            * Limpiar los campos e ingresar datos para agregar un nuevo quiz.
            */

            if (e.getSource() == vq.btnNuevo) {
                vq.p_totales.setText("0");
                limpiar();
            }

            /**
            * Agregar preguntar al haber seleccionado de la tabla el quiz, y para poder realizar esta 
            * función la pregunta debe de estar desactivada. 
            */

            if (e.getSource() == vq.btnAgrPreg) {
                if (vq.checkActivar.isSelected() == true) {
                    JOptionPane.showMessageDialog(null, "Para agregar una pregunta, modificarla o eliminarla,\n"
                            + "debe de desactivar el Quizz para los empleados.");
                } else {
                    VstPreguntas vp = new VstPreguntas();
                    ModvariablesPreguntas varp = new ModvariablesPreguntas();
                    CtrlPreguntas ctrlP = new CtrlPreguntas(con, varp, var, varU, vp, vq);
                    vp.txtNombre.setText(vq.txtNombre.getText());
                    vp.id.setText(vq.id.getText());
                    vp.actual.setText(vq.actual.getText());
                    vp.p_totales.setText(vq.p_totales.getText());
                    ctrlP.iniciar();
                    vp.setVisible(true);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vq.setVisible(false);
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
        vq.btnNuevo.setVisible(false);
        vq.checkCamName.setVisible(false);
        vq.checkActivar.setVisible(false);
        vq.btnGuardar.setVisible(true);
        vq.btnModificar.setVisible(false);
        vq.btnEliminar.setVisible(false);
        vq.btnAgrPreg.setVisible(false);
    }
    
    /**
     * Método para seleccionar cuantas horas o minutos tendrá el empleado para realizar el quiz. 
     * @param ComboLimTimeAgrQuizz variable la cual se utiliza para grabar las opciones en el 
     * comboBox (horas)
     * @param ComboTimeAgrQuizz variable la cual se utiliza para grabar las opciones en el 
     * comboBox (minutos)
     */

    public static void tiempo(JComboBox ComboLimTimeAgrQuizz, JComboBox ComboTimeAgrQuizz) {
        ComboLimTimeAgrQuizz.removeAllItems();
        ComboLimTimeAgrQuizz.addItem("Horas:");
        ComboLimTimeAgrQuizz.addItem("00");
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
    /**
     * Método para grabar en el comboBox las opciones de los años. 
     * @param ComboAñoAgrQuizz graba las opciones en el comboBox (años)
     */

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
    /**
     * Método para grabar en el comboBox las opciones de los meses.
     * @param ComboMesAgrQuizz graba las opciones en el comboBox (meses)
     */

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

  
    /**
     * Método para la visualización del calendario. 
     * @param ComboAñoAgrQuizz variable que da orden a grabar las opciones
     * de los meses en el comboBox. 
     * @param ComboMesAgrQuizz variable que da orden a grabar las opciones
     * de los días en el comboBox. 
     * @param ComboDiaAgrQuizz variable que graba las opciones acorde al 
     * año y mes seleccionado. 
     */
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
    
    /**
     * Método que despliega las opciones señaladas por un comboBox anterior
     * @param comboModCalf variable que da referencia al comboBox donde se van
     * a grabar las opciones. 
     * @param comboIntentos variable del comboBox que da la orden a grabar las
     * opciones del comboBox anterior. 
     */

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
    /**
     * @param comboIntentos variable que da referencia al comboBox donde se 
     * van a grabar las opciones. 
     */
    public static void intentos(JComboBox comboIntentos) {
        comboIntentos.addItem("1");
        comboIntentos.addItem("2");
        comboIntentos.addItem("3");
        comboIntentos.addItem("4");
        comboIntentos.addItem("5");
    }
}
