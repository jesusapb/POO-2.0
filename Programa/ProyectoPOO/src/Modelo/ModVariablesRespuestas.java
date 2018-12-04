package Modelo;

/**
 * Clase encargada en almacenar los datos al momento de ser registrado en la
 * base de datos alguna pregunta abierta contestada.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesRespuestas {

    private int id;
    private String ident;
    private String puntuacion;
    private String quizz;
    private String pregunta;
    private String respuesta;
    private String status;
    private String p_asignada;

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
     * @return devuelve el identificador único de un empleado (matrícula).
     */
    public String getIdent() {
        return ident;
    }

    /**
     * @param ident almacena el identificador único de un empleado (matrícula).
     */
    public void setIdent(String ident) {
        this.ident = ident;
    }

    /**
     * @return devuelve la puntuación total de la pregunta abierta contestada.
     */
    public String getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion almacena la puntuación total de la pregunta abierta
     * contestada.
     */
    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return devuelve el nombre del quizz del cuál fué contestada la pregunta
     * abierta.
     */
    public String getQuizz() {
        return quizz;
    }

    /**
     * @param quizz almacena el nombre del quizz del cuál fué contestada la
     * pregunta abierta.
     */
    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    /**
     * @return devuelve la pregunta que el empleado contestó.
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta almacena la pregunta que el empleado contestó.
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return devuelve la respuesta de la pregunta contestada.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta almacena la respuesta de la pregunta contestada.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return devuelve el status el cuál indica si ya fué calificada o no.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el status el cuál indica si ya fué calificada o
     * no.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve los puntos que el administrador le dió a la respuesta
     * del empleado al momento de calificarle el quizz.
     */
    public String getP_asignada() {
        return p_asignada;
    }

    /**
     * @param p_asignada almacena los puntos que el administrador le dió a la respuesta
     * del empleado al momento de calificarle el quizz.
     */
    public void setP_asignada(String p_asignada) {
        this.p_asignada = p_asignada;
    }

}
