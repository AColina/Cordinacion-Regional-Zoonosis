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
package ve.zoonosis.model.entidades.administracion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ve.zoonosis.model.entidades.Entidad;

/**
 *
 * @author angel.colina
 */
public class Persona extends Entidad {

    @NotNull(message = "Debe ingresar el nombre")
    @Size(min = 2, max = 45, message = "El nombre debe poseer minimo 2\n caracteres y maximo 45")
    private String nombre;
    @NotNull(message = "Debe ingresar el apelldio")
    @Size(min = 2, max = 45, message = "El apellido debe poseer minimo 2\n caracteres y maximo 45")
    private String apellido;
    @NotNull
    @Pattern(regexp = "^[A-Z+]-[\\d+]{7,12}$", message = "Formato de cedula no valido")
    private String cedula;
    @JsonIgnore
    private Cliente cliente;
    @JsonIgnore
    private Usuario usuario;

    public Persona() {
    }

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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
        return nombre + " " + apellido;
    }

}
