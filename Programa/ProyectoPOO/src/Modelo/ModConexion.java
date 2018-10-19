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
    
    public static final String url = "jdbc:mysql://localhost:3306/crear_examen";
    public static final String username = "root";
    public static final String password = "Rfmb5851";
    private Connection con = null;
    
    public Connection getConexion() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            con = (Connection) DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
            
            System.err.println(e);       
            JOptionPane.showMessageDialog(null, "Sin conexión.");
            
        }
        
        return con;
    }
    
}
