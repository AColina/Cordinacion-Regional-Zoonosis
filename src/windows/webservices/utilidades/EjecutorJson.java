/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.webservices.utilidades;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.apache.http.client.utils.URIBuilder;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.administracion.Permiso;
import ve.zoonosis.model.entidades.administracion.Persona;
import ve.zoonosis.model.entidades.administracion.Usuario;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.entidades.funcionales.Animal;
import ve.zoonosis.model.entidades.funcionales.Especie;
import ve.zoonosis.model.entidades.proceso.Animal_has_Caso;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion_has_Animal;
import ve.zoonosis.model.entidades.proceso.Vacunacion;
import windows.webservices.JsonDeserializer.funcionales.AnimalDeserializer;
import windows.webservices.JsonDeserializer.proceso.CasoDeserializer;
import windows.webservices.JsonDeserializer.funcionales.EspecieDeserializer;
import windows.webservices.JsonDeserializer.administracion.MunicipioDeserializer;
import windows.webservices.JsonDeserializer.administracion.ParroquiaDeserializer;
import windows.webservices.JsonDeserializer.administracion.PermisoDeserializer;
import windows.webservices.JsonDeserializer.administracion.PersonaDeserializer;
import windows.webservices.JsonDeserializer.calendario.SemanaDeserializer;
import windows.webservices.JsonDeserializer.administracion.UsuarioDeserializer;
import windows.webservices.JsonDeserializer.proceso.Animal_has_CasoDeserializer;
import windows.webservices.JsonDeserializer.proceso.NovedadesDeserializer;
import windows.webservices.JsonDeserializer.proceso.RegistroVacunacionDeserializer;
import windows.webservices.JsonDeserializer.proceso.RegistroVacunacion_has_AnimalDeserializer;
import windows.webservices.JsonDeserializer.proceso.VacunacionDeserializer;

/**
 * Clase de ultilidades para enviar request a webservices
 *
 * @author angel colina
 */
public abstract class EjecutorJson {

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private static final String EXCEPTION_REPORT = "Exception report";
    private static final String ERROR_REPORT = "Error report";
    private static final String INFORME_ERROR = "Informe de Error";
    private static final String MESSAGE = "message";
    private static final String DESCRIPTION = "description";
    private OkHttpClient client;
    private URI uri;
    private ObjectMapper mapper;
    private String ultimoJson;
    private String path;
    private Properties propiedadesDeConexion;
    private String json;
    private MetodosDeEnvio metodosDeEnvio;
    private HashMap<String, Object> param;

    {
        client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);
        mapper = new ObjectMapper();
        metodosDeEnvio = MetodosDeEnvio.GET;

    }

    /**
     * Crea una nueva instancia de ejecutor json, sin url y sin parametros.
     * <br>
     * Metodo por defecto GET
     */
    public EjecutorJson() {
    }

    /**
     * Crea una nueva instancia de ejecuator json con la url completa del
     * servicio de destino, sin parametros.
     * <br>
     * Metodo por defecto GET
     * <br>
     * NOTA: esta url puede tener los parametros de envio simpre y cuando el
     * metodosDeEnvio de envio sea GET.
     *
     * @param uri url a la cual se enviara el request, debe contener el http://
     */
    public EjecutorJson(URI uri) {
        this.uri = uri;
    }

    /**
     * Crea una nueva instancia de ejecuator json con la url completa del
     * servicio de destino, y con sus respectivos query params.
     * <br>
     * Metodo por defecto GET
     * <br>
     * NOTA: esta url puede tener los parametros de envio simpre y cuando el
     * metodosDeEnvio de envio sea GET.
     *
     * @param uri url a la cual se enviara el request, debe contener el http://
     * @param param la key debe ser el nombre con que el servicio esta
     * recibiendo el valor.
     */
    public EjecutorJson(URI uri, HashMap<String, Object> param) {
        this.uri = uri;
        this.param = param;
    }

    /**
     * Crea una nueva instancia de ejecuator json con la url completa del
     * servicio de destino, y con sus respectivos query params y con el
     * metodosDeEnvio de envio.
     * <br>
     * NOTA: esta url puede tener los parametros de envio simpre y cuando el
     * metodosDeEnvio de envio sea GET.
     *
     * @param uri Url a la cual se enviara el request, debe contener el http://
     * @param param La key debe ser el nombre con que el servicio esta
     * recibiendo el valor.
     * @param metodosDeEnvio Enum con el metodosDeEnvio de envio post,get, put..
     */
    public EjecutorJson(URI uri, HashMap<String, Object> param, MetodosDeEnvio metodosDeEnvio) {
        this.uri = uri;
        this.param = param;
        this.metodosDeEnvio = metodosDeEnvio;
    }

    /**
     * Crea una nueva instancia de ejecuator json con la url especificada en el
     * propertie Config, no tiene parametros.
     * <br> url : htpps://test:2032/
     * <br>
     * Metodo por defecto GET
     *
     * @param path Url donde esta ubicado el metodosDeEnvio q recibe el request,
     * {rest/test}
     * @throws java.net.URISyntaxException Url no valido
     */
    public EjecutorJson(String path) throws URISyntaxException {
        defaultProperty();
        prepararUri(path);

    }

    /**
     * Crea una nueva instancia de ejecuator json con la url especificada en el
     * propertie Config.
     * <br> url : htpps://test:2032/
     * <br>
     * Metodo por defecto GET
     *
     * @param path Url donde esta ubicado el metodosDeEnvio q recibe el request,
     * {rest/test}
     * @param param La key debe ser el nombre con que el servicio esta
     * recibiendo el valor.
     * @throws java.net.URISyntaxException
     */
    public EjecutorJson(String path, HashMap<String, Object> param) throws URISyntaxException {
        defaultProperty();
        prepararUri(path);
        this.param = param;
    }

    /**
     * Crea una nueva instancia de ejecuator json con la url especificada en el
     * propertie Config.
     * <br> url : htpps://test:2032/
     * <br>
     * Metodo por defecto GET
     *
     * @param path Url donde esta ubicado el metodosDeEnvio q recibe el request,
     * {rest/test}
     * @param param La key debe ser el nombre con que el servicio esta
     * recibiendo el valor.
     * @param metodosDeEnvio Enum con el metodosDeEnvio de envio post,get, put..
     * @throws java.net.URISyntaxException
     */
    public EjecutorJson(String path, HashMap<String, Object> param, MetodosDeEnvio metodosDeEnvio) throws URISyntaxException {
        defaultProperty();
        prepararUri(path);
        this.param = param;
        this.metodosDeEnvio = metodosDeEnvio;
    }

    /**
     * Metodo para crear un json a partir de un objecto
     *
     * @param objeto Instancia del objeto a escribir en json
     * @return instacia de Ejecutar de json
     */
    public EjecutorJson crearJson(Object objeto) {
        try {
            json = mapper.writeValueAsString(objeto);
        } catch (IOException ex) {
            Logger.getLogger(EjecutorJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    /**
     * Envia el request al servicio web y no recibe respuesta
     *
     * @throws java.io.IOException
     */
    public void ejecutarJson() throws IOException {
        ejecutarJsonToString();
    }

    /**
     * Envia el request al servicio web y recibe la respuesta como string.
     *
     * @return String con la respuesta del servicio web
     * @throws java.io.IOException si el servicio de timeout o si no se pudo
     * leer el json de respuesta.
     */
    public String ejecutarJsonToString() throws IOException {
        return ejecutarJson(String.class, null);
    }

    /**
     * Envia el request al servicio web y no recibe respuesta
     *
     * @param <T> Clase Generica
     * @param entidad
     * @param modulus modeulos a desderializar
     * @return
     * @throws java.io.IOException
     */
    public <T> T ejecutarJson(Class<T> entidad, SimpleModule[] modulus) throws IOException {
        return ejecutarJsonGeneral(prepararRequest(), null, null, null, entidad, modulus);
    }

    /**
     *
     * @param <T> Clase Generica
     * @param colleccion
     * @param entidad
     * @param modulus
     * @return
     * @throws java.io.IOException
     */
    public <T> List<T> ejecutarJson(Class<? extends List> colleccion, Class<T> entidad, SimpleModule[] modulus) throws IOException {
        return (List<T>) ejecutarJsonGeneral(prepararRequest(), colleccion, null, null, entidad, modulus);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param mapClass
     * @param classKey
     * @param classValue
     * @param modulus
     * @return
     * @throws IOException
     */
    public <K, V> Map<K, V> ejecutarJson(Class<? extends Map> mapClass, Class<K> classKey, Class<V> classValue, SimpleModule[] modulus) throws IOException {
        return (Map<K, V>) ejecutarJsonGeneral(prepararRequest(), null, mapClass, classKey, classValue, modulus);
    }

    //METODOS PRIVADOS
    private <T> T ejecutarJsonGeneral(Request request, Class<? extends List> colleccion,
            Class<? extends Map> map, Class<?> key, Class<T> mappe, SimpleModule[] modules) throws IOException {
        mapper = new ObjectMapper();
        SimpleModule modul = new SimpleModule("entidades")
                   .addDeserializer(Parroquia.class, new ParroquiaDeserializer())
                    .addDeserializer(Semana.class, new SemanaDeserializer())
                    .addDeserializer(Animal.class, new AnimalDeserializer())
                    .addDeserializer(Semana.class, new SemanaDeserializer())
                    .addDeserializer(Especie.class, new EspecieDeserializer())
                    .addDeserializer(Persona.class, new PersonaDeserializer())
                    .addDeserializer(Permiso.class, new PermisoDeserializer())
                    .addDeserializer(Usuario.class, new UsuarioDeserializer())
                    .addDeserializer(Municipio.class, new MunicipioDeserializer())
                    .addDeserializer(Animal_has_Caso.class, new Animal_has_CasoDeserializer())
                    .addDeserializer(Caso.class, new CasoDeserializer())
                    .addDeserializer(Novedades.class, new NovedadesDeserializer())
                    .addDeserializer(RegistroVacunacion.class, new RegistroVacunacionDeserializer())
                    .addDeserializer(RegistroVacunacion_has_Animal.class, new RegistroVacunacion_has_AnimalDeserializer())
                    .addDeserializer(Vacunacion.class, new VacunacionDeserializer());
        mapper.registerModule(modul);
        if (modules != null) {
            for (SimpleModule module : modules) {
                mapper.registerModule(module);
            }
        }

        try {
            System.out.println("enlace : " + request.urlString()
                    + (json != null && !json.isEmpty()
                            ? "\n body :" + json : ""));
            Response response = client.newCall(request).execute();

            ultimoJson = response.body().string();
            if (colleccion != null) {
                return (T) mapperToList(ultimoJson, mappe);
            } else if (map != null) {
                return (T) mapperToMap(ultimoJson, map, key, mappe);
            } else {
                if (mappe.equals(String.class)) {
                    return (T) ultimoJson;
                }
                return mapper.readValue(ultimoJson, mappe);
            }
        } catch (IOException ex) {
            if ((ex instanceof JsonParseException) || (ex instanceof EOFException)) {

                if ((ultimoJson.contains(EXCEPTION_REPORT) || ultimoJson.contains(ERROR_REPORT) | ultimoJson.contains(INFORME_ERROR))
                        && ultimoJson.contains(MESSAGE) && ultimoJson.contains(DESCRIPTION)) {
                    String title = ultimoJson.substring(ultimoJson.indexOf("<h1>") + 4, ultimoJson.indexOf("</h1>"));
                    String[] tags = ultimoJson.split("<p>");
                    String msj = tags[2].substring(tags[2].indexOf("<u>") + 3, tags[2].indexOf("</u>"));
                    String desb = tags[3].substring(tags[3].indexOf("<u>") + 3, tags[3].indexOf("</u>"));

                    Logger.getLogger(EjecutorJson.class.getName()).log(Level.INFO,
                            "Titulo : {0}\nMensaje : {1}\nDescripción : {2}",
                            new String[]{title, msj, desb});
                    throw new RuntimeException();
                }
                Logger.getLogger(EjecutorJson.class.getName()).log(Level.WARNING,
                        "No se pudo castear el siguiente jSon : \n {0}", ultimoJson);

            } else if (ex instanceof SocketTimeoutException) {
                Logger.getLogger(EjecutorJson.class.getName()).log(Level.WARNING,
                        "Se agoto el tiempo de respuesta");

                throw new SocketTimeoutException("Se agoto el tiempo de respuesta");
            } else if (ex instanceof ConnectException) {
                Logger.getLogger(EjecutorJson.class.getName()).log(Level.WARNING,
                        "No se pudo conectar al servidor");
                throw new ConnectException("No se pudo conectar al servidor");

            } else {
                Logger.getLogger(EjecutorJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private <K, V> Map<K, V> mapperToMap(String json, Class<? extends Map> mapClass, Class<K> claseKey, Class<V> valueClass) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructMapType(mapClass, claseKey, valueClass));
        } catch (IOException ex) {
        }
        return new HashMap<>();
    }

    private <T> List<T> mapperToList(String json, Class<T> mappe) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(Collection.class, mappe));
        } catch (IOException ex) {
        }
        return new ArrayList<>();
    }

    private void prepararUri(String path) throws URISyntaxException {
        this.path = path;
        String enlace = this.propiedadesDeConexion.getProperty("scheme") + "://"
                + this.propiedadesDeConexion.getProperty("host") + ":"
                + this.propiedadesDeConexion.getProperty("puerto") + "/"
                + this.propiedadesDeConexion.getProperty("fromPath") + "/" + path;
        uri = new URI(enlace);
    }

    private void prepararParam() {
        if (param != null) {
            URIBuilder builder = new URIBuilder(uri);

            for (Map.Entry entry : param.entrySet()) {
                builder.addParameter(entry.getKey().toString(), entry.getValue().toString());
            }

            try {
                this.uri = builder.build();
            } catch (URISyntaxException ex) {
                Logger.getLogger(EjecutorJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Request prepararRequest() {
        Request.Builder build;

        if ((json == null || json.isEmpty()) && metodosDeEnvio.equals(MetodosDeEnvio.POST) && param != null) {
            json = "recibir datos por query param";
        }

        if (json != null && !json.equals("")) {
            build = new Request.Builder().
                    url(uri.toString());
            RequestBody body = RequestBody.create(com.squareup.okhttp.MediaType.parse(MediaType.APPLICATION_JSON), json);
            build.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            switch (metodosDeEnvio) {
                case POST:
                    prepararParam();
                    build.url(uri.toString())
                            .post(body);
                    break;
                case GET:
                    build.method(metodosDeEnvio.getMetodo(), body);
                    break;
                case PUT:
                    prepararParam();
                    build.url(uri.toString())
                            .put(body);
                    break;
                case DELETE:
                    prepararParam();
                    build.url(uri.toString())
                            .delete(body);
                    break;
            }
        } else {
            prepararParam();
            build = new Request.Builder().
                    url(uri.toString());
            build.method(metodosDeEnvio.getMetodo(), null);

        }
        return build.build();
    }

    private void defaultProperty() {

        try {
            propiedadesDeConexion = new Properties();
            /**
             * Cargamos el archivo desde la ruta especificada
             */
            propiedadesDeConexion
                    .load(EjecutorJson.class.getResourceAsStream("Config.properties"));

        } catch (FileNotFoundException e) {
            Logger.getLogger(EjecutorJson.class.getName()).log(Level.WARNING, "El archivo de configuracion no existe", e);
        } catch (IOException e) {
            Logger.getLogger(EjecutorJson.class.getName()).log(Level.WARNING, "No se puede leer el archivo", e);
        }

    }

    //GETEER AND SETTER
    public String getPath() {
        return path;
    }

    public EjecutorJson setPath(String path) {
        try {
            prepararUri(path);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EjecutorJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public EjecutorJson setClient(OkHttpClient client) {
        this.client = client;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public EjecutorJson setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public EjecutorJson setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
        return this;

    }

    public String getUltimoJson() {
        return ultimoJson;
    }

    public EjecutorJson setUltimoJson(String ultimoJson) {
        this.ultimoJson = ultimoJson;
        return this;
    }

    public String getJson() {
        return json;
    }

    public EjecutorJson setJson(String json) {
        this.json = json;
        return this;
    }

    public MetodosDeEnvio getMetodosDeEnvio() {
        return metodosDeEnvio;
    }

    public EjecutorJson setMetodo(MetodosDeEnvio metodo) {
        this.metodosDeEnvio = metodo;
        return this;
    }

}
