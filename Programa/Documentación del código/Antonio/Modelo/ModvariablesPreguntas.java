package Modelo;

/**
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModvariablesPreguntas {

    /**
     * @param incremento variable implementada para contar y almacenar la
     * cantidad de quizzes registrados, asi mismo sirve para comparar con otras
     * variables.
     * @param id es la número el cual obtuvo al registrarse a la base de datos.
     * @param quizz es el nombre del quiz del cuál es registrada la pregunta.
     * @param pregunta es la pregunta que escribe el administrador.
     * @param tipo es el tipo que el administrador le asigna, puede ser de tipo
     * abierto, único o múltiple. Especifíca el tipo de pregunta que se va a
     * registrar.
     * @param num_resp esta variable cuenta cuantos tipos de respuesta se estan
     * asignando, va de cero a 4.
     * @param puntuacion_total es la puntuación que el administrador asigna para
     * especificar el valor de la pregunta.
     * @param resp1 es la primera respuesta válida que asigna el administrador.
     * @param r1 es lo que valdrá ésta respuesta.
     * @param resp2 es la segunda respuesta válida que asigna el administrador.
     * @param r2 es lo que valdrá ésta respuesta.
     * @param resp3 es la tercera respuesta válida que asigna el administrador.
     * @param r3 es lo que valdrá ésta respuesta.
     * @param resp4 es la cuarta respuesta válida que asigna el administrador.
     * @param r4 es lo que valdrá ésta respuesta.
     * @param dis1 es el primer distractor (respuesta errónea) que asigna el
     * administrador.
     * @param dis2 es el segundo distractor (respuesta errónea) que asigna el
     * administrador.
     * @param dis3 es el tercer distractor (respuesta errónea) que asigna el
     * administrador.
     * @param dis4 es el cuarto distractor (respuesta errónea) que asigna el
     * administrador.
     */
    private int incremento;
    private int id;
    private String quizz;
    private String pregunta;
    private String tipo;
    private String num_resp;
    private String puntuacion_total;
    private String resp1;
    private String r1;
    private String resp2;
    private String r2;
    private String resp3;
    private String r3;
    private String resp4;
    private String r4;
    private String dis1;
    private String dis2;
    private String dis3;
    private String dis4;

    public int getIncremento() {
        return incremento;
    }

    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNum_resp() {
        return num_resp;
    }

    public void setNum_resp(String num_resp) {
        this.num_resp = num_resp;
    }

    public String getPuntuacion_total() {
        return puntuacion_total;
    }

    public void setPuntuacion_total(String puntuacion_total) {
        this.puntuacion_total = puntuacion_total;
    }

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getResp3() {
        return resp3;
    }

    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }

    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    public String getResp4() {
        return resp4;
    }

    public void setResp4(String resp4) {
        this.resp4 = resp4;
    }

    public String getR4() {
        return r4;
    }

    public void setR4(String r4) {
        this.r4 = r4;
    }

    public String getDis1() {
        return dis1;
    }

    public void setDis1(String dis1) {
        this.dis1 = dis1;
    }

    public String getDis2() {
        return dis2;
    }

    public void setDis2(String dis2) {
        this.dis2 = dis2;
    }

    public String getDis3() {
        return dis3;
    }

    public void setDis3(String dis3) {
        this.dis3 = dis3;
    }

    public String getDis4() {
        return dis4;
    }

    public void setDis4(String dis4) {
        this.dis4 = dis4;
    }

}
