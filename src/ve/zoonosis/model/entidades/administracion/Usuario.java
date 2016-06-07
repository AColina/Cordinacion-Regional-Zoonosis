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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion;

/**
 *
 * @author clases
 */
public class Usuario extends Entidad {

    @NotNull
    private String nombre;
    @NotNull
    @Pattern(regexp = "")
    private String contrasena;
    @NotNull
    private Date fechaNacimiento;

    @NotNull
    private Persona persona;
    @JsonIgnore
    private List<Novedades> novedades;
    @NotNull
    private Permiso permiso;
    @JsonIgnore
    private List<RegistroVacunacion> registroVacunacion;

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Novedades> getNovedades() {
        if (novedades == null) {
            novedades = new ArrayList<>();
        }
        return novedades;
    }

    public void setNovedades(List<Novedades> novedades) {
        this.novedades = novedades;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public List<RegistroVacunacion> getRegistroVacunacion() {
        if (registroVacunacion == null) {
            registroVacunacion = new ArrayList<>();
        }
        return registroVacunacion;
    }

    public void setRegistroVacunacion(List<RegistroVacunacion> registroVacunacion) {
        this.registroVacunacion = registroVacunacion;
    }

    @Override
    public String toString() {
        return persona.toString();
    }
}
