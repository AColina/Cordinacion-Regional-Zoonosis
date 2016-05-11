/*
 * mGestión, un producto de MegaGroup
 * Desarrollador: Víctor A. Mars M.
 */
package windows.webservices.pojo;

import java.io.Serializable;

/**
 *
 * @author Víctor Mars
 */
public class ResultadoEjecucion implements Serializable {

    private Boolean exitoso;
    private String descripcion;

    public ResultadoEjecucion() {
    }

    public ResultadoEjecucion(Boolean exitoso) {
        this.exitoso = exitoso;
    }

    public ResultadoEjecucion(Boolean exitoso, String descripcion) {
        this.exitoso = exitoso;
        this.descripcion = descripcion;
    }

    public Boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

}
