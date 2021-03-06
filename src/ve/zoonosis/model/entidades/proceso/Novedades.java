/*
 * Copyright 2016 clases.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ve.zoonosis.model.entidades.proceso;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.administracion.Usuario;
import windows.webservices.JsonSerializer.JsonDateSerializer;

/**
 *
 * @author Angel Colina
 */
public class Novedades extends Entidad {
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date fechaElaboracion;
    @NotNull
    @Size(min = 5, max = 45)
    private String nombre;
    @NotNull
    @Size(min = 5)
    private String descripcion;
    @NotNull
    private Cliente cliente;
    private Usuario usuario;

    public Novedades() {
    }

    public Novedades(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
