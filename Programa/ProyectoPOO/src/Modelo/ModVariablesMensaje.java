package Modelo;

/**
 * Clase encargada en almacenar los datos correspondientes a los mensajes
 * enviados o recibidos a los usuarios.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesMensaje {

    private int id;
    private String de_mat;
    private String de_nom;
    private String para_mat;
    private String para_nom;
    private String fecha;
    private String asunto;
    private String mensaje;
    private String status;

    /**
     * @return devuelve el valor numérico con el cuál fue registrado en la base
     * de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id almacena en la variable el valor numérico con el cuál fué
     * registrado en la base de datos.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el dato almacenado en la variabel el cuál es la
     * matrícula del usuario que está enviando algún mensaje.
     */
    public String getDe_mat() {
        return de_mat;
    }

    /**
     * @param de_mat almacena la matrícula del usuario que está enviando algún
     * mensaje.
     */
    public void setDe_mat(String de_mat) {
        this.de_mat = de_mat;
    }

    /**
     * @return devuelve el nombre del usuario quien está enviando algún mensaje.
     */
    public String getDe_nom() {
        return de_nom;
    }

    /**
     * @param de_nom almacena el nombre del usuaro que está enviando algún
     * mensaje.
     */
    public void setDe_nom(String de_nom) {
        this.de_nom = de_nom;
    }

    /**
     * @return devuelve la matrícula del usuario a quien va dirigido el mensaje.
     */
    public String getPara_mat() {
        return para_mat;
    }

    /**
     * @param para_mat almacena la matrícula del usaurio a quien va dirigido el
     * mensaje.
     */
    public void setPara_mat(String para_mat) {
        this.para_mat = para_mat;
    }

    /**
     * @return devuelve el nombre del usuaurio a quien va dirigido el mensaje.
     */
    public String getPara_nom() {
        return para_nom;
    }

    /**
     * @param para_nom almacena el nombre del usuario a quien va dirigido el
     * mensaje.
     */
    public void setPara_nom(String para_nom) {
        this.para_nom = para_nom;
    }

    /**
     * @return devuelbe la fecha de la emisión del mensaje.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha almacena la fecha de cuando fué enviado el mensaje.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return devuelve el asunto (propósito) por el cuál es enviado el mensaje.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto almacena el asunto por el cuál es enviado el mensaje.
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return devuelve el contenido del mensaje escrito por el usuario.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje almacena el contenido del mensaje escrito por el usuario.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return devuelve el status actuál del mensaje, indica si ya ha sido visto
     * o no.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el status actuál del mensaje, indica si ya ha sido
     * visto o no.
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
