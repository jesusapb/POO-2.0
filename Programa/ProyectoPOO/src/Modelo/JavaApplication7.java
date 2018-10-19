/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Antonio
 */
public class JavaApplication7 {
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
}

//                                        if (con.rPreguntas(var)) {
//                                            try {
//                                                PreparedStatement ps = null;
//                                                ModConexion objCon = new ModConexion();
//                                                Connection con = objCon.getConexion();
//                                                ps = con.prepareStatement("UPDATE quizzes SET p_actuales = ? WHERE id = '" + vp.id.getText() + "'");
//                                                ps.setInt(1, num);
//                                                ps.execute();
//
//                                                JOptionPane.showMessageDialog(null, "Se agregó exitosamente la pregunta.");
//                                                vp.actual.setText(num + "");
//                                                vq.actual.setText(num + "");
//                                                ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
//                                                limpiar();
//                                            } catch (SQLException ex) {
//                                                Logger.getLogger(CtrlPreguntas.class.getName()).log(Level.SEVERE, null, ex);
//                                            }
//                                        } else {
//                                            JOptionPane.showMessageDialog(null, "Error al agregar la pregunta.");
//                                        }

//                                        if (con.mPreguntas(var, vp)) {
//                                            JOptionPane.showMessageDialog(null, "Se modificó exitosamente la pregunta.");
//                                            ModConsultasSQL.tablaQuizz(vq.tablaQuizzes);
//                                            limpiar();
//                                        } else {
//                                            JOptionPane.showMessageDialog(null, "Error al moficar la pregunta.");
//                                        }