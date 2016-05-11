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

import java.util.Date;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Novedades;

/**
 *
 * @author clases
 */
public class Usuario extends Entidad {

    private String usuario;
    private String contrasena;
    private Date fechaNacimiento;

    private Persona persona;
    private List<Novedades> novedades;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
