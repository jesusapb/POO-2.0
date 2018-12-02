package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase engargada en extraer los datos correspondientes de la base de datos y
 * almacenarlo en arreglos.
 *
 * @author Karina Carmona, Antonio Cetzal, Jessica González y Jesús Pacheco
 * @version 29/11/2018/ProyectoPoo_Acompañamiento
 */
public class ModListas {

    /**
     * Arreglo que contiene los datos de todos los usuarios registrados en la
     * base de datos. Se usa para el manejo y comparacion en diversos casos en
     * el programa, asi mismo sirve para en envio de mensajes. Este arreglo
     * Tiene como parámetro todas las variables registradas en la función
     * ModVariablesReg.
     *
     * @return todos los datos de los usuarios registrados en la base de datos
     * almacenados en forma de un arreglo.
     */
    public ArrayList<ModVariablesReg> listaE() {
        ArrayList<ModVariablesReg> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesReg var = new ModVariablesReg();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setNombre_completo(rs.getString(6) + "/" + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de almacenar todos los mensajes que se tengan en la
     * tabla correspondiente de la base de datos. Tiene como parámetro todas las
     * variables registradas en la función ModVariablesMensaje.
     *
     * @return en contenido, asi como su descripcion de los mensajes que se
     * tengan en la base de datos.
     */
    public ArrayList<ModVariablesMensaje> listaMR() {
        ArrayList<ModVariablesMensaje> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM mensajes";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesMensaje var = new ModVariablesMensaje();
                var.setId(rs.getInt(1));
                var.setDe_mat(rs.getString(2));
                var.setDe_nom(rs.getString(3));
                var.setPara_mat(rs.getString(4));
                var.setPara_nom(rs.getString(5));
                var.setFecha(rs.getString(6));
                var.setAsunto(rs.getString(7));
                var.setMensaje(rs.getString(8));
                var.setStatus(rs.getString(9));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de almacenar los tados correspondientes a la tabla de
     * los documentos registrados en la base de datos. Tiene como parámetro
     * todas las variables registradas en la función ModVariablesDoc. Tiene como
     * parámetro todas las variables registradas en la función ModVariablesDoc.
     *
     * @return contenido de cada registro de documentos en la base de datos.
     */
    public ArrayList<ModVariablesDoc> listaDocs() {
        ArrayList<ModVariablesDoc> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM documentos";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesDoc var = new ModVariablesDoc();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setStatus(rs.getString(3));
                var.setDescripcion(rs.getString(4));
                var.setArchivo(rs.getBytes(5));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de almacenar cada dato del registro de cada quizz en la
     * base de datos. Tiene como parámetro todas las variables registradas en la
     * función ModVariablesQuizzes.
     *
     * @return valores registrados de los quizzes en la base de datos
     * almacenados en forma de arreglo.
     */
    public ArrayList<ModVariablesQuizzes> listaQuizz() {
        ArrayList<ModVariablesQuizzes> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM quizzes";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesQuizzes var = new ModVariablesQuizzes();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setDescripcion(rs.getString(3));
                var.setP_totales(rs.getString(4));
                var.setP_actuales(rs.getString(5));
                var.setStatus(rs.getString(6));
                var.setIntentos(rs.getString(7));
                var.setMod_calif(rs.getString(8));
                var.setTiempo(rs.getString(9));
                var.setF_registro(rs.getString(10));
                var.setF_activacion(rs.getString(11));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Ésta función a diferencia de la primera, ésta es la encargada de extraer
     * y almacenar los datos del usuario que esté usando el programa. Tiene como
     * parámetro todas las variables registradas en la función ModVariablesUsr.
     *
     * @return todos los valores registrados del usuario que esté usando el
     * programa.
     */
    public ArrayList<ModVariablesUsr> listaUs() {
        ArrayList<ModVariablesUsr> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesUsr var = new ModVariablesUsr();
                var.setId(rs.getInt(1));
                var.setNombre(rs.getString(2));
                var.setAp_pat(rs.getString(3));
                var.setAp_mat(rs.getString(4));
                var.setTipo(rs.getString(5));
                var.setMatricula(rs.getString(6));
                var.setContraseña(rs.getString(7));
                var.setCorreo(rs.getString(8));
                var.setStatus(rs.getString(11));
                var.setIp(rs.getString(12));
                var.setEquipo(rs.getString(13));
                var.setComando(rs.getString(14));
                var.setCodigo(rs.getString(15));
                var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de almacenar en arreglos todos los valores de todas las
     * preguntas registradas en la base de datos. Tiene como parámetro todas las
     * variables registradas en la función ModvariablesPreguntas.
     *
     * @return todos los valores de cada pregunta registrada.
     */
    public ArrayList<ModvariablesPreguntas> listaPreg() {
        ArrayList<ModvariablesPreguntas> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM preguntas";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModvariablesPreguntas var = new ModvariablesPreguntas();
                var.setId(rs.getInt(1));
                var.setQuizz(rs.getString(2));
                var.setPregunta(rs.getString(3));
                var.setTipo(rs.getString(4));
                var.setNum_resp(rs.getString(5));
                var.setPuntuacion_total(rs.getString(6));
                var.setResp1(rs.getString(7));
                var.setR1(rs.getString(8));
                var.setResp2(rs.getString(9));
                var.setR2(rs.getString(10));
                var.setResp3(rs.getString(11));
                var.setR3(rs.getString(12));
                var.setResp4(rs.getString(13));
                var.setR4(rs.getString(14));
                var.setDis1(rs.getString(15));
                var.setDis2(rs.getString(16));
                var.setDis3(rs.getString(17));
                var.setDis4(rs.getString(18));
                list.add(var);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de almacenar los datos específicos de las preguntas
     * registradas en la base de datos. Tiene como parámetro todas las variables
     * registradas en la función ModvariablesPreguntas.
     *
     * @param quizz es el número del quizz (id) en el cual fué registrada la
     * pregunta.
     * @return los valores específicos de las preguntas.
     */
    public ArrayList<ModvariablesPreguntas> listaPregMod(int quizz) {
        ArrayList<ModvariablesPreguntas> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM preguntas";
        int a = 0;

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                /**
                 * Se aplica una validación en búsqueda del registro de las o la
                 * pregunta registrada en el quizz específico.
                 */
                if (rs.getString(2).equals(quizz + "")) {
                    ModvariablesPreguntas var = new ModvariablesPreguntas();
                    var.setIncremento(a);
                    a = a + 1;
                    var.setId(rs.getInt(1));
                    var.setQuizz(rs.getString(2));
                    var.setPregunta(rs.getString(3));
                    var.setTipo(rs.getString(4));
                    var.setNum_resp(rs.getString(5));
                    var.setPuntuacion_total(rs.getString(6));
                    var.setResp1(rs.getString(7));
                    var.setR1(rs.getString(8));
                    var.setResp2(rs.getString(9));
                    var.setR2(rs.getString(10));
                    var.setResp3(rs.getString(11));
                    var.setR3(rs.getString(12));
                    var.setResp4(rs.getString(13));
                    var.setR4(rs.getString(14));
                    var.setDis1(rs.getString(15));
                    var.setDis2(rs.getString(16));
                    var.setDis3(rs.getString(17));
                    var.setDis4(rs.getString(18));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada en asignar (llenar) los campos correspondientes para
     * poder visualizar las preguntas. En este caso es para que se vea en la
     * ventana correspondiente del empleado y asi pueda presentar el quizz. Ésta
     * función es una complementaria para el funcionamiento y el manejo de las
     * preguntas aleatorias y que no se repitan.
     *
     * @param id es la variable que tiene el valor de la pregunta que se está
     * presentando.
     * @param quizz el la variable que dice cuál quizz se está presentando.
     * @return los valores de la pregunta que se va a asignar para que el
     * empleado pueda contestar.
     */
    public String[] listaResp(int id, int quizz) {
        String[] list = new String[12];
        ModvariablesPreguntas var;
        ModListas mens = new ModListas();
        /**
         * Llama a la función de ésta misma clase la cual contiene los valores
         * del quizz específico.
         */
        ArrayList<ModvariablesPreguntas> lista = mens.listaPregMod(quizz);

        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                var = lista.get(i);
                if (var.getId() == id) {
                    list[0] = var.getResp1() + "~" + var.getR1();
                    if (var.getR2().equals("*/null/*")) {
                        list[1] = var.getResp2() + "~0.00";
                    } else {
                        list[1] = var.getResp2() + "~" + var.getR2();
                    }
                    if (var.getR3().equals("*/null/*")) {
                        list[2] = var.getResp3() + "~0.00";
                    } else {
                        list[2] = var.getResp3() + "~" + var.getR3();
                    }
                    if (var.getR3().equals("*/null/*")) {
                        list[3] = var.getResp4() + "~0.00";
                    } else {
                        list[3] = var.getResp4() + "~" + var.getR4();
                    }
                    list[4] = var.getDis1() + "~0.00";
                    list[5] = var.getDis2() + "~0.00";
                    list[6] = var.getDis3() + "~0.00";
                    list[7] = var.getDis4() + "~0.00";
                }
            }
        }

        return list;
    }

    /**
     * Ésta función es una función complementaria la cual se encarga en enviár
     * los avisos correspondientes al tipo de usuario. Tiene como parámetro
     * todas las variables registradas en la función ModVariablesReg.
     *
     * @param tipo corresponde al tipo de usario el cual va a ser enviado el
     * aviso (Empleado o Administrador).
     * @param comp es la matrícula del usuario que está emitiendo el aviso, se
     * debe de comparar para que no se envie el mensaje a si mismo.
     * @return todos los usuarios específicos a los cuales se enviará el aviso.
     */
    public ArrayList<ModVariablesReg> listaT(String tipo, String comp) {
        ArrayList<ModVariablesReg> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesReg var = new ModVariablesReg();
                /**
                 * Se compara en la búsqueda del tipo de usuario.
                 */
                if (rs.getString(5).equals(tipo)) {
                    /**
                     * Se descarta el usuario que emite el mensaje si el está en
                     * ese tipo de usuarios.
                     */
                    if (rs.getString(6).equals(comp)); else {
                        var.setAvisos(rs.getString(6));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de obtener los datos específicos del documento que se
     * requiera. Tiene como parámetro todas las variables registradas en la
     * función ModVariablesDoc.
     *
     * @param nombre es el nombre con el cuál está registrado el documento.
     * @return los valores específicos del documento.
     */
    public ArrayList<ModVariablesDoc> Listar_PdfMod(String nombre) {
        ArrayList<ModVariablesDoc> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM documentos";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesDoc var = new ModVariablesDoc();
                /**
                 * Se compara para poder encontrar al documento requerido.
                 */
                if (rs.getString(2).equals(nombre)) {
                    var.setId(rs.getInt(1));
                    var.setNombre(rs.getString(2));
                    var.setStatus(rs.getString(3));
                    var.setDescripcion(rs.getString(4));
                    var.setArchivo(rs.getBytes(5));
                    list.add(var);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Son todos los avisos con su contenido y descripcion enviados al usuario
     * correspondiente. Tiene como parámetro todas las variables registradas en
     * la función ModVariablesAvisos.
     *
     * @param matricula es la matrícula del usuario que está usando el programa.
     * @return el contenido del aviso para su observación.
     */
    public ArrayList<ModVariablesAvisos> listaAv(String matricula) {
        ArrayList<ModVariablesAvisos> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM avisos";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesAvisos var = new ModVariablesAvisos();
                /**
                 * Se compara para encontrar todos los avisos que correspondan
                 * al usuario.
                 */
                if (rs.getString(2).equals(matricula)) {
                    if (rs.getString(6).equals("no visto")) {
                        var.setId(rs.getInt(1));
                        var.setPara(rs.getString(2));
                        var.setQuien(rs.getString(3));
                        var.setQue(rs.getString(4));
                        var.setCuando(rs.getString(5));
                        var.setStatus(rs.getString(6));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Ésta función es utilizada para buscar al usuario que tenga una sesión
     * activa en la maquina donde se ejecuta. Ésta función es complementaria a
     * otra (clase principal) para el funcionamiento y validación del usuario.
     * Tiene como parámetro todas las variables registradas en la función
     * ModVariablesUsr.
     *
     * @param equipo es el nombre del equipo donde se está ejecutando el
     * programa.
     * @return los datos correspondientes al usuario que tenga una sesión activa
     * en la maquina donde se está ejecutando.
     */
    public ArrayList<ModVariablesUsr> listaUsr(String equipo) {
        ArrayList<ModVariablesUsr> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesUsr var = new ModVariablesUsr();
                /**
                 * Se compara para encontrar cada usuario que se encuente con
                 * sesión activa para posteriormente comparar si es de la
                 * maquina donde se ejecuta en el momento.
                 */
                if (rs.getString(11).equals("Conectado")) {
                    /**
                     * Una vez que se encuente una sesión activa, se compara si
                     * es del usuario que está ejecutando el programa en ese
                     * momento.
                     */
                    if (rs.getString(13).equals(equipo)) {
                        var.setId(rs.getInt(1));
                        var.setNombre(rs.getString(2));
                        var.setAp_pat(rs.getString(3));
                        var.setAp_mat(rs.getString(4));
                        var.setTipo(rs.getString(5));
                        var.setMatricula(rs.getString(6));
                        var.setContraseña(rs.getString(7));
                        var.setCorreo(rs.getString(8));
                        var.setStatus(rs.getString(11));
                        var.setIp(rs.getString(12));
                        var.setEquipo(rs.getString(13));
                        var.setComando(rs.getString(14));
                        var.setCodigo(rs.getString(15));
                        var.setNombre_completo(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                        list.add(var);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Ésta función está encargada de extraer todos los datos existentes del
     * empleado que presentó algun quizz. Tiene como parámetro todas las
     * variables registradas en la función ModVariablesPresentados.
     *
     * @param matricula es la matrícula del empleado que se desea encontrar
     * todos sus registros.
     * @param quizz es el nombre del quizz al cual se está solicitando extraer
     * los datos.
     * @return los datos específicos del empleado que presenta un quizz.
     */
    public ArrayList<ModVariablesPresentados> listaPre(String matricula, String quizz) {
        ArrayList<ModVariablesPresentados> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM presentados";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesPresentados var = new ModVariablesPresentados();
                /**
                 * Se compara en la búsqueda del empleado que se solicita.
                 */
                if (rs.getString(2).equals(matricula)) {
                    /**
                     * Al encontrar al empleado se compara en sus registros la
                     * existencia del quizz que se solicita encontrar.
                     */
                    if (rs.getString(3).equals(quizz)) {
                        var.setId(rs.getInt(1));
                        var.setIdent(rs.getString(2));
                        var.setQuizz(rs.getString(3));
                        var.setIntento(rs.getString(4));
                        var.setP_totales(rs.getString(5));
                        var.setCalificacion(rs.getString(6));
                        var.setStatus(rs.getString(7));
                        var.setAbrt(rs.getString(8));
                        list.add(var);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Es la función encargada de extraer y almacenar todos los datos
     * correspondientes del registro del empleado que presentó algun quizz.
     * Tiene como parámetro todas las variables registradas en la función
     * ModVariablesCalif.
     *
     * @param matricula es la matrícula del empleado que se desea encontrar
     * todos sus registros.
     * @param quizz es el nombre del quizz al cual se está solicitando extraer
     * los datos.
     * @return los datos del empleado que presentó el quizz requerido.
     */
    public ArrayList<ModVariablesCalif> listaCalif(String matricula, String quizz) {
        ArrayList<ModVariablesCalif> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM calificaciones";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesCalif var = new ModVariablesCalif();
                /**
                 * Se compara en la búsqueda del empleado que se solicite.
                 */
                if (rs.getString(2).equals(matricula)) {
                    /**
                     * Al encontrar al empleado se busca el registro del quizz
                     * solicitado.
                     */
                    if (rs.getString(4).equals(quizz)) {
                        var.setId(rs.getInt(1));
                        var.setIdent(rs.getString(2));
                        var.setIntentos(rs.getString(3));
                        var.setQuizz(rs.getString(4));
                        var.setPuntos(rs.getString(5));
                        var.setStatus(rs.getString(6));
                        var.setMod_calif(rs.getString(7));
                        var.setTpuntos(rs.getString(8));
                        list.add(var);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada de extraer los datos absolutos del empleado que se
     * solicite el cuál haya presentado algún quizz. Ésta función está
     * implementada para complementar la visualización de los datos en una
     * tabla. Tiene como parámetro todas las variables registradas en la función
     * ModVariablesCalif.
     *
     * @param matricula la matrícula del empleado que se solicita ls extracción
     * de los datos.
     * @return todos los datos del empleado que haya presentado algún quizz.
     */
    public ArrayList<ModVariablesCalif> listaCalifMod(String matricula) {
        ArrayList<ModVariablesCalif> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM calificaciones";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesCalif var = new ModVariablesCalif();
                /**
                 * Se compara en la búsqueda del empleado solicitado.
                 */
                if (rs.getString(2).equals(matricula)) {
                    var.setId(rs.getInt(1));
                    var.setIdent(rs.getString(2));
                    var.setIntentos(rs.getString(3));
                    var.setQuizz(rs.getString(4));
                    var.setPuntos(rs.getString(5));
                    var.setStatus(rs.getString(6));
                    var.setMod_calif(rs.getString(7));
                    var.setTpuntos(rs.getString(8));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Función encargada en extraer los datos absolutos del empleado que se haya
     * solicitado observar el historial de presentación de cada quizz. Tiene
     * como parámetro todas las variables registradas en la función
     * ModVariablesPresentados.
     *
     * @param matricula la matrícula del empleado que se solicita ls extracción
     * de los datos.
     * @return todos los datos del empleado que haya presentado algún quizz.
     */
    public ArrayList<ModVariablesPresentados> listaReg(String matricula) {
        ArrayList<ModVariablesPresentados> list = new ArrayList<>();
        ModConexion con = new ModConexion();
        String sql = "SELECT * FROM presentados";

        try {
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModVariablesPresentados var = new ModVariablesPresentados();
                /**
                 * Se compara en la búsqueda del empleado que se solicita.
                 */
                if (rs.getString(2).equals(matricula)) {
                    var.setId(rs.getInt(1));
                    var.setIdent(rs.getString(2));
                    var.setQuizz(rs.getString(3));
                    var.setIntento(rs.getString(4));
                    var.setP_totales(rs.getString(5));
                    var.setCalificacion(rs.getString(6));
                    var.setStatus(rs.getString(7));
                    var.setAbrtNum(rs.getString(8));
                    var.setAbrtTot(rs.getString(9));
                    var.setAbrt(rs.getString(10));
                    var.setMod_calif(rs.getString(11));
                    var.setMarca(rs.getString(12));
                    list.add(var);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
