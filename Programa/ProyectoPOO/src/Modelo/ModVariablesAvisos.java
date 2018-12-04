package Modelo;

/**
 * Clase encargada en almacenar todos los datos correspondientes a los avisos
 * que se envian y reciben.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesAvisos {

    private int id;
    private String para;
    private String quien;
    private String que;
    private String cuando;
    private String status;

    /**
     * @return entrega el valor que fué almacenado en su variable
     * correspondiente, éste valor es la identificación única del registro en la
     * base de datos de su respectiva tabla.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id en ésta variable se almacena algún valor que el programa
     * extrajo en algún proceso del programa, éste valor corresponde a la
     * identificación única del registro en la base de datos (valor en
     * autoincremento).
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return entrega el valor que fué almacenado en su variable
     * correspondiente, éste valor se refiere al destinatario del mensaje que se
     * está enviando.
     */
    public String getPara() {
        return para;
    }

    /**
     * @param para en ésta variable se almacena algún dato el cuál se extrajo en
     * algún proceso del programa, éste valor contiene el valor (matrícula) del
     * destinatario al cuál se envió algún mensaje.
     */
    public void setPara(String para) {
        this.para = para;
    }

    /**
     * @return entrega el valor que fué almacenado en su variable
     * correspondiente, éste valor se refiere a quién está emitiendo el mensaje.
     */
    public String getQuien() {
        return quien;
    }

    /**
     * @param quien en ésta variable de guarda el dato del usuario quien está
     * emitiendo o emitió algún mensaje, éste dato es extraído y almacenado en
     * ésta variable durante algún proceso del programa.
     */
    public void setQuien(String quien) {
        this.quien = quien;
    }

    /**
     * @return entrega el dato que fué almacenado en ésta variable, éste dato se
     * refiere al propósito del mensaje.
     */
    public String getQue() {
        return que;
    }

    /**
     * @param que en ésta variable se guarda el dato que se refiere al cuerpo
     * del mensaje (el propósito del mensaje), éste dato es guardado en ésta
     * variable durando algún proceso del programa.
     */
    public void setQue(String que) {
        this.que = que;
    }

    /**
     * @return entrega el dato que fue almacenado en su variable
     * correspondiente, éste dato se refiere al día y hora de la emisión del
     * mensaje.
     */
    public String getCuando() {
        return cuando;
    }

    /**
     * @param cuando en ésta variable se guarda el dato correspondiente al día y
     * hora de cuando fué o es emitido el mensaje, este dato es obtenido a
     * travez de algún proceso de extracción en el programa.
     */
    public void setCuando(String cuando) {
        this.cuando = cuando;
    }

    /**
     * @return entrega el dato almacenado en la variable que corresponde, éste
     * dato contiene el status de visión del mensaje, contiene el dato si el
     * mensaje ya ha sido leído o no.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status en ésta variable se guarda el dato que corresponde a la
     * afirmación o negación si el mensaje que se envio ya ha sido leído o falta
     * por leer.
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
