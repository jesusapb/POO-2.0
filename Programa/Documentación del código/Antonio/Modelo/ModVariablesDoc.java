package Modelo;

/**
 * Clase encargada en almacenar los datos de cada documento que se registre o
 * que se solicite hacer alguna gestión.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModVariablesDoc {

    /**
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param nombre es el nombre que se le asigna al documento al momento de
     * ser registrado.
     * @param status es el status el cual obtiene el quiezz, esto afecta solo a
     * los empleados ya que se modifica la visualización del documento para
     * ellos.
     * @param descripcion es la descripción con la cual se registra el
     * documento, puede ser idea o descripción en general de lo que se va a
     * tratar el documento.
     * @param archivo es el documento (en PDF) el cual es registrado.
     */
    private int id;
    private String nombre;
    private String status;
    private String descripcion;
    byte[] archivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}
