package Modelo;

/**
 * Clase que se encarga en almacenar todos los datos relacionados al registro
 * tras terminar de presentar algun quizz.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesPresentados {

    private int id;
    private String ident;
    private String quizz;
    private String intento;
    private String p_totales;
    private String calificacion;
    private String status;
    private String abrt;
    private String abrtNum;
    private String mod_calif;
    private String marca;
    private String hora;
    private String minuto;
    private String totales;
    private String abrtTot;

    /**
     * @return devuelve el número total de las preguntas contestadas en el
     * quizz.
     */
    public String getAbrtNum() {
        return abrtNum;
    }

    /**
     * @param abrtNum almacena el número total de las preguntas contestadas en
     * el quizz.
     */
    public void setAbrtNum(String abrtNum) {
        this.abrtNum = abrtNum;
    }

    /**
     * @return devuelve el modo de calificación que esta dado en el quizz.
     */
    public String getMod_calif() {
        return mod_calif;
    }

    /**
     * @param mod_calif almacena el modo de calificación que esta dado en el
     * quizz.
     */
    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    /**
     * @return devuelve la identificación si se puede acceder a calificar o no.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca almacena la identificación si se puede acceder a calificar o
     * no.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return devuelve el número total de las preguntas abiertas contestadas en
     * el quizz.
     */
    public String getAbrtTot() {
        return abrtTot;
    }

    /**
     * @param abrtTot almacena el número total de las preguntas abiertas
     * contestadas en el quizz.
     */
    public void setAbrtTot(String abrtTot) {
        this.abrtTot = abrtTot;
    }

    /**
     * @return devuelve el número el cual obtuvo al registrarse a la base de
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
     * @return devuelve la matrícula del empleado que se registras tras concluir
     * el quizz.
     */
    public String getIdent() {
        return ident;
    }

    /**
     * @param ident almacena la matrícula del empleado que se registras tras
     * concluir el quizz.
     */
    public void setIdent(String ident) {
        this.ident = ident;
    }

    /**
     * @return devuelve el nombre del quizz que presentó el empleado.
     */
    public String getQuizz() {
        return quizz;
    }

    /**
     * @param quizz almacena el nombre del quizz que presentó el empleado.
     */
    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    /**
     * @return devuelve el intento actual de presentar el quizz.
     */
    public String getIntento() {
        return intento;
    }

    /**
     * @param intento almacena el intento actual de presentar el quizz.
     */
    public void setIntento(String intento) {
        this.intento = intento;
    }

    /**
     * @return devuelve la calificación obtenida tras concluir un quizz.
     */
    public String getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion almacena la calificación obtenida tras concluir un
     * quizz.
     */
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return devuelve el estatus que obtiene el empleado a corde a la
     * calificación obtenida (aprobado o reprobado).
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el estatus que obtiene el empleado a corde a la
     * calificación obtenida (aprobado o reprobado).
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve la puntuación total de todas las preguntas presentadas
     * en el quizz.
     */
    public String getP_totales() {
        return p_totales;
    }

    /**
     * @param p_totales almacena la puntuación total de todas las preguntas
     * presentadas en el quizz.
     */
    public void setP_totales(String p_totales) {
        this.p_totales = p_totales;
    }

    /**
     * @return devuelve el registro de todas las preguntas abiertas contestadas
     * en el quizz.
     */
    public String getAbrt() {
        return abrt;
    }

    /**
     * @param abrt almacena el registro de todas las preguntas abiertas
     * contestadas en el quizz.
     */
    public void setAbrt(String abrt) {
        this.abrt = abrt;
    }

    /**
     * @return devuelve el tiempo (horas) de duración del quizz con el cuál lo
     * registró el administrador.
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora almacena el tiempo (horas) de duración del quizz con el cuál
     * lo registro el administrador.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return devuelve el tiempo (minutos) de duración del quizz con el cuál lo
     * registró el administrador.
     */
    public String getMinuto() {
        return minuto;
    }

    /**
     * @param minuto almacena el tiempo (minutos) de duración del quizz con el
     * cuál lo registró el administrador.
     */
    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    /**
     * @return devuelve la variable empleada para el funcionamiento exclusivo al
     * momento de calificar, se usa para acumular sin arruinar algun dato que se
     * pueda reutilizar.
     */
    public String getTotales() {
        return totales;
    }

    /**
     * @param totales almacena la variable empleada para el funcionamiento
     * exclusivo al momento de calificar, se usa para acumular sin arruinar
     * algun dato que se pueda reutilizar.
     */
    public void setTotales(String totales) {
        this.totales = totales;
    }

}
