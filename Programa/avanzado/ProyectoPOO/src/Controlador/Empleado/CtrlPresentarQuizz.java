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
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.Empleado.VstPresentarQuizz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText());
            Listas mens = new Listas();
            String[] lista = new String[8];
            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

            vpq.txtPregunta.setText(varP.getPregunta());
            vpq.nump.setText("" + varP.getId() + "/");
            vpq.contador.setText("1");

            int cont = 0;

            if (varP.getTipo().equals("abierto")) {
                vpq.abierto.setVisible(true);
            } else {
                vpq.abierto.setVisible(false);
                for (int i = 0; i < 8; i++) {
                    if (!temp[i].equals("")) {
                        lista[cont] = temp[i];
                        cont++;
                    }
                    //System.out.println(temp[i]);
                }
                //JOptionPane.showMessageDialog(null, cont);
                insertarPreg(cont, lista);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int acum = 0;
        if (e.getSource() == vpq.btnSigTerm) {
            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText());
            int conta = Integer.parseInt(vpq.contador.getText()) + 1;
            int com = Integer.parseInt(varPre.getTotales());
            Listas mens = new Listas();
            String[] lista = new String[8];
            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

            int ra = 0;
            int rb = 0;
            int rc = 0;
            int rd = 0;
            int re = 0;
            int rf = 0;
            int rg = 0;
            int rh = 0;

            if (!varP.getTipo().equals("abierto")) {
                if (vpq.r_a.isSelected()) {
                    ra = Integer.parseInt(vpq.r_a.getName());
                    acum += ra;
                }
                if (vpq.r_b.isSelected()) {
                    rb = Integer.parseInt(vpq.r_b.getName());
                    acum += rb;
                }
                if (vpq.r_c.isSelected()) {
                    rc = Integer.parseInt(vpq.r_c.getName());
                    acum += rc;
                }
                if (vpq.r_d.isSelected()) {
                    rd = Integer.parseInt(vpq.r_d.getName());
                    acum += rd;
                }
                if (vpq.r_e.isSelected()) {
                    re = Integer.parseInt(vpq.r_e.getName());
                    acum += re;
                }
                if (vpq.r_f.isSelected()) {
                    rf = Integer.parseInt(vpq.r_f.getName());
                    acum += rf;
                }
                if (vpq.r_g.isSelected()) {
                    rg = Integer.parseInt(vpq.r_g.getName());
                    acum += rg;
                }
                if (vpq.r_h.isSelected()) {
                    rh = Integer.parseInt(vpq.r_h.getName());
                    acum += rh;
                }

                JOptionPane.showMessageDialog(null, "Puntuación de la pregunta: " + acum);
                System.out.println("");
            }
            JOptionPane.showMessageDialog(null, "Puntuación de la pregunta: " + acum);
            JOptionPane.showMessageDialog(null, vpq.r_a.getName() + " " + vpq.r_b.getName() + " " + vpq.r_c.getName() + " "
                    + vpq.r_d.getName() + " " + vpq.r_e.getName() + " " + vpq.r_f.getName() + " " + vpq.r_g.getName() + " " + vpq.r_h.getName());

            if (conta <= com) {
                vpq.txtPregunta.setText(varP.getPregunta());
                vpq.nump.setText(vpq.nump.getText() + "/" + varP.getId());
                vpq.contador.setText(conta + "");

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
                int cont = 0;

                if (varP.getTipo().equals("abierto")) {
                    vpq.abierto.setVisible(true);
                } else {
                    vpq.abierto.setVisible(false);
                    for (int i = 0; i < 8; i++) {
                        if (!temp[i].equals("")) {
                            lista[cont] = temp[i];
                            cont++;
                        }
                    }
                    insertarPreg(cont, lista);
                }

            } else {
//                vpq.r_a.setName("hola");
                //System.out.println(vpq.r_a.getName());
                JOptionPane.showMessageDialog(null, "Quizz terminado");
            }
        }
    }

    public void insertarPreg(int cont, String lista[]) {
        if (cont == 3) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
        }
        if (cont == 4) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }
        }
        if (cont == 5) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }
        }
        if (cont == 6) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }
        }
        if (cont == 7) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a]) || vpq.txtR6.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR7.setVisible(true);
            vpq.r_g.setVisible(true);
            vpq.txtR7.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }
        }
        if (cont == 8) {
            int a = (int) (Math.random() * cont);
            vpq.txtR1.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR2.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR3.setVisible(true);
            vpq.r_c.setVisible(true);
            vpq.txtR3.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR4.setVisible(true);
            vpq.r_d.setVisible(true);
            vpq.txtR4.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR5.setVisible(true);
            vpq.r_e.setVisible(true);
            vpq.txtR5.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR6.setVisible(true);
            vpq.r_f.setVisible(true);
            vpq.txtR6.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a]) || vpq.txtR6.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR7.setVisible(true);
            vpq.r_g.setVisible(true);
            vpq.txtR7.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }

            a = (int) (Math.random() * cont);
            while (vpq.txtR1.getText().equals(lista[a]) || vpq.txtR2.getText().equals(lista[a]) || vpq.txtR3.getText().equals(lista[a])
                    || vpq.txtR4.getText().equals(lista[a]) || vpq.txtR5.getText().equals(lista[a]) || vpq.txtR6.getText().equals(lista[a])
                    || vpq.txtR7.getText().equals(lista[a])) {
                a = (int) (Math.random() * cont);
            }
            vpq.txtR8.setVisible(true);
            vpq.r_h.setVisible(true);
            vpq.txtR8.setText(lista[a]);
            if (lista[a].equals(varP.getResp1())) {
                vpq.r_a.setName(varP.getR1());
            }
            if (lista[a].equals(varP.getResp2())) {
                vpq.r_a.setName(varP.getR2());
            }
            if (lista[a].equals(varP.getResp3())) {
                vpq.r_a.setName(varP.getR3());
            }
            if (lista[a].equals(varP.getResp4())) {
                vpq.r_a.setName(varP.getR4());
            }
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
