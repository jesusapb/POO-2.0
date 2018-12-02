package Modelo;

/**
 * Clase encargada en almacenar los datos corresondientes a los empleados que se
 * registran, asi mismo sirve para enviar mensaje a otros usuarios o avisos.
 * Tiene casi las mismas variables que en ModVariablesUsr pero ésta es
 * implementada para que en esa clase no se dañen los datos del usuario que
 * controla el programa en ese momento.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesReg {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param nombre es el nombre con el cuál se registra algún empleado.
     * @param ap_pat es el apellido paterno con el cuál se registra algún
     * empleado.
     * @param ap_mat es el apellido materno con el cuál se registra algún
     * empleado.
     * @param tipo es el tipo que corresponde el usuario que se está buscando,
     * ya sea para enviar mensaje o aviso.
     * @param matrícula es la matrícula con la cuál se registra algún empleado.
     * @param contraseña es la contraseña con la cuál se registra algún
     * empleado.
     * @param correo es el correo con el cuál es registrado algún empleado.
     * @param dia es el dia en el cuál fue registrado o el último día que tuvo
     * una sesión activa el usuario.
     * @param hora es la hora en la cuál fue registrado o la última hora que
     * tuvo una sesión activa el usuario.
     * @param status es el estatus con el cual es registrado algún empleado o el
     * cuál tiene (nuevo, conectado o desconectado).
     * @param ip es la ip del equipo donde se ejecuta el programa, esta variable
     * ya no se usa.
     * @param equipo es el nombre del equipo donde se ejecuta el programa.
     * @param comando es el que da la orden al programa para que el identifique
     * si se solicita algún cambio de contraseña.
     * @param codigo es el codigo que obtiene el usuario para poder realizar el
     * cambio de contraseña.
     * @param nombre_completo es la unión del nombre, apellido paterno y materno
     * con respectivos separadores.
     * @param aviso es el aviso que manda o recibe el usuario.
     */
    private int id;
    private String nombre;
    private String ap_pat;
    private String ap_mat;
    private String tipo;
    private String matricula;
    private String contraseña;
    private String correo;
    private String dia;
    private String hora;
    private String status;
    private String ip;
    private String equipo;
    private String comando;
    private String codigo;
    private String nombre_completo;
    private String avisos;

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

    public String getAp_pat() {
        return ap_pat;
    }

    public void setAp_pat(String ap_pat) {
        this.ap_pat = ap_pat;
    }

    public String getAp_mat() {
        return ap_mat;
    }

    public void setAp_mat(String ap_mat) {
        this.ap_mat = ap_mat;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getAvisos() {
        return avisos;
    }

    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }

}
