/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class ModListas {

    public ArrayList<ModVariablesReg> listaE() {
        ArrayList<ModVariablesReg> list = new ArrayList<ModVariablesReg>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesReg var = new ModVariablesReg();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setNombre_completo(rs.getString(6) + "/" + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesMensaje> listaMR() {
        ArrayList<ModVariablesMensaje> list = new ArrayList<ModVariablesMensaje>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM mensajes";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesMensaje var = new ModVariablesMensaje();
                var.setId(rs.getInt(1));
                var.setDe_mat(rs.getString(2));
                var.setDe_nom(rs.getString(3));
                var.setPara_mat(rs.getString(4));
                var.setPara_nom(rs.getString(5));
                var.setFecha(rs.getString(6));
                var.setAsunto(rs.getString(7));
                var.setMensaje(rs.getString(8));
                var.setStatus(rs.getString(9));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesDoc> listaDocs() {
        ArrayList<ModVariablesDoc> list = new ArrayList<ModVariablesDoc>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM documentos";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesDoc var = new ModVariablesDoc();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setStatus(rs.getString(3));
                var.setDescripcion(rs.getString(4));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesQuizzes> listaQuizz() {
        ArrayList<ModVariablesQuizzes> list = new ArrayList<ModVariablesQuizzes>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM quizzes";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesQuizzes var = new ModVariablesQuizzes();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setDescripcion(rs.getString(3));
                var.setP_totales(rs.getString(4));
                var.setP_actuales(rs.getString(5));
                var.setStatus(rs.getString(6));
                var.setIntentos(rs.getString(7));
                var.setMod_calif(rs.getString(8));
                var.setTiempo(rs.getString(9));
                var.setF_registro(rs.getString(10));
                var.setF_activacion(rs.getString(11));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesUsr> listaUs() {
        ArrayList<ModVariablesUsr> list = new ArrayList<ModVariablesUsr>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesUsr var = new ModVariablesUsr();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setContraseña(rs.getString(7));
                var.setCorreo(rs.getString(8));
                var.setStatus(rs.getString(11));
                var.setIp(rs.getString(12));
                var.setEquipo(rs.getString(13));
                var.setComando(rs.getString(14));
                var.setCodigo(rs.getString(15));
                var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModvariablesPreguntas> listaPreg() {
        ArrayList<ModvariablesPreguntas> list = new ArrayList<ModvariablesPreguntas>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM preguntas";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModvariablesPreguntas var = new ModvariablesPreguntas();
                var.setId(rs.getInt(1));
                var.setQuizz(rs.getString(2));
                var.setPregunta(rs.getString(3));
                var.setTipo(rs.getString(4));
                var.setNum_resp(rs.getString(5));
                var.setPuntuacion_total(rs.getString(6));
                var.setResp1(rs.getString(7));
                var.setR1(rs.getString(8));
                var.setResp2(rs.getString(9));
                var.setR2(rs.getString(10));
                var.setResp3(rs.getString(11));
                var.setR3(rs.getString(12));
                var.setResp4(rs.getString(13));
                var.setR4(rs.getString(14));
                var.setDis1(rs.getString(15));
                var.setDis2(rs.getString(16));
                var.setDis3(rs.getString(17));
                var.setDis4(rs.getString(18));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModvariablesPreguntas> listaPregMod(int quizz) {
        ArrayList<ModvariablesPreguntas> list = new ArrayList<ModvariablesPreguntas>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM preguntas";
        ResultSet rs = null;
        PreparedStatement ps = null;
        int a = 0;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(2).equals(quizz + "")) {
                    ModvariablesPreguntas var = new ModvariablesPreguntas();
                    var.setIncremento(a);
                    a = a + 1;
                    var.setId(rs.getInt(1));
                    var.setQuizz(rs.getString(2));
                    var.setPregunta(rs.getString(3));
                    var.setTipo(rs.getString(4));
                    var.setNum_resp(rs.getString(5));
                    var.setPuntuacion_total(rs.getString(6));
                    var.setResp1(rs.getString(7));
                    var.setR1(rs.getString(8));
                    var.setResp2(rs.getString(9));
                    var.setR2(rs.getString(10));
                    var.setResp3(rs.getString(11));
                    var.setR3(rs.getString(12));
                    var.setResp4(rs.getString(13));
                    var.setR4(rs.getString(14));
                    var.setDis1(rs.getString(15));
                    var.setDis2(rs.getString(16));
                    var.setDis3(rs.getString(17));
                    var.setDis4(rs.getString(18));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public String[] listaResp(int id, int quizz) {
        String[] list = new String[12];
        ModvariablesPreguntas var;
        ModListas mens = new ModListas();
        ArrayList<ModvariablesPreguntas> lista = mens.listaPregMod(quizz);

        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                var = lista.get(i);
                if (var.getId() == id) {
                    list[0] = var.getResp1() + "~" + var.getR1();
                    if (var.getR2().equals("*/null/*")) {
                        list[1] = var.getResp2() + "~0.00";
                    } else {
                        list[1] = var.getResp2() + "~" + var.getR2();
                    }
                    if (var.getR3().equals("*/null/*")) {
                        list[2] = var.getResp3() + "~0.00";
                    } else {
                        list[2] = var.getResp3() + "~" + var.getR3();
                    }
                    if (var.getR3().equals("*/null/*")) {
                        list[3] = var.getResp4() + "~0.00";
                    } else {
                        list[3] = var.getResp4() + "~" + var.getR4();
                    }
                    list[4] = var.getDis1() + "~0.00";
                    list[5] = var.getDis2() + "~0.00";
                    list[6] = var.getDis3() + "~0.00";
                    list[7] = var.getDis4() + "~0.00";
                }
            }
        }

        return list;
    }

    public ArrayList<ModVariablesReg> listaT(String tipo, String comp) {
        ArrayList<ModVariablesReg> list = new ArrayList<ModVariablesReg>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesReg var = new ModVariablesReg();
                if (rs.getString(5).equals(tipo)) {
                    if (rs.getString(6).equals(comp)); else {
                        var.setAvisos(rs.getString(6));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesDoc> Listar_Pdf() {
        ArrayList<ModVariablesDoc> list = new ArrayList<ModVariablesDoc>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM documentos";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesDoc var = new ModVariablesDoc();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setStatus(rs.getString(3));
                var.setDescripcion(rs.getString(4));
                var.setArchivo(rs.getBytes(5));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesDoc> Listar_PdfMod(String nombre) {
        ArrayList<ModVariablesDoc> list = new ArrayList<ModVariablesDoc>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM documentos";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesDoc var = new ModVariablesDoc();
                if (rs.getString(2).equals(nombre)) {
                    var.setId(rs.getInt(1));
                    var.setNombre(rs.getString(2));
                    var.setStatus(rs.getString(3));
                    var.setDescripcion(rs.getString(4));
                    var.setArchivo(rs.getBytes(5));
                    list.add(var);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesAvisos> listaAv(String matricula) {
        ArrayList<ModVariablesAvisos> list = new ArrayList<ModVariablesAvisos>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM avisos";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesAvisos var = new ModVariablesAvisos();
                if (rs.getString(2).equals(matricula)) {
                    if (rs.getString(6).equals("no visto")) {
                        var.setId(rs.getInt(1));
                        var.setPara(rs.getString(2));
                        var.setQuien(rs.getString(3));
                        var.setQue(rs.getString(4));
                        var.setCuando(rs.getString(5));
                        var.setStatus(rs.getString(6));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesUsr> listaUsr(String equipo) {
        ArrayList<ModVariablesUsr> list = new ArrayList<ModVariablesUsr>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesUsr var = new ModVariablesUsr();
                if (rs.getString(11).equals("Conectado")) {
                    if (rs.getString(13).equals(equipo)) {
                        var.setId(rs.getInt(1));
                        var.setNombre(rs.getString(2));
                        var.setAp_pat(rs.getString(3));
                        var.setAp_mat(rs.getString(4));
                        var.setTipo(rs.getString(5));
                        var.setMatricula(rs.getString(6));
                        var.setContraseña(rs.getString(7));
                        var.setCorreo(rs.getString(8));
                        var.setStatus(rs.getString(11));
                        var.setIp(rs.getString(12));
                        var.setEquipo(rs.getString(13));
                        var.setComando(rs.getString(14));
                        var.setCodigo(rs.getString(15));
                        var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesPresentados> listaPre(String matricula, String quizz) {
        ArrayList<ModVariablesPresentados> list = new ArrayList<ModVariablesPresentados>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM presentados";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesPresentados var = new ModVariablesPresentados();
                if (rs.getString(2).equals(matricula)) {
                    if (rs.getString(3).equals(quizz)) {
                        var.setId(rs.getInt(1));
                        var.setIdent(rs.getString(2));
                        var.setQuizz(rs.getString(3));
                        var.setIntento(rs.getString(4));
                        var.setP_totales(rs.getString(5));
                        var.setCalificacion(rs.getString(6));
                        var.setStatus(rs.getString(7));
                        var.setAbrt(rs.getString(8));
                        list.add(var);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesCalif> listaCalif(String matricula, String quizz) {
        ArrayList<ModVariablesCalif> list = new ArrayList<ModVariablesCalif>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM calificaciones";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesCalif var = new ModVariablesCalif();
                if (rs.getString(2).equals(matricula)) {
                    if (rs.getString(4).equals(quizz)) {
                        var.setId(rs.getInt(1));
                        var.setIdent(rs.getString(2));
                        var.setIntentos(rs.getString(3));
                        var.setQuizz(rs.getString(4));
                        var.setPuntos(rs.getString(5));
                        var.setStatus(rs.getString(6));
                        var.setMod_calif(rs.getString(7));
                        var.setTpuntos(rs.getString(8));
                        list.add(var);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesCalif> listaCalifMod(String matricula) {
        ArrayList<ModVariablesCalif> list = new ArrayList<ModVariablesCalif>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM calificaciones";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesCalif var = new ModVariablesCalif();
                if (rs.getString(2).equals(matricula)) {
                    var.setId(rs.getInt(1));
                    var.setIdent(rs.getString(2));
                    var.setIntentos(rs.getString(3));
                    var.setQuizz(rs.getString(4));
                    var.setPuntos(rs.getString(5));
                    var.setStatus(rs.getString(6));
                    var.setMod_calif(rs.getString(7));
                    var.setTpuntos(rs.getString(8));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public ArrayList<ModVariablesPresentados> listaReg(String matricula) {
        ArrayList<ModVariablesPresentados> list = new ArrayList<ModVariablesPresentados>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM presentados";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesPresentados var = new ModVariablesPresentados();
                if (rs.getString(2).equals(matricula)) {
                    var.setId(rs.getInt(1));
                    var.setIdent(rs.getString(2));
                    var.setQuizz(rs.getString(3));
                    var.setIntento(rs.getString(4));
                    var.setP_totales(rs.getString(5));
                    var.setCalificacion(rs.getString(6));
                    var.setStatus(rs.getString(7));
                    var.setAbrtNum(rs.getString(8));
                    var.setAbrtTot(rs.getString(9));
                    var.setAbrt(rs.getString(10));
                    var.setMod_calif(rs.getString(11));
                    var.setMarca(rs.getString(12));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
