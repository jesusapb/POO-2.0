/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Documentos;

import Modelo.ModConexion;
import Modelo.ModVariablesDoc;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public class PDF {
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
    
    public void agregar(ModVariablesDoc var) {
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
    
    public void modificar(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE documentos SET nombre = ?, status = ?, descripcion = ?, archivo = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setString(2, var.getStatus());
            ps.setString(3, var.getDescripcion());
            ps.setBytes(4, var.getArchivo());
            ps.setInt(5, var.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modificarNom(ModVariablesDoc var) {
        ModConexion con = new ModConexion();
        String sql = "UPDATE documentos SET nombre = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, var.getNombre());
            ps.setInt(2, var.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void eliminar(ModVariablesDoc var) {
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
    
    public void abrir(int id){
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
            System.out.println("Error al abrir archivo PDF "+ex.getMessage());
        }
    }
}
