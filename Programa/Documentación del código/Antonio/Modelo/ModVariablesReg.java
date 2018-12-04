package Modelo;

/**
 * Clase encargada en almacenar los datos corresondientes a otros usuarios que
 * no sea el usuario que está manejando el programa, asi mismo sirve para enviar
 * mensaje a otros usuarios o avisos. Tiene casi las mismas variables que en
 * ModVariablesUsr pero ésta es implementada para que en esa clase no se dañen
 * los datos del usuario que controla el programa en ese momento.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesReg {

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

    /**
     * @return devuelve el número el cual obtuvo al ser registrado en la base de
     * datos (valor numérico único).
     */
    public int getId() {
        return id;
    }

    /**
     * @param id almacena el número el cual obtuvo al ser registrado en la base
     * de datos (valor numérico único).
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el nombre del usuario registrado en la base de datos.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre almacena el nombre del usuario registrado en la base de
     * datos.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return devuelve el apellido paterno del usuario registrado.
     */
    public String getAp_pat() {
        return ap_pat;
    }

    /**
     * @param ap_pat almacena el apellido paterno del usuario registrado.
     */
    public void setAp_pat(String ap_pat) {
        this.ap_pat = ap_pat;
    }

    /**
     * @return devuelve el apellido materno del usuario registrado.
     */
    public String getAp_mat() {
        return ap_mat;
    }

    /**
     * @param ap_mat almacena el apellido materno del usuario registrado.
     */
    public void setAp_mat(String ap_mat) {
        this.ap_mat = ap_mat;
    }

    /**
     * @return devuelve el indicador del tipo de usuario al cual pertenece el
     * usuario que se está extrayendo los datos.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo almacena el indicador del tipo de usuario al cual pertenece
     * el usuario que se está extrayendo los datos.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return devuelve la matrícula del usuario.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula almacena la matricula del usuario.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * @return devuelve la contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña almacena la contraseña del usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * @return devuelve el correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo almacena el correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return devuelve el día de la última conexión del usuario.
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia almacena el día de la última conexión del usuario.
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @return devuelve la hora de la última conexión del usuario.
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora almacena la hora de la última conexión del usuario.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return devuelve el status de conexión del usuario.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el status de conexión del usuario.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve la IP del equipo que utilizó por última vez el usuario.
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip almacena la IP del equipo que utilizó por última vez el
     * usuario.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return devuelve el nombre del ordenador donde se conectó por última vez
     * el usuario.
     */
    public String getEquipo() {
        return equipo;
    }

    /**
     * @param equipo almacena el nombre del ordenador donde se conectó por
     * última vez el usuario.
     */
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    /**
     * @return almacena el tag de la base de datos el cuál indica que se
     * solicitó un cambio de contraseña.
     */
    public String getComando() {
        return comando;
    }

    /**
     * @param comando almacena el tag de la base de datos el cuál indica que se
     * solicitó un cambio de contraseña.
     */
    public void setComando(String comando) {
        this.comando = comando;
    }

    /**
     * @return devuelve el código que debe de usar el usuario para el cambio de
     * contraseña.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo almacena el código que debe de usar el usuario para el
     * cambio de contraseña.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return devuelve el nombre completo de usuario, es la unón del nombre con
     * los 2 apellidos.
     */
    public String getNombre_completo() {
        return nombre_completo;
    }

    /**
     * @param nombre_completo almacena el nombre completo de usuario, es la unón
     * del nombre con los 2 apellidos.
     */
    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    /**
     * @return devuelve el aviso que se extrae de la base de datos para su
     * visualización.
     */
    public String getAvisos() {
        return avisos;
    }

    /**
     * @param avisos almacena el aviso que se extrae de la base de datos para su
     * visualización.
     */
    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }

}
