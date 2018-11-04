/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Empleado;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.Empleado.VstLeerDocs;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class CtrlLeerDocs implements MouseListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private VstLeerDocs vld;

    public CtrlLeerDocs(ModConsultasSQL con, ModVariablesUsr var, VstLeerDocs vld) {
        this.con = con;
        this.var = var;
        this.vld = vld;
    }

    public void iniciar() {
        vld.setTitle("Leer documentos.");
        vld.setLocationRelativeTo(null);
        con.visualizarPE(vld.tablaDocumentos);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Date date = new Date();
        DateFormat horaDate = new SimpleDateFormat("HH:mm:ss");
        DateFormat fechaDate = new SimpleDateFormat("dd/MM/yyyy");

        var.setMatricula(var.getMatricula());
        var.setDia(fechaDate.format(date));
        var.setHora(horaDate.format(date));
        ModConsultasSQL.recarga(var);
        ModConsultasSQL.status(var);

        if (con.existeUsr(var.getMatricula()) == 1) {
            if ("Permanente".equals(var.getStatus())) {
                JOptionPane.showMessageDialog(null, "Acceso denegado.");
                vld.setVisible(false);
            } else {
                if (me.getSource() == vld.tablaDocumentos) {
//                    int id = -1;
//                    int column = vld.tablaDocumentos.getColumnModel().getColumnIndexAtX(me.getX());//Posicion donde se da "click" el la columna
//                    int row = me.getY() / vld.tablaDocumentos.getRowHeight();
//                    if (row < vld.tablaDocumentos.getRowCount() && row >= 0 && column < vld.tablaDocumentos.getColumnCount() && column >= 0) {
//
//                        id = (int) vld.tablaDocumentos.getValueAt(row, 0);
//                        Object value = vld.tablaDocumentos.getValueAt(row, column);
//                        if (value instanceof JButton) {
//                            ((JButton) value).doClick();
//                            JButton boton = (JButton) value;
//
//                            if (boton.getText().equals("Vacio")) {
//                                JOptionPane.showMessageDialog(null, "No hay archivo");
//                            } else {
//                                ModConsultasSQL pdf = new ModConsultasSQL();
//                                pdf.abrirD(id);
//                                try {
//                                    Desktop.getDesktop().open(new File("new.pdf"));
//                                } catch (IOException ex) {
//                                }
//                            }
//                        }
//                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vld.setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
