/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Documentos;

import Modelo.ModConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Antonio
 */
public class sql {
    public int auto_incremento(String sql) {
        int id = 1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ModConexion con = new ModConexion();
        
        try {
            ps = con.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("id"+ex.getMessage());
            id = 1;
        }
        return id;
    }
}
