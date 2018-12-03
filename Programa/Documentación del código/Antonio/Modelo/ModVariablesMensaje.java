package Modelo;

/**
 * Clase encargada en almacenar los datos correspondientes a los mensajes
 * enviados o recibidos a los usuarios.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesMensaje {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param de_mat es la matrícula del usuario quien está enviando el mensaje.
     * @param de_nom es el nombre del usuario quien está enviando el mensaje.
     * @param para_mat es la matrícula del usuario a quien va dirigido el
     * mensaje.
     * @param para_nom es el nombre del usuario a quien va dirigido el mensaje.
     * @param fecha es la fecha que se registra cuando se envia en mensaje.
     * @param asunto es el asunto del mensaje (de que se va a tratar, etc).
     * @param mensaje es el contenido del mensaje.
     * @param status es el status del mensaje, si es leido o no, es la
     * identificación para la gestión.
     */
    private int id;
    private String de_mat;
    private String de_nom;
    private String para_mat;
    private String para_nom;
    private String fecha;
    private String asunto;
    private String mensaje;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDe_mat() {
        return de_mat;
    }

    public void setDe_mat(String de_mat) {
        this.de_mat = de_mat;
    }

    public String getDe_nom() {
        return de_nom;
    }

    public void setDe_nom(String de_nom) {
        this.de_nom = de_nom;
    }

    public String getPara_mat() {
        return para_mat;
    }

    public void setPara_mat(String para_mat) {
        this.para_mat = para_mat;
    }

    public String getPara_nom() {
        return para_nom;
    }

    public void setPara_nom(String para_nom) {
        this.para_nom = para_nom;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
