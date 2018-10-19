/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.Administrador.VstEmpleados;
import Vista.Administrador.VstPreguntas;
import Vista.Mensajes.VstEnviados;
import Vista.Mensajes.VstRecibido;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Antonio
 */
public class ModConsultasSQL extends ModConexion {

    public boolean sesion(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, contraseña, correo, status, comando, codigo, ip, equipo "
                + "FROM usuarios WHERE ip = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getIp());
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setStatus(rs.getString(9));

                String equipo = rs.getString(13);

                if ("Permanente".equals(rs.getString(9))) {
                    JOptionPane.showMessageDialog(null, "Se le há negado el accedo\n"
                            + "totalmente, le sugerimos ponerse\n"
                            + "en contacto con el administrador\n"
                            + "a traves de poo.acompanamiento@gmail.com");
                    return false;
                } else if (var.getStatus().equals("Conectado")) {
                    if (var.getEquipo().equals(equipo)) {
                        String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                        ps = con.prepareStatement(update);
                        ps.setString(1, var.getDia());
                        ps.setString(2, var.getHora());
                        ps.setString(3, "Conectado");
                        ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                        ps.setString(5, InetAddress.getLocalHost().getHostName());
                        ps.setInt(6, rs.getInt(1));
                        ps.execute();

                        var.setId(rs.getInt(1));
                        var.setNombre(rs.getString(2));
                        var.setAp_pat(rs.getString(3));
                        var.setAp_mat(rs.getString(4));
                        var.setTipo(rs.getString(5));
                        var.setMatricula(rs.getString(6));
                        var.setComando(rs.getString(10));
                        var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException | UnknownHostException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Inicio LOGIN//
    public boolean loginMat(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, contraseña, correo, status, comando, codigo "
                + "FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setStatus(rs.getString(9));

                String pass = rs.getString(7);
                //String[] parte = pass.split("/");
                //String p = parte[0];
                String contr = var.getContraseña();
                int cont = contr.length();
                String s = "";

                if (cont >= 6) {
                    s = contr.substring(0, 6).toUpperCase();
                }

                if ("Permanente".equals(rs.getString(9))) {
                    JOptionPane.showMessageDialog(null, "Se le há negado el accedo\n"
                            + "totalmente, le sugerimos ponerse\n"
                            + "en contacto con el administrador\n"
                            + "a traves de poo.acompanamiento@gmail.com");
                    return false;
                } else if ("Conectado".equals(rs.getString(9))) {
                    JOptionPane.showMessageDialog(null, "El usuario " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                            + " tiene su sesión activa.");
                    return false;
                } else if ("Habilitado".equals(rs.getString(10))) {
                    if (var.getContraseña().equals(rs.getString(11))) {
                        JOptionPane.showMessageDialog(null, "Hola " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + ".\n"
                                + "Se há detectado que solicitó el cambio de su contraseña\n"
                                + "por medio de la funsión *¿Olvidaste tu contraseña?*, por"
                                + "este medio, se va a habilitar únicamente el campo de\n "
                                + "ingreso de la contraseña, ahí debera de ingresar su\n"
                                + "nueva contraseña y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                        var.setMensaje("Detección de cambio de contraseña.");
                        /*vl.txtMat.setEditable(false);
                        vl.txtPass.setText(null);
                        vl.btnAcceder.setVisible(false);
                        vl.btnValidar.setVisible(true);*/
                        return true;
                    }

                } else if ("@NUEVO".equals(s)) {
                    JOptionPane.showMessageDialog(null, "Hola " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + ".\n"
                            + "Se há detectado que es tu primer inicio de sesión, por\n"
                            + "lo tanto, se le pide el inmediato cambio de contraseña.\n"
                            + "Se le habilitara únicamente el campo de ingreso de la\n "
                            + "contraseña, ahi deberá de ingresar su nueva contraseña\n"
                            + "y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    var.setMensaje("Detección de nuevo usuario.");
                    /*vl.txtMat.setEditable(false);
                    vl.txtPass.setText(null);
                    vl.btnAcceder.setVisible(false);
                    vl.btnValidar.setVisible(true);*/
                    return true;

                } else if (var.getContraseña().equals(pass)) {
                    String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                    ps = con.prepareStatement(update);
                    ps.setString(1, var.getDia());
                    ps.setString(2, var.getHora());
                    ps.setString(3, "Conectado");
                    ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                    ps.setString(5, InetAddress.getLocalHost().getHostName());
                    ps.setInt(6, rs.getInt(1));
                    ps.execute();

                    var.setId(rs.getInt(1));
                    var.setNombre(rs.getString(2));
                    var.setAp_pat(rs.getString(3));
                    var.setAp_mat(rs.getString(4));
                    var.setTipo(rs.getString(5));
                    var.setMatricula(rs.getString(6));
                    var.setCorreo(rs.getString(8));
                    var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException | UnknownHostException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //**************************************************************************

    public boolean loginCor(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, contraseña, correo, status, comando, codigo "
                + "FROM usuarios WHERE correo = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getCorreo());
            rs = ps.executeQuery();

            if (rs.next()) {
                var.setStatus(rs.getString(9));

                String pass = rs.getString(7);
                //String[] parte = pass.split("/");
                //String p = parte[0].toUpperCase();
                String contr = var.getContraseña();
                int cont = contr.length();
                String s = "";

                if (cont >= 6) {
                    s = contr.substring(0, 6).toUpperCase();
                }

                if ("Permanente".equals(rs.getString(9))) {
                    JOptionPane.showMessageDialog(null, "Se le há negado el accedo\n"
                            + "totalmente, le sugerimos ponerse\n"
                            + "en contacto con el administrador\n"
                            + "a traves de poo.acompanamiento@gmail.com");
                    return false;
                } else if ("Conectado".equals(rs.getString(9))) {
                    JOptionPane.showMessageDialog(null, "El usuario " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
                            + " tiene su sesión activa.");
                    return false;
                } else if ("Habilitado".equals(rs.getString(10))) {
                    if (var.getContraseña().equals(rs.getString(11))) {
                        JOptionPane.showMessageDialog(null, "Hola " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + ".\n"
                                + "Se há detectado que solicitó el cambio de su contraseña\n"
                                + "por medio de la funsión *¿Olvidaste tu contraseña?*, por"
                                + "este medio, se va a habilitar únicamente el campo de\n "
                                + "ingreso de la contraseña, ahí debera de ingresar su\n"
                                + "nueva contraseña y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                        var.setMensaje("Detección de cambio de contraseña.");
                        /*vl.txtMat.setEditable(false);
                        vl.txtPass.setText(null);
                        vl.btnAcceder.setVisible(false);
                        vl.btnValidar.setVisible(true);*/
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "Código de seguridad incorrecto,\n favor de verificarlo.");
                    return false;
                } else if ("@NUEVO".equals(s)) {
                    JOptionPane.showMessageDialog(null, "Hola " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + ".\n"
                            + "Se há detectado que es tu primer inicio de sesión, por\n"
                            + "lo tanto, se le pide el inmediato cambio de contraseña.\n"
                            + "Se le habilitara únicamente el campo de ingreso de la\n "
                            + "contraseña, ahi deberá de ingresar su nueva contraseña\n"
                            + "y esperar la validación.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    var.setMensaje("Detección de nuevo usuario.");
                    /*vl.txtMat.setEditable(false);
                    vl.txtPass.setText(null);
                    vl.btnAcceder.setVisible(false);
                    vl.btnValidar.setVisible(true);*/
                    return false;

                } else if (var.getContraseña().equals(pass)) {
                    String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                    ps = con.prepareStatement(update);
                    ps.setString(1, var.getDia());
                    ps.setString(2, var.getHora());
                    ps.setString(3, "Conectado");
                    ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                    ps.setString(5, InetAddress.getLocalHost().getHostName());
                    ps.setInt(6, rs.getInt(1));
                    ps.execute();

                    var.setId(rs.getInt(1));
                    var.setNombre(rs.getString(2));
                    var.setAp_pat(rs.getString(3));
                    var.setAp_mat(rs.getString(4));
                    var.setTipo(rs.getString(5));
                    var.setMatricula(rs.getString(6));
                    var.setCorreo(rs.getString(8));
                    var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException | UnknownHostException ex) {
            //JOptionPane.showMessageDialog(null, "Matricula/Correo o Contraseña incorrecta.");
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //**************************************************************************

    public boolean emailCorreo(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, correo, status, comando, codigo, contraseña "
                + "FROM usuarios WHERE correo = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setCorreo(rs.getString(7));
                var.setStatus(rs.getString(8));
                var.setComando(rs.getString(9));
                var.setCodigo(rs.getString(10));
                var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                //**************************************************************
                String contra = rs.getString(11);
                String sub = contra.substring(0, 6).toUpperCase();

                if ("Conectado".equals(var.getStatus()) || "Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "No se puede hacer al uso de esta función,\nya que el usuario se encuentra conectado o suspendido.");
                    return false;
                } else if ("@NUEVO".equals(sub)) {
                    JOptionPane.showMessageDialog(null, "No se puede hacer el uso de esta función,\nya que el usuario es nuevo.");
                    var.setMensaje("Nuevo");
                    return false;
                } else {
                    String update = "UPDATE usuarios SET comando = ?, codigo = ? WHERE id = ?";
                    ps = con.prepareStatement(update);
                    ps.setString(1, "Habilitado");

                    String uuid = UUID.randomUUID().toString();
                    ps.setString(2, uuid);

                    ps.setInt(3, rs.getInt(1));
                    ps.execute();

                    String correoRemitente = "poo.acompanamiento@gmail.com";
                    String passwordRemitente = "administrador";
                    String correoReceptor = rs.getString(7);
                    String asunto = "CÓDIGO DE SEGURIDAD.";
                    String mensaje = "Su código de seguridad es: " + uuid + ". El cual debe de ingresar en el campo correspondiente de la contraseña en la ventana de Inicio de sesión. Así mismo debe de ingresar su matricula o correo eléctronico en su campo correspondiente y acceder, de este modo podra hacer el cambio inmediato de tu contraseña.";

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
            }
            return false;
            //******************************************************************
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (AddressException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //**************************************************************************

    public boolean emailMatricula(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                + "tipo, matricula, correo, status, comando, codigo, contraseña "
                + "FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            if (rs.next()) {
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setCorreo(rs.getString(7));
                var.setStatus(rs.getString(8));
                var.setComando(rs.getString(9));
                var.setCodigo(rs.getString(10));
                var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                //**************************************************************
                String contra = rs.getString(11);
                String sub = contra.substring(0, 6).toUpperCase();

                if ("Conectado".equals(var.getStatus()) || "Permanente".equals(var.getStatus())) {
                    JOptionPane.showMessageDialog(null, "No se puede hacer al uso de esta función,\nya que el usuario se encuentra conectado o suspendido.");
                    return false;
                } else if ("@NUEVO".equals(sub)) {
                    JOptionPane.showMessageDialog(null, "No se puede hacer el uso de esta función,\nya que el usuario es nuevo.");
                    var.setMensaje("Nuevo");
                    return false;
                } else {
                    String update = "UPDATE usuarios SET comando = ?, codigo = ? WHERE id = ?";
                    ps = con.prepareStatement(update);
                    ps.setString(1, "Habilitado");

                    String uuid = UUID.randomUUID().toString();
                    ps.setString(2, uuid);

                    ps.setInt(3, rs.getInt(1));
                    ps.execute();

                    String correoRemitente = "poo.acompanamiento@gmail.com";
                    String passwordRemitente = "administrador";
                    String correoReceptor = rs.getString(7);
                    String asunto = "CÓDIGO DE SEGURIDAD.";
                    String mensaje = "Su código de seguridad es: " + uuid + ". El cual debe de ingresar en el campo correspondiente de la contraseña en la ventana de Inicio de sesión. Así mismo debe de ingresar su matricula o correo eléctronico en su campo correspondiente y acceder, de este modo podra hacer el cambio inmediato de tu contraseña.";

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
            }
            return false;
            //******************************************************************
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se encontro la matrícula.");
            return false;
        } catch (AddressException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error en el proceso.");
        } catch (MessagingException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo enviar el mensaje.");
        }
        return false;
    }
    //**************************************************************************

    public boolean loginNuevo(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        if ("MATRICULA".equals(var.getAsunto())) {
            try {
                String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                        + "tipo, matricula, contraseña, correo, status, comando, codigo "
                        + "FROM usuarios WHERE matricula = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, var.getMatricula());
                rs = ps.executeQuery();

                if (rs.next()) {
                    var.setStatus(rs.getString(9));

                    String pass = rs.getString(7);
                    //String[] parte = pass.split("/");
                    //String p = parte[0];
                    String contr = var.getContraseña();
                    String s = contr.substring(0, 6).toUpperCase();

                    if (var.getContraseña().equals(pass)) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else if ("@NUEVO".equals(s)) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else {
                        String contra = "UPDATE usuarios SET contraseña = ?, comando = ? WHERE id = ?";
                        ps = con.prepareStatement(contra);
                        ps.setString(1, contr);
                        ps.setString(2, "Deshabilitado");
                        ps.setInt(3, rs.getInt(1));
                        ps.execute();

                        String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                        ps = con.prepareStatement(update);
                        ps.setString(1, var.getDia());
                        ps.setString(2, var.getHora());
                        ps.setString(3, "Conectado");
                        ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                        ps.setString(5, InetAddress.getLocalHost().getHostName());
                        ps.setInt(6, rs.getInt(1));
                        ps.execute();

                        var.setId(rs.getInt(1));
                        var.setNombre(rs.getString(2));
                        var.setAp_pat(rs.getString(3));
                        var.setAp_mat(rs.getString(4));
                        var.setTipo(rs.getString(5));
                        var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                        return true;
                    }
                }
            } catch (SQLException | UnknownHostException ex) {
                Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en el proceso.");
                return false;
            }
        } else if ("CORREO".equals(var.getAsunto())) {
            try {
                String sql = "SELECT id, nombre, ap_pat, ap_mat, "
                        + "tipo, matricula, contraseña, correo, status, comando, codigo "
                        + "FROM usuarios WHERE correo = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, var.getMatricula());
                rs = ps.executeQuery();

                if (rs.next()) {
                    var.setStatus(rs.getString(9));

                    String pass = rs.getString(7);
                    //String[] parte = pass.split("/");
                    //String p = parte[0];
                    String contr = var.getContraseña();
                    String s = contr.substring(0, 6).toUpperCase();

                    if (var.getContraseña().equals(pass)) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else if ("@NUEVO".equals(s)) {
                        JOptionPane.showMessageDialog(null, "Contraseña inválida.\n"
                                + "Favor de ingresar una nueva.");
                        return false;
                    } else {
                        String contra = "UPDATE usuarios SET contraseña = ?, comando = ? WHERE id = ?";
                        ps = con.prepareStatement(contra);
                        ps.setString(1, contr);
                        ps.setString(2, "Deshabilitado");
                        ps.setInt(3, rs.getInt(1));
                        ps.execute();

                        String update = "UPDATE usuarios SET dia = ?, hora = ?, status = ?, ip = ?, equipo = ? WHERE id = ?";
                        ps = con.prepareStatement(update);
                        ps.setString(1, var.getDia());
                        ps.setString(2, var.getHora());
                        ps.setString(3, "Conectado");
                        ps.setString(4, InetAddress.getLocalHost().getHostAddress());
                        ps.setString(5, InetAddress.getLocalHost().getHostName());
                        ps.setInt(6, rs.getInt(1));
                        ps.execute();

                        var.setId(rs.getInt(1));
                        var.setNombre(rs.getString(2));
                        var.setAp_pat(rs.getString(3));
                        var.setAp_mat(rs.getString(4));
                        var.setTipo(rs.getString(5));
                        var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                        return true;
                    }
                }
            } catch (SQLException | UnknownHostException ex) {
                Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en el proceso.");
                return false;
            }
        }
        return false;
    }
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Cerrar sesion//
    public boolean cerrarM(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, matricula FROM usuarios WHERE matricula = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getMatricula());
            rs = ps.executeQuery();

            if (rs.next()) {
                String status = "UPDATE usuarios SET status = ? WHERE id = ?";
                ps = con.prepareCall(status);
                ps.setString(1, var.getStatus());
                ps.setInt(2, rs.getInt(1));
                ps.execute();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //**************************************************************************

    public boolean cerrarC(ModVariablesUsr var) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, correo FROM usuarios WHERE correo = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getCorreo());
            rs = ps.executeQuery();

            if (rs.next()) {
                String status = "UPDATE usuarios SET status = ? WHERE id = ?";
                ps = con.prepareCall(status);
                ps.setString(1, var.getStatus());
                ps.setInt(2, rs.getInt(1));
                ps.execute();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //**************************************************************************
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Administrador//
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
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************

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

            String sql = "SELECT matricula, nombre, ap_pat, ap_mat, correo, tipo FROM usuarios WHERE tipo = 'Empleado' ";
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
    //**************************************************************************

    public static void tablaConectados(JTable tablaConectados) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //Bloquea la edision.
                }
            };
            tablaConectados.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            ModConexion mod = new ModConexion();
            Connection con = mod.getConexion();

            String sql = "SELECT matricula, nombre, ap_pat, ap_mat, tipo, status FROM usuarios WHERE status = 'Conectado' ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Matrícula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Apellido Materno");
            modelo.addColumn("Usuario");

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
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************

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

            String sql = "SELECT nombre, descripcion, status, p_totales, p_actuales, f_registro, f_activacion FROM quizzes";
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
    //**************************************************************************

    public static void tablaTEmp(JTable tablaTUsuarios, ModVariablesReg var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaTUsuarios.setModel(modelo);

        modelo.addColumn("Usuarios:");
        modelo.addColumn("Tipo:");

        Listas mens = new Listas();
        ArrayList<ModVariablesReg> list = mens.listaE();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[2];
                var = list.get(i);
                fila[0] = var.getNombre_completo();
                fila[1] = var.getTipo();

                modelo.addRow(fila);
            }
        }
    }
    //**************************************************************************

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

        Listas mens = new Listas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                var = list.get(i);

                if (var.getPara_mat().equals(varU.getMatricula())) {
                    if (var.getStatus().equals("NO VISTO")) {

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
    }
    //**************************************************************************

    public static void todos(JTable tablaTodos, ModVariablesMensaje var, ModVariablesUsr varU) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaTodos.setModel(modelo);

        modelo.addColumn("ID");
        modelo.addColumn("De:");
        modelo.addColumn("Asunto:");
        modelo.addColumn("Fecha:");
        modelo.addColumn("Status:");

        Listas mens = new Listas();
        ArrayList<ModVariablesMensaje> list = mens.listaMR();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                var = list.get(i);

                if (var.getPara_mat().equals(varU.getMatricula())) {
                    if (var.getStatus().equals("VISTO")) {

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
    }
    //**************************************************************************

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
    //**************************************************************************

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
    //**************************************************************************

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

        Listas mens = new Listas();
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
    //**************************************************************************

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
    //**************************************************************************

    public static void DocsAct(JTable tablaADocumentos, ModVariablesDoc var) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Bloquea la edision.
            }
        };
        tablaADocumentos.setModel(modelo);

        modelo.addColumn("Nombre:");

        Listas mens = new Listas();

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
    //**************************************************************************

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

        Listas mens = new Listas();

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
    //**************************************************************************
    
    public boolean rPreguntas(ModvariablesPreguntas var) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO preguntas (quizz, pregunta, tipo, num_resp, puntuacion_total, op1, p1, "
                + "op2, p2, op3, p3, op4, p4) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getQuizz());
            ps.setString(2, var.getPregunta());
            ps.setString(3, var.getTipo());
            ps.setString(4, var.getNum_resp());
            ps.setString(5, var.getPuntuacion_total());
            ps.setString(6, var.getOp1());
            ps.setString(7, var.getP1());
            ps.setString(8, var.getOp2());
            ps.setString(9, var.getP2());
            ps.setString(10, var.getOp3());
            ps.setString(11, var.getP3());
            ps.setString(12, var.getOp4());
            ps.setString(13, var.getP4());
            ps.execute();
            
            

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }
    //**************************************************************************
    
    public boolean mPreguntas(ModvariablesPreguntas var, VstPreguntas vp) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE preguntas SET quizz=?, pregunta=?, tipo=?, num_resp=?, puntuacion_total=?, op1=?, p1=?, "
                + "op2=?, p2=?, op3=?, p3=?, op4=?, p4=? WHERE quizz = '" + vp.id.getText() + "'";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, var.getQuizz());
            ps.setString(2, var.getPregunta());
            ps.setString(3, var.getTipo());
            ps.setString(4, var.getNum_resp());
            ps.setString(5, var.getPuntuacion_total());
            ps.setString(6, var.getOp1());
            ps.setString(7, var.getP1());
            ps.setString(8, var.getOp2());
            ps.setString(9, var.getP2());
            ps.setString(10, var.getOp3());
            ps.setString(11, var.getP3());
            ps.setString(12, var.getOp4());
            ps.setString(13, var.getP4());
            ps.execute();
            
            

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ModConsultasSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            return false;
        }

    }
}
