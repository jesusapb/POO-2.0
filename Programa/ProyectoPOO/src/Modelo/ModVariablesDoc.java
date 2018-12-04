package Modelo;

/**
 * Clase encargada en almacenar los datos de cada documento que se registre o
 * que se solicite hacer alguna gestión.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesDoc {

    private int id;
    private String nombre;
    private String status;
    private String descripcion;
    byte[] archivo;

    /**
     * @return devuelve el valor numerico del registro de la base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id almacena el valor numerido del registro de la base de datos.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el valor almacenado en la variable correspondiente, el
     * valor que devuelve es el nombre del documento registrado en la base de
     * datos.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre almacena el nombre del documento con el cuál se va a
     * registrar en la base de datos.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return devuelve el status de visualización del documento para los
     * empleados.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status almacena el valor del estatus el cuál va a corresponder al
     * status de visualización del documento para los empleados.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return devuelve el valor almacenado en la variable el cuál corresponde a
     * la descripción que el administrador le asignó al documento registrado.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion almacena en la variable la descripción que el
     * administrador le asigno al documento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return devuelve el documento que se almacenó en la base de datos.
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     * @param archivo almacena la información del documento que se desea
     * registrar en la base de datos.
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}
