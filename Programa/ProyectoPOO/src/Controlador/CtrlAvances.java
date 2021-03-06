
package Controlador;

import Modelo.ModConexion;
import Modelo.ModConsultasSQL;
import Modelo.ModListas;
import Modelo.ModVariablesAbierto;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesUsr;
import Vista.VstAvances;
import Vista.VstEmpleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Esta es la clase que controla los avances de los empleados.
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlAvances implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesReg var;
    private ModVariablesUsr varU;
    private ModVariablesPresentados varP;
    private VstEmpleados ve;
    private VstAvances va;
    private Timer t;
    private String nombre = "";
    private String[] matricula = {};
    private String[] preg = {};
    private String temp[] = new String[10];
    private int cont = 0;
    private int acum = 0;
    private ModVariablesQuizzes varQ;

    /**
     * Contrusctor de la clase
     * @param cons es la clase donde están almacenadas las funciones de consulta.
     * @param var es la clase que contiene las variables utilizadas para el usuario que inicia sesion y para que sus datos sean almacenados.
     * @param varU parametro con las variables de los usuarios.
     * @param varP es la clase que contiene las variables utilizadas por los quizzes presentados y hacen la verificación de si aún puede 
     * realizar el quiz (intentos).
     * @param ve es la interfaz de la creación del empleado.
     * @param va es la interfaz de los avances.
     */
    public CtrlAvances(ModConsultasSQL cons, ModVariablesReg var, ModVariablesUsr varU, ModVariablesPresentados varP, VstEmpleados ve, VstAvances va) {
        this.cons = cons;
        this.var = var;
        this.varU = varU;
        this.varP = varP;
        this.ve = ve;
        this.va = va;

        this.va.btnCalificar.addActionListener(this);
        this.va.btnGuardar.addActionListener(this);
        this.va.btnCancelar.addActionListener(this);
        this.va.btnRegresar.addActionListener(this);
    }
    
    /**
     * Método para visualizar la interfaz del administrador.
     */
    public void iniciar() {
        va.setTitle("Avances");
        va.setLocationRelativeTo(null);
        va.calificar.setVisible(false);
        va.btnCalificar.setVisible(false);
        va.calificacion.setText(null);
        nombre = va.txtNombre.getText();
        matricula = nombre.split("/");
        va.btnRegresar.setVisible(false);
        ModConsultasSQL.tablaRegistro(va.tablaRegistro, varP, matricula[0], "Administrador");
        t = new Timer(10, acciones);
        t.start();
    }

     /**
     * Es el contructor encargado en recibir y ejecutar las acciones correspondientes a lo que va ocurriendo en la vista de avances.
     * @param e es la variable encargada de recibir cada acciones de los botones de la interfaz gráfica.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(var.getMatricula());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(varU);
        ModConsultasSQL.status(varU);
        ModConsultasSQL.obtenerResp(varP, matricula[0], va.txtIntento.getText(), va.nomQuizz.getText());
        preg = varP.getAbrt().split("~");

        if (cons.existeUsr(varU.getMatricula()) == 1) {
            if (e.getSource() == va.btnCalificar) {
                t.stop();
                va.btnCalificar.setVisible(false);
                va.calificar.setVisible(true);
                va.calificacion.setText(null);
                cont = 0;
                acum = 0;
                ModVariablesAbierto varA = new ModVariablesAbierto();
                ModConsultasSQL.llenarResp(va, varA, matricula[0], va.nomQuizz.getText(), preg[cont], va.txtIntento.getText());
                if (cont + 1 == Integer.parseInt(varP.getTotales())) {
                    va.btnGuardar.setText("Calificar pregunta.");
                } else {
                    va.btnGuardar.setText("Siguiente pregunta.");
                }
                cont = cont + 1;
                acum = acum + 1;
            }
            if (e.getSource() == va.btnGuardar) {
                if (va.txtCalificacion.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe de asignar una puntuación.");
                } else {
                    if (trueDouble(va.txtCalificacion.getText()) == true) {
                        double pts = cambio(va.txtCalificacion.getText());
                        if (pts <= Double.parseDouble(va.txtPuntuacion.getText()) && pts >= 0) {
                            if (va.txtComentario.getText().equals("")) {
                                cons.calificar(matricula[0], preg[cont - 1], va.nomQuizz.getText(), pts + "", "/*null*/");
                            } else {
                                cons.calificar(matricula[0], preg[cont - 1], va.nomQuizz.getText(), pts + "", va.txtComentario.getText());
                            }
                            if (va.calificacion.getText().equals("")) {
                                va.calificacion.setText(pts + "");
                            } else {
                                double pTotales = Double.parseDouble(va.calificacion.getText()) + pts;
                                va.calificacion.setText(pTotales + "");
                            }

                            ModVariablesReg varR = new ModVariablesReg();
                            String tipo = "Administrador";
                            String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                            String que = "Calificó con una puntuación de: " + va.txtCalificacion.getText() + " la respuesta de la pregunta: " + va.txtPregunta.getText()
                                    + " del Quizz: " + va.nomQuizz.getText() + " con valor de: " + va.txtPuntuacion.getText() + " puntos la pregunta.";
                            String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                            String comp = varU.getMatricula();
                            if (cons.avisoAA(varR, tipo, quien, que, cuando, comp));

                            String comentario;
                            if (va.txtComentario.getText().equals("")) {
                                comentario = "/*null*/";
                            } else {
                                comentario = va.txtComentario.getText();
                            }
                            String todo = va.txtPregunta.getText() + "~" + va.txtRespuesta.getText() + "~" + va.txtPuntuacion.getText() + "~" + va.txtCalificacion.getText() + "~" + comentario;
                            temp[acum] = todo;
                            if (va.btnGuardar.getText().equals("Calificar pregunta.")) {
                                cont = cont + 1;
                                acum = acum + 1;
                            } else {
                                ModVariablesAbierto varA = new ModVariablesAbierto();
                                if (cont == Integer.parseInt(varP.getTotales()) || acum == Integer.parseInt(varP.getTotales())); else {
                                    ModConsultasSQL.llenarResp(va, varA, matricula[0], va.nomQuizz.getText(), preg[cont], va.txtIntento.getText());
                                }
                                if (cont + 1 == Integer.parseInt(varP.getTotales())) {
                                    va.btnGuardar.setText("Terminar de calificar.");
                                    cont = cont + 1;
                                    acum = acum + 1;
                                } else {
                                    va.btnGuardar.setText("Siguiente pregunta.");
                                    cont = cont + 1;
                                    acum = acum + 1;
                                }
                            }
                            if (cont > Integer.parseInt(varP.getTotales())) {
                                String[] p = varP.getP_totales().split("~");
                                String[] c = varP.getCalificacion().split("~");
                                double n = Double.parseDouble(c[0]) - Double.parseDouble(c[2]);
                                double punTot = Double.parseDouble(p[0]);
                                double punObt = 0;
                                if (p[1].equals("100")) {
                                    punObt = Double.parseDouble(va.calificacion.getText()) / Integer.parseInt(varP.getAbrtTot());
                                } else {
                                    punObt = Double.parseDouble(va.calificacion.getText()) / (Integer.parseInt(varP.getAbrtTot()) - 1);
                                }
                                
                                DecimalFormat op = new DecimalFormat("#.00");
                                double tot = n + punObt;
                                String to = op.format(tot);
                                double TOT = Double.parseDouble(to);
                                
                                double punAho = 0;
                                if (p[1].equals("100")) {
                                    punAho = (TOT * 100) / punTot;
                                } else {
                                    double valor = Double.parseDouble(p[1]);
                                    punAho = (TOT * valor) / punTot;
                                }
                                String status;
                                String bd = op.format(punAho);
                                String ob = op.format(punObt);
                                double fin = Double.parseDouble(bd);
                                double fin2 = Double.parseDouble(ob);

                                if (fin >= 70) {
                                    if (p[1].equals("100")) {
                                        status = "Aprobado";
                                    } else {
                                        status = "Aprobado/Sin terminar";
                                    }
                                } else {
                                    if (p[1].equals("100")) {
                                        status = "Reprobado";
                                    } else {
                                        status = "Reprobado/Sin terminar";
                                    }
                                }

                                ModConexion con = new ModConexion();
                                String sql = "UPDATE presentados SET calificacion = ?, status = ? WHERE (ident = '" + matricula[0] + "' AND quizz = '" + va.nomQuizz.getText() + "' AND intento = '" + va.txtIntento.getText() + "')";
                                PreparedStatement ps = null;
                                String pun = TOT + "~" + fin + "~" + fin2;

                                try {
                                    ps = con.getConexion().prepareStatement(sql);
                                    ps.setString(1, pun);
                                    ps.setString(2, status);
                                    ps.executeUpdate();
                                } catch (SQLException ex) {
                                    System.out.println(ex.getMessage());
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }

                                varR = new ModVariablesReg();
                                quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                que = "Calificó el Quizz: " + va.nomQuizz.getText() + " en el cual,"
                                        + "obtuvo una puntuación de: " + fin;
                                cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                if (cons.aviso(quien, que, cuando, matricula[0]));

                                ModListas mens = new ModListas();
                                ArrayList<ModVariablesQuizzes> list = mens.listaQuizz();

                                if (list.size() > 0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        varQ = list.get(i);
                                        if (varQ.getNombre().equals(va.nomQuizz.getText())) {
                                            int intentos = 0;
                                            if (cons.existeCalif(matricula[0], va.nomQuizz.getText(), varQ.getMod_calif()) == 1) {
                                                String Tpuntos = cons.PtotalesMod(matricula[0], va.nomQuizz.getText(), varQ.getMod_calif());
                                                String[] partirP = Tpuntos.split("~");
                                                intentos = Integer.parseInt(partirP[0]);
                                                String Tpuntos2 = cons.Ptotales(matricula[0], va.nomQuizz.getText(), varQ.getMod_calif());
                                                String[] partirP2 = Tpuntos2.split("~");
                                                double suma = 0;
                                                double alto = fin;
                                                String Tpunt = "";
                                                for (int ik = 0; ik < (intentos - 1); ik++) {
                                                    suma = suma + Double.parseDouble(partirP2[ik]);
                                                    if (Tpunt.equals("")) {
                                                        Tpunt = partirP2[ik] + "~";
                                                    } else {
                                                        Tpunt = Tpunt + partirP2[ik] + "~";
                                                    }
                                                    if (alto < Double.parseDouble(partirP2[ik])) {
                                                        alto = Double.parseDouble(partirP2[ik]);
                                                    }
                                                }
                                                String statuss = "";
                                                double promedio = (suma + fin) / intentos;
                                                if (promedio < 70) {
                                                    statuss = "Reprobado";
                                                } else {
                                                    statuss = "Aprobado";
                                                }

                                                DecimalFormat pr = new DecimalFormat("#.00");
                                                String prom = pr.format(promedio);
                                                double punt = Double.parseDouble(prom);

                                                if (varQ.getMod_calif().equals("Promedio de calificaciones")) {
                                                    cons.updCalif(punt + "", statuss, intentos + "", Tpunt + "" + fin + "~", matricula[0], va.nomQuizz.getText(), varQ.getMod_calif());
                                                }
                                                if (varQ.getMod_calif().equals("Calificacion más alta")) {
                                                    cons.updCalif(alto + "", status, intentos + "", Tpunt + "" + fin + "~", matricula[0], va.nomQuizz.getText(), varQ.getMod_calif());
                                                }
                                                if (varQ.getMod_calif().equals("Primer intento")) {
                                                    cons.updCalif(fin + "", status, intentos + "", fin + "~", matricula[0], va.nomQuizz.getText(), varQ.getMod_calif());
                                                }
                                            }
                                            i = list.size() - 1;
                                        }
                                    }
                                }

                                va.btnCalificar.setVisible(true);
                                va.calificar.setVisible(false);
                                t.start();
                                va.txtPregunta.setText(null);
                                va.txtRespuesta.setText(null);
                                va.txtIntento.setText(null);
                                va.txtPuntuacion.setText(null);
                                va.txtCalificacion.setText(null);
                                va.btnRegresar.setVisible(false);
                                va.calificacion.setText(null);
                                va.btnCalificar.setVisible(false);
                                va.RefQuizz.setVisible(false);
                                va.nomQuizz.setVisible(false);
                                ModConsultasSQL.tablaRegistro(va.tablaRegistro, varP, matricula[0], "Administrador");
                                cont = 0;
                                acum = 0;
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "La puntuación asignada es inválida.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La puntuación asignada es inválida.");
                    }
                }
            }
            if (e.getSource() == va.btnCancelar) {
                va.btnCalificar.setVisible(true);
                va.calificar.setVisible(false);
                t.start();
                va.txtPregunta.setText(null);
                va.txtRespuesta.setText(null);
                va.txtIntento.setText(null);
                va.txtPuntuacion.setText(null);
                va.txtCalificacion.setText(null);
                cont = 0;
            }
            if (acum > 1) {
                va.btnRegresar.setVisible(true);
            }
            if (e.getSource() == va.btnRegresar) {
                int retro = - 1;
                int arr = acum + retro;
                String[] partir = temp[arr].split("~");
                va.txtPregunta.setText(partir[0]);
                va.txtRespuesta.setText(partir[1]);
                va.txtPuntuacion.setText(partir[2]);
                va.txtCalificacion.setText(partir[3]);
                if (partir[4].equals("/*null*/")) {
                    va.txtComentario.setText(null);
                } else {
                    va.txtComentario.setText(partir[4]);
                }
                double resta = Double.parseDouble(partir[3]);
                double puntos = Double.parseDouble(va.calificacion.getText());
                double residuo = puntos - resta;
                va.calificacion.setText(residuo + "");
                acum = acum - 1;
                cont = cont - 1;
                if (cont == Integer.parseInt(varP.getTotales())) {
                    va.btnGuardar.setText("Terminar de calificar.");
                } else {
                    va.btnGuardar.setText("Siguiente pregunta.");
                }
                if (acum == 1) {
                    va.btnRegresar.setVisible(false);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            va.setVisible(false);
        }
    }
     /**
     * Método que identifica si el valor que se ingreso en la puntuación es un número (double).
     * @param cadena cadena extraida de la interfaz gráfica para su validación.
     * @return true si la cadena es un double y false si no lo es.
     */
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

     /**
     * Método que toma el valor de la puntuación y si detecta que tiene más de 2 decimales, se aplica el redondeo.
     * @param cadena es la puntuación convertida en un string.
     * @return devuelve el valor redondeado.
     */
    public double cambio(String cadena) {
        DecimalFormat op = new DecimalFormat("#.00");
        double db = Double.parseDouble(cadena);
        String bd = op.format(db);
        double fin = Double.parseDouble(bd);

        return fin;
    }
    
     /**
     * Método para el uso del cronómetro
     *
     * @param h variable para hora
     * @param m variable para minutos
     * @param s variable para segundos
     * @param cs variale para centésima de segundo
     */
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
                String nombre = va.txtNombre.getText();
                String[] matricula = nombre.split("/");
                ModConsultasSQL.tablaRegistro(va.tablaRegistro, varP, matricula[0], "Administrador");
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
