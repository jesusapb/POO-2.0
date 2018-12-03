package Modelo;

/**
 * Clase encargada de almacenar los datos correspondientes a la asignación de
 * las preguntas abiertas, asi mismo es utilizada para el registro del los datos
 * en la base de datos, al igual para la calificación de éste tipo de preguntas.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesAbierto {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param ident es la identificación del empleado que está presentando el
     * quizz (matrícula).
     * @param puntuacion es la puntuación que vale esa pregunta en específico.
     * @param pregunta es la pregunta la cuál el empleado contestó.
     * @param respuesta es la respuesta que el empleado escribió a corde a la
     * pregunta que se le presentó.
     * @param status es el estatus que obtiene al ser registrado o calificado
     * ("no calificado" o "calificado") para poder identificar cuál falta por
     * atender.
     * @param p_asignada es la puntuación que le asigna el administrador al
     * momento de ser calificado.
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
