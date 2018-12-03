/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModConsultasSQL;
import Modelo.ModVariablesUsr;
import Vista.VstLeerDocs;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
/**
 * Es la clase encargada de ser el controlador de la clase vista leer documentos
 * la cual muestra los documentos disponibles para leectura
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */


public class CtrlLeerDocs implements MouseListener {

    private ModConsultasSQL con;
    private ModVariablesUsr var;
    private VstLeerDocs vld;

/**
 * constructor de la clase enviar
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */      
    
    public CtrlLeerDocs(ModConsultasSQL con, ModVariablesUsr var, VstLeerDocs vld) {
        this.con = con;
        this.var = var;
        this.vld = vld;
    }

/**
 * metodo encargado de iniciar la variable title
 * como "leer documentos"
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
    
    public void iniciar() {
        vld.setTitle("Leer documentos.");
        vld.setLocationRelativeTo(null);
        con.visualizarPE(vld.tablaDocumentos);
    }

/**
 * Es el constructor encargado en recibir y ejecutar la acciones
 * correspondiente a lo que va ocurriendo en la vista vista leer documentos
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */    
    
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
            if (me.getSource() == vld.tablaDocumentos);
        } else {
            JOptionPane.showMessageDialog(null, "La sesión actual fue eliminada.");
            vld.setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
