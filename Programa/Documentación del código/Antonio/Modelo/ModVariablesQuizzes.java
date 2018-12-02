package Modelo;

/**
 * Clase que se encarga en almacenar todos los datos relacionados al registro o
 * gestión de cada quizz.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesQuizzes {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param nombre es el nombre que se asigna al quizz.
     * @param descripcion es de lo que se va a tratar el quizz o lo que sea que
     * un administrador ponga en la descripción.
     * @param p_totales son las preguntas que se van a mostrar al momento de
     * presentar algun quizz.
     * @param p_actuales son todas las preguntas registradas en ese quiz.
     * @param totales fué implementada para el funcionamiento de la tabla de
     * búsqueda para que así no se pueda dañar otros datos pero a la vez se
     * pueda manejar los existentes, cumple la misma función que p_totales.
     * @param actuales al igual que la anterior fue implementada para el
     * funcionamiento de la tabla de búsqueda, cumple el mismo propósito que
     * p_actuales.
     * @param status es el status que se le asigna, influye únicamente para el
     * empleado ya que es el que da la cciión si lo ve o no un empleado.
     * @param intentos son los intentos que se da para que se pueda pasar el
     * quizz.
     * @param mod_calif es el modo de calificación que se da al quizz para que
     * así se pueda calificar.
     * @param tiempo es el tiempo que un administrador da para pasar el quizz.
     * @param f_activacion es la fecha de activación del quizz.
     * @param f_registro es la fecha la cuál se registró el quizz.
     */
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
