/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Documentos;

import Modelo.ModVariablesDoc;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Antonio
 */
public class tablaPDF {

    PDF pdf = null;

    public void visualizar(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new tabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        dt.addColumn("ID");
        dt.addColumn("Nombre");
        dt.addColumn("Status");
        dt.addColumn("Descripción");
        dt.addColumn("Archivo");

        ImageIcon icono = null;
        if (get_Image("/Imagenes/icons8_PDF_32px.png") != null) {
            icono = new ImageIcon(get_Image("/Imagenes/icons8_PDF_32px.png"));
        }

        pdf = new PDF();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = pdf.Listar_Pdf();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                var = list.get(i);
                fila[0] = var.getId();
                fila[1] = var.getNombre();
                fila[2] = var.getStatus();
                fila[3] = var.getDescripcion();
                if (var.getArchivo() != null) {
                    fila[4] = new JButton(icono);
                } else {
                    fila[4] = new JButton("Vacio");
                }
                dt.addRow(fila);
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32); //Da el tamaño a la tabla (Cada celda)
        }
    }
    
    public Image get_Image(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta));
            Image mainIcon = imageIcon.getImage();
            return mainIcon;
        } catch (Exception e) {
        }
        return null;
    }
}
