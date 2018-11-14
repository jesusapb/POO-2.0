/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class ModConexion {

    public String databases = "";
    public String hostname = "";
    public String port = "";
    public String url = "";
    public String username = "";
    public String password = "";
    Connection con = null;
    
//    public static final String urlLocal = "jdbc:mysql://localhost:3306/crear_examen";
//    public static final String usernameLocal = "root";
//    public static final String passwordLocal = "Rfmb5851";

    public Connection getConexion() {
        ModConsultasSQL cons = new ModConsultasSQL();
        String texto = "";
        try {
            texto = "" + ModConsultasSQL.Desencriptar(cons.desencriptar().getPalabra());
//            System.out.println(texto);
        } catch (Exception ex) {
            Logger.getLogger(ModConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] partir = texto.split("~");
        databases = partir[0];
        hostname = partir[1];
        port = partir[2];
        url = "jdbc:mysql://" + hostname + ":" + port + "/" + databases;
        username = partir[3];
        password = partir[4];
//        System.out.println(url + " " + username + " " + password);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Sin conexión.");
        }
        return con;
    }
}
