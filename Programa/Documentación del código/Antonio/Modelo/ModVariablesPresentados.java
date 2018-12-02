package Modelo;

/**
 * Clase que se encarga en almacenar todos los datos relacionados al registro
 * tras terminar de presentar algun quizz.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesPresentados {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param iden es la matrícula del empleado que se registras tras concluir
     * el quizz.
     * @param quizz es el nombre del quizz que presentó el empleado.
     * @param intento es el intento actual de presentar el quizz.
     * @param p_totales es la puntuación total de todas las preguntas
     * presentadas en el quizz.
     * @param calificacion es la calificación obtenida tras concluir un quizz.
     * @param status es el estatus que obtiene el empleado a corde a la
     * calificación obtenida (aprobado o reprobado).
     * @param abrt es el registro de todas las preguntas abiertas contestadas en
     * el quizz.
     * @param abrtNum es el número total de las preguntas contestadas en el
     * quizz.
     * @param mod_calif es el modo de calificación que esta dado en el quizz.
     * @param marca es la identificación si se puede acceder a calificar o no.
     * @param hora es el tiempo (en horas) de duración del quizz.
     * @param minutos es el tiempo (en minutos) de duración del quizz.
     * @param totales variable empleada para el funcionamiento exclusivo al
     * momento de calificar, se usa para acumular sin arruinar algun dato que se
     * pueda reutilizar.
     * @param abrtTot es el número total de las preguntas abiertas contestadas
     * en el quizz.
     */
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

    public String getAbrtNum() {
        return abrtNum;
    }

    public void setAbrtNum(String abrtNum) {
        this.abrtNum = abrtNum;
    }

    public String getMod_calif() {
        return mod_calif;
    }

    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAbrtTot() {
        return abrtTot;
    }

    public void setAbrtTot(String abrtTot) {
        this.abrtTot = abrtTot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getQuizz() {
        return quizz;
    }

    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    public String getIntento() {
        return intento;
    }

    public void setIntento(String intento) {
        this.intento = intento;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_totales() {
        return p_totales;
    }

    public void setP_totales(String p_totales) {
        this.p_totales = p_totales;
    }

    public String getAbrt() {
        return abrt;
    }

    public void setAbrt(String abrt) {
        this.abrt = abrt;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public String getTotales() {
        return totales;
    }

    public void setTotales(String totales) {
        this.totales = totales;
    }

}
