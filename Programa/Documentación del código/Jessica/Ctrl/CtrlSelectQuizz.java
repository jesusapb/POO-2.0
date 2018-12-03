
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModListas;
import Modelo.ModVariablesCalif;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.VstPresentarQuizz;
import Vista.VstSelectQuizz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Esta clase muestra en una tabla los quizzes habilitados y su función es que el usuario 
 * seleccione el quiz a realizar. 
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco. 
 * @version 01/12/2018/ProyectoPoo_Acompañamiento
 */
public class CtrlSelectQuizz implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr varU;
    private VstSelectQuizz vsq;
    private ModVariablesQuizzes varQ;
    private ModvariablesPreguntas varP;

    /**
    * Constructor de la clase 
    * @param cons es la clse donde están almacenadas las funciones de consulta.
    * @param varU es la clase que contiene las variables utilizadas para el usuario que 
    * que inicia la sesión y para que sus datos sean almacenados. 
    * @param vsq es la interfaz gráfica de SelectQuizz 
    * @param varQ es la clase que contiene las variables utilizadas para los quizzes
    * y sean mostrados. 
    * @param varP es la clase que contiene las variable utilizadas para las preguntas 
    * y sean mostrados. 
    */

    public CtrlSelectQuizz(ModConsultasSQL cons, ModVariablesUsr varU, VstSelectQuizz vsq, ModVariablesQuizzes varQ, ModvariablesPreguntas varP) {
        this.cons = cons;
        this.varU = varU;
        this.vsq = vsq;
        this.varQ = varQ;
        this.varP = varP;

        this.vsq.btnPresentar.addActionListener(this);
    }

    /**
    *Método para mostrar la tabla de los quizzes habilitados. 
    */

    public void iniciar() {
        vsq.setTitle("Seleccionar Quizz.");
        vsq.setLocationRelativeTo(null);

        ModConsultasSQL.tablaSelectQuiz(vsq.tablaSelectQuizz, varQ);
        vsq.matricula.setText(varU.getMatricula());
    }

    /**
    * Es el contructor encargado en recibir y ejecutar las acciones 
    * correspondientes a lo que va ocurriendo en la vista de SelectQuizz
    * @param e es la variable encargada de recibir cada acción de los botones
    * de la interfaz gráfica. 
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

        if (cons.existeUsr(varU.getMatricula()) == 1) {
            if (e.getSource() == vsq.btnPresentar) {
                if (vsq.Quizz.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Seleccione un Quizz.");
                } else {
                    VstPresentarQuizz vpq = new VstPresentarQuizz();
                    ModVariablesPresentados varPre = new ModVariablesPresentados();

                    vpq.NomQuizz.setText(vsq.Quizz.getText());

                    String divide = vpq.NomQuizz.getText();
                    String[] partir = divide.split("/");
                    String partNom = partir[0];
                    String partNum = partir[1];
                    String parTiempo = partir[2];
                    varPre.setQuizz(partNom);
                    varPre.setTotales(partNum);
                    String partT = parTiempo;
                    String[] tiempo = partT.split(":");
                    String hora = tiempo[0];
                    String minuto = tiempo[1];
                    varPre.setHora(hora);
                    varPre.setMinuto(minuto);
                    ModConsultasSQL.obtenerQuizz(varQ, varPre.getQuizz());

                    ModListas mens = new ModListas();
                    ArrayList<ModVariablesCalif> list = mens.listaCalif(varU.getMatricula(), partNom);
                    ModVariablesCalif var = new ModVariablesCalif();

                    if (list.size() > 0) {
                        for (int i = list.size() - 1; i < list.size(); i++) {
                            var = list.get(i);
                            int intentosAc = Integer.parseInt(var.getIntentos());
                            int intentosTot = Integer.parseInt(varQ.getIntentos());
                            if (intentosAc < intentosTot) {
                                if (var.getStatus().equals("Aprobado")) {
                                    JOptionPane.showMessageDialog(null, "Ya has pasado el Quizz con " + var.getPuntos() + " puntos.");
                                } else {
                                    if (var.getStatus().equals("Por calificar")) {
                                        JOptionPane.showMessageDialog(null, "Aún no se ha calificado este Quizz.");
                                    } else {
                                        CtrlPresentarQuizz ctrlP = new CtrlPresentarQuizz(cons, varU, varQ, varP, varPre, vpq);
                                        ctrlP.iniciar();
                                        vpq.setVisible(true);
                                    }
                                }
                            } else {
                                String anuncio = "";
                                if (var.getStatus().equals("Aprobado") || var.getStatus().equals("Aprobado/Sin terminar")) {
                                    anuncio = "Usted aprobó este Quizz con una puntuación de: " + var.getPuntos();
                                }
                                if (var.getStatus().equals("Reprobado") || var.getStatus().equals("Reprobado/Sin terminar")) {
                                    anuncio = "Usted reprobó este Quizz con una puntuación de: " + var.getPuntos();
                                }
                                if (var.getStatus().equals("Por calificar")) {
                                    anuncio = "Espere a que un administrador califique este Quizz.";
                                }
                                JOptionPane.showMessageDialog(null, "Ya ha utilizado todos los intentos pare presentar\n"
                                        + "este Quizz (los intentos son de: " + varQ.getIntentos() + ").\n"
                                        + anuncio);
                            }
                        }
                    } else {
                        CtrlPresentarQuizz ctrlP = new CtrlPresentarQuizz(cons, varU, varQ, varP, varPre, vpq);
                        ctrlP.iniciar();
                        vpq.setVisible(true);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vsq.setVisible(false);
        }
    }

}
