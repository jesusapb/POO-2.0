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
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @vesion 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModConsultasSQL extends ModConexion {

    public boolean login(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        String contr = var.getContraseña();
        int cont = contr.length();
        String s = "";
        if (cont >= 6) {
            s = contr.substring(0, 6).toUpperCase();
        }

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);

                if (var.getMatricula().equals(varU.getMatricula()) || var.getMatricula().equals(varU.getCorreo())) {
                    if ("Conectado".equals(varU.getStatus())) {
                        JOptionPane.showMessageDialog(null, "El usuario " + varU.getNombre_completo() + " tiene su sesión activa.");
                        return false;
                    } else if ("Habilitado".equals(varU.getComando())) {
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
                        JOptionPane.showMessageDialog(null, "Hola " + varU.getNombre_completo() + ".\n"
                                + "Se ha detectado que es tu primer inicio de sesión, por\n"
                                + "lo tanto, se le pide el inmediato cambio de contraseña.\n"
                                + "Se le habilitara únicamente el campo de ingreso de la\n "
                                + "contraseña, ahi deberá de ingresar su nueva contraseña\n"
                                + "y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                        var.setMensaje("Detección de nuevo usuario.");
                        return false;
                    } else if (var.getContraseña().equals(varU.getContraseña())) {
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
                            JOptionPane.showMessageDialog(null, "No se puede hacer al uso de esta función,\nya que el usuario se encuentra conectado o suspendido.");
                            return false;
                        } else if ("@NUEVO".equals(sub)) {
                            JOptionPane.showMessageDialog(null, "No se puede hacer el uso de esta función,\nya que el usuario es nuevo.");
                            return false;
                        } else {
                            String update = "UPDATE usuarios SET comando = ?, codigo = ? WHERE id = ?";
                            ps = con.prepareStatement(update);
                            ps.setString(1, "Habilitado");

                            String uuid = UUID.randomUUID().toString();
                            ps.setString(2, uuid);

                            ps.setInt(3, varU.getId());
                            ps.execute();

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
                            //**************************************************************
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

    public boolean loginNuevo(ModVariablesUsr var) {
        ModVariablesUsr varU = new ModVariablesUsr();
        ModListas mens = new ModListas();
        ArrayList<ModVariablesUsr> list = mens.listaUs();

        String contr = var.getContraseña();
        String s = contr.substring(0, 6).toUpperCase();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                varU = list.get(i);

                if (varU.getMatricula().equals(var.getMatricula()) || varU.getCorreo().equals(var.getMatricula())) {
                    if (var.getContraseña().equals(varU.getContraseña())) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else if ("@NUEVO".equals(s)) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else {
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

    public static void recarga(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion mod = new ModConexion();
        Connection con = mod.getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, correo "
                + "FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            if (rs.next()) {
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
                    try {
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

    public boolean email(ModVariablesUsr varU, ModVariablesReg varR) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            //**************************************************************
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
            //**************************************************************
            return true;
        } catch (AddressException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void tablaEmp(JTable tablaRegistrados) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            tablaRegistrados.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            String sql = "SELECT matricula, nombre, ap_pat, ap_mat, correo, tipo FROM usuarios WHERE tipo = 'Empleado' order by ap_pat";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Matrícula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Apellido Materno");

            while (rs.next()) {
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

    public static void tablaConectados(ModVariablesUsr var, JTable tablaConectados, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaConectados.setModel(modelo);

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
                if (var.getMatricula().equals(matricula)); else if (var.getStatus().equals("Conectado")) {
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

    public static void tablaAva(JTable TablaAvances, VstEmpleados vce) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            TablaAvances.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            String sql = "SELECT nombre_examen, dia, hora, puntos, status, matricula FROM presentados WHERE matricula = '" + vce.txtMatricula.getText() + "'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Quiz");
            modelo.addColumn("Día presentado");
            modelo.addColumn("Hora presentada");
            modelo.addColumn("Calificación");
            modelo.addColumn("Status");

            while (rs.next()) {
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

    public static void tablaQuizz(JTable tablaQuizzes) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            tablaQuizzes.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            String sql = "SELECT nombre, descripcion, status, p_totales, p_actuales, f_registro, f_activacion FROM quizzes order by nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Nombre");
            modelo.addColumn("Descripción");
            modelo.addColumn("Status");
            modelo.addColumn("#PregTot");
            modelo.addColumn("#PregAct");
            modelo.addColumn("FRegistro");
            modelo.addColumn("FActivado");

            while (rs.next()) {
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

    public static void tablaDocs(JTable tablaDocs) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            tablaDocs.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            String sql = "SELECT nombre, status, descripcion FROM documentos order by nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Nombre");
            modelo.addColumn("Status");
            modelo.addColumn("Descripción");

            while (rs.next()) {
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

    public static void tablaTEmp(JTable tablaTUsuarios, ModVariablesReg var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaTUsuarios.setModel(modelo);

        modelo.addColumn("Usuarios:");
        modelo.addColumn("Tipo:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesReg> list = mens.listaE();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[2];
                var = list.get(i);
                if (var.getMatricula().equals(matricula)); else {
                    fila[0] = var.getNombre_completo();
                    fila[1] = var.getTipo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    public static void recibidos(JTable tablaBandejaEntrada, ModVariablesMensaje var, ModVariablesUsr varU) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaBandejaEntrada.setModel(modelo);

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

                if (var.getPara_mat().equals(varU.getMatricula())) {
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

    public static void leer(String a, String b, VstRecibido vr, ModVariablesMensaje var) {
        try {
            //a: id, b: status.
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            ps = con.prepareStatement("SELECT id, de_mat, de_nom, para_mat, para_nom, fecha, asunto, mensaje, status "
                    + "FROM mensajes WHERE id = ?");
            ps.setString(1, a);
            rs = ps.executeQuery();

            if (rs.next()) {
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

    public static void enviados(JTable tablaEnviados, ModVariablesMensaje var, ModVariablesUsr varU) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaEnviados.setModel(modelo);

        modelo.addColumn("ID:");
        modelo.addColumn("Para:");
        modelo.addColumn("Asunto:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                if (var.getDe_mat().equals(varU.getMatricula())) {
                    fila[0] = var.getId();
                    fila[1] = var.getPara_mat() + "/" + var.getPara_nom();
                    fila[2] = var.getAsunto();

                    modelo.addRow(fila);
                }
            }
        }
    }

    public static void mensaje(String a, VstEnviados ve, ModVariablesMensaje var) {
        try {
            //a: id, b: status.
            PreparedStatement ps = null;
            ResultSet rs = null;

            ModConexion objCon = new ModConexion();
            Connection con = objCon.getConexion();

            ps = con.prepareStatement("SELECT id, de_mat, de_nom, para_mat, para_nom, fecha, asunto, mensaje, status "
                    + "FROM mensajes WHERE id = ?");
            ps.setString(1, a);
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

    public static void DocsAct(JTable tablaADocumentos, ModVariablesDoc var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaADocumentos.setModel(modelo);

        modelo.addColumn("Nombre:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesDoc> list = mens.listaDocs();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                if (var.getStatus().equals("Habilitado")) {
                    fila[0] = var.getNombre();

                    modelo.addRow(fila);
                }
            }
        }
    }

    public static void QuizzAct(JTable tablaAQuizzes, ModVariablesQuizzes var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaAQuizzes.setModel(modelo);

        modelo.addColumn("Nombre:");
        modelo.addColumn("FActivo:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesQuizzes> list = mens.listaQuizz();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                if (var.getStatus().equals("Habilitado")) {
                    fila[0] = var.getNombre();
                    fila[1] = var.getF_activacion();

                    modelo.addRow(fila);
                }
            }
        }
    }

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

    public static void tablaPreg(JTable tablaPreguntas, ModvariablesPreguntas var, String id) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaPreguntas.setModel(modelo);

        modelo.addColumn("ID:");
        modelo.addColumn("Pregunta:");
        modelo.addColumn("Tipo:");

        ModListas mens = new ModListas();
        ArrayList<ModvariablesPreguntas> list = mens.listaPreg();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                if (var.getQuizz().equals(id)) {
                    fila[0] = var.getId();
                    fila[1] = var.getPregunta();
                    fila[2] = var.getTipo();

                    modelo.addRow(fila);
                }
            }
        }
    }

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

    public boolean avisoAA(ModVariablesReg var, String tipo, String quien, String que, String cuando, String comp) {
        ModListas mens = new ModListas();
        ArrayList<ModVariablesReg> list = mens.listaT(tipo, comp);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                var = list.get(i);

                //System.out.println("Para: " + var.getAvisos() + ". De: " + quien + ". Hizo: " + que + ". A las: " + cuando);
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

    public boolean aviso(ModVariablesReg var, String quien, String que, String cuando, String para) {

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

    public void abrirD(int id) {
        ModConexion con = new ModConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte[] archivo = null;

        try {
            ps = con.getConexion().prepareStatement("SELECT archivo FROM documentos WHERE id = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                archivo = rs.getBytes(1);
            }

            InputStream bos = new ByteArrayInputStream(archivo);
            int tamaño = bos.available();
            byte[] datosPDF = new byte[tamaño];
            bos.read(datosPDF, 0, tamaño);

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

    public void visualizar(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
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

        ModListas mens = new ModListas();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = mens.Listar_Pdf();

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

    public void visualizarMod(JTable tabla, String nombre) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
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

        ModListas mens = new ModListas();
        ModVariablesDoc var = new ModVariablesDoc();
        ArrayList<ModVariablesDoc> list = mens.Listar_PdfMod(nombre);

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

    public void visualizarPE(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new ModFunTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
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
        ArrayList<ModVariablesDoc> list = mens.Listar_Pdf();

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

    public static void tablaAvisos(JTable tablaAvisos, ModVariablesAvisos var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
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

    public static void tablaSelectQuiz(JTable tablaSelectQuizz, ModVariablesQuizzes var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaSelectQuizz.setModel(modelo);

        modelo.addColumn("Nombre:");
        modelo.addColumn("Preguntas:");
        modelo.addColumn("Duración:");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesQuizzes> list = mens.listaQuizz();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[3];
                var = list.get(i);

                if (var.getStatus().equals("Habilitado")) {
                    fila[0] = var.getNombre();
                    fila[1] = var.getP_totales();
                    fila[2] = var.getTiempo();

                    modelo.addRow(fila);
                }
            }
        }
    }

    public static void obtenerPreg(ModvariablesPreguntas varP, int quizz, String todos, int contador) {
        ModvariablesPreguntas var;
        ModListas mens = new ModListas();
        ArrayList<ModvariablesPreguntas> list = mens.listaPregMod(quizz);
        int a = (int) (Math.random() * list.size());
        String sub = "";

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

    public static void tablaRegistro(JTable tablaRegistro, ModVariablesPresentados var, String matricula, String tipo) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaRegistro.setModel(modelo);

        modelo.addColumn("Quizzes");
        modelo.addColumn("Intentos");
        modelo.addColumn("Calificaciónes");
        modelo.addColumn("Status");
        modelo.addColumn("Preg. abiertas");
        if (tipo.equals("Empleado")); else {
            modelo.addColumn("Modo");
        }

        ModListas mens = new ModListas();
        ArrayList<ModVariablesPresentados> list = mens.listaReg(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
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

    public static void tablaCalif(JTable tablaRegistro, ModVariablesCalif var, String matricula) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaRegistro.setModel(modelo);

        modelo.addColumn("Quizzes");
        modelo.addColumn("Intentos");
        modelo.addColumn("Calificaciónes");
        modelo.addColumn("Status");

        ModListas mens = new ModListas();
        ArrayList<ModVariablesCalif> list = mens.listaCalifMod(matricula);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
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

    public static String Encriptar(String texto) {

        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
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

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
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

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    File archivo = new File("texto.txt");

    public void Enc() {
        crearArchivo();
    }

    void crearArchivo() {
        try {
            if (archivo.exists()) {

            } else {
                archivo.createNewFile();
            }
        } catch (Exception e) {
        }
    }

    public void encriptar(Vista.VstConfiguracion.Texto texto) {
        try {
            ObjectOutputStream escribir = new ObjectOutputStream(new FileOutputStream(archivo));
            escribir.writeObject(texto);
            escribir.close();
        } catch (Exception e) {
        }
    }

    public Texto desencriptar() {
        try {
            ObjectInputStream leer = new ObjectInputStream(new FileInputStream(archivo));
            Texto oTexto = (Texto) leer.readObject();
            leer.close();
            return oTexto;
        } catch (Exception e) {
            return null;
        }
    }

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
