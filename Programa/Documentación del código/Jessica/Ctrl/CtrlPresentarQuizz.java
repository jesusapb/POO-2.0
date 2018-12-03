
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModListas;
import Modelo.ModVariablesPresentados;
import Modelo.ModVariablesQuizzes;
import Modelo.ModVariablesReg;
import Modelo.ModVariablesRespuestas;
import Modelo.ModVariablesUsr;
import Modelo.ModvariablesPreguntas;
import Vista.VstPresentarQuizz;
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
 * Esta es la clase de Presentar quiz, aqui ocurre la realización del quiz que se haya
 * seleccionado en la tabla por el empleado. 
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco.
 * @version 02/12/2018/ProyectoPoo_Acompañamiento
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
    private String guard[] = new String[10];
    private int Vcontador = 0;
    private int moment = 0;
    private String idsTem = "";
    private int cnt = 0;
    private double pts = 0;

    /**
    * Constructor de la clase
    * @param cons es la clase donde estan almacenadas las funciones de consulta.
    * @param varU es la clase que contiene las variables utilizadas por el usuario y 
    * para que los datos sean almacenados. 
    * @param varQ es la clase que contiene las variables utilizados por los quizzes y 
    * para que los datos sean almacenados. 
    * @param VarP es la clase que contiene las varuiables utilizados por las preguntas y 
    * para que los datos sean almacenados. 
    * @param varPre es la clase que contiene las variables utilizados por los quizzes 
    * presentados y hacer la verificación de si aún puede realizar el quiz (intentos).
    * @param vpq es la interfaz gráfica de PresentarQuiz.
    */ 

    public CtrlPresentarQuizz(ModConsultasSQL cons, ModVariablesUsr varU, ModVariablesQuizzes varQ, ModvariablesPreguntas varP, ModVariablesPresentados varPre, VstPresentarQuizz vpq) {
        this.cons = cons;
        this.varU = varU;
        this.varQ = varQ;
        this.varP = varP;
        this.varPre = varPre;
        this.vpq = vpq;

        this.vpq.btnSigTerm.addActionListener(this);
        this.vpq.btnRegresar.addActionListener(this);
    }

    /**
    * Método para mostrar la interfaz gráfica. 
    */ 

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
        vpq.btnRegresar.setVisible(false);

        if (varPre.getHora().equals("00") && varPre.getMinuto().equals("00")) {
            vpq.cronometro.setVisible(false);
            t = new Timer(10, acciones);
            t.stop();
        } else {
            t = new Timer(10, acciones);
            t.start();
            int b = 0;
            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText(), b);
            String[] lista = new String[8];
            ModListas mens = new ModListas();
            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

            vpq.txtPregunta.setText(varP.getPregunta());
            vpq.nump.setText(varP.getId() + "");
            vpq.contador.setText("1");
            moment = moment + 1;
            vpq.puntosT.setText(varP.getPuntuacion_total());

            int cont = 0;

            if (varP.getTipo().equals("abierto")) {
                vpq.nump_resp.setText("0");
                ocultar();
            } else {
                if (varP.getTipo().equals("unico") || varP.getTipo().equals("multiple")) {
                    vpq.nump_resp.setText(varP.getNum_resp());
                    vpq.abierto.setVisible(false);
                    vpq.txtR1.setVisible(true);
                    vpq.txtR2.setVisible(true);
                    vpq.r_a.setVisible(true);
                    vpq.r_b.setVisible(true);
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

    /**
    *  Es el constructor encargado de recibir y ejecutar las acciones correspondientes
    * a lo que va ocurriendo en la vista de Presentar quiz. 
    * @param e es la variable encargada de recibir cada acción de la interfaz gráfica. 
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
            if (e.getSource() == vpq.btnRegresar) {
                moment = moment - 1;
                int conta = Integer.parseInt(vpq.contador.getText()) - 1;
                vpq.contador.setText(conta + "");
                String[] partir = guard[moment].split("~");
                if (partir[0].equals("abierto")) {
                    ocultar();
                    vpq.txtPregunta.setText(partir[2]);
                    if (partir[3].equals("/*null*/")) {
                        vpq.txtRespuesta.setText(null);
                    } else {
                        vpq.txtRespuesta.setText(partir[3]);
                    }

                    String[] div = guard[moment].split("¬");
                    if (partir[7].equals("/*null*/")) {
                        vpq.ab.setText(null);
                        vpq.abrt.setText(null);
                    } else {
                        vpq.ab.setText(null);
                        vpq.abrt.setText(null);
                        vpq.ab.setText(partir[7]);
                        vpq.abrt.setText(div[1]);
                    }
                    vpq.puntosT.setText(null);
                    vpq.puntos.setText(null);
                    vpq.nump_resp.setText(null);
                    vpq.puntosT.setText(partir[4]);
                    vpq.puntos.setText(partir[5]);
                    vpq.nump_resp.setText(partir[6]);
                    vpq.nump.setText(null);
                    vpq.nump.setText(div[3]);
                    vpq.btnSigTerm.setText("Siguiente");

                    if (conta > 1) {
                        vpq.btnRegresar.setVisible(true);
                    } else {
                        vpq.btnRegresar.setVisible(false);
                    }

                } else {
                    ocultar();
                    vpq.abierto.setVisible(false);
                    vpq.txtR1.setVisible(true);
                    vpq.txtR2.setVisible(true);
                    vpq.r_a.setVisible(true);
                    vpq.r_b.setVisible(true);
                    vpq.txtPregunta.setText(partir[2]);
                    if (partir[3].equals("a")) {
                        vpq.r_a.setSelected(true);
                    } else {
                        vpq.r_a.setSelected(false);
                    }
                    vpq.r_a.setName(partir[4]);
                    vpq.txtR1.setText(partir[5]);
                    if (partir[6].equals("b")) {
                        vpq.r_b.setSelected(true);
                    } else {
                        vpq.r_b.setSelected(false);
                    }
                    vpq.r_b.setName(partir[7]);
                    vpq.txtR2.setText(partir[8]);
                    if (partir[9].equals("nada")) {
                        vpq.r_c.setVisible(false);
                        vpq.txtR3.setVisible(false);
                    } else {
                        vpq.r_c.setVisible(true);
                        vpq.txtR3.setVisible(true);
                        if (partir[9].equals("c")) {
                            vpq.r_c.setSelected(true);
                        } else {
                            vpq.r_c.setSelected(false);
                        }
                        vpq.r_c.setName(partir[10]);
                        vpq.txtR3.setText(partir[11]);
                    }
                    if (partir[12].equals("nada")) {
                        vpq.r_d.setVisible(false);
                        vpq.txtR4.setVisible(false);
                    } else {
                        vpq.r_d.setVisible(true);
                        vpq.txtR4.setVisible(true);
                        if (partir[12].equals("d")) {
                            vpq.r_d.setSelected(true);
                        } else {
                            vpq.r_d.setSelected(false);
                        }
                        vpq.r_d.setName(partir[13]);
                        vpq.txtR4.setText(partir[14]);
                    }
                    if (partir[15].equals("nada")) {
                        vpq.r_e.setVisible(false);
                        vpq.txtR5.setVisible(false);
                    } else {
                        vpq.r_e.setVisible(true);
                        vpq.txtR5.setVisible(true);
                        if (partir[15].equals("e")) {
                            vpq.r_e.setSelected(true);
                        } else {
                            vpq.r_e.setSelected(false);
                        }
                        vpq.r_e.setName(partir[16]);
                        vpq.txtR5.setText(partir[17]);
                    }
                    if (partir[18].equals("nada")) {
                        vpq.r_f.setVisible(false);
                        vpq.txtR6.setVisible(false);
                    } else {
                        vpq.r_f.setVisible(true);
                        vpq.txtR6.setVisible(true);
                        if (partir[18].equals("f")) {
                            vpq.r_f.setSelected(true);
                        } else {
                            vpq.r_f.setSelected(false);
                        }
                        vpq.r_f.setName(partir[19]);
                        vpq.txtR6.setText(partir[20]);
                    }
                    if (partir[21].equals("nada")) {
                        vpq.r_g.setVisible(false);
                        vpq.txtR7.setVisible(false);
                    } else {
                        vpq.r_g.setVisible(true);
                        vpq.txtR7.setVisible(true);
                        if (partir[21].equals("g")) {
                            vpq.r_g.setSelected(true);
                        } else {
                            vpq.r_g.setSelected(false);
                        }
                        vpq.r_g.setName(partir[22]);
                        vpq.txtR7.setText(partir[23]);
                    }
                    if (partir[24].equals("nada")) {
                        vpq.r_h.setVisible(false);
                        vpq.txtR8.setVisible(false);
                    } else {
                        vpq.r_h.setVisible(true);
                        vpq.txtR8.setVisible(true);
                        if (partir[24].equals("h")) {
                            vpq.r_h.setSelected(true);
                        } else {
                            vpq.r_h.setSelected(false);
                        }
                        vpq.r_h.setName(partir[25]);
                        vpq.txtR8.setText(partir[26]);
                    }
                    vpq.puntosT.setText(null);
                    vpq.puntos.setText(null);
                    vpq.puntosT.setText(partir[27]);
                    vpq.puntos.setText(partir[28]);
                    vpq.nump_resp.setText(null);
                    vpq.nump_resp.setText(partir[29]);

                    String[] div = guard[moment].split("¬");
                    if (partir[30].equals("/*null*/")) {
                        vpq.ab.setText(null);
                        vpq.abrt.setText(null);
                    } else {
                        vpq.ab.setText(null);
                        vpq.abrt.setText(null);
                        vpq.ab.setText(partir[30]);
                        vpq.abrt.setText(div[1]);
                    }
                    vpq.nump.setText(null);
                    vpq.nump.setText(div[3]);
                    vpq.btnSigTerm.setText("Siguiente");

                    if (conta > 1) {
                        vpq.btnRegresar.setVisible(true);
                    } else {
                        vpq.btnRegresar.setVisible(false);
                    }
                }
            }

            if (e.getSource() == vpq.btnSigTerm) {
                boolean a = true;
                int b = Integer.parseInt(vpq.contador.getText());
                if (guard[moment] != null) {
                    String[] partir = guard[moment].split("~");
                    varP.setTipo(partir[0]);
                }
                if (varP.getTipo().equals("abierto")) {
                    ModVariablesRespuestas varRe = new ModVariablesRespuestas();
                    varRe.setIdent(varU.getMatricula());
                    varRe.setPuntuacion(varP.getPuntuacion_total());
                    String partir = vpq.NomQuizz.getText();
                    String[] parte = partir.split("/");
                    varRe.setQuizz(parte[0]);
                    varRe.setPregunta(vpq.txtPregunta.getText());
                    varRe.setStatus("Por calificar");
                    varRe.setP_asignada("0");

                    double puntosT = Double.parseDouble(vpq.puntosT.getText());
                    double puntos;
                    if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                        puntos = 0;
                    } else {
                        puntos = Double.parseDouble(vpq.puntos.getText());
                    }

                    int contAb = 0;
                    if (vpq.ab.getText().equals("")) {
                        contAb = 0;
                    } else {
                        contAb = Integer.parseInt(vpq.ab.getText());
                    }
                    vpq.ab.setText((contAb + 1) + "");
                    if (vpq.abrt.getText().equals("")) {
                        vpq.abrt.setText(varP.getPregunta());
                    } else {
                        vpq.abrt.setText(vpq.abrt.getText() + "~" + varP.getPregunta());
                    }
                    if (guard[moment] != null) {
                        String[] cambio = guard[moment].split("~");
                        String[] div = guard[moment].split("¬");
                        vpq.ab.setText(cambio[7]);
                        vpq.abrt.setText(div[1]);
                    }

                    String todo = null;
                    if (vpq.txtRespuesta.getText().equals("")) {
                        varRe.setRespuesta(vpq.txtRespuesta.getText());
                        if (vpq.ab.getText().equals("")) {
                            todo = varP.getTipo() + "~" + vpq.contador.getText() + "~" + vpq.txtPregunta.getText() + "~/*null*/" + "~" + puntosT + "~" + puntos + "~" + vpq.nump_resp.getText() + "~" + "/*null*/~¬/*null*/¬~¬" + vpq.nump.getText();
                        } else {
                            varP.setPregunta(vpq.abrt.getText());
                            todo = varP.getTipo() + "~" + vpq.contador.getText() + "~" + vpq.txtPregunta.getText() + "~/*null*/~" + puntosT + "~" + puntos + "~" + vpq.nump_resp.getText() + "~" + vpq.ab.getText() + "~¬" + vpq.abrt.getText() + "¬~¬" + vpq.nump.getText();
                        }
                    } else {
                        varRe.setRespuesta(vpq.txtRespuesta.getText());
                        if (vpq.ab.getText().equals("")) {
                            todo = varP.getTipo() + "~" + vpq.contador.getText() + "~" + vpq.txtPregunta.getText() + "~" + vpq.txtRespuesta.getText() + "~" + puntosT + "~" + puntos + "~" + vpq.nump_resp.getText() + "~/*null*/~¬/*null*/¬~¬" + vpq.nump.getText();
                        } else {
                            varP.setPregunta(vpq.abrt.getText());
                            todo = varP.getTipo() + "~" + vpq.contador.getText() + "~" + vpq.txtPregunta.getText() + "~" + vpq.txtRespuesta.getText() + "~" + puntosT + "~" + puntos + "~" + vpq.nump_resp.getText() + "~" + vpq.ab.getText() + "~¬" + vpq.abrt.getText() + "¬~¬" + vpq.nump.getText();
                        }
                    }
                    guard[moment] = todo;
                    String[] otro = guard[moment].split("~");

                    if (cons.existeRP(varU.getMatricula(), parte[0], otro[2]) == 1) {
                        if (otro[3].equals("")) {
                            if (cons.rPAbiertaMod(varU.getMatricula(), parte[0], otro[2], "/*null*/")); else {
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
                                a = false;
                            }
                        } else {
                            if (cons.rPAbiertaMod(varU.getMatricula(), parte[0], otro[2], otro[3])); else {
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
                                a = false;
                            }
                        }
                    } else {
                        ModListas mens = new ModListas();
                        String intento = "";
                        ArrayList<ModVariablesPresentados> list = mens.listaPre(varU.getMatricula(), parte[0]);
                        ModVariablesPresentados var = new ModVariablesPresentados();

                        if (list.size() > 0) {
                            for (int i = list.size() - 1; i < list.size(); i++) {
                                var = list.get(i);
                                int intentosAc = Integer.parseInt(var.getIntento()) + 1;

                                intento = intentosAc + "";
                            }
                        } else {
                            intento = "1";
                        }

                        if (cons.rPAbierta(varRe, intento)); else {
                            JOptionPane.showMessageDialog(null, "Error en la conexión.");
                            a = false;
                        }
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
                    double acum;
                    double puntos = 0;
                    String va, vb, vc, vd, ve, vf, vg, vh;
                    if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                        puntos += 0;
                    } else {
                        puntos += Double.parseDouble(vpq.puntos.getText());
                    }

                    if (!varP.getTipo().equals("abierto")) {
                        if (vpq.r_a.isSelected()) {
                            ra = Double.parseDouble(vpq.r_a.getName());
                            contad = contad + 1;
                            va = "a~" + vpq.r_a.getName() + "~" + vpq.txtR1.getText();
                        } else {
                            va = "0~" + vpq.r_a.getName() + "~" + vpq.txtR1.getText();
                        }
                        if (vpq.r_b.isSelected()) {
                            rb = Double.parseDouble(vpq.r_b.getName());
                            contad = contad + 1;
                            vb = "b~" + vpq.r_b.getName() + "~" + vpq.txtR2.getText();
                        } else {
                            vb = "0~" + vpq.r_b.getName() + "~" + vpq.txtR2.getText();
                        }
                        if (vpq.r_c.isSelected()) {
                            rc = Double.parseDouble(vpq.r_c.getName());
                            contad = contad + 1;
                            vc = "c~" + vpq.r_c.getName() + "~" + vpq.txtR3.getText();
                        } else {
                            if (vpq.txtR3.getText().equals("")) {
                                vc = "nada~nada~nada";
                            } else {
                                vc = "0~" + vpq.r_c.getName() + "~" + vpq.txtR3.getText();
                            }
                        }
                        if (vpq.r_d.isSelected()) {
                            rd = Double.parseDouble(vpq.r_d.getName());
                            contad = contad + 1;
                            vd = "d~" + vpq.r_d.getName() + "~" + vpq.txtR4.getText();
                        } else {
                            if (vpq.txtR4.getText().equals("")) {
                                vd = "nada~nada~nada";
                            } else {
                                vd = "0~" + vpq.r_d.getName() + "~" + vpq.txtR4.getText();
                            }
                        }
                        if (vpq.r_e.isSelected()) {
                            re = Double.parseDouble(vpq.r_e.getName());
                            contad = contad + 1;
                            ve = "e~" + vpq.r_e.getName() + "~" + vpq.txtR5.getText();
                        } else {
                            if (vpq.txtR5.getText().equals("")) {
                                ve = "nada~nada~nada";
                            } else {
                                ve = "0~" + vpq.r_e.getName() + "~" + vpq.txtR5.getText();
                            }
                        }
                        if (vpq.r_f.isSelected()) {
                            rf = Double.parseDouble(vpq.r_f.getName());
                            contad = contad + 1;
                            vf = "f~" + vpq.r_f.getName() + "~" + vpq.txtR6.getText();
                        } else {
                            if (vpq.txtR6.getText().equals("")) {
                                vf = "nada~nada~nada";
                            } else {
                                vf = "0~" + vpq.r_f.getName() + "~" + vpq.txtR6.getText();
                            }
                        }
                        if (vpq.r_g.isSelected()) {
                            rg = Double.parseDouble(vpq.r_g.getName());
                            contad = contad + 1;
                            vg = "d~" + vpq.r_g.getName() + "~" + vpq.txtR7.getText();
                        } else {
                            if (vpq.txtR7.getText().equals("")) {
                                vg = "nada~nada~nada";
                            } else {
                                vg = "0~" + vpq.r_g.getName() + "~" + vpq.txtR7.getText();
                            }
                        }
                        if (vpq.r_h.isSelected()) {
                            rh = Double.parseDouble(vpq.r_h.getName());
                            contad = contad + 1;
                            vh = "h~" + vpq.r_h.getName() + "~" + vpq.txtR8.getText();
                        } else {
                            if (vpq.txtR8.getText().equals("")) {
                                vh = "nada~nada~nada";
                            } else {
                                vh = "0~" + vpq.r_h.getName() + "~" + vpq.txtR8.getText();
                            }
                        }

                        int nump_resp = Integer.parseInt(vpq.nump_resp.getText());
                        if (contad > nump_resp) {
                            acum = puntos;
                        } else {
                            acum = ra + rb + rc + rd + re + rf + rg + rh + puntos;
                        }

                        String ab, abrt;

                        if (vpq.ab.getText().equals("")) {
                            ab = "/*null*/";
                            abrt = "/*null*/";
                        } else {
                            ab = vpq.ab.getText();
                            abrt = vpq.abrt.getText();
                        }

                        contad = 0;
                        vpq.puntos.setText(acum + "");
                        double puntosT = Double.parseDouble(vpq.puntosT.getText());
                        String todo = varP.getTipo() + "~" + vpq.contador.getText() + "~" + vpq.txtPregunta.getText() + "~" + va + "~" + vb + "~" + vc + "~" + vd + "~" + ve + "~" + vf + "~" + vg + "~" + vh + "~" + puntosT + "~" + puntos + "~" + vpq.nump_resp.getText() + "~" + ab + "~¬" + abrt + "¬~¬" + vpq.nump.getText();
                        guard[moment] = todo;
                    }

                    int conta = Integer.parseInt(vpq.contador.getText()) + 1;
                    cnt = conta - 1;
                    int com = Integer.parseInt(varPre.getTotales());

                    if (guard[moment + 1] != null) {
                        moment = moment + 1;
                        vpq.contador.setText(conta + "");
                        String[] partir = guard[moment].split("~");
                        if (partir[0].equals("abierto")) {
                            ocultar();
                            vpq.txtPregunta.setText(partir[2]);
                            if (partir[3].equals("/*null*/")) {
                                vpq.txtRespuesta.setText(null);
                            } else {
                                vpq.txtRespuesta.setText(partir[3]);
                            }

                            String[] div = guard[moment].split("¬");
                            if (partir[7].equals("/*null*/")) {
                                vpq.ab.setText(null);
                                vpq.abrt.setText(null);
                            } else {
                                vpq.ab.setText(null);
                                vpq.abrt.setText(null);
                                vpq.ab.setText(partir[7]);
                                vpq.abrt.setText(div[1]);
                            }
                            vpq.puntosT.setText(null);
                            vpq.nump_resp.setText(null);
                            vpq.puntosT.setText(partir[4]);
                            vpq.nump_resp.setText(partir[6]);
                            vpq.nump.setText(null);
                            vpq.nump.setText(div[3]);
                            vpq.btnSigTerm.setText("Siguiente");

                            if (conta > 1) {
                                vpq.btnRegresar.setVisible(true);
                            } else {
                                vpq.btnRegresar.setVisible(false);
                            }

                        } else {
                            ocultar();
                            vpq.abierto.setVisible(false);
                            vpq.txtR1.setVisible(true);
                            vpq.txtR2.setVisible(true);
                            vpq.r_a.setVisible(true);
                            vpq.r_b.setVisible(true);
                            vpq.txtPregunta.setText(partir[2]);
                            if (partir[3].equals("a")) {
                                vpq.r_a.setSelected(true);
                            } else {
                                vpq.r_a.setSelected(false);
                            }
                            vpq.r_a.setName(partir[4]);
                            vpq.txtR1.setText(partir[5]);
                            if (partir[6].equals("b")) {
                                vpq.r_b.setSelected(true);
                            } else {
                                vpq.r_b.setSelected(false);
                            }
                            vpq.r_b.setName(partir[7]);
                            vpq.txtR2.setText(partir[8]);
                            if (partir[9].equals("nada")) {
                                vpq.r_c.setVisible(false);
                                vpq.txtR3.setVisible(false);
                            } else {
                                vpq.r_c.setVisible(true);
                                vpq.txtR3.setVisible(true);
                                if (partir[9].equals("c")) {
                                    vpq.r_c.setSelected(true);
                                } else {
                                    vpq.r_c.setSelected(false);
                                }
                                vpq.r_c.setName(partir[10]);
                                vpq.txtR3.setText(partir[11]);
                            }
                            if (partir[12].equals("nada")) {
                                vpq.r_d.setVisible(false);
                                vpq.txtR4.setVisible(false);
                            } else {
                                vpq.r_d.setVisible(true);
                                vpq.txtR4.setVisible(true);
                                if (partir[12].equals("d")) {
                                    vpq.r_d.setSelected(true);
                                } else {
                                    vpq.r_d.setSelected(false);
                                }
                                vpq.r_d.setName(partir[13]);
                                vpq.txtR4.setText(partir[14]);
                            }
                            if (partir[15].equals("nada")) {
                                vpq.r_e.setVisible(false);
                                vpq.txtR5.setVisible(false);
                            } else {
                                vpq.r_e.setVisible(true);
                                vpq.txtR5.setVisible(true);
                                if (partir[15].equals("e")) {
                                    vpq.r_e.setSelected(true);
                                } else {
                                    vpq.r_e.setSelected(false);
                                }
                                vpq.r_e.setName(partir[16]);
                                vpq.txtR5.setText(partir[17]);
                            }
                            if (partir[18].equals("nada")) {
                                vpq.r_f.setVisible(false);
                                vpq.txtR6.setVisible(false);
                            } else {
                                vpq.r_f.setVisible(true);
                                vpq.txtR6.setVisible(true);
                                if (partir[18].equals("f")) {
                                    vpq.r_f.setSelected(true);
                                } else {
                                    vpq.r_f.setSelected(false);
                                }
                                vpq.r_f.setName(partir[19]);
                                vpq.txtR6.setText(partir[20]);
                            }
                            if (partir[21].equals("nada")) {
                                vpq.r_g.setVisible(false);
                                vpq.txtR7.setVisible(false);
                            } else {
                                vpq.r_g.setVisible(true);
                                vpq.txtR7.setVisible(true);
                                if (partir[21].equals("g")) {
                                    vpq.r_g.setSelected(true);
                                } else {
                                    vpq.r_g.setSelected(false);
                                }
                                vpq.r_g.setName(partir[22]);
                                vpq.txtR7.setText(partir[23]);
                            }
                            if (partir[24].equals("nada")) {
                                vpq.r_h.setVisible(false);
                                vpq.txtR8.setVisible(false);
                            } else {
                                vpq.r_h.setVisible(true);
                                vpq.txtR8.setVisible(true);
                                if (partir[24].equals("h")) {
                                    vpq.r_h.setSelected(true);
                                } else {
                                    vpq.r_h.setSelected(false);
                                }
                                vpq.r_h.setName(partir[25]);
                                vpq.txtR8.setText(partir[26]);
                            }
                            vpq.puntosT.setText(null);
                            vpq.puntosT.setText(partir[27]);
                            vpq.nump_resp.setText(null);
                            vpq.nump_resp.setText(partir[29]);

                            String[] div = guard[moment].split("¬");
                            if (partir[30].equals("/*null*/")) {
                                vpq.ab.setText(null);
                                vpq.abrt.setText(null);
                            } else {
                                vpq.ab.setText(null);
                                vpq.abrt.setText(null);
                                vpq.ab.setText(partir[30]);
                                vpq.abrt.setText(div[1]);
                            }
                            vpq.nump.setText(null);
                            vpq.nump.setText(div[3]);
                            vpq.btnSigTerm.setText("Siguiente");

                            if (conta > 1) {
                                vpq.btnRegresar.setVisible(true);
                            } else {
                                vpq.btnRegresar.setVisible(false);
                            }
                        }
                    } else {
                        if (conta <= com) {
                            ModConsultasSQL.obtenerPreg(varP, varQ.getId(), vpq.nump.getText(), b);
                            String[] lista = new String[8];
                            ModListas mens = new ModListas();
                            String[] temp = mens.listaResp(varP.getId(), varQ.getId());

                            if (conta == com) {
                                vpq.btnSigTerm.setText("Terminar");
                            }

                            if (conta > 1) {
                                vpq.btnRegresar.setVisible(true);
                            } else {
                                vpq.btnRegresar.setVisible(false);
                            }
                            vpq.txtPregunta.setText(varP.getPregunta());
                            vpq.nump.setText(vpq.nump.getText() + "~" + varP.getId());
                            vpq.contador.setText(conta + "");
                            moment = moment + 1;
                            double puntosT = Double.parseDouble(vpq.puntosT.getText());
                            puntosT += Double.parseDouble(varP.getPuntuacion_total());
                            vpq.puntosT.setText(puntosT + "");

                            vpq.setVisible(true);
                            ocultar();

                            int cont = 0;

                            if (varP.getTipo().equals("abierto")) {
                                vpq.nump_resp.setText("0");
                                vpq.txtR1.setVisible(false);
                                vpq.txtR2.setVisible(false);
                                vpq.abierto.setVisible(true);
                            } else {
                                if (varP.getTipo().equals("unico") || varP.getTipo().equals("multiple")) {
                                    vpq.nump_resp.setText(varP.getNum_resp());
                                    vpq.txtR1.setVisible(true);
                                    vpq.txtR2.setVisible(true);
                                    vpq.r_a.setVisible(true);
                                    vpq.r_b.setVisible(true);
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
                            if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                                puntos = 0;
                            } else {
                                puntos = Double.parseDouble(vpq.puntos.getText());
                            }

                            String partir = vpq.NomQuizz.getText();
                            String[] parte = partir.split("/");
                            varPre.setIdent(varU.getMatricula());
                            varPre.setQuizz(parte[0]);

                            ModListas mens = new ModListas();
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

                            int contAb;
                            int contAbTot = 0;
                            String marca;
                            if (!vpq.ab.getText().equals("")) {
                                varPre.setStatus("Por calificar");
                                que = "Terminó de presentar el Quizz: ''" + parte[0] + "'', se necesita la revision y calificación de la(s) pregunta(s) abierta(s)";
                                varPre.setAbrt(vpq.abrt.getText());
                                contAb = Integer.parseInt(vpq.ab.getText());
                                marca = "Acceso";
                                contAbTot = Integer.parseInt(vpq.contador.getText());
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
                                contAb = 0;
                                marca = "Sin acceso";
                            }

                            varPre.setP_totales(fin + "~100");
                            varPre.setCalificacion(fin1 + "~" + fin2 + "~0");

                            if (cons.rPresentados(varPre, contAb, contAbTot, varQ.getMod_calif(), marca)) {
                                if (cons.existeCalif(varU.getMatricula(), parte[0], varQ.getMod_calif()) == 1) {
                                    if ((Integer.parseInt(varPre.getIntento()) - 1) > 0) {
                                        cons.camMarca(varU.getMatricula(), parte[0], (Integer.parseInt(varPre.getIntento()) - 1) + "");
                                    }

                                    String Tpuntos = cons.Ptotales(varU.getMatricula(), parte[0], varQ.getMod_calif());
                                    String[] partirP = Tpuntos.split("~");
                                    int intentos = Integer.parseInt(varPre.getIntento());
                                    double suma = 0;
                                    double alto = fin2;
                                    for (int i = 0; i < (intentos - 1); i++) {
                                        suma = suma + Double.parseDouble(partirP[i]);
                                        if (alto < Double.parseDouble(partirP[i])) {
                                            alto = Double.parseDouble(partirP[i]);
                                        }
                                    }
                                    String status = "";
                                    double promedio = (suma + fin2) / intentos;
                                    if (promedio < 70) {
                                        status = "Reprobado";
                                    } else {
                                        status = "Aprobado";
                                    }

                                    DecimalFormat pr = new DecimalFormat("#.00");
                                    String prom = pr.format(promedio);
                                    double punt = Double.parseDouble(prom);

                                    if (varQ.getMod_calif().equals("Promedio de calificaciones")) {
                                        cons.updCalif(punt + "", status, varPre.getIntento(), Tpuntos + "" + fin2 + "~", varU.getMatricula(), parte[0], varQ.getMod_calif());
                                    } else if (varQ.getMod_calif().equals("Calificacion más alta")) {
                                        cons.updCalif(alto + "", varPre.getStatus(), varPre.getIntento(), Tpuntos + "" + fin2 + "~", varU.getMatricula(), parte[0], varQ.getMod_calif());
                                    }
                                } else {
                                    cons.rCalif(varU.getMatricula(), varPre.getIntento(), parte[0], fin2 + "", varPre.getStatus(), varQ.getMod_calif(), fin2 + "~");
                                }

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
                                t.stop();
                                if (vpq.abrt.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "Quizz terminado, Puntuación: " + fin2 + ", Status: " + status + ".");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Quizz terminado, espere que un administrador le califique.");
                                }
                                vpq.setVisible(false);
                            } else {
                                t.stop();
                                JOptionPane.showMessageDialog(null, "Error en la conexión.");
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

    public void ocultar() {
        vpq.txtR1.setVisible(false);
        vpq.txtR2.setVisible(false);
        vpq.abierto.setVisible(true);

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
    }
    /**
    * Método que selecciona preguntas del quiz de manera aleatoria 
    * @param cont variable 
    * @param lista[] id de la pregunta almacenada
    */

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
        /**
        * Método que toma en cuenta el tiempo asignado al quiz. Al realizar el quiz, empieza a correr el tiempo,
        *cuando este finaliza, guarda la puntuación hasta la pregunta anterior. 
        @param ae variable encargada en recibir el tiempo límite y llevar a cabo
        * su conteo. 
        */ 

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
                JOptionPane.showMessageDialog(null, "Tiempo agotado.");
                if ((Integer.parseInt(vpq.contador.getText()) - 1) == 0); else {
                    String partir = vpq.NomQuizz.getText();
                    String[] parte = partir.split("/");
                    varPre.setIdent(varU.getMatricula());
                    varPre.setQuizz(parte[0]);

                    ModListas mens = new ModListas();
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
                    int p_totales = Integer.parseInt(varQ.getP_totales());
                    double p = (cnt * 100) / p_totales;
                    String conv = op.format(p);
                    double porcentage = Double.parseDouble(conv);
                    double pp = ((Double.parseDouble(vpq.puntosT.getText()) - Double.parseDouble(varP.getPuntuacion_total())) / cnt);
                    String bd = op.format(pp);
                    double fin = Double.parseDouble(bd);
                    DecimalFormat op1 = new DecimalFormat("#.00");
                    double ppp = 0;
                    if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                        ppp = 0;
                    } else {
                        ppp = (Double.parseDouble(vpq.puntos.getText()) / cnt);
                    }
                    String bd1 = op1.format(ppp);
                    double fin1 = Double.parseDouble(bd1);

                    String que;
                    double fin2 = 0;

                    int contAb;
                    int contAbTot = 0;
                    String marca;
                    if (!vpq.ab.getText().equals("")) {
                        varPre.setStatus("Por calificar/Sin terminar");
                        que = "No terminó de presentar el Quizz: ''" + parte[0] + "'', por que se le terminó el tiempo "
                                + "pero se necesita la revision y calificación de la(s) pregunta(s) abierta(s)";
                        varPre.setAbrt(vpq.abrt.getText());
                        contAb = Integer.parseInt(vpq.ab.getText());
                        marca = "Acceso";
                        contAbTot = Integer.parseInt(vpq.contador.getText());
                    } else {
                        DecimalFormat op2 = new DecimalFormat("#.00");
                        double prom = 0;
                        if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                            prom = (0 * porcentage) / ((Double.parseDouble(vpq.puntosT.getText()) - Double.parseDouble(varP.getPuntuacion_total())) / cnt);
                        } else {
                            prom = ((Double.parseDouble(vpq.puntos.getText()) / cnt) * porcentage) / ((Double.parseDouble(vpq.puntosT.getText()) - Double.parseDouble(varP.getPuntuacion_total())) / cnt);
                        }
                        String bd2 = op2.format(prom);
                        fin2 = Double.parseDouble(bd2);

                        if (fin2 < 70) {
                            que = "No terminó de presentar el Quizz: '" + parte[0] + "' pero obtuvo una puntuación de: " + fin2 + " (Reprobado).";
                            varPre.setStatus("Reprobado/Sin terminar");
                        } else {
                            que = "No terminó de presentar el Quizz: '" + parte[0] + "' pero obtuvo una puntuación de: " + fin2 + " (Aprobado).";
                            varPre.setStatus("Aprobado/Sin terminar");
                        }
                        varPre.setAbrt("nada");
                        contAb = 0;
                        marca = "Sin acceso";
                    }

                    varPre.setP_totales(fin + "~" + porcentage);
                    varPre.setCalificacion(fin1 + "~" + fin2 + "~0");

                    if (cons.rPresentados(varPre, contAb, contAbTot, varQ.getMod_calif(), marca)) {
                        if (cons.existeCalif(varU.getMatricula(), parte[0], varQ.getMod_calif()) == 1) {
                            if ((Integer.parseInt(varPre.getIntento()) - 1) > 0) {
                                cons.camMarca(varU.getMatricula(), parte[0], (Integer.parseInt(varPre.getIntento()) - 1) + "");
                            }

                            String Tpuntos = cons.Ptotales(varU.getMatricula(), parte[0], varQ.getMod_calif());
                            String[] partirP = Tpuntos.split("~");
                            int intentos = Integer.parseInt(varPre.getIntento());
                            double suma = 0;
                            double alto = fin2;
                            for (int i = 0; i < (intentos - 1); i++) {
                                suma = suma + Double.parseDouble(partirP[i]);
                                if (alto < Double.parseDouble(partirP[i])) {
                                    alto = Double.parseDouble(partirP[i]);
                                }
                            }
                            String status = "";
                            double promedio = (suma + fin2) / intentos;
                            if (promedio < 70) {
                                status = "Reprobado";
                            } else {
                                status = "Aprobado";
                            }

                            DecimalFormat pr = new DecimalFormat("#.00");
                            String prom = pr.format(promedio);
                            double punt = Double.parseDouble(prom);

                            if (varQ.getMod_calif().equals("Promedio de calificaciones")) {
                                cons.updCalif(punt + "", status, varPre.getIntento(), Tpuntos + "" + fin2 + "~", varU.getMatricula(), parte[0], varQ.getMod_calif());
                            } else if (varQ.getMod_calif().equals("Calificacion más alta")) {
                                cons.updCalif(alto + "", varPre.getStatus(), varPre.getIntento(), Tpuntos + "" + fin2 + "~", varU.getMatricula(), parte[0], varQ.getMod_calif());
                            }
                        } else {
                            cons.rCalif(varU.getMatricula(), varPre.getIntento(), parte[0], fin2 + "", varPre.getStatus(), varQ.getMod_calif(), fin2 + "~");
                        }

                        Date date = new Date();
                        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
                        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

                        DecimalFormat op2 = new DecimalFormat("#.00");
                        double prom = 0;
                        if (vpq.puntos.getText().equals("") || vpq.puntos.getText().equals("0")) {
                            prom = (0 * porcentage) / ((Double.parseDouble(vpq.puntosT.getText()) - Double.parseDouble(varP.getPuntuacion_total())) / cnt);
                        } else {
                            prom = ((Double.parseDouble(vpq.puntos.getText()) / cnt) * porcentage) / ((Double.parseDouble(vpq.puntosT.getText()) - Double.parseDouble(varP.getPuntuacion_total())) / cnt);
                        }
                        String bd2 = op2.format(prom);
                        fin2 = Double.parseDouble(bd2);

                        ModVariablesReg varReg = new ModVariablesReg();
                        String tipo = "Administrador";
                        String quien = varU.getMatricula() + "/ " + varU.getNombre_completo();
                        String cuando = fechaDate.format(date) + " " + horaDate.format(date);
                        String comp = varU.getMatricula();
                        if (cons.avisoAA(varReg, tipo, quien, que, cuando, comp));
                        String status;
                        if (fin2 < 70) {
                            status = "Reprobado/Sin terminar";
                        } else {
                            status = "Aprobado/Sin terminar";
                        }
                        t.stop();
                        if (vpq.abrt.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Quizz terminado por tiempo, Puntuación: " + fin2 + ", Status: " + status + ".");
                        } else {
                            JOptionPane.showMessageDialog(null, "Quizz terminado por tiempo, espere que un administrador le califique.");
                        }
                        vpq.setVisible(false);
                    } else {
                        t.stop();
                        JOptionPane.showMessageDialog(null, "Error en la conexión.");
                        vpq.setVisible(false);
                    }
                }
                vpq.setVisible(false);
            }
            actualizarLabel();

        }
    };

    private void actualizarLabel() {
        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s/* + ":" + (cs <= 9 ? "0" : "") + cs*/;
        vpq.cronometro.setText(tiempo);
    }
}
