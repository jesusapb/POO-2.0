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

    /**
     * @return devuelve el valor numérico de la variable implementada para
     * contar y almacenar la cantidad de quizzes registrados, asi mismo sirve
     * para comparar con otras variables.
     */
    public int getIncremento() {
        return incremento;
    }

    /**
     * @param incremento almacena el valor numérido de la variable implementada
     * para contar y almacenar la cantidad de quizzes registrados, asi mismo
     * sirve para comparar con otras variables.
     */
    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

    /**
     * @return devuelve el número el cuál obtuvo al ser registrado en la base de
     * datos.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id almacena el número el cuál obtuvo al ser registrado en la base
     * de datos.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return devuelve el nombre del quizz al que se le está agregando las
     * preguntas.
     */
    public String getQuizz() {
        return quizz;
    }

    /**
     * @param quizz almacena el nombre del quizz al que se le está agregando las
     * preguntas.
     */
    public void setQuizz(String quizz) {
        this.quizz = quizz;
    }

    /**
     * @return devuelve la pregunta que se está registrando o se registró.
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta almacena la pregunta que se está registrando o se
     * registró.
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return devuelve el tipo de pregunta que va a ser o con la que se
     * registró.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo almacena el tipo de pregunta que va a ser o con la que se
     * registró.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return devuelve un valor númerico el cual indica cuantos tipos de
     * preguntas se están mostrando.
     */
    public String getNum_resp() {
        return num_resp;
    }

    /**
     * @param num_resp almacena un valor númerico el cual indica cuantos tipos
     * de preguntas se están mostrando.
     */
    public void setNum_resp(String num_resp) {
        this.num_resp = num_resp;
    }

    /**
     * @return devuelve la puntuación total asignada a la pregunta.
     */
    public String getPuntuacion_total() {
        return puntuacion_total;
    }

    /**
     * @param puntuacion_total almacena la puntuación total asignada a la
     * pregunta.
     */
    public void setPuntuacion_total(String puntuacion_total) {
        this.puntuacion_total = puntuacion_total;
    }

    /**
     * @return devuelve la primera respuesta válida que asigna el administrador.
     */
    public String getResp1() {
        return resp1;
    }

    /**
     * @param resp1 almacena la primera respuesta válida que asigna el
     * administrador.
     */
    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    /**
     * @return devuelve la puntuación de la primera respuesta válida.
     */
    public String getR1() {
        return r1;
    }

    /**
     * @param r1 almacena la puntuación de la primera respuesta válida.
     */
    public void setR1(String r1) {
        this.r1 = r1;
    }

    /**
     * @return devuelve la segunda respuesta válida que asigna el administrador.
     */
    public String getResp2() {
        return resp2;
    }

    /**
     * @param resp2 almacena la segunta respuesta válida que asigna el
     * administrador.
     */
    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    /**
     * @return devuelve la puntuación de la segunda respuesta válida.
     */
    public String getR2() {
        return r2;
    }

    /**
     * @param r2 almacena la puntuación de la segunda respuesta válida.
     */
    public void setR2(String r2) {
        this.r2 = r2;
    }

    /**
     * @return devuelve la tercera respuesta válida que asigna el administrador.
     */
    public String getResp3() {
        return resp3;
    }

    /**
     * @param resp3 almacena la tercera respuesta válida que asigna el
     * administrador.
     */
    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }

    /**
     * @return devuelve la puntuación de la tercera respuesta válida.
     */
    public String getR3() {
        return r3;
    }

    /**
     * @param r3 almacena la puntuación de la tercera respuesta válida.
     */
    public void setR3(String r3) {
        this.r3 = r3;
    }

    /**
     * @return devuelve la cuarta respuesta válida que asigna el administrador.
     */
    public String getResp4() {
        return resp4;
    }

    /**
     * @param resp4 almacena la cuarta respuesta válida que asigna el
     * administrador.
     */
    public void setResp4(String resp4) {
        this.resp4 = resp4;
    }

    /**
     * @return devuelve la puntuación de la cuarta respuesta válida.
     */
    public String getR4() {
        return r4;
    }

    /**
     * @param r4 almacena la puntuación de la cuarta respuesta válida.
     */
    public void setR4(String r4) {
        this.r4 = r4;
    }

    /**
     * @return devuelve la primera respuesta no válida que asigna el
     * administrador.
     */
    public String getDis1() {
        return dis1;
    }

    /**
     * @param dis1 almacena la primera respuesta no válida que asigna el
     * administrador.
     */
    public void setDis1(String dis1) {
        this.dis1 = dis1;
    }

    /**
     * @return devuelve la segunda respuesta no válida que asigna el
     * administrador.
     */
    public String getDis2() {
        return dis2;
    }

    /**
     * @param dis2 almacena la segunda respuesta no válida que asigna el
     * administrador.
     */
    public void setDis2(String dis2) {
        this.dis2 = dis2;
    }

    /**
     * @return devuelve la tercera respuesta no válida que asigna el
     * administrador.
     */
    public String getDis3() {
        return dis3;
    }

    /**
     * @param dis3 almacena la tercera respuesta no válida que asigna el
     * administrador.
     */
    public void setDis3(String dis3) {
        this.dis3 = dis3;
    }

    /**
     * @return devuelve la cuarta respuesta no válida que asigna el
     * administrador.
     */
    public String getDis4() {
        return dis4;
    }

    /**
     * @param dis4 almacena la cuarta respuesta no válida que asigna el
     * administrador.
     */
    public void setDis4(String dis4) {
        this.dis4 = dis4;
    }

}
