package Modelo;

import Vista.VstAvances;
import Vista.VstEmpleados;
import Vista.VstPerfilEmp;
import Vista.VstEnviados;
import Vista.VstRecibido;
import Vista.VstConfiguracion.Texto;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * Ésta clase se encarga de hacer las transacciones en la base de datos, asi
 * mismo como acutalizar los valores, eliminarlos o simplemente extraerlos,
 * además, también se encarga de hacer validaciones para el funcionamiento del
 * programa.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModConsultasSQL extends ModConexion {

    /**
     * Ésta función se engarda de extraer los datos de los usuarios y
     * compararlos con los datos previamente introducidos por el usuario, así
     * mismo hace comparaciones para encontrar los datos exactos del usuario que
     * está por usar el programa.
     *
     * @param var es la variable que contiene en ella distintos valores los
     * cuales se van a comparar para encontrar los datos exactos del usuario que
     * está por usar el programa.
     * @return devuelve una confirmación o negación para el acceso al programa,
     * no todas las negaciones implica que el usario no podrá acceder al
     * programa.
     */
    public boolean login(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        String contr = var.getContraseña();
        int cont = contr.length();
        String s = "";
        /**
         * Se divide la contraseña para luego ser comparada con un tag
         * previamente establecido.
         */
        if (cont >= 6) {
            s = contr.substring(0, 6).toUpperCase();
        }

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);

                if (var.getMatricula().equals(varU.getMatricula()) || var.getMatricula().equals(varU.getCorreo())) {
                    /**
                     * Se compara todas las matrículas y correos en búsqueda del
                     * valor (matrícula o correo) que el usuario introdujo.
                     */
                    if ("Conectado".equals(varU.getStatus())) {
                        /**
                         * Se valida que el usario al cual se desea acceder no
                         * tenga una sesión activa.
                         */
                        JOptionPane.showMessageDialog(null, "El usuario " + varU.getNombre_completo() + " tiene su sesión activa.");
                        return false;
                    } else if ("Habilitado".equals(varU.getComando())) {
                        /**
                         * Se hace una validación para verificar y confirmar si
                         * solicitó un cambio de contraseña, si se solicitó un
                         * cambio de contraseña se compara el código que el
                         * usuario introdujo y se compara con el existente si es
                         * el mismo.
                         */
                        if (var.getContraseña().equals(varU.getCodigo())) {
                            JOptionPane.showMessageDialog(null, "Hola " + varU.getNombre_completo() + ".\n"
                                    + "Se ha detectado que solicitó el cambio de su contraseña\n"
                                    + "por medio de la función ''¿Olvidaste tu contraseña?'', por\n"
                                    + "lo tanto, se va a habilitar únicamente el campo de\n "
                                    + "ingreso de la contraseña, ahí deberá de ingresar su\n"
                                    + "nueva contraseña y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                            var.setMensaje("Detección de cambio de contraseña.");
                            return false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Código de seguridad incorrecto,\n favor de verificarlo.");
                            return false;
                        }
                    } else if ("@NUEVO".equals(s)) {
                        /**
                         * Aquí se valida el tag @NUEVO el cual indica que el
                         * usuario es nuevo y es su primer inicio de sesión, se
                         * le solicitará el cambio de contraseña.
                         */
                        JOptionPane.showMessageDialog(null, "Hola " + varU.getNombre_completo() + ".\n"
                                + "Se ha detectado que es tu primer inicio de sesión, por\n"
                                + "lo tanto, se le pide el inmediato cambio de contraseña.\n"
                                + "Se le habilitara únicamente el campo de ingreso de la\n "
                                + "contraseña, ahi deberá de ingresar su nueva contraseña\n"
                                + "y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                        var.setMensaje("Detección de nuevo usuario.");
                        return false;
                    } else if (var.getContraseña().equals(varU.getContraseña())) {
                        /**
                         * Si no está con sesión activa y no pidio cambio de
                         * contraseña y si no usuario nuevo, se compara la
                         * contraseña que introdujo con la existente para darle
                         * o negarle el acceso.
                         */
                        try {
                            String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                            PreparedStatement ps = null;
                            Connection con = getConexion();

                            ps = con.prepareStatement(update);
                            ps.setString(1, var.getDia());
                            ps.setString(2, var.getHora());
                            ps.setString(3, "Conectado");
                            ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                            ps.setString(5, InetAddress.getLocalHost().getHostName());
                            ps.setInt(6, varU.getId());
                            ps.execute();

                            var.setId(varU.getId());
                            var.setNombre(varU.getNombre());
                            var.setAp_pat(varU.getAp_pat());
                            var.setAp_mat(varU.getAp_mat());
                            var.setTipo(varU.getTipo());
                            var.setMatricula(varU.getMatricula());
                            var.setContraseña(varU.getContraseña());
                            var.setCorreo(varU.getCorreo());
                            var.setNombre_completo(varU.getNombre_completo());
                            return true;

                        } catch (SQLException ex) {
                            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Fallo en la conexión.");
                            return false;
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Matrícula y/o contraseña inválida.");
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Función encargada de generár el código que se le enviará por correo
     * electrónico al usuario para el cambio de su contraseña.
     *
     * @param var es la variable que contiene valores necesario para el envio
     * del correo electrónico.
     * @return devuelve una confirmación o negación para el envío del correo
     * electrónico.
     */
    public boolean emailVal(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        PreparedStatement ps = null;
        Connection con = getConexion();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);

                if (varU.getMatricula().equals(var.getMatricula()) || varU.getCorreo().equals(var.getMatricula())) {
                    /**
                     * Se compara tódas las matrículas y tódos los correos
                     * electrónicos registrados para encontrar el dato que el
                     * usuario introdujo para continuar con la generación del
                     * código y el envio del correo electrónico, al ser
                     * encontrado, se extrae el dato requerido para la acción.
                     */

                    try {
                        Properties props = new Properties();
                        props.setProperty("mail.smtp.host", "smtp.gmail.com");
                        props.setProperty("mail.smtp.starttls.enable", "true");
                        props.setProperty("mail.smtp.port", "587");
                        props.setProperty("mail.smtp.auth", "true");

                        Session session = Session.getDefaultInstance(props);

                        var.setId(varU.getId());
                        var.setNombre(varU.getNombre());
                        var.setAp_pat(varU.getAp_pat());
                        var.setAp_mat(varU.getAp_mat());
                        var.setTipo(varU.getTipo());
                        var.setMatricula(varU.getMatricula());
                        var.setContraseña(varU.getContraseña());
                        var.setCorreo(varU.getCorreo());
                        var.setNombre_completo(varU.getNombre_completo());

                        String contra = varU.getContraseña();
                        String sub = contra.substring(0, 6).toUpperCase();

                        if ("Conectado".equals(varU.getStatus()) || "Permanente".equals(varU.getStatus())) {
                            /**
                             * Se valida que el usuario no tenga sesión activa
                             * para continuar.
                             */
                            JOptionPane.showMessageDialog(null, "No se puede hacer al uso de esta función,\nya que el usuario se encuentra conectado o suspendido.");
                            return false;
                        } else if ("@NUEVO".equals(sub)) {
                            /**
                             * Se valida que el usuario no sea usuario nuevo.
                             */
                            JOptionPane.showMessageDialog(null, "No se puede hacer el uso de esta función,\nya que el usuario es nuevo.");
                            return false;
                        } else {
                            /**
                             * Al pasar los filtros anteriores se hace el cambio
                             * del identificador en la base de datos el cual
                             * marca si se solicita un cambio de contraseña.
                             */
                            String update = "UPDATE usuarios SET comando = ?, codigo = ? WHERE id = ?";
                            ps = con.prepareStatement(update);
                            ps.setString(1, "Habilitado");

                            String uuid = UUID.randomUUID().toString();
                            ps.setString(2, uuid);

                            ps.setInt(3, varU.getId());
                            ps.execute();

                            /**
                             * Se genera y envia el correo electrónico al
                             * usuario que lo solicitó.
                             */
                            String correoRemitente = "poo.acompanamiento@gmail.com";
                            String passwordRemitente = "administrador";
                            String correoReceptor = varU.getCorreo();
                            String asunto = "CÓDIGO DE SEGURIDAD.";
                            String mensaje = "Su código de seguridad es: " + uuid + ". "
                                    + "El cual debe de ingresar en el campo correspondiente "
                                    + "de la contraseña en la ventana de Inicio de sesión. "
                                    + "Así mismo debe de ingresar su matricula o correo "
                                    + "eléctronico en su campo correspondiente y acceder, "
                                    + "de este modo podra hacer el cambio inmediato de tu contraseña.";

                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(correoRemitente));

                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));

                            message.setSubject(asunto);
                            message.setText(mensaje);

                            Transport t = session.getTransport("smtp");
                            t.connect(correoRemitente, passwordRemitente);
                            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                            t.close();

                            JOptionPane.showMessageDialog(null, "Se le ha enviado un mensaje a: " + var.getNombre_completo() + ".\nCon un código de seguridad e instrucciones.");
                            return true;
                        }
                    } catch (AddressException ex) {
                        Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Fallo en la conexión.");
                        return false;
                    } catch (MessagingException | SQLException ex) {
                        Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Fallo en la conexión.");
                        return false;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "No se encontró la matrícula.");
            return false;
        }
        return false;
    }

    /**
     * Ésta función actúa al momento que se solicite un cambio de contraseña o
     * al momento que se detecte que el usuario es nuevo.
     *
     * @param var contiene los datos necesarios para validar y ejecitar la
     * acción.
     * @return devuelve la confirmación o negación de la acción que se está
     * solicitando.
     */
    public boolean loginNuevo(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        /**
         * La contraseña introducida se subdivide para una validación siguiente.
         */
        String contr = var.getContraseña();
        String s = contr.substring(0, 6).toUpperCase();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);

                if (varU.getMatricula().equals(var.getMatricula()) || varU.getCorreo().equals(var.getMatricula())) {
                    /**
                     * Se compara tódas las matrículas y tódos los correos
                     * electrónicos para encontrar el dato que el usuario
                     * introdujo.
                     */
                    if (var.getContraseña().equals(varU.getContraseña())) {
                        /**
                         * Se valida que la contraseña introducida no sea la que
                         * tenga en el momento, si lo es le niega y solicita el
                         * cambio.
                         */
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else if ("@NUEVO".equals(s)) {
                        /**
                         * Aquí ocurre la validación para lo cuál la contraseña
                         * fue subdividida, aquí se valida que no se introduzca
                         * el tag reservado por el programa que identifica que
                         * el usuario es nuevo, si lo es le niega y le da el
                         * aviso solicitando el cambio de la contraseña.
                         */
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else {
                        /**
                         * Al pasar los filtros anteriores se hace la ejecución
                         * y el cambio de contraseña, así mismo le da el acceso
                         * inmediato al programa con la sesión del usuario.
                         */
                        try {
                            PreparedStatement ps = null;
                            ResultSet rs = null;
                            Connection con = getConexion();

                            String contra = "UPDATE usuarios SET contraseña = ?, comando = ? WHERE id = ?";
                            ps = con.prepareStatement(contra);
                            ps.setString(1, contr);
                            ps.setString(2, "Deshabilitado");
                            ps.setInt(3, varU.getId());
                            ps.execute();

                            String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                            ps = con.prepareStatement(update);
                            ps.setString(1, var.getDia());
                            ps.setString(2, var.getHora());
                            ps.setString(3, "Conectado");
                            ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                            ps.setString(5, InetAddress.getLocalHost().getHostName());
                            ps.setInt(6, varU.getId());
                            ps.execute();

                            var.setId(varU.getId());
                            var.setNombre(varU.getNombre());
                            var.setAp_pat(varU.getAp_pat());
                            var.setAp_mat(varU.getAp_mat());
                            var.setTipo(varU.getTipo());
                            var.setMatricula(varU.getMatricula());
                            var.setContraseña(varU.getContraseña());
                            var.setCorreo(varU.getCorreo());
                            var.setNombre_completo(varU.getNombre_completo());
                            return true;

                        } catch (SQLException | UnknownHostException ex) {
                            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Fallo en la conexión.");
                            return false;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Función encargada en obtener todos los datos del usuario que está usando
     * el programa cada vez que se le requiera.
     *
     * @param var contiene valores que se utilizarán para validar y efectuar el
     * propósito de la función.
     */
    public static void recarga(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        /**
         * Se obtienen los datos del usuario por medio de la matrícula.
         */
        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, correo "
                + "FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            if (rs.next()) {
                /**
                 * Al ser encontrado se actualizan valores requeridos.
                 */
                String update = "UPDATE usuarios SET dia = ?, hora = ? WHERE id = ?";
                ps = con.prepareStatement(update);
                ps.setString(1, var.getDia());
                ps.setString(2, var.getHora());
                ps.setInt(3, rs.getInt(1));
                ps.execute();

                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setCorreo(rs.getString(7));
                var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función encargada en extraer únicamente el status actual de la sesión del
     * usuario.
     *
     * @param var contiene la matrícula con la cual se va a realizar las
     * búsqueda en la base de datos.
     */
    public static void status(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT id, matricula, status "
                + "FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setMatricula(rs.getString(2));
                var.setStatus(rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función encargada en cerrar la sesión activa del usuario en la base de
     * datos.
     *
     * @param var contiene la matrícula o correo electrónico para validar y
     * ejecutar la función.
     * @return devuelve la confirmación o negación de la petición de cerrar la
     * sesión activa del usuario en la base de datos.
     */
    public boolean cerrar(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        PreparedStatement ps = null;
        Connection con = getConexion();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);
                if (varU.getMatricula().equals(var.getMatricula()) || varU.getCorreo().equals(var.getMatricula())) {
                    /**
                     * Se hace una búsqueda con la comparación de la matrícula o
                     * correo elctrónico con el dato que el usuario contenga.
                     */
                    try {
                        /**
                         * Al ser encontrado se hace el cambio de status del
                         * usuario en la base de datos, se para a un status
                         * desconectado.
                         */
                        String status = "UPDATE usuarios SET status = ? WHERE id = ?";
                        ps = con.prepareCall(status);
                        ps.setString(1, var.getStatus());
                        ps.setInt(2, varU.getId());
                        ps.execute();
                        return true;
                    } catch (SQLException ex) {
                        Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Fallo en la conexión.");
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Función encargada en decir si el dato que se solicita ya se encuentra
     * registrado en la base de datos.
     *
     * @param usuario es la matrícula que se desea saber si existe en la base de
     * datos.
     * @return devuelve un valor 1 si se encuentra la matrícula ó 0 si no se
     * encuentra.
     */
    public int existeUsr(String usuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Función encargada en decir si el dato que se solicita ya se encuentra
     * registrado en la base de datos.
     *
     * @param usuario contiene el correo electrónico que se desea saber si ya
     * existe en la base de datos.
     * @return devuelve un valor 1 si se encuentra la matrícula ó 0 si no se
     * encuentra.
     */
    public int existeCorreo(String usuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM usuarios WHERE correo = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Función encargada en realizar el registro de los datos en la base de
     * datos.
     *
     * @param var valores que son entregados por un administrador que contienen
     * los datos del nuevo empleado que se va a registrar.
     * @return devuelve la confirmación si se pudo o no hacer el registro en la
     * base de datos de los datos entregados por el administrador.
     */
    public boolean registrar(ModVariablesReg var) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuarios (nombre, ap_pat, ap_mat, tipo, matricula, contraseña, "
                + "correo, dia, hora, status, ip, equipo, comando, codigo) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setString(2, var.getAp_pat());
            ps.setString(3, var.getAp_mat());
            ps.setString(4, var.getTipo());
            ps.setString(5, var.getMatricula());
            ps.setString(6, var.getContraseña());
            ps.setString(7, var.getCorreo());
            ps.setString(8, var.getDia());
            ps.setString(9, var.getHora());
            ps.setString(10, var.getStatus());
            ps.setString(11, var.getIp());
            ps.setString(12, var.getEquipo());
            ps.setString(13, var.getComando());
            ps.setString(14, var.getCodigo());
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función encargada en enviar correo electrónicos a los empleados, ya sea
     * por la eliminación de su sesión o el cambio de la contraseña.
     *
     * @param varU datos necesarios extraidos del administrador, se usan para el
     * funcionamiento y ejecución de la función.
     * @param varR datos necesarios del empleado que se extraen para la función
     * y ejecución de la función.
     * @return devuelve la confirmación o negación si fue enviado el correo
     * electrónico al empleado.
     */
    public boolean email(ModVariablesUsr varU, ModVariablesReg varR) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            String correoRemitente = "poo.acompanamiento@gmail.com";
            String passwordRemitente = "administrador";
            String correoReceptor = varR.getCorreo();
            String asunto = varU.getAsunto();
            String mensaje = varU.getMensaje();

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));

            message.setSubject(asunto);
            message.setText(mensaje);

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            return true;
        } catch (AddressException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Función encargada de generar el contenido a la tabla donde se muestran
     * todos los empleados registrados.
     *
     * @param tablaRegistrados es la tabla que se va a llenar.
     */
    public static void tablaEmp(JTable tablaRegistrados) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                /**
                 * Cancelación de la edición de la tabla cuando se muestre.
                 */
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tablaRegistrados.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            /**
             * Se extrae los datos requeridos para llenar la tabla.
             */
            String sql = "SELECT matricula, nombre, ap_pat, ap_mat, correo, tipo FROM usuarios WHERE tipo = 'Empleado' order by ap_pat";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            /**
             * Nombres de las columnas de la tabla
             */
            modelo.addColumn("Matrícula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Apellido Materno");

            while (rs.next()) {
                /**
                 * Se llena la tabla con los valores extraidos en sus
                 * respectivas columnas.
                 */
                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Función encargada en generar el contenido a la tabla que muestra los
     * usuarios conectados en ese momento.
     *
     * @param var variable que se utilizara para almacenar los valores extraidos
     * de la base de datos.
     * @param tablaConectados es la tabla que se va a llenar con los datos.
     * @param matricula es la matrícula de usuario que esta usando el programa,
     * es utilizada para hacer un filtro y que no se muestre a si mismo en la
     * tabla de conectados.
     */
    public static void tablaConectados(ModVariablesUsr var, JTable tablaConectados, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaConectados.setModel(modelo);

        /**
         * Nombre de las columnas que se van a usar de la tabla.
         */
        modelo.addColumn("Matrícula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");
        modelo.addColumn("Usuario");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                var = list.get(i);
                /**
                 * Se efectua el filtro para que no se refleje el mismo usuario
                 * que esta usando el programa en la tabla.
                 */
                if (var.getMatricula().equals(matricula)); else if (var.getStatus().equals("Conectado")) {
                    /**
                     * Se hace el llenado de la tabla con los datos extraídos.
                     */
                    fila[0] = var.getMatricula();
                    fila[1] = var.getNombre();
                    fila[2] = var.getAp_pat();
                    fila[3] = var.getAp_mat();
                    fila[4] = var.getTipo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función encargada en generar el contenido los datos de todos los quizzes
     * presentados del usuario al cual se le indique.
     *
     * @param TablaAvances el la tabla donde se va a generar los datos
     * requeridos.
     * @param vce es la variable donde se va a manipular la interfaz gráfica
     * donde está la tabla y donde se va a estraer el dato necesario para la
     * extracción de los datos.
     */
    public static void tablaAva(JTable TablaAvances, VstEmpleados vce) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                /**
                 * Cancelación de la edición de la tabla cuando se muestre.
                 */
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            TablaAvances.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            /**
             * Se extraen los datos solicitados.
             */
            String sql = "SELECT nombre_examen, dia, hora, puntos, status, matricula FROM presentados WHERE matricula = '" + vce.txtMatricula.getText() + "'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            /**
             * Nombre de las columnas de la tabla.
             */
            modelo.addColumn("Quiz");
            modelo.addColumn("Día presentado");
            modelo.addColumn("Hora presentada");
            modelo.addColumn("Calificación");
            modelo.addColumn("Status");

            while (rs.next()) {
                /**
                 * Se llenan la tabla con los datos extraídos.
                 */
                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Función que se encarga de buscar si el dato que se solicita ya se
     * encuentra registrado en la base de datos.
     *
     * @param quizz es el nombre del quizz que se desea saber si ya existe.
     * @return devuelve 1 si existe ó 0 si no existe.
     */
    public int existequizz(String quizz) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM quizzes WHERE nombre = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, quizz);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Función encargada en registrar los datos introducidos a la base de datos.
     *
     * @param var es la variable de la clase donde se encuentran almacenados los
     * datos que se van a registrar.
     * @return devuelve la confirmación o negación si se pudo registrar los
     * datos en la base de datos.
     */
    public boolean Rquizzes(ModVariablesQuizzes var) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO quizzes (nombre, descripcion, p_totales, p_actuales, status, "
                + "intentos, mod_calif, tiempo, f_registro, f_activacion) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setString(2, var.getDescripcion());
            ps.setString(3, var.getP_totales());
            ps.setString(4, var.getP_actuales());
            ps.setString(5, var.getStatus());
            ps.setString(6, var.getIntentos());
            ps.setString(7, var.getMod_calif());
            ps.setString(8, var.getTiempo());
            ps.setString(9, var.getF_registro());
            ps.setString(10, var.getF_activacion());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }
    }

    /**
     * Función encargada en generar todos los datos de los quizzes registrados
     * en una tabla.
     *
     * @param tablaQuizzes es la tabla donde se va a generar los datos.
     */
    public static void tablaQuizz(JTable tablaQuizzes) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                /**
                 * Cancelación de la edición de la tabla cuando se muestre.
                 */
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tablaQuizzes.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            /**
             * Se extraen los datos necesarios para reflejarlos en la tabla.
             */
            String sql = "SELECT nombre, descripcion, status, p_totales, p_actuales, f_registro, f_activacion FROM quizzes order by nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            /**
             * Nombre de las columnas de la tabla donde se van a reflejar los
             * datos extraídos.
             */
            modelo.addColumn("Nombre");
            modelo.addColumn("Descripción");
            modelo.addColumn("Status");
            modelo.addColumn("#PregTot");
            modelo.addColumn("#PregAct");
            modelo.addColumn("FRegistro");
            modelo.addColumn("FActivado");

            while (rs.next()) {
                /**
                 * Se llenan las columnas con los datos extraídos.
                 */
                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Función encargada en generar los datos de los documentos registrados en
     * una tabla.
     *
     * @param tablaDocs tabla donde se van a generar los datos.
     */
    public static void tablaDocs(JTable tablaDocs) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                /**
                 * Cancelación de la edición de la tabla cuando se muestre.
                 */
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tablaDocs.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            /**
             * Se extraen los datos necesarios para reflejarlos en la tabla.
             */
            String sql = "SELECT nombre, status, descripcion FROM documentos order by nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            /**
             * Nombre de las columnas de la tabla.
             */
            modelo.addColumn("Nombre");
            modelo.addColumn("Status");
            modelo.addColumn("Descripción");

            while (rs.next()) {
                /**
                 * Se llenar las columnas con los datos extraídos.
                 */
                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Función encargada en generar los datos necesarios en una tabla para que
     * se pueda observar todos los usuarios registrados.
     *
     * @param tablaTUsuarios es la tabla donde se van a generar los datos
     * extraídos.
     * @param var variable de la función que se va a utilizar para la extracción
     * de los datos de la base de datos.
     * @param matricula es la matrícula del usuario que está utilizando el
     * programa, se utilizará para hacer un filtro.
     */
    public static void tablaTEmp(JTable tablaTUsuarios, ModVariablesReg var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTUsuarios.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("Usuarios:");
        modelo.addColumn("Tipo:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesReg> list = mens.listaE();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[2];
                var = list.get(i);
                /**
                 * Se hace un filtro para que los datos del usuario que esté
                 * usando el programa no salgan en la tabla.
                 */
                if (var.getMatricula().equals(matricula)); else {
                    /**
                     * Se llenan las columnas con los datos extraídos.
                     */
                    fila[0] = var.getNombre_completo();
                    fila[1] = var.getTipo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función encargada en generar datos en la tabla la cual refleja los
     * mensajes recibidos del usuario.
     *
     * @param tablaBandejaEntrada es la tabla donde se van a generar los datos.
     * @param var variable de la clase que va a contener los datos extraidos de
     * la base de datos.
     * @param varU variable de una clase que contiene los datos del usuario que
     * este usando el programa, se utilizará para hacer filtros y validaciones.
     */
    public static void recibidos(JTable tablaBandejaEntrada, ModVariablesMensaje var, ModVariablesUsr varU) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaBandejaEntrada.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("ID");
        modelo.addColumn("De:");
        modelo.addColumn("Asunto:");
        modelo.addColumn("Fecha:");
        modelo.addColumn("Status:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                var = list.get(i);

                /**
                 * Se realiza una validación para obtener todos los mensajes
                 * enviados para el usuario que este usando el programa.
                 */
                if (var.getPara_mat().equals(varU.getMatricula())) {
                    /**
                     * Se realiza el llenado de los datos extraídos en la tabla.
                     */
                    fila[0] = var.getId();
                    fila[1] = var.getDe_mat() + "/" + var.getDe_nom();
                    fila[2] = var.getAsunto();
                    fila[3] = var.getFecha();
                    fila[4] = var.getStatus();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función que se encarga en avisar si hay algun mensaje que aún no se ha
     * leído.
     *
     * @param var variable de la clase que se va a utilizar para almacenar los
     * datos extraídos de la base de datos.
     * @param matricula es la matrícula del usuario que este usando el programa,
     * se utilizará para validar si hay algun mensaje no leído para ese usuario.
     * @return devuelve 1 si hay algun mensaje no leído ó 0 si no lo hay.
     */
    public int ENVisto(ModVariablesMensaje var, String matricula) {
        ModListas mens = new ModListas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);
                if (var.getPara_mat().equals(matricula)) {
                    if (var.getStatus().equals("NO VISTO")) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Función que se encarga en extraér los datos del mensaje para que el
     * usuario lo pueda observar.
     *
     * @param id es la identificación del mensaje en la base de datos para que
     * se pueda extraer.
     * @param vr es la variable de la interfaz gráfica donde ocurre el proceso.
     * @param var es la variable de la clase que se va a utilizar para almacenar
     * los datos extraidos de la base de datos.
     */
    public static void leer(String id, VstRecibido vr, ModVariablesMensaje var) {
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            /**
             * Se extraen los datos de la base de datos.
             */
            ps = con.prepareStatement("SELECT id, de_mat, de_nom, para_mat, para_nom, fecha, asunto, mensaje, status "
                    + "FROM mensajes WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                /**
                 * Al ser encontrado el mensaje se actualiza el status de "no
                 * visto" a "visto".
                 */
                String update = "UPDATE mensajes SET status = ? WHERE id = ?";
                ps = con.prepareStatement(update);
                ps.setString(1, "VISTO");
                ps.setInt(2, rs.getInt(1));
                ps.execute();

                vr.setVisible(true);
                var.setId(rs.getInt(1));
                var.setDe_mat(rs.getString(2));
                var.setDe_nom(rs.getString(3));
                var.setPara_mat(rs.getString(4));
                var.setPara_nom(rs.getString(5));
                var.setMensaje(rs.getString(8));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        vr.txtMensaje.setText(var.getMensaje());
        vr.id.setText(var.getId() + "");
    }

    /**
     * Función que se encarga de enviar los mensajes a otros usuarios, registra
     * los datos entrados por el usuario emisor en la base de datos.
     *
     * @param var es la variable de la clase que contiene los datos del mensaje
     * que se está enviando.
     * @return devuelve la confirmación o negación si se envió el mensaje.
     */
    public boolean enviar(ModVariablesMensaje var) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO mensajes (de_mat, de_nom, para_mat, para_nom, fecha, "
                + "asunto, mensaje, status) VALUES (?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getDe_mat());
            ps.setString(2, var.getDe_nom());
            ps.setString(3, var.getPara_mat());
            ps.setString(4, var.getPara_nom());
            ps.setString(5, var.getFecha());
            ps.setString(6, var.getAsunto());
            ps.setString(7, var.getMensaje());
            ps.setString(8, var.getStatus());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }
    }

    /**
     * Función encargada en mostrar en una tabla todos los mensajes que el
     * usuario ha enviado.
     *
     * @param tablaEnviados es la tabla donde se va a mostrar los mensajes
     * enviados.
     * @param var variable de la clase que se va a utilizar para almacenar los
     * datos que se extraen de la base de datos y asi ser mostrados en la tabla.
     * @param varU variable de la clase que contiene los datos del usuario que
     * esta usuando el programa, se utilizará para hacer validaciones.
     */
    public static void enviados(JTable tablaEnviados, ModVariablesMensaje var, ModVariablesUsr varU) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEnviados.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("ID:");
        modelo.addColumn("Para:");
        modelo.addColumn("Asunto:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                /**
                 * Se valida con la matrícla para obtener todos los mensajes que
                 * ese usuario envió.
                 */
                if (var.getDe_mat().equals(varU.getMatricula())) {
                    /**
                     * Se llena la tabla con los datos extraídos de la base de
                     * datos.
                     */
                    fila[0] = var.getId();
                    fila[1] = var.getPara_mat() + "/" + var.getPara_nom();
                    fila[2] = var.getAsunto();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función que se encarga en leer el mensaje enviado o recibido.
     *
     * @param id es la identificacion única del registro del mensaje en la base
     * de datos.
     * @param ve es la variable de la interfaz gráfica donde ocurre el proceso.
     * @param var variable de la clase que va a contener los datos extraídos de
     * la base de datos.
     */
    public static void mensaje(String id, VstEnviados ve, ModVariablesMensaje var) {
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            ps = con.prepareStatement("SELECT id, de_mat, de_nom, para_mat, para_nom, fecha, asunto, mensaje, status "
                    + "FROM mensajes WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setDe_mat(rs.getString(2));
                var.setDe_nom(rs.getString(3));
                var.setPara_mat(rs.getString(4));
                var.setPara_nom(rs.getString(5));
                var.setMensaje(rs.getString(8));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        ve.txtMensaje.setText(var.getMensaje());
        ve.id.setText(var.getId() + "");
    }

    /**
     * Función que muestra en una tabla al administrador todos los documentos
     * que se encuentras visibles para el empleado.
     *
     * @param tablaADocumentos es la tabla donde se va a mostrar el nombre del
     * documento visible.
     * @param var es la variable de la clase que se utilizará para almacenar los
     * datos de la base de datos y posteriormente ser mostrados en la tabla.
     */
    public static void DocsAct(JTable tablaADocumentos, ModVariablesDoc var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaADocumentos.setModel(modelo);

        /**
         * Nombre de la columna de la tabla.
         */
        modelo.addColumn("Nombre:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesDoc> list = mens.listaDocs();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                /**
                 * Se valida que solo se extraigan los nombres de los documentos
                 * activos.
                 */
                if (var.getStatus().equals("Habilitado")) {
                    /**
                     * Se llena la tabla con los datos extraídos.
                     */
                    fila[0] = var.getNombre();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función que muestra en una tabla al administrador todos los quizzes que
     * se encuentras visibles para el empleado.
     *
     * @param tablaAQuizzes es la tabla donde se va a mostrar el nombre del
     * quizz visible así como la fecha que fue activado.
     * @param var es la variable de la clase que se utilizará para almacenar los
     * datos de la base de datos y posteriormente ser mostrados en la tabla.
     */
    public static void QuizzAct(JTable tablaAQuizzes, ModVariablesQuizzes var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAQuizzes.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("Nombre:");
        modelo.addColumn("FActivo:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesQuizzes> list = mens.listaQuizz();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                /**
                 * Se valida en la búsqueda de los nombres de los quizzes
                 * activos.
                 */
                if (var.getStatus().equals("Habilitado")) {
                    /**
                     * Se llena la tabla con los datos extraídos.
                     */
                    fila[0] = var.getNombre();
                    fila[1] = var.getF_activacion();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función que se encarga en registrar en la base de datos los datos de las
     * preguntas.
     *
     * @param var variable de la clase que contiene los datos necesarios para el
     * registro.
     * @return decuelve la confirmación o negación del registro de la pregunta.
     */
    public boolean rPreguntas(ModvariablesPreguntas var) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO preguntas (quizz, pregunta, tipo, num_resp, puntuacion_total, resp1, r1, "
                + "resp2, r2, resp3, r3, resp4, r4, dis1, dis2, dis3, dis4) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getQuizz());
            ps.setString(2, var.getPregunta());
            ps.setString(3, var.getTipo());
            ps.setString(4, var.getNum_resp());
            ps.setString(5, var.getPuntuacion_total());
            ps.setString(6, var.getResp1());
            ps.setString(7, var.getR1());
            ps.setString(8, var.getResp2());
            ps.setString(9, var.getR2());
            ps.setString(10, var.getResp3());
            ps.setString(11, var.getR3());
            ps.setString(12, var.getResp4());
            ps.setString(13, var.getR4());
            ps.setString(14, var.getDis1());
            ps.setString(15, var.getDis2());
            ps.setString(16, var.getDis3());
            ps.setString(17, var.getDis4());
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función que se encarga en actualizar los datos del registro de la
     * pregunta almacenada en la base de datos.
     *
     * @param var nombre de la variable que contiene los nuevos datos que van a
     * ser utilizados para actualizar en la base de datos.
     * @return devuelve la confirmación o negación de la modificación de los
     * datos.
     */
    public boolean mPreguntas(ModvariablesPreguntas var) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE preguntas SET pregunta=?, tipo=?, num_resp=?, puntuacion_total=?, resp1=?, r1=?, "
                + "resp2=?, r2=?, resp3=?, r3=?, resp4=?, r4=?, dis1=?, dis2=?, dis3=?, dis4=? WHERE id = '" + var.getId() + "'";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getPregunta());
            ps.setString(2, var.getTipo());
            ps.setString(3, var.getNum_resp());
            ps.setString(4, var.getPuntuacion_total());
            ps.setString(5, var.getResp1());
            ps.setString(6, var.getR1());
            ps.setString(7, var.getResp2());
            ps.setString(8, var.getR2());
            ps.setString(9, var.getResp3());
            ps.setString(10, var.getR3());
            ps.setString(11, var.getResp4());
            ps.setString(12, var.getR4());
            ps.setString(13, var.getDis1());
            ps.setString(14, var.getDis2());
            ps.setString(15, var.getDis3());
            ps.setString(16, var.getDis4());
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función que se encarga en mostrar en una tabla todas la preguntas
     * registradas, asi como algunos datos.
     *
     * @param tablaPreguntas es la tabla donde se va a mostrar los datos de las
     * preguntas registradas.
     * @param var variable de la clase que contendra los datos extraídos de la
     * base de datos para luego ser utilizados para mostrarlo en la tabla.
     * @param id es el identificador del quizz del cual se está registrando la
     * pregunta.
     */
    public static void tablaPreg(JTable tablaPreguntas, ModvariablesPreguntas var, String id) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPreguntas.setModel(modelo);

        /**
         * Es el nombre de las columnas de la tabla.
         */
        modelo.addColumn("ID:");
        modelo.addColumn("Pregunta:");
        modelo.addColumn("Tipo:");

        ModListas mens = new ModListas();
        ArrayList<ModvariablesPreguntas> list = mens.listaPreg();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                /**
                 * Se hace un filtro para buscar todas las preguntas registradas
                 * de ese quizz.
                 */
                if (var.getQuizz().equals(id)) {
                    fila[0] = var.getId();
                    fila[1] = var.getPregunta();
                    fila[2] = var.getTipo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Función encargada en eliminar la pregunta que se le indique.
     *
     * @param var es la variable la cual va a indicar cuál pregunta se debe de
     * eliminar.
     * @return devuelve la confirmación o negacion si la pregunta fué eliminada.
     */
    public boolean ElimPregunta(ModvariablesPreguntas var) {
        PreparedStatement ps = null;

        try {
            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            ps = con.prepareStatement("DELETE FROM preguntas WHERE id = '" + var.getId() + "'");
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Función encargada en enviár los avisos entre usuarios de forma grupal.
     *
     * @param var variable que contiene el destinatario del aviso.
     * @param tipo se refiere al grupo de usuarios a los que se va a ir el
     * mensaje (empleados o administradores).
     * @param quien indica el emisor del aviso (contiene la matrícula y el
     * nombre completo del usuario).
     * @param que indica el propósito del aviso.
     * @param cuando indica la fecha de cuando se emite el aviso (día y hora).
     * @param comp contiene la matrícula para descartarlo si en el grupo de
     * usuarios a los que se va a enviar el aviso se encuentra el.
     * @return devuelve la confirmación o negación si se envió el aviso.
     */
    public boolean avisoAA(ModVariablesReg var, String tipo, String quien, String que, String cuando, String comp) {
        ModListas mens = new ModListas();
        ArrayList<ModVariablesReg> list = mens.listaT(tipo, comp);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);

                try {
                    PreparedStatement ps = null;
                    Connection con = getConexion();

                    String sql = "INSERT INTO avisos (para, quien, que, cuando, status) VALUES(?,?,?,?,?)";

                    ps = con.prepareStatement(sql);
                    ps.setString(1, var.getAvisos());
                    ps.setString(2, quien);
                    ps.setString(3, que);
                    ps.setString(4, cuando);
                    ps.setString(5, "no visto");
                    ps.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    /**
     * Ésta función fué implementada para mandar avisos particulares y no en
     * grupos.
     *
     * @param quien indica el emisor del aviso (contiene la matrícula y el
     * nombre completo del usuario).
     * @param que indica el propósito del aviso.
     * @param cuando indica la fecha de cuando se emite el aviso (día y hora).
     * @param para indica al destinatario del aviso.
     * @return devuelve la confirmación o negación si se envió el aviso.
     */
    public boolean aviso(String quien, String que, String cuando, String para) {

        try {
            PreparedStatement ps = null;
            Connection con = getConexion();

            String sql = "INSERT INTO avisos (para, quien, que, cuando, status) VALUES(?,?,?,?,?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, para);
            ps.setString(2, quien);
            ps.setString(3, que);
            ps.setString(4, cuando);
            ps.setString(5, "no visto");
            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Función encargada en registrar los datos y el archivo del documento.
     *
     * @param var variable de la clase que contiene los datos para el registro
     * del documento en la base de datos.
     */
    public void agregarD(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "INSERT INTO documentos (id, nombre, status, descripcion, archivo) VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setInt(1, var.getId());
            ps.setString(2, var.getNombre());
            ps.setString(3, var.getStatus());
            ps.setString(4, var.getDescripcion());
            ps.setBytes(5, var.getArchivo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función que se encarga en la actualización de los datos del documento en
     * la base de datos.
     *
     * @param var variable de la clase que contiene los datos que se desea
     * actualizar del documento en la base de datos.
     */
    public void modificarD(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE documentos SET nombre = ?, descripcion = ?, archivo = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setString(2, var.getDescripcion());
            ps.setBytes(3, var.getArchivo());
            ps.setInt(4, var.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función que se encarga en la actualización de los datos del documento en
     * la base de datos, a diferencia del anterior, éste sólo actualiza el
     * nombre y descripción, el anterior actualiza todo.
     *
     * @param var variable de la clase que contiene los datos que se desea
     * actualizar del documento en la base de datos.
     */
    public void modificarNomD(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE documentos SET nombre = ?, descripcion = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setString(2, var.getDescripcion());
            ps.setInt(3, var.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función encargada en eliminar el registro del documento en la base de
     * datos.
     *
     * @param var variable que contiene el identificador único del documento en
     * la base de datos.
     */
    public void eliminarD(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "DELETE FROM documentos WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setInt(1, var.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función encargada en descargar y abrir el documento que se seleccionó.
     *
     * @param id es el identificador único del documento que se desea abrir en
     * la base de datos.
     */
    public void abrirD(int id) {
        ModConexion con = new ModConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte[] archivo = null;

        try {
            /**
             * Se busca el documento y se extrae sólamente el archivo.
             */
            ps = con.getConexion().prepareStatement("SELECT archivo FROM documentos WHERE id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                archivo = rs.getBytes(1);
            }

            /**
             * Cuando ya se obtiene el archivo se prepara y estructura para que
             * sea abierto.
             */
            InputStream bos = new ByteArrayInputStream(archivo);
            int tamaño = bos.available();
            byte[] datosPDF = new byte[tamaño];
            bos.read(datosPDF, 0, tamaño);

            /**
             * Se prepara para abrir el archivo que se obtuvo en formato PDF.
             */
            OutputStream out = new FileOutputStream("new.pdf");
            out.write(datosPDF);

            out.close();
            bos.close();
            ps.close();
            rs.close();
        } catch (SQLException | IOException ex) {
            System.out.println("Error al abrir archivo PDF " + ex.getMessage());
        }
    }

    /**
     * Ésta función se encarga de detectar y hacer un auto incremento a la id
     * del documento que se registró.
     *
     * @param sql contiene la dirección y los parámetros que se van a hacer para
     * detectar la id.
     * @return devuelve el incremento total de la id.
     */
    public int auto_incremento(String sql) {
        int id = 1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion con = new ModConexion();

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("id" + ex.getMessage());
            id = 1;
        }
        return id;
    }

    /**
     * Función que se encarga de mostrar todos los documentos registrados, asi
     * mismo, se encarga en generar en cada fila un botón para que se pueda
     * abrir el archivo.
     *
     * @param tabla es la tabla donde se va a mostrar todos los documentos
     * registrados.
     */
    public void visualizar(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        /**
         * Nombre de las columnas donde se van a mostrar los datos de los
         * documentos.
         */
        dt.addColumn("ID");
        dt.addColumn("Nombre");
        dt.addColumn("Status");
        dt.addColumn("Descripción");
        dt.addColumn("Archivo");

        ImageIcon icono = null;
        /**
         * Cuando se detecta algun registro y se muestre en la tabla, se le
         * asigna el formato al botón.
         */
        if (get_Image("/Imagenes/icons8_PDF_32px.png") != null) {
            icono = new ImageIcon(get_Image("/Imagenes/icons8_PDF_32px.png"));
        }

        ModListas mens = new ModListas();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = mens.listaDocs();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                /**
                 * Se llenan las columnas con el contenido extraido de la base
                 * de datos.
                 */
                Object fila[] = new Object[5];
                var = list.get(i);
                fila[0] = var.getId();
                fila[1] = var.getNombre();
                fila[2] = var.getStatus();
                fila[3] = var.getDescripcion();
                /**
                 * A la ultima columna se le inserta el botón.
                 */
                if (var.getArchivo() != null) {
                    fila[4] = new JButton(icono);
                } else {
                    fila[4] = new JButton("Vacio");
                }
                dt.addRow(fila);
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32);
        }
    }

    /**
     * Ésta función hace el mismo proceso que la anterior, el único cambio es
     * que ésta función sólo muestra en la tabla al documento que se le pida
     * mostrar.
     *
     * @param tabla tabla donde se va a mostrar los datos del documento que se
     * solicite.
     * @param nombre nombre del documento solicitado para mostrar.
     */
    public void visualizarMod(JTable tabla, String nombre) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        /**
         * Nombre de las columnas donde se van a mostrar los datos de los
         * documentos.
         */
        dt.addColumn("ID");
        dt.addColumn("Nombre");
        dt.addColumn("Status");
        dt.addColumn("Descripción");
        dt.addColumn("Archivo");

        ImageIcon icono = null;
        /**
         * Cuando se detecta algun registro y se muestre en la tabla, se le
         * asigna el formato al botón.
         */
        if (get_Image("/Imagenes/icons8_PDF_32px.png") != null) {
            icono = new ImageIcon(get_Image("/Imagenes/icons8_PDF_32px.png"));
        }

        ModListas mens = new ModListas();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = mens.Listar_PdfMod(nombre);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                /**
                 * Se llenan las columnas con el contenido extraido de la base
                 * de datos.
                 */
                Object fila[] = new Object[5];
                var = list.get(i);
                fila[0] = var.getId();
                fila[1] = var.getNombre();
                fila[2] = var.getStatus();
                fila[3] = var.getDescripcion();
                /**
                 * A la ultima columna se le inserta el botón.
                 */
                if (var.getArchivo() != null) {
                    fila[4] = new JButton(icono);
                } else {
                    fila[4] = new JButton("Vacio");
                }
                dt.addRow(fila);
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32);
        }
    }

    /**
     * Ésta función se encarga de mostrar a los empleados todos los documentos
     * visibles para ellos para que los puedan abrir y ver.
     *
     * @param tabla es la tabla donde se va a mostrar los datos extraidos de la
     * base de datos asi como el botón que le corresponde a cada fila.
     */
    public void visualizarPE(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dt.addColumn("");
        dt.addColumn("Nombre");
        dt.addColumn("Descripción");
        dt.addColumn("Archivo");

        ImageIcon icono = null;
        if (get_Image("/Imagenes/icons8_PDF_32px.png") != null) {
            icono = new ImageIcon(get_Image("/Imagenes/icons8_PDF_32px.png"));
        }

        ModListas mens = new ModListas();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = mens.listaDocs();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[4];
                var = list.get(i);

                if (var.getStatus().equals("Habilitado")) {
                    fila[0] = var.getId();
                    fila[1] = var.getNombre();
                    fila[2] = var.getDescripcion();
                    if (var.getArchivo() != null) {
                        fila[3] = new JButton(icono);
                    } else {
                        fila[3] = new JButton("Vacio");
                    }
                    dt.addRow(fila);
                }
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32);
        }
    }

    /**
     * Función encargada en asignar imagen (formato) al botón que es insertado
     * en cada fila de las tablas donde se pueden ber los documentos registrados
     * y visibles.
     *
     * @param ruta es la ruta donde está almacenada la imagen que se va a usar
     * para que se ponga en el botón.
     * @return devuelve la inserción o la negación de la imagen al botón.
     */
    public Image get_Image(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta));
            Image mainIcon = imageIcon.getImage();
            return mainIcon;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Función encargada de extraer y mostrar los avisos que aun no se han visto
     * para el usuario que esté usando el programa.
     *
     * @param tablaAvisos es la tabla donde se van a mostrar los avisos.
     * @param var variable de la clase donde se van a almacenar los datos de los
     * avisos extraidos de la base de datos.
     * @param matricula es la matrícula del usuario que esta usando el programa,
     * es utilizada para hacer el filtro y encontrar todos los avisos no visto
     * por este usuario para que se le muestre en la tabla.
     */
    public static void tablaAvisos(JTable tablaAvisos, ModVariablesAvisos var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAvisos.setModel(modelo);

        modelo.addColumn("");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesAvisos> list = mens.listaAv(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[1];
                var = list.get(i);

                fila[0] = var.getId() + "/" + var.getQuien() + ".\n" + var.getQue() + ".\nA las: " + var.getCuando();

                modelo.addRow(fila);
            }
        }
    }

    /**
     * Función encargada en actualizar en la base de datos todos los avisos que
     * son para el usuario para que no salgan en la tabla.
     *
     * @param var variable de la clase que va a servir para almacenar los datos
     * ara comparar y actualizar datos.
     * @param matricula la matrícula del usuario que esta usando el programa, es
     * utilizado para que se busquen todos los avisos y se actualicen a vistos
     * en la base de datos.
     */
    public static void LeerTodo(ModVariablesAvisos var, String matricula) {
        ModListas mens = new ModListas();
        ArrayList<ModVariablesAvisos> list = mens.listaAv(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);
                try {
                    PreparedStatement ps = null;

                    ModConexion objCon = new ModConexion();
                    Connection con = objCon.getConexion();

                    String update = "UPDATE avisos SET status = ? WHERE id = ?";
                    ps = con.prepareStatement(update);
                    ps.setString(1, "visto");
                    ps.setInt(2, var.getId());
                    ps.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Función encargada en mostrarle a los empleados los quizzes que tiene para
     * realizar (quizzes visibles).
     *
     * @param tablaSelectQuizz es la tabla donde van a salir todos los quizzes
     * activos.
     * @param var es la variable de la clase que va a almacenar los datos
     * extraidos de la base de datos para su inserción a la tabla.
     */
    public static void tablaSelectQuiz(JTable tablaSelectQuizz, ModVariablesQuizzes var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSelectQuizz.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("Nombre:");
        modelo.addColumn("Preguntas:");
        modelo.addColumn("Duración:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesQuizzes> list = mens.listaQuizz();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                /**
                 * Se hace el filtro para mostrar solo a los habilitados a la
                 * vista del empleado.
                 */
                if (var.getStatus().equals("Habilitado")) {
                    /**
                     * Se insertan los datos extraidos a las columnas.
                     */
                    fila[0] = var.getNombre();
                    fila[1] = var.getP_totales();
                    fila[2] = var.getTiempo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    /**
     * Ésta función se encarga de extraer las preguntas del quizz que se
     * presenta, lo da en forma aleatoria, compara las que ya se han insertado y
     * las descarta para encontrar las posibles para su inserción.
     *
     * @param varP es la variable de la clase que va a contener los valores
     * extraidos de la base de datos.
     * @param quizz es la identificación del quizz del cuál se van a extraer las
     * preguntas.
     * @param todos son todas las prguntas ya insertadas y por descartar.
     * @param contador indica el número de preguntas que ya se han mostrado.
     */
    public static void obtenerPreg(ModvariablesPreguntas varP, int quizz, String todos, int contador) {
        ModvariablesPreguntas var;
        ModListas mens = new ModListas();
        ArrayList<ModvariablesPreguntas> list = mens.listaPregMod(quizz);
        int a = (int) (Math.random() * list.size());
        String sub = "";

        /**
         * Si el contador es 0 se hace la inserción de la pregunta que se
         * entregó aleatoria.
         */
        if (contador == 0) {
            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);
                if (var.getIncremento() == a) {
                    varP.setId(var.getId());
                    varP.setQuizz(var.getQuizz());
                    varP.setPregunta(var.getPregunta());
                    varP.setTipo(var.getTipo());
                    varP.setNum_resp(var.getNum_resp());
                    varP.setPuntuacion_total(var.getPuntuacion_total());
                    varP.setResp1(var.getResp1());
                    varP.setR1(var.getR1());
                    varP.setResp2(var.getResp2());
                    varP.setR2(var.getR2());
                    varP.setResp3(var.getResp3());
                    varP.setR3(var.getR3());
                    varP.setResp4(var.getResp4());
                    varP.setR4(var.getR4());
                    varP.setDis1(var.getDis1());
                    varP.setDis2(var.getDis2());
                    varP.setDis3(var.getDis3());
                    varP.setDis4(var.getDis4());
                }
            }
        } else {
            boolean band = true;
            while (band == true) {
                /**
                 * Si el contador es mayor a 0, se comparan todas las preguntas
                 * insertadas para descartarlas y encontrar una que aún no se
                 * halla mostrado.
                 */
                for (int i = 0; i < list.size(); i++) {
                    var = list.get(i);
                    if (var.getIncremento() == a) {
                        for (int j = 0; j < contador; j++) {
                            String[] partir = todos.split("~");
                            sub = partir[j];
                            if (var.getId() == Integer.parseInt(sub)) {
                                a = (int) (Math.random() * list.size());
                                j = contador - 1;
                                i = list.size() - 1;
                            } else {
                                if (j == contador - 1) {
                                    i = list.size() - 1;
                                    band = false;
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);
                if (var.getIncremento() == a) {
                    varP.setId(var.getId());
                    varP.setQuizz(var.getQuizz());
                    varP.setPregunta(var.getPregunta());
                    varP.setTipo(var.getTipo());
                    varP.setNum_resp(var.getNum_resp());
                    varP.setPuntuacion_total(var.getPuntuacion_total());
                    varP.setResp1(var.getResp1());
                    varP.setR1(var.getR1());
                    varP.setResp2(var.getResp2());
                    varP.setR2(var.getR2());
                    varP.setResp3(var.getResp3());
                    varP.setR3(var.getR3());
                    varP.setResp4(var.getResp4());
                    varP.setR4(var.getR4());
                    varP.setDis1(var.getDis1());
                    varP.setDis2(var.getDis2());
                    varP.setDis3(var.getDis3());
                    varP.setDis4(var.getDis4());
                }
            }
        }
    }

    /**
     * Función encargada en obtener todos los datos importantes del quizz que se
     * está presentando para que se pueda presentar correctamente el número de
     * preguntas, de que modo va a ser calificado, etc.
     *
     * @param varQ variable de la clase que va a almacenar los datos extraidos
     * de la base de datos.
     * @param nombre es el nombre del quizz que se tiene que encontrar en la
     * base de datos y extraerle los datos.
     */
    public static void obtenerQuizz(ModVariablesQuizzes varQ, String nombre) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT * FROM quizzes WHERE nombre = '" + nombre + "'";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                varQ.setId(rs.getInt(1));
                varQ.setNombre(rs.getString(2));
                varQ.setDescripcion(rs.getString(3));
                varQ.setP_totales(rs.getString(4));
                varQ.setP_actuales(rs.getString(5));
                varQ.setStatus(rs.getString(6));
                varQ.setIntentos(rs.getString(7));
                varQ.setMod_calif(rs.getString(8));
                varQ.setTiempo(rs.getString(9));
                varQ.setF_registro(rs.getString(10));
                varQ.setF_activacion(rs.getString(11));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función para identificar y decir si existe o no la pregunta que se
     * quieres registrar en la base de datos.
     *
     * @param pregunta es la pregunta que se quiere registrar en la base de
     * datos.
     * @return entrega un 1 si existe la pregunta ó 0 si no existe.
     */
    public int existePre(String pregunta) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM preguntas WHERE pregunta = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pregunta);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Función encargada en registrar los datos obtenidos tras presentar un
     * quizz.
     *
     * @param var variable que contiene los datos que se van a registrar en la
     * base de datos.
     * @param num es el número de preguntas totales que se contestaron en el
     * quizz.
     * @param tot es el número total de sólo las preguntas abiertas que se
     * contestaron el el quizz.
     * @param mod_calif es el modo de calificación del quizz.
     * @param marca cuando se registra, ésta variable es la que le indica al
     * administrador que tiene o no el acceso a calificar el quizz.
     * @return devuelve la confirmación o negación si fué registrado los datos.
     */
    public boolean rPresentados(ModVariablesPresentados var, int num, int tot, String mod_calif, String marca) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO presentados (ident, quizz, intento, p_totales, calificacion, status, abrtNum, abrtTot, abrt, mod_calif, marca) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getIdent());
            ps.setString(2, var.getQuizz());
            ps.setString(3, var.getIntento());
            ps.setString(4, var.getP_totales());
            ps.setString(5, var.getCalificacion());
            ps.setString(6, var.getStatus());
            ps.setInt(7, num);
            ps.setInt(8, tot);
            ps.setString(9, var.getAbrt());
            ps.setString(10, mod_calif);
            ps.setString(11, marca);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función encargada de registrar sólo las preguntas abiertas contestadas en
     * el quizz.
     *
     * @param var variable de la clase que contiene los datos que se desean
     * registrar en la base de datos.
     * @param intento es el intento actual al presentar el quizz.
     * @return devuelve la confirmación o negación si se pudo hacer el registro
     * correctamente.
     */
    public boolean rPAbierta(ModVariablesRespuestas var, String intento) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO abierto (ident, puntuacion, quizz, pregunta, respuesta, status, p_asignada, retro, intento) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getIdent());
            ps.setString(2, var.getPuntuacion());
            ps.setString(3, var.getQuizz());
            ps.setString(4, var.getPregunta());
            ps.setString(5, var.getRespuesta());
            ps.setString(6, var.getStatus());
            ps.setString(7, var.getP_asignada());
            ps.setString(8, "/*null*/");
            ps.setString(9, intento);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función encargada en actualizar la respuesta de la pregunta abierta que
     * se le indique.
     *
     * @param matricula es la matrícula del empleado que está presentando el
     * quizz.
     * @param quizz es el quizz que está presentando el empleado.
     * @param pregunta es la pregunta que contestó el empleado.
     * @param respDef es la nueva respuesta que está dando el empleado a la
     * pregunta.
     * @return devuelve la confirmación o negación si se actualizó correctamente
     * la respuesta.
     */
    public boolean rPAbiertaMod(String matricula, String quizz, String pregunta, String respDef) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE abierto SET respuesta = ? WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' "
                + "AND pregunta = '" + pregunta + "' AND status = 'Por calificar')";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, respDef);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }

    /**
     * Función que se encarga de verifiar y señalar si ya se hizo previamente un
     * registro de datos con los parámetros señalados.
     *
     * @param matricula es la mtrícula del empleado que está presentando el
     * quizz.
     * @param quizz es el nombre del quizz que está presentando.
     * @param pregunta es la pregunta del quizz que está contestando.
     * @return devuelve 1 si el registro ya se ha hecho ó 0 si no.
     */
    public int existeRP(String matricula, String quizz, String pregunta) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM abierto WHERE (ident = ? AND quizz = ? AND pregunta = ? AND status = ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setString(2, quizz);
            ps.setString(3, pregunta);
            ps.setString(4, "Por calificar");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Funcíon que se encarga en extraer y mostrar por medio de una tabla los
     * datos de todos los quizzes presentados, mostrados intentos por intentos
     * de un empleado obtenidos tras haber presentado un quizz.
     *
     * @param tablaRegistro es la tabla donde se va a mostrar los datos.
     * @param var es la variable de la clase que se utilizará para almacenar los
     * datos extraidos.
     * @param matricula es la matrícula del empleado que se solicita la
     * extracción de los datos.
     * @param tipo ésta variable señala el tipo de dato extra que se mostrará
     * dependiento que tipo de usuario sea (empleado o administrador).
     */
    public static void tablaRegistro(JTable tablaRegistro, ModVariablesPresentados var, String matricula, String tipo) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRegistro.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("Quizzes");
        modelo.addColumn("Intentos");
        modelo.addColumn("Calificaciónes");
        modelo.addColumn("Status");
        modelo.addColumn("Preg. abiertas");
        /**
         * Se hace una validación para saber que tipo de usuario es y asi
         * deplegar esta columna.
         */
        if (tipo.equals("Empleado")); else {
            /**
             * Si es de tipo administrador, se le desplegara la columna donde se
             * señala si tiene acceso o no a calificar el quizz.
             */
            modelo.addColumn("Modo");
        }

        ModListas mens = new ModListas();
        ArrayList<ModVariablesPresentados> list = mens.listaReg(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                /**
                 * Se llenan las columnas con los datos extraidos.
                 */
                Object fila[] = new Object[6];
                var = list.get(i);

                fila[0] = var.getQuizz();
                fila[1] = var.getIntento();
                String calificacion = var.getCalificacion();
                String[] partir = calificacion.split("~");
                fila[2] = partir[1];
                fila[3] = var.getStatus();
                fila[4] = var.getAbrtNum();
                if (tipo.equals("Empleado")); else {
                    fila[5] = var.getMarca();
                }

                modelo.addRow(fila);
            }
        }
    }

    /**
     * Función encargada de mostrar por medio de una tabla las calificaciónes
     * obtenidas por los empleados tras presentar un quizz, en esta tabla ya se
     * refleja el modo de calificación del quizz presentado.
     *
     * @param tablaRegistro es la tabla donde se mostrarán los datos obtenidos.
     * @param var es la variable de la clase que va a almacenar los datos
     * extraidos de la base de datos.
     * @param matricula es la matrícula del empleado que se solicita ver sus
     * calificaciónes.
     */
    public static void tablaCalif(JTable tablaRegistro, ModVariablesCalif var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            /**
             * Cancelación de la edición de la tabla cuando se muestre.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRegistro.setModel(modelo);

        /**
         * Nombre de las columnas de la tabla.
         */
        modelo.addColumn("Quizzes");
        modelo.addColumn("Intentos");
        modelo.addColumn("Calificaciónes");
        modelo.addColumn("Status");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesCalif> list = mens.listaCalifMod(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                /**
                 * Se llena las columnas con los datos obtenidos.
                 */
                Object fila[] = new Object[4];
                var = list.get(i);

                fila[0] = var.getQuizz();
                fila[1] = var.getIntentos();
                fila[2] = var.getPuntos();
                fila[3] = var.getStatus();

                modelo.addRow(fila);
            }
        }
    }

    /**
     * Función que se encarga en encriptar los datos de la base de datos
     * utilizados para hacer las conexiones a la misma.
     *
     * @param texto es el texto (datos) que se desea encriptar.
     * @return devuelve el texto ya encriptado.
     */
    public static String Encriptar(String texto) {

        String secretKey = "qualityinfosolutions";
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
        }
        return base64EncryptedString;
    }

    /**
     * Función que se encarga de extraér y desencriptar el texto para poder ser
     * utilizado en la conexión a la base de datos.
     *
     * @param textoEncriptado es el texto extraído de un archivo de texto el
     * cual se desea desencriptar.
     * @return devuelve el texto ya desencriptado.
     * @throws Exception
     */
    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "qualityinfosolutions";
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
        }
        return base64EncryptedString;
    }

    /**
     * Nombre del archivo de texto en el cuál se va a almacenar el texto
     * encriptado.
     */
    File archivo = new File("texto.txt");

    public void Enc() {
        crearArchivo();
    }

    /**
     * Función encargada de generar el archivo.
     */
    void crearArchivo() {
        try {
            if (archivo.exists()) {

            } else {
                archivo.createNewFile();
            }
        } catch (IOException e) {
        }
    }

    /**
     * Función que se encarga de gestionar el encriptado del texto.
     *
     * @param texto texto que se obtiene de la interfaz gráfica.
     */
    public void encriptar(Vista.VstConfiguracion.Texto texto) {
        try {
            try (ObjectOutputStream escribir = new ObjectOutputStream(new FileOutputStream(archivo))) {
                escribir.writeObject(texto);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Función que se encarga de gestionar el desencriptado del texto.
     *
     * @return devuelve el texto desencriptado para el manejo y la conexión a la
     * base de datos.
     */
    public Texto desencriptar() {
        try {
            Texto oTexto;
            try (ObjectInputStream leer = new ObjectInputStream(new FileInputStream(archivo))) {
                oTexto = (Texto) leer.readObject();
            }
            return oTexto;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Función encargada en extraer los datos necesariós para poder hacer
     * validaciónes y excepxiones.
     *
     * @param var es la variable de la clase donde se va a almacenar los datos
     * extraidos de la base de datos.
     * @param matricula es la matrícula del empleado que presenta o presentó el
     * quizz.
     * @param intento es el intento que el empleado ha utilizado para presentar
     * el quizz.
     * @param quizz es el nombre del quizz del cual se va a obtener los datos.
     */
    public static void obtenerResp(ModVariablesPresentados var, String matricula, String intento, String quizz) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT * FROM presentados WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND intento = '" + intento + "')";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setIdent(rs.getString(2));
                var.setQuizz(rs.getString(3));
                var.setIntento(rs.getString(4));
                var.setP_totales(rs.getString(5));
                var.setCalificacion(rs.getString(6));
                var.setStatus(rs.getString(7));
                var.setTotales(rs.getString(8));
                var.setAbrtTot(rs.getString(9));
                var.setAbrt(rs.getString(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función encargada en extraer y mostrar las respuestas que dan los
     * empleados a las preguntas del quizz que presentaron, se usa para que el
     * administrador pueda calificar.
     *
     * @param va es la variable de la interfaz gráfica donde ocurre la acción.
     * @param var es la variable de la clase que va a almacenar los datos
     * extraidos de la base de datos.
     * @param matricula es la matrícula del empleado al cuál se ver la respuesta
     * que dió a la pregunta del quizz.
     * @param quizz es el nombre del quizz del cual se va a obtener las
     * respuestas que el empleado dió.
     * @param pregunta es la pregunta de la cuál se solicita ver la respuesta.
     * @param intento es el intento que el empleado ha utilizado para presentar
     * el quizz.
     */
    public static void llenarResp(VstAvances va, ModVariablesAbierto var, String matricula, String quizz, String pregunta, String intento) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT * FROM abierto WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND pregunta = '" + pregunta + "' AND intento = '" + intento + "')";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setIdent(rs.getString(2));
                var.setPuntuacion(rs.getString(3));
                var.setQuizz(rs.getString(4));
                var.setPregunta(rs.getString(5));
                var.setRespuesta(rs.getString(6));
                var.setStatus(rs.getString(7));
                var.setP_asignada(rs.getString(8));

                if (rs.getString(9).equals("/*null*/")) {
                    va.txtComentario.setText(null);
                } else {
                    va.txtComentario.setText(rs.getString(9));
                }

                va.txtPregunta.setText(var.getPregunta());
                va.txtRespuesta.setText(var.getRespuesta());
                va.txtPuntuacion.setText(var.getPuntuacion());
                va.txtCalificacion.setText(var.getP_asignada());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función encargada de mostrar al empleado lo que ha contestado en el quizz
     * luego de haber presentado, asi mismo se le presenta los puntos que vale
     * la pregunta y si el administrador ya calificó esa pregunta, aparece la
     * puntuación que le dió el administrador asi como el comentario o
     * retroalimentación que le dió.
     *
     * @param vp es la variable de la interfaz gráfica donde ocurre la acción.
     * @param var es la variable de la clase donde se va a almacenar los datos
     * extraidos de la base de datos.
     * @param matricula es la matrícula del empleado que desea ver sus datos.
     * @param quizz es el nombre de quizz del cual provienen las preguntas que
     * el empleado desea ver.
     * @param pregunta es la pregunta que el empleado desea ver la respuesta.
     */
    public static void llenarRespMod(VstPerfilEmp vp, ModVariablesAbierto var, String matricula, String quizz, String pregunta) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT * FROM abierto WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND pregunta = '" + pregunta + "')";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setIdent(rs.getString(2));
                var.setPuntuacion(rs.getString(3));
                var.setQuizz(rs.getString(4));
                var.setPregunta(rs.getString(5));
                var.setRespuesta(rs.getString(6));
                var.setStatus(rs.getString(7));
                var.setP_asignada(rs.getString(8));

                if (rs.getString(9).equals("/*null*/")) {
                    vp.txtComentario.setText("SIN COMENTARIO O RETROALIMANTECIÓN.");
                } else {
                    vp.txtComentario.setText(rs.getString(9));
                }

                vp.txtPregunta.setText(var.getPregunta());
                vp.txtRespuesta.setText(var.getRespuesta());
                vp.txtPuntuacion.setText(var.getPuntuacion());
                vp.txtCalificacion.setText(var.getP_asignada());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función que le sirve al administrador al momento de calificar algúna
     * pregunta del algún quizz.
     *
     * @param matricula matrícula del empleado que se va a calificar.
     * @param intento es el intento utilizado por el empleado para contestar el
     * quizz.
     * @param quizz es el nombre del quizz que presentó el empleado.
     * @param puntos son los puntos que el administrador le asigno a la
     * respuesta del empleado.
     * @param comentario es el comentario o retroalimentación que el
     * administrador le dió a la respuesta del empleado.
     */
    public void calificar(String matricula, String intento, String quizz, String puntos, String comentario) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE abierto SET status = ?, p_asignada = ?, retro = ? WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND pregunta = '" + intento + "')";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, "Calificado");
            ps.setString(2, puntos);
            ps.setString(3, comentario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función que se encarga de saber si ya fue registrada la calificación del
     * empleado.
     *
     * @param matricula es la matrícula del empleado que se desea saber si ya
     * tiene calificación.
     * @param quizz es el nombre del quizz del cuál se solicita daber si ya fué
     * calificado.
     * @param mod es el modo de calificación que tiene el quizz.
     * @return devuelve 1 si ya tiene calificación ó 0 si no lo tiene.
     */
    public int existeCalif(String matricula, String quizz, String mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM calificaciones WHERE (ident = ? AND quizz = ? AND mod_calif = ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setString(2, quizz);
            ps.setString(3, mod);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    /**
     * Función encargada de extraer la puntuación máxima que puede tener algun
     * quizz.
     *
     * @param matricula es la matrícula del empleado que presento algún quizz.
     * @param quizz es el nombre del quizz que se va a extraer el dato
     * necesario.
     * @param mod es el modo de calificación que tiene el quizz.
     * @return entrega la puntuación máxima que puede tener un quizz.
     */
    public String Ptotales(String matricula, String quizz, String mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT Tpuntos FROM calificaciones WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND mod_calif = '" + mod + "')";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Función modificada de la función anterior, la modificación en esta
     * función es que tambien devuelve el numero de intentos que hizo el
     * empleado para contestar el quizz.
     *
     * @param matricula es la matrícula del empleado que presento algún quizz.
     * @param quizz es el nombre del quizz que se va a extraer el dato
     * necesario.
     * @param mod es el modo de calificación que tiene el quizz.
     * @return entrega el intento actual para contestar el quizz y la puntuación
     * máxima que puede tener un quizz.
     */
    public String PtotalesMod(String matricula, String quizz, String mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT intentos, Tpuntos FROM calificaciones WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND mod_calif = '" + mod + "')";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1) + "~" + rs.getString(2);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Función que se encarga en registrar inmediatamente en la base de datos
     * los valores que obtuvo un empleado al momento de terminar un quizz.
     *
     * @param matricula es la matrícula del empleado que presentó el quizz.
     * @param intentos es el número de intentos actual que le tomó al empleado
     * contestar el quizz.
     * @param quizz es el nombre del quizz que presentó.
     * @param puntos es la puntuación que obtuvo al terminar de contestar el
     * quizz.
     * @param status es el status correspondiente a la puntuación que obtuvo
     * (aprobado o reprobado).
     * @param mod es el modo de calificación que tiene el quizz.
     * @param Tpuntos es la puntuación máxima que pudo obtener en el quizz.
     */
    public void rCalif(String matricula, String intentos, String quizz, String puntos, String status, String mod, String Tpuntos) {

        try {
            PreparedStatement ps = null;
            Connection con = getConexion();

            String sql = "INSERT INTO calificaciones (ident, intentos, quizz, puntos, status, mod_calif, Tpuntos) VALUES(?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setString(2, intentos);
            ps.setString(3, quizz);
            ps.setString(4, puntos);
            ps.setString(5, status);
            ps.setString(6, mod);
            ps.setString(7, Tpuntos);
            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función que se encarga en actualizar los datos de la calificación del
     * empleado.
     *
     * @param puntos la nueva puntuación del empleado que se va a actualizar en
     * la base de datos.
     * @param status es el status correspondiente a la puntuación que obtuvo
     * (aprobado o reprobado).
     * @param intentos es el número de intentos actual que le tomó al empleado
     * contestar el quizz.
     * @param Tpuntos es la puntuación máxima que pudo obtener en el quizz.
     * @param matricula es la matrícula del empleado que presentó el quizz.
     * @param quizz es el nombre del quizz que presentó.
     * @param mod es el modo de calificación que tiene el quizz.
     */
    public void updCalif(String puntos, String status, String intentos, String Tpuntos, String matricula, String quizz, String mod) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE calificaciones SET intentos = ?, puntos = ?, status = ?, Tpuntos = ? WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND mod_calif = '" + mod + "')";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, intentos);
            ps.setString(2, puntos);
            ps.setString(3, status);
            ps.setString(4, Tpuntos);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Función encargada en cambiar el acceso al momento de calificar algun
     * quizz que tenga preguntas abiertas.
     *
     * @param matricula es la matrícula del empleado que presentó algún quizz.
     * @param quizz el nombre del quizz que se va a cambiar el acceso.
     * @param intento es el número de intentos actual que le tomó al empleado
     * contestar el quizz.
     */
    public void camMarca(String matricula, String quizz, String intento) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE presentados SET marca = ? WHERE (ident = '" + matricula + "' AND quizz = '" + quizz + "' AND intento = '" + intento + "')";
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, "Sin acceso");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
