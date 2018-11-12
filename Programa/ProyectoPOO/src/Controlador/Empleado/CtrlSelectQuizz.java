/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Empleado;

import Modelo.Listas;
import Modelo.ModConsultasSQL;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesRespuestas;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.Empleado.VstPresentarQuizz;
import Vista.Empleado.VstSelectQuizz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlSelectQuizz implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr varU;
    private VstSelectQuizz vsq;
    private ModVariablesQuizzes varQ;
    private ModvariablesPreguntas varP;

    public CtrlSelectQuizz(ModConsultasSQL cons, ModVariablesUsr varU, VstSelectQuizz vsq, ModVariablesQuizzes varQ, ModvariablesPreguntas varP) {
        this.cons = cons;
        this.varU = varU;
        this.vsq = vsq;
        this.varQ = varQ;
        this.varP = varP;

        this.vsq.btnPresentar.addActionListener(this);
    }

    public void iniciar() {
        vsq.setTitle("Seleccionar Quizz.");
        vsq.setLocationRelativeTo(null);

        ModConsultasSQL.tablaSelectQuiz(vsq.tablaSelectQuizz, varQ);
        vsq.matricula.setText(varU.getMatricula());
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

        if (cons.existeUsr(varU.getMatricula()) == 1) {
            if ("Permanente".equals(varU.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vsq.setVisible(false);
            } else {
                if (e.getSource() == vsq.btnPresentar) {
                    if (vsq.Quizz.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Seleccione un Quizz.");
                    } else {
                        VstPresentarQuizz vpq = new VstPresentarQuizz();
                        ModVariablesPresentados varPre = new ModVariablesPresentados();

                        vpq.NomQuizz.setText(vsq.Quizz.getText());

                        String divide = vpq.NomQuizz.getText();
                        String[] partir = divide.split("/");
                        String partNom = partir[0]; //Del Quizz
                        String partNum = partir[1]; //p_totales para el Quizz
                        String parTiempo = partir[2]; //Tiempo del Quizz
                        varPre.setQuizz(partNom);//VARIABLES PRESENTADOS (NOMBRE DEL QUIZZ)
                        varPre.setTotales(partNum);//VARIABLES PRESENTADOS (PREGUNTAS DEL QUIZZ)
                        String partT = parTiempo;
                        String[] tiempo = partT.split(":");
                        String hora = tiempo[0];
                        String minuto = tiempo[1];
                        varPre.setHora(hora);//VARIABLES PRESENTADOS ("LAS HORAS PARA PRESENTAR")
                        varPre.setMinuto(minuto);//VARIABLES PRESENTADOS ("LOS MINUTOS PARA PRESENTAR")
                        ModConsultasSQL.obtenerQuizz(varQ, varPre.getQuizz());
                        //JOptionPane.showMessageDialog(null, varQ.getId() + "En seleccion");

                        Listas mens = new Listas();
                        ArrayList<ModVariablesPresentados> list = mens.listaPre(varU.getMatricula(), partNom);
                        ModVariablesPresentados var = new ModVariablesPresentados();

                        if (list.size() > 0) {
                            for (int i = list.size() - 1; i < list.size(); i++) {
                                var = list.get(i);
                                int intentosAc = Integer.parseInt(var.getIntento());
                                int intentosTot = Integer.parseInt(varQ.getIntentos());
                                if (intentosAc < intentosTot) {
                                    if (var.getStatus().equals("Aprobado")) {
                                        JOptionPane.showMessageDialog(null, "Ya has pasado el Quizz.");
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
                                    JOptionPane.showMessageDialog(null, "Ya ha utilizado todos los intentos pare presentar\n"
                                            + "este Quizz (los intentos son de: " + varQ.getIntentos() + ").");
                                }
                            }
                        } else {
                            CtrlPresentarQuizz ctrlP = new CtrlPresentarQuizz(cons, varU, varQ, varP, varPre, vpq);
                            ctrlP.iniciar();
                            vpq.setVisible(true);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vsq.setVisible(false);
        }
    }

}
