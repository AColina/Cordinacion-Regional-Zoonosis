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
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Novedades;

/**
 *
 * @author clases
 */
public class Cliente extends Entidad {

    @NotNull
    @Pattern(regexp = "")
    private String correo;
    private String telefono;
    @NotNull
    @Size(min = 10,max = 255)
    private String direccion;
    @NotNull
    private Persona persona;
    @NotNull
    private Parroquia parroquia;
    @JsonIgnore
    private List<Novedades> novedades;

    public Cliente() {
    }

    public Cliente(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
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

    @Override
    public String toString() {
        return persona.toString();
    }

}
