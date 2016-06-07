/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this TEMPLATE file, choose Tools | Templates
 * and open the TEMPLATE in the editor.
 */
package windows;

import com.megagroup.Application;
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.Logger;
import java.awt.Cursor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import ve.zoonosis.vistas.Index;
import windows.webservices.utilidades.EjecutorJson;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class RequestBuilder extends EjecutorJson {

    private final Index index;

    {
        index = Application.getAPLICATION_FRAME();
    }
    private final Logger LOG = Logger.getLogger(RequestBuilder.class);

    /**
     * Crea una nueva instancia de ejecutor json, sin url y sin parametros.
     * <br>
     * Metodo por defecto GET
     */
    public RequestBuilder() {
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
    public RequestBuilder(URI uri) {
        super(uri);
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
    public RequestBuilder(URI uri, HashMap<String, Object> param) {
        super(uri, param);
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
    public RequestBuilder(URI uri, HashMap<String, Object> param, MetodosDeEnvio metodosDeEnvio) {
        super(uri, param, metodosDeEnvio);
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
    public RequestBuilder(String path) throws URISyntaxException {
        super(path);

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
    public RequestBuilder(String path, HashMap<String, Object> param) throws URISyntaxException {
        super(path, param);
    }

    @Override
    public RequestBuilder crearJson(Object objeto) {
        super.crearJson(objeto);
        return this;
    }

    @Override
    public RequestBuilder setMetodo(MetodosDeEnvio metodo) {
        super.setMetodo(metodo); //To change body of generated methods, choose Tools | Templates.
        return this;
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
    public RequestBuilder(String path, HashMap<String, Object> param, MetodosDeEnvio metodosDeEnvio) throws URISyntaxException {
        super(path, param, metodosDeEnvio);
    }

    /**
     * Envia el request al servicio web y no recibe respuesta
     */
    @Override
    public void ejecutarJson() {
        index.setCursor(Recursos.WAIT_CURSOR);
        try {
            super.ejecutarJson();
        } catch (Exception ex) {
            alerta(ex);
            LOG.LOGGER.log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } finally {
            index.setCursor(Recursos.DEFAULT_CURSOR);
        }

    }

    /**
     * Envia el request al servicio web y recibe la respuesta como string.
     *
     * @return String con la respuesta del servicio web
     */
    @Override
    public String ejecutarJsonToString() {
        index.setCursor(Recursos.WAIT_CURSOR);
        try {
            return super.ejecutarJson(String.class);
        } catch (Exception ex) {
            alerta(ex);
            LOG.LOGGER.log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } finally {
            index.setCursor(Recursos.DEFAULT_CURSOR);
        }

    }

    /**
     * Envia el request al servicio web y no recibe respuesta
     *
     * @param <T> Clase Generica
     * @param entidad
     * @return
     */
    @Override
    public <T> T ejecutarJson(Class<T> entidad) {
        index.setCursor(Recursos.WAIT_CURSOR);
        try {
            return super.ejecutarJson(entidad);
        } catch (Exception ex) {
            alerta(ex);
            LOG.LOGGER.log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } finally {
            index.setCursor(Recursos.DEFAULT_CURSOR);
        }

    }

    /**
     *
     * @param <T> Clase Generica
     * @param colleccion
     * @param entidad
     * @return
     */
    @Override
    public <T> List<T> ejecutarJson(Class<? extends List> colleccion, Class<T> entidad) {
        index.setCursor(Recursos.WAIT_CURSOR);
        try {
            return super.ejecutarJson(colleccion, entidad);
        } catch (Exception ex) {
            alerta(ex);
            LOG.LOGGER.log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } finally {
            index.setCursor(Recursos.DEFAULT_CURSOR);
        }
    }

    @Override
    public <K, V> Map<K, V> ejecutarJson(Class<? extends Map> mapClass, Class<K> classKey, Class<V> classValue) {
        index.setCursor(Recursos.WAIT_CURSOR);
        try {
            return super.ejecutarJson(mapClass, classKey, classValue);
        } catch (Exception ex) {
            alerta(ex);
            LOG.LOGGER.log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        } finally {
            index.setCursor(Recursos.DEFAULT_CURSOR);
        }

    }

    private void alerta(Exception exception) {
        alerta(exception.getMessage());
    }

    private void alerta(String msj) {
        MGrowl.showGrowl(MGrowlState.ERROR, msj);
        index.setCursor(Cursor.getDefaultCursor());
    }

}
