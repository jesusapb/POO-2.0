/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Antonio
 */
public class ModVariablesQuizzes {
    private int id;
    private String nombre;
    private String descripcion;
    private String p_totales;
    private String p_actuales;
    private int totales;
    private int actuales;
    private String status;
    private String intentos;
    private String mod_calif;
    private String tiempo;
    private String f_activacion;
    private String f_registro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getP_totales() {
        return p_totales;
    }

    public void setP_totales(String p_totales) {
        this.p_totales = p_totales;
    }

    public String getP_actuales() {
        return p_actuales;
    }

    public void setP_actuales(String p_actuales) {
        this.p_actuales = p_actuales;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntentos() {
        return intentos;
    }

    public void setIntentos(String intentos) {
        this.intentos = intentos;
    }

    public String getMod_calif() {
        return mod_calif;
    }

    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getF_activacion() {
        return f_activacion;
    }

    public void setF_activacion(String f_activacion) {
        this.f_activacion = f_activacion;
    }

    public String getF_registro() {
        return f_registro;
    }

    public void setF_registro(String f_registro) {
        this.f_registro = f_registro;
    }

    public int getTotales() {
        return totales;
    }

    public void setTotales(int totales) {
        this.totales = totales;
    }

    public int getActuales() {
        return actuales;
    }

    public void setActuales(int actuales) {
        this.actuales = actuales;
    }
    
}
