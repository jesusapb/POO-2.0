package Modelo;

/**
 * Clase encargada en almacenar los datos al momento de ser registrado en la
 * base de datos alguna pregunta abierta contestada.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesRespuestas {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param ident es la matrícula del empleado que contesta alguna pregunta
     * abierta.
     * @param puntuacion es la puntuación total de la pregunta abierta.
     * @param quizz es el quizz del cuál poviene la pregunta.
     * @param pregunta es la pregunta que se muestra para que sea respondida.
     * @param respuesta es la respuesta que da el empleado a la pregunta
     * mostrada.
     * @param status es el que da acceso o no al momento de calificar la
     * pregunta.
     * @param p_asignada es la puntuación que asigna el administrador a lo que
     * contestó el empleado.
     */
    private int id;
    private String ident;
    private String puntuacion;
    private String quizz;
    private String pregunta;
    private String respuesta;
    private String status;
    private String p_asignada;

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

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getQuizz() {
        return quizz;
    }

    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_asignada() {
        return p_asignada;
    }

    public void setP_asignada(String p_asignada) {
        this.p_asignada = p_asignada;
    }

}
