package Modelo;

/**
 * Clase encargada en almacenar los datos correspondientes a la(s)
 * calificación(es) obtenidas por los empleados al terminar de presentar algún
 * quizz.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesCalif {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param ident es la identificación del empleado que está presentando el
     * quizz (matrícula).
     * @param intentos es el número de intentos utilizados en el momento de
     * presentar el quizz.
     * @param quizz es el nombre del quizz el cuál se ha registrado que ha
     * terminado el empleado.
     * @param puntos es la puntuación que obtuvo el empleado al concluir el
     * quizz.
     * @param status es el status que obtiene a corde a la calificación que
     * obtiene el empleado (aprobado o reprobado).
     * @param mod_calif es el modo de calificación el cuál está configurado el
     * quizz.
     * @param Tpuntos son los puntos totales del quizz (es el 100% de la
     * puntuación del quizz).
     */
    private int id;
    private String ident;
    private String intentos;
    private String quizz;
    private String puntos;
    private String status;
    private String mod_calif;
    private String Tpuntos;

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

    public String getIntentos() {
        return intentos;
    }

    public void setIntentos(String intentos) {
        this.intentos = intentos;
    }

    public String getQuizz() {
        return quizz;
    }

    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMod_calif() {
        return mod_calif;
    }

    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    public String getTpuntos() {
        return Tpuntos;
    }

    public void setTpuntos(String Tpuntos) {
        this.Tpuntos = Tpuntos;
    }

}
