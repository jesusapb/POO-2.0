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
import Modelo.ModVariablesReg;
import Modelo.ModVariablesRespuestas;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.Empleado.VstPresentarQuizz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Antonio
 */
public class CtrlPresentarQuizz implements ActionListener {

    private ModConsultasSQL cons;
    private ModVariablesUsr varU;
    private ModVariablesQuizzes varQ;
    private ModvariablesPreguntas varP;
    private ModVariablesPresentados varPre;
    private VstPresentarQuizz vpq;
    private Timer t;
    private int contad = 0;

    public CtrlPresentarQuizz(ModConsultasSQL cons, ModVariablesUsr varU, ModVariablesQuizzes varQ, ModvariablesPreguntas varP, ModVariablesPresentados varPre, VstPresentarQuizz vpq) {
        this.cons = cons;
        this.varU = varU;
        this.varQ = varQ;
        this.varP = varP;
        this.varPre = varPre;
        this.vpq = vpq;

        this.vpq.btnSigTerm.addActionListener(this);
    }

    public void iniciar() {
        vpq.setTitle("Presentar Quizz");
        vpq.setLocationRelativeTo(null);

        vpq.setVisible(true);
        vpq.txtR3.setVisible(false);
        vpq.txtR4.setVisible(false);
        vpq.txtR5.setVisible(false);
        vpq.txtR6.setVisible(false);
        vpq.txtR7.setVisible(false);
        vpq.txtR8.setVisible(false);
        vpq.r_c.setVisible(false);
        vpq.r_d.setVisible(false);
        vpq.r_e.setVisible(false);
        vpq.r_f.setVisible(false);
        vpq.r_g.setVisible(false);
        vpq.r_h.setVisible(false);

        /*JOptionPane.showMessageDialog(null, varPre.getHora() + varPre.getMinuto());
        JOptionPane.showMessageDialog(null, varQ.getId() + "En presentar");*/
        //JOptionPane.showMessageDialog(null, vpq.nump.getText());
        if (varPre.getHora().equals("00") && varPre.getMinuto().equals("00")) {
            vpq.cronometro.setVisible(false);
            t = new Timer(10, acciones);
            t.stop();
        } else {
            t = new Timer(10, acciones);
            t.start();
            //int a = cons.randomPre(varQ.getId());
            //int verificar = Integer.parseInt(cons.verificador(vpq.nump.getText()));
            int b = 0;
            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText(), b);
            vpq.nump_resp.setText(varP.getNum_resp());
            Listas mens = new Listas();
            String[] lista = new String[8];
            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

            vpq.txtPregunta.setText(varP.getPregunta());
            vpq.nump.setText(varP.getId() + "");
            vpq.contador.setText("1");
            vpq.puntosT.setText(varP.getPuntuacion_total());

            int cont = 0;

            if (varP.getTipo().equals("abierto")) {
                vpq.ab.setText("1");
                vpq.abrt.setText("" + varP.getPregunta());
                vpq.abierto.setVisible(true);
            } else {
                if (varP.getTipo().equals("unico") || varP.getTipo().equals("multiple")) {
                    vpq.abierto.setVisible(false);
                    for (int i = 0; i < 8; i++) {
                        String[] part = temp[i].split("~");
                        String parte = part[0];
                        if (parte.equals("*/null/*")); else {
                            lista[cont] = temp[i];
                            cont++;
                        }
                    }
                    insertarPreg(cont, lista);
                }
            }
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

        if (cons.existeUsr(varU.getMatricula()) == 1) {
            if ("Permanente".equals(varU.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vpq.setVisible(false);
            } else {

                if (e.getSource() == vpq.btnSigTerm) {
                    boolean a = true;
                    int b = Integer.parseInt(vpq.contador.getText());
                    //ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText(), b);
                    if (varP.getTipo().equals("abierto")) {

                        ModVariablesRespuestas varRe = new ModVariablesRespuestas();
                        varRe.setIdent(varU.getMatricula());
                        varRe.setPuntuacion(varP.getPuntuacion_total());
                        varRe.setQuizz(varP.getQuizz());
                        varRe.setPregunta(vpq.txtPregunta.getText());
                        varRe.setRespuesta(vpq.txtRespuesta.getText());
                        varRe.setStatus("Por calificar");
                        varRe.setP_asignada("0");

                        if (cons.rPAbierta(varRe)) {
                            System.out.println("Respuesta guardada.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error en la conexión.");
                            a = false;
                        }
                    }

                    if (a == true) {
                        double ra = 0;
                        double rb = 0;
                        double rc = 0;
                        double rd = 0;
                        double re = 0;
                        double rf = 0;
                        double rg = 0;
                        double rh = 0;
                        double acum = 0;
                        double puntos = 0;
                        if (vpq.puntos.getText().equals("")) {
                            puntos += 0;
                        } else {
                            puntos += Double.parseDouble(vpq.puntos.getText());
                        }

                        if (!varP.getTipo().equals("abierto")) {
                            if (vpq.r_a.isSelected()) {
                                ra = Double.parseDouble(vpq.r_a.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_b.isSelected()) {
                                rb = Double.parseDouble(vpq.r_b.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_c.isSelected()) {
                                rc = Double.parseDouble(vpq.r_c.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_d.isSelected()) {
                                rd = Double.parseDouble(vpq.r_d.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_e.isSelected()) {
                                re = Double.parseDouble(vpq.r_e.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_f.isSelected()) {
                                rf = Double.parseDouble(vpq.r_f.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_g.isSelected()) {
                                rg = Double.parseDouble(vpq.r_g.getName());
                                contad = contad + 1;
                            }
                            if (vpq.r_h.isSelected()) {
                                rh = Double.parseDouble(vpq.r_h.getName());
                                contad = contad + 1;
                            }

                            //System.out.println("Contad: " + contad + " Puntos: " + puntos);
                            int nump_resp = Integer.parseInt(varP.getNum_resp());
                            if (contad > nump_resp) {
                                acum = puntos;
                            } else {
                                acum = ra + rb + rc + rd + re + rf + rg + rh + puntos;
                            }
                            contad = 0;
                            //System.out.println("Acum: " + acum);
                            vpq.puntos.setText(acum + "");
                        }

                        int conta = Integer.parseInt(vpq.contador.getText()) + 1;
                        int com = Integer.parseInt(varPre.getTotales());

                        if (conta <= com) {
                            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText(), b);
                            Listas mens = new Listas();
                            String[] lista = new String[8];
                            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

                            if (conta == com) {
                                vpq.btnSigTerm.setText("Terminar");
                            }
                            vpq.txtPregunta.setText(varP.getPregunta());
                            vpq.nump.setText(vpq.nump.getText() + "~" + varP.getId());
                            vpq.contador.setText(conta + "");
                            double puntosT = Double.parseDouble(vpq.puntosT.getText());
                            puntosT += Double.parseDouble(varP.getPuntuacion_total());
                            vpq.puntosT.setText(puntosT + "");

                            vpq.setVisible(true);
                            vpq.txtRespuesta.setText(null);
                            vpq.txtR3.setVisible(false);
                            vpq.txtR4.setVisible(false);
                            vpq.txtR5.setVisible(false);
                            vpq.txtR6.setVisible(false);
                            vpq.txtR7.setVisible(false);
                            vpq.txtR8.setVisible(false);
                            vpq.r_c.setVisible(false);
                            vpq.r_d.setVisible(false);
                            vpq.r_e.setVisible(false);
                            vpq.r_f.setVisible(false);
                            vpq.r_g.setVisible(false);
                            vpq.r_h.setVisible(false);

                            vpq.r_a.setSelected(false);
                            vpq.r_b.setSelected(false);
                            vpq.r_c.setSelected(false);
                            vpq.r_d.setSelected(false);
                            vpq.r_e.setSelected(false);
                            vpq.r_f.setSelected(false);
                            vpq.r_g.setSelected(false);
                            vpq.r_h.setSelected(false);

                            int cont = 0;

                            if (varP.getTipo().equals("abierto")) {
                                vpq.abierto.setVisible(true);
                                int contAb = Integer.parseInt(vpq.ab.getText());
                                vpq.ab.setText((contAb + 1) + "");
                                vpq.abrt.setText(vpq.abrt.getText() + "~" + varP.getPregunta());
                            } else {
                                if (varP.getTipo().equals("unico") || varP.getTipo().equals("multiple")) {
                                    vpq.abierto.setVisible(false);
                                    for (int i = 0; i < 8; i++) {
                                        String[] part = temp[i].split("~");
                                        String parte = part[0];
                                        if (parte.equals("*/null/*")); else {
                                            lista[cont] = temp[i];
                                            cont++;
                                        }
                                    }
                                    insertarPreg(cont, lista);
                                }
                            }

                        } else {
                            double puntosT = 0;
                            if (vpq.puntosT.getText().equals("")); else {
                                puntosT = Double.parseDouble(vpq.puntosT.getText());
                            }
                            if (vpq.puntos.getText().equals("")) {
                                puntos = 0;
                            } else {
                                puntos = Double.parseDouble(vpq.puntos.getText());
                            }

                            System.out.println("Preguntas abiertas: " + vpq.ab.getText());
                            System.out.println("Puntos totales: " + (puntosT / (conta - 1)));
                            System.out.println("Puntos obtenidos: " + (puntos / (conta - 1)));
                            System.out.println((conta - 1));

                            String partir = vpq.NomQuizz.getText();
                            String[] parte = partir.split("/");
                            varPre.setIdent(varU.getMatricula());
                            varPre.setQuizz(parte[0]);

                            Listas mens = new Listas();
                            ArrayList<ModVariablesPresentados> list = mens.listaPre(varU.getMatricula(), parte[0]);
                            ModVariablesPresentados var = new ModVariablesPresentados();

                            if (list.size() > 0) {
                                for (int i = list.size() - 1; i < list.size(); i++) {
                                    var = list.get(i);
                                    int intentosAc = Integer.parseInt(var.getIntento()) + 1;

                                    varPre.setIntento(intentosAc + "");
                                }
                            } else {
                                varPre.setIntento("1");
                            }

                            DecimalFormat op = new DecimalFormat("#.00");
                            String bd = op.format((puntosT / (conta - 1)));
                            double fin = Double.parseDouble(bd);

                            DecimalFormat op1 = new DecimalFormat("#.00");
                            String bd1 = op1.format((puntos / (conta - 1)));
                            double fin1 = Double.parseDouble(bd1);
                            String que;
                            double fin2 = 0;

                            if (!vpq.ab.getText().equals("")) {
                                varPre.setStatus("Por calificar");
                                que = "Terminó de presentar el Quizz: ''" + parte[0] + "'', se necesita la revision y calificación de la(s) pregunta(s) abierta(s)";
                                varPre.setAbrt(vpq.abrt.getText());
                            } else {
                                DecimalFormat op2 = new DecimalFormat("#.00");
                                String bd2 = op2.format(((puntos / (conta - 1)) * 100) / (puntosT / (conta - 1)));
                                fin2 = Double.parseDouble(bd2);
                                if (fin2 < 70) {
                                    que = "Terminó de presentar el Quizz: '" + parte[0] + "' con una puntuación de: " + fin2 + " (Reprobaado).";
                                    varPre.setStatus("Reprobado");
                                } else {
                                    que = "Terminó de presentar el Quizz: '" + parte[0] + "' con una puntuación de: " + fin2 + " (Aprobado).";
                                    varPre.setStatus("Aprobado");
                                }
                                varPre.setAbrt("nada");
                            }
                            
                            
                            varPre.setP_totales(fin + "~100");
                            varPre.setCalificacion(fin1 + "~" + fin2);

                            if (cons.rPresentados(varPre)) {
                                DecimalFormat op2 = new DecimalFormat("#.00");
                                String bd2 = op2.format(((puntos / (conta - 1)) * 100) / (puntosT / (conta - 1)));
                                fin2 = Double.parseDouble(bd2);

                                ModVariablesReg varReg = new ModVariablesReg();
                                String tipo = "Administrador";
                                String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                                String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                                String comp = varU.getMatricula();
                                if (cons.avisoAA(varReg, tipo, quien, que, cuando, comp));
                                String status;
                                if (fin2 < 70) {
                                    status = "Reprobado";
                                } else {
                                    status = "Aprobado";
                                }
                                if (vpq.puntos.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "Quizz terminado, espere que un administrador le califique.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Quizz terminado, Puntuación: " + fin2 + ", Status: " + status + ".");
                                }
                                t.stop();
                                vpq.setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
                                t.stop();
                                vpq.setVisible(false);
                            }
                        }
                    }

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vpq.setVisible(false);
        }
    }

    public void insertarPreg(int cont, String lista[]) {
        if (cont == 2) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);
        }
        if (cont == 3) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);
        }
        if (cont == 4) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(parte1);
            vpq.r_d.setName(parte2);
        }
        if (cont == 5) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(parte1);
            vpq.r_d.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(parte1);
            vpq.r_e.setName(parte2);
        }
        if (cont == 6) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(parte1);
            vpq.r_d.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];
            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(parte1);
            vpq.r_e.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(parte1);
            vpq.r_f.setName(parte2);
        }
        if (cont == 7) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];
            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(parte1);
            vpq.r_d.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(parte1);
            vpq.r_e.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(parte1);
            vpq.r_f.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1) || vpq.txtR6.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR7.setVisible(true);
            vpq.r_g.setVisible(true);
            vpq.txtR7.setText(parte1);
            vpq.r_g.setName(parte2);
        }
        if (cont == 8) {
            int a = (int) (Math.random() * cont);
            String temp = lista[a];
            String[] part = temp.split("~");
            String parte1 = part[0];
            String parte2 = part[1];
            vpq.txtR1.setText(parte1);
            vpq.r_a.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR2.setText(parte1);
            vpq.r_b.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(parte1);
            vpq.r_c.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(parte1);
            vpq.r_d.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(parte1);
            vpq.r_e.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(parte1);
            vpq.r_f.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1) || vpq.txtR6.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR7.setVisible(true);
            vpq.r_g.setVisible(true);
            vpq.txtR7.setText(parte1);
            vpq.r_g.setName(parte2);

            a = (int) (Math.random() * cont);
            temp = lista[a];
            part = temp.split("~");
            parte1 = part[0];
            parte2 = part[1];

            while (vpq.txtR1.getText().equals(parte1) || vpq.txtR2.getText().equals(parte1) || vpq.txtR3.getText().equals(parte1)
                    || vpq.txtR4.getText().equals(parte1) || vpq.txtR5.getText().equals(parte1) || vpq.txtR6.getText().equals(parte1)
                    || vpq.txtR7.getText().equals(parte1)) {
                a = (int) (Math.random() * cont);
                temp = lista[a];
                part = temp.split("~");
                parte1 = part[0];
                parte2 = part[1];
            }

            vpq.txtR8.setVisible(true);
            vpq.r_h.setVisible(true);
            vpq.txtR8.setText(parte1);
            vpq.r_h.setName(parte2);
        }
    }

    private int h, m, s, cs;
    private final ActionListener acciones = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent ae) {
            int Thoras, Tminutos;

            if (varPre.getHora().equals("") || varPre.getHora().equals("0")) {
                Thoras = 0;
            } else {
                Thoras = Integer.parseInt(varPre.getHora());
            }

            if (varPre.getMinuto().equals("") || varPre.getMinuto().equals("0")) {
                Tminutos = 0;
            } else {
                Tminutos = Integer.parseInt(varPre.getMinuto());
            }

            //JOptionPane.showMessageDialog(null, Thoras + Tminutos);
            cs++;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
            if (cs == 0 && s == 0 && h == Thoras && m == Tminutos) {
                t.stop();
                //vpq.cronometro.setVisible(false);
                JOptionPane.showMessageDialog(null, "Tiempo agotado.");
            }
            actualizarLabel();

        }
    };

    private void actualizarLabel() {
        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s/* + ":" + (cs <= 9 ? "0" : "") + cs*/;
        vpq.cronometro.setText(tiempo);
    }
}
