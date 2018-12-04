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

    private int id;
    private String ident;
    private String intentos;
    private String quizz;
    private String puntos;
    private String status;
    private String mod_calif;
    private String Tpuntos;

    /**
     * @return devuelve el dato almacenado en la variable correspondiente, es el
     * valor único que se registrar en la base de datos en su tabla
     * correspondiente.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id es la variable en la cuál se almacena el dato que se extrae por
     * medio de un proceso del programa, este dato corresponde al dato único con
     * el cuál es registrado en la base de datos.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el dato almacenado en la variable correspondiente, el
     * dato que devuelve es la matrícula del empleado que presenta o presentró
     * algun quizz.
     */
    public String getIdent() {
        return ident;
    }

    /**
     * @param ident es la variable donde se almacena la variable del empleado
     * que presenta o presentró algún quizz.
     */
    public void setIdent(String ident) {
        this.ident = ident;
    }

    /**
     * @return devuelve el valor de la variable almacenada en la variable
     * correspondiente.
     */
    public String getIntentos() {
        return intentos;
    }

    /**
     * @param intentos es la variable donde se va a almacenar el valor del
     * intento que utilizó el empleado para presentar el quizz.
     */
    public void setIntentos(String intentos) {
        this.intentos = intentos;
    }

    /**
     * @return devuelve el valor almacenado en ella, el valor que contiene la
     * variable correspondiente es el nombre del quizz que está presentando.
     */
    public String getQuizz() {
        return quizz;
    }

    /**
     * @param quizz almacena en la variable el nombre del quizz que presenta o
     * presentó el empleado.
     */
    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    /**
     * @return devuelve el valor de la variable el cuál es la puntuación
     * obtenida tras presentar algún quizz.
     */
    public String getPuntos() {
        return puntos;
    }

    /**
     * @param puntos almacena los puntos obtenidos del quizz presentado.
     */
    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    /**
     * @return devuelve el status obtenido tras haber obtenido la calificación
     * del quizz (aprobado o reprobado).
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el status obtenido tras haber concluido un quizz.
     * Se obtiene a partir de la puntuación obtenida.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve el valor almacenado en la variable que le corresponde,
     * el valor almacenado en ella es el modo de calificación del quizz.
     */
    public String getMod_calif() {
        return mod_calif;
    }

    /**
     * @param mod_calif se almacena en la variable el modo de calificación del
     * quizz.
     */
    public void setMod_calif(String mod_calif) {
        this.mod_calif = mod_calif;
    }

    /**
     * @return devuelve las puntuación total que se puede obtener en el quizz
     * presentado.
     */
    public String getTpuntos() {
        return Tpuntos;
    }

    /**
     * @param Tpuntos almacena la puntuación total que un empleado puede obtener
     * tras haber presentado un quizz.
     */
    public void setTpuntos(String Tpuntos) {
        this.Tpuntos = Tpuntos;
    }

}
