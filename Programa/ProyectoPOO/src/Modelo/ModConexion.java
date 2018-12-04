package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Es la clase encargada en hacer la conexión a la base de datos cada vez que se
 * le requiera.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModConexion {

    public String databases = "";
    public String hostname = "";
    public String port = "";
    public String url = "";
    public String username = "";
    public String password = "";
    Connection con = null;

    /**
     * Constructor que tiene como darea decodificar los datos y extraerlos para
     * que así pueda hacer una conexión a la base de datos y obtener los
     * registros.
     *
     * @return El acceso a la base de datos.
     */
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
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Sin conexión.");
        }
        return con;
    }
}
