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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.calendario.Semana;

/**
 *
 * @author clases
 */
public class Vacunacion extends Entidad {

    private Date fechaElaboracion;
    private Semana semana;
    @NotNull(message = "Debe seleccionar una parroquia")
    private Parroquia parroquia;
    @JsonManagedReference("registro_Vacunacion")
    private List<RegistroVacunacion> registroVacunacion;

    public Vacunacion() {
    }

    public Vacunacion(Date fechaElaboracion, Semana semana) {
        this.fechaElaboracion = fechaElaboracion;
        this.semana = semana;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
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

}
