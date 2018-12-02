package Modelo;

/**
 * Clase encargada en almacenar todos los datos correspondientes a los avisos
 * que se envian y reciben.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesAvisos {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param para es para quien va a ser enviado el mensaje.
     * @param quien es quíen lo esta enviando el aviso.
     * @param que es el asunto del aviso.
     * @param cuando es la fecha de la cuan fue emitido el aviso.
     * @param status es el status con el cual se envia o está almacenado
     * ("visto" o "no visto").
     */
    private int id;
    private String para;
    private String quien;
    private String que;
    private String cuando;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getQuien() {
        return quien;
    }

    public void setQuien(String quien) {
        this.quien = quien;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getCuando() {
        return cuando;
    }

    public void setCuando(String cuando) {
        this.cuando = cuando;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
