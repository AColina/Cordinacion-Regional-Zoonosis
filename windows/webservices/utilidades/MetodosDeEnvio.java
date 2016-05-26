/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.webservices.utilidades;

/**
 *
 * @author angel.colina
 */
public enum MetodosDeEnvio {

    /**
     * Metodo put
     */
    PUT("PUT"),

    /**
     * Metodo Post
     */
    POST("POST"),

    /**
     * Metodo get
     */
    GET("GET"),

    /**
     *Metodo Delete
     */
    DELETE("DELETE");
    

    protected final String metodo;

    private MetodosDeEnvio(String metodo) {
        this.metodo = metodo;
    }

    public String getMetodo() {
        return metodo;
    }
}
