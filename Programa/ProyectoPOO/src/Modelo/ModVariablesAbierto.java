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

    private int id;
    private String ident;
    private String puntuacion;
    private String quizz;
    private String pregunta;
    private String respuesta;
    private String status;
    private String p_asignada;

    /**
     * @return entrega el valor almacenado en él, el valor que entrega es el
     * número el cual obtuvo al registrarse a la base de datos, es un valor que
     * va en incremento cada vez que se registrar un dato, por ello siempre va a
     * ser único para el registro.
     */
    public int getId() {
        return id;
    }

    /**
     * *
     * @param id función que se encarga en almacenar el dato extraido de la base
     * de datos o entregado en algun momento durante la ejecución del programa.
     * Ésta variable da referencia a ese número único que tiene en la base de
     * datos el registro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return entrega el valor almacenado en él, es la identificación del
     * empleado que presentó el quizz (matrícula).
     */
    public String getIdent() {
        return ident;
    }

    /**
     * @param ident función encargada en almacenar el dato extraido de la base
     * de datos o entregado durante la ejecución del programa. Se amacena en el
     * la matrícula del empleado que presenta o presentó algún quizz.
     */
    public void setIdent(String ident) {
        this.ident = ident;
    }

    /**
     * @return entrega el valor que se almacena en su variable, éste dato da
     * referencia a la puntuación que vale esa pregunta en específico del quizz
     * que está presentando el empleado.
     */
    public String getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion en ésta función se almacena algun dato a la vez en la
     * variable especificada, éste almacenamiento se da al ser extraido el dato
     * de la base de datos o entregado por el programa durante su ejecución en
     * algún proceso. Ésta dato que se almacena, da referencia a la puntuación
     * que algún empleado obtuvo tras contestar algúna pregunta abierta.
     */
    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return entrega el dato que fué almacenado en su variable
     * correspondiente, éste dato que se entrega se refiere al quizz del cuál
     * proviene la pregunta que contestó el empleado.
     */
    public String getQuizz() {
        return quizz;
    }

    /**
     * @param quizz en ella se va a almacenar algún dato ya sea que se extrajo
     * de la base de datos o bien, fúe entregado por el programa en algún
     * proceso hecho. El dato que se almacena, es el nombre del quizz del cuál
     * está presentando el empleado.
     */
    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    /**
     * @return devuelve el dato que se almacenó en su variable correspontiende,
     * éste dato da como referencia a la pregunta que contestó el empleado.
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta en ésta función se almacena algún dato que se extrajo
     * durante algún proceso en el programa. El dato que se está almacenando en
     * la variable es la pregunta que el empleado ha contestado.
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return devuelve el valor que se almacenó en su variable correspondiente,
     * éste dato da referencia a la respuesta que el empleado dió a la pregunta
     * que se le mostró.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta función que se encarga de almacenar algún dato que se
     * extrajo durante algún proceso en el programa. El dato que se entrega para
     * su amacenamiento es la respuesta que el empleado entregó como respuesta a
     * la pregunta que se le planteó en el quizz.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return devuelve el valor que fué almacenada en su variable
     * correspondiente, el dato que entrega se refiere al status el cual tiene
     * la respuesta del empleado, en pocas palabras es el identificador en la
     * base de datos que dice si ya fué calificado o aún no.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status en esta función se almacena en la variable algún dato
     * extraido durando algún proceso del programa. Ésta status es el
     * identificador que hay en la base de datos el cuál indica si ya fué
     * calificada la respuesta que el empleado dió a la pregunta que contestó.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return función que se encarga de devolver el valor almacenado en su
     * variable, éste valor se refiere a la puntuación máxima que pudo obtener
     * el empleado al contestar esa pregunta en particular.
     */
    public String getP_asignada() {
        return p_asignada;
    }

    /**
     * @param p_asignada función que se encarga de almacenar algún valor en la
     * variable, éste valor es entregado por el programa el algún proceso, el
     * dato que se almacena en él, es la puntuación total de la pregunta que
     * contestó.
     */
    public void setP_asignada(String p_asignada) {
        this.p_asignada = p_asignada;
    }

}
