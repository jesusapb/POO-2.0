/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Empleado;

/**
 *
 * @author Antonio
 */
public class VstPresentarQuizz extends javax.swing.JFrame {

    /**
     * Creates new form VstPresentarQuizz
     */
    public VstPresentarQuizz() {
        initComponents();

//        JOptionPane.showMessageDialog(null, horas.getText() + minutos.getText());

//        if (horas.getText().equals("") && minutos.getText().equals("")) {
//            cronometro.setVisible(false);
//        }

//        t = new Timer(10, acciones);
//        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abierto = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtRespuesta = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPregunta = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtR1 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtR2 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtR3 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtR4 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtR5 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtR6 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtR7 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtR8 = new javax.swing.JTextPane();
        contador = new javax.swing.JTextField();
        r_a = new javax.swing.JCheckBox();
        r_b = new javax.swing.JCheckBox();
        r_c = new javax.swing.JCheckBox();
        r_d = new javax.swing.JCheckBox();
        r_e = new javax.swing.JCheckBox();
        r_f = new javax.swing.JCheckBox();
        r_g = new javax.swing.JCheckBox();
        r_h = new javax.swing.JCheckBox();
        cronometro = new javax.swing.JLabel();
        btnSigTerm = new javax.swing.JButton();
        NomQuizz = new javax.swing.JTextField();
        nump = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        abierto.setBackground(new java.awt.Color(255, 255, 255));
        abierto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setViewportView(txtRespuesta);

        abierto.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 410, 370));

        getContentPane().add(abierto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 450, 400));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setEnabled(false);

        txtPregunta.setEditable(false);
        jScrollPane1.setViewportView(txtPregunta);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 390, 90));

        txtR1.setEditable(false);
        jScrollPane3.setViewportView(txtR1);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 300, 40));

        txtR2.setEditable(false);
        jScrollPane4.setViewportView(txtR2);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 300, 40));

        txtR3.setEditable(false);
        jScrollPane5.setViewportView(txtR3);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 300, 40));

        txtR4.setEditable(false);
        jScrollPane6.setViewportView(txtR4);

        jPanel1.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 300, 40));

        txtR5.setEditable(false);
        jScrollPane7.setViewportView(txtR5);

        jPanel1.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, 300, 40));

        txtR6.setEditable(false);
        jScrollPane2.setViewportView(txtR6);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 300, 40));

        txtR7.setEditable(false);
        jScrollPane9.setViewportView(txtR7);

        jPanel1.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, 300, 40));

        txtR8.setEditable(false);
        jScrollPane8.setViewportView(txtR8);

        jPanel1.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 300, 40));

        contador.setEditable(false);
        contador.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(contador, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 120, -1));

        r_a.setText("A)");
        r_a.setContentAreaFilled(false);
        r_a.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_a, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 70, -1));

        r_b.setText("B)");
        r_b.setContentAreaFilled(false);
        r_b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_b, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 70, -1));

        r_c.setText("C)");
        r_c.setContentAreaFilled(false);
        r_c.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_c, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 70, -1));

        r_d.setText("D)");
        r_d.setContentAreaFilled(false);
        r_d.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_d, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 70, -1));

        r_e.setText("E)");
        r_e.setContentAreaFilled(false);
        r_e.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_e, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 70, -1));

        r_f.setText("F)");
        r_f.setContentAreaFilled(false);
        r_f.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_f, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 70, -1));

        r_g.setText("G)");
        r_g.setContentAreaFilled(false);
        r_g.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_g, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 70, -1));

        r_h.setText("H)");
        r_h.setContentAreaFilled(false);
        r_h.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(r_h, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 70, -1));

        cronometro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cronometro.setText("00:00:00");
        jPanel1.add(cronometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 110, -1));

        btnSigTerm.setText("Siguente");
        btnSigTerm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnSigTerm, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, -1, -1));

        NomQuizz.setEditable(false);
        NomQuizz.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(NomQuizz, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 120, -1));

        nump.setEditable(false);
        nump.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(nump, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 140, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    private Timer t;
//    private int h, m, s, cs;
//    private ActionListener acciones = new ActionListener() {
//        @Override
//
//        public void actionPerformed(ActionEvent ae) {
//            int Thoras, Tminutos;
//
//            if (horas.getText().equals("") || horas.getText().equals("0")) {
//                Thoras = 0;
//            } else {
//                Thoras = Integer.parseInt(horas.getText());
//            }
//
//            if (minutos.getText().equals("") || minutos.getText().equals("0")) {
//                Tminutos = 0;
//            } else {
//                Tminutos = Integer.parseInt(minutos.getText());
//            }
//
//            cs++;
//            if (cs == 100) {
//                cs = 0;
//                ++s;
//            }
//            if (s == 60) {
//                s = 0;
//                ++m;
//            }
//            if (m == 60) {
//                m = 0;
//                ++h;
//            }
//            if (cs == 0 && s == 0 && h == Thoras && m == Tminutos) {
//                cronometro.setVisible(false);
//                JOptionPane.showMessageDialog(null, "Tiempo agotado.");
//            }
//            actualizarLabel();
//
//        }
//    };
//
//    private void actualizarLabel() {
//        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s + ":" + (cs <= 9 ? "0" : "") + cs;
//        cronometro.setText(tiempo);
//    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VstPresentarQuizz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VstPresentarQuizz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VstPresentarQuizz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VstPresentarQuizz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VstPresentarQuizz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField NomQuizz;
    public javax.swing.JPanel abierto;
    public javax.swing.JButton btnSigTerm;
    public javax.swing.JTextField contador;
    public javax.swing.JLabel cronometro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTextField nump;
    public javax.swing.JCheckBox r_a;
    public javax.swing.JCheckBox r_b;
    public javax.swing.JCheckBox r_c;
    public javax.swing.JCheckBox r_d;
    public javax.swing.JCheckBox r_e;
    public javax.swing.JCheckBox r_f;
    public javax.swing.JCheckBox r_g;
    public javax.swing.JCheckBox r_h;
    public javax.swing.JTextPane txtPregunta;
    public javax.swing.JTextPane txtR1;
    public javax.swing.JTextPane txtR2;
    public javax.swing.JTextPane txtR3;
    public javax.swing.JTextPane txtR4;
    public javax.swing.JTextPane txtR5;
    public javax.swing.JTextPane txtR6;
    public javax.swing.JTextPane txtR7;
    public javax.swing.JTextPane txtR8;
    private javax.swing.JTextPane txtRespuesta;
    // End of variables declaration//GEN-END:variables
}
