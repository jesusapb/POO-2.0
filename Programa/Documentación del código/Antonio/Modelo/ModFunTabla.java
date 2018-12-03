package Modelo;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Esta clase está para el funcionamiento de la tabla que se puede observar en
 * Agregar documentos (Administrador) y Leer documentos (Empleado).
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModFunTabla extends DefaultTableCellRenderer {

    /**
     * Constructor encargado en asirnar un boton a cada fila donde exista algún
     * documento que esté almacenado en la base de datos.
     *
     * @param table es la tabla de la interfaz gráfica en la cual se visualizan
     * los documentos existentes y accesibles para la lectura.
     * @param value es la posición dentro de la tabla donde va a estar asignado
     * el botón.
     * @param isSelected valor de selección en la tabla.
     * @param hasFocus
     * @param row la fila correspondiente de la tabla.
     * @param column columna correspondiente de la tabla.
     * @return la generación del botón en el campo correspondiente.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof JLabel) {
            JLabel lbl = (JLabel) value;
            return lbl;
        }

        if (value instanceof JButton) {
            JButton button = (JButton) value;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.backgroud"));
            }
            return button;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
