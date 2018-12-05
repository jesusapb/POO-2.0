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

    /**
     * @return devuelve la número el cual obtuvo al registrarse a la base de
     * datos.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id almacena el número el cual obtuvo al registrarse a la base de
     * datos.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el nombre que se asigna al quizz.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre almacena el nombre que se asigna al quizz.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return devuelve la descripción puesta por un administrador al momento de
     * registrar el quizz.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion almacena la descripción puesta por un administrador al
     * momento de registrar el quizz.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return devuelve la cantidad de preguntas que se van a mostrar al momento
     * de que un empleado presente un quizz.
     */
    public String getP_totales() {
        return p_totales;
    }

    /**
     * @param p_totales almacena la cantidad de preguntas que se van a mostrar
     * al momento de que un empleado presente un quizz.
     */
    public void setP_totales(String p_totales) {
        this.p_totales = p_totales;
    }

    /**
     * @return devuelve la cantidad de preguntas registradas en el quizz.
     */
    public String getP_actuales() {
        return p_actuales;
    }

    /**
     * @param p_actuales almacena la cantidad de preguntas registradas en el
     * quizz.
     */
    public void setP_actuales(String p_actuales) {
        this.p_actuales = p_actuales;
    }

    /**
     * @return devuelve el status actual de visibilidad del quizz para el
     * empleado.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el status actual de visibilidad del quizz para el
     * empleado.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve la cantidad de intentos que el administrador señaló que
     * tiene el quizz.
     */
    public String getIntentos() {
        return intentos;
    }

    /**
     * @param intentos almacena la cantidad de intentos que el administrador
     * señaló que tiene el quizz.
     */
    public void setIntentos(String intentos) {
        this.intentos = intentos;
    }

    /**
     * @return devuelve el formato (modo) del cuál se va a tomar para calificar
     * el quizz.
     */
    public String getMod_calif() {
        return mod_calif;
    }

    /**
     * @param mod_calif almacena el formato (modo) del cuál se va a tomar para
     * calificar el quizz.
     */
    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    /**
     * @return devuelve el tiempo con el cuál es registrado el quizz para que el
     * empleado lo tuilice para presentar el quizz.
     */
    public String getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo almacena el tiempo con el cuál es registrado el quizz para
     * que el empleado lo tuilice para presentar el quizz.
     */
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return devuelve la fecha de la cuál fué activado el quizz para que los
     * empleados puedan verlo y presentarlo.
     */
    public String getF_activacion() {
        return f_activacion;
    }

    /**
     * @param f_activacion almacena la fecha de la cuál fué activado el quizz
     * para que los empleados puedan verlo y presentarlo.
     */
    public void setF_activacion(String f_activacion) {
        this.f_activacion = f_activacion;
    }

    /**
     * @return devuelve la fecha de la cuál fué registrado el quizz en la base
     * de datos.
     */
    public String getF_registro() {
        return f_registro;
    }

    /**
     * @param f_registro almacena la fecha de la cuál fué o es registrado el
     * quizz en la base de datos.
     */
    public void setF_registro(String f_registro) {
        this.f_registro = f_registro;
    }

    /**
     * @return devuele un valor numérico el cuál fué implementada para el
     * funcionamiento de la tabla de búsqueda para que así no se pueda dañar
     * otros datos pero a la vez se pueda manejar los existentes, cumple la
     * misma función que p_totales.
     */
    public int getTotales() {
        return totales;
    }

    /**
     * @param totales almacena un valor numérico el cuál fué implementada para
     * el funcionamiento de la tabla de búsqueda para que así no se pueda dañar
     * otros datos pero a la vez se pueda manejar los existentes, cumple la
     * misma función que p_totales.
     */
    public void setTotales(int totales) {
        this.totales = totales;
    }

    /**
     * @return devuelve un valor numérico así como el anterior fue implementada
     * para el funcionamiento de la tabla de búsqueda, cumple el mismo propósito
     * que p_actuales.
     */
    public int getActuales() {
        return actuales;
    }

    /**
     * @param actuales almacena un valor numérico así como el anterior fue
     * implementada para el funcionamiento de la tabla de búsqueda, cumple el
     * mismo propósito que p_actuales.
     */
    public void setActuales(int actuales) {
        this.actuales = actuales;
    }

}
