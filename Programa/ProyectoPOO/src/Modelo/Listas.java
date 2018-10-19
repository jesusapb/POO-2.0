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
public class Listas {
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
}
