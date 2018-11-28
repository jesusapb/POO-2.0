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

    public Connection getConexion() {
        ModConsultasSQL cons = new ModConsultasSQL();
        String texto = "";
        try {
            texto = "" + ModConsultasSQL.Desencriptar(cons.desencriptar().getPalabra());
        } catch (Exception ex) {
            Logger.getLogger(ModConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] partir = texto.split("~");
        databases = partir[0];
        hostname = partir[1];
        port = partir[2];
        url = "jdbc:mysql://" + hostname + ":" + port + "/" + databases + "?autoReconnect=true&useSSL=false";
        username = partir[3];
        password = partir[4];
        
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
