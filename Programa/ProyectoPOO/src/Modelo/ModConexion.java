/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class ModConexion {
    public static final String urlLocal = "jdbc:mysql://localhost:3306/poo";
    public static final String usernameLocal = "root";
    public static final String passwordLocal = "160798";
    public static final String urlWEB = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10264550";
    public static final String usernameWEB = "sql10264550";
    public static final String passwordWEB = "tt1DMlZ5ra";
    private Connection con = null;
    public Connection getConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(urlLocal, usernameLocal, passwordLocal);
        } catch (Exception e) {
            System.err.println(e);       
            JOptionPane.showMessageDialog(null, "Sin conexión.");
        }
        return con;
    }
}