/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Mensajes;

import Modelo.ModConexion;
import Modelo.ModVariablesMensaje;
import Modelo.ModVariablesReg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class Mensajes {
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
}
