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
public class Caso extends Entidad {

    private Date fechaElaboracion;
    @NotNull
    private Parroquia parroquia;
    private Semana semana;

    private List<Animal_has_Caso> animal_has_Caso;

    public Caso() {
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

    public List<Animal_has_Caso> getAnimal_has_Caso() {
        if (animal_has_Caso == null) {
            animal_has_Caso = new ArrayList<>();
        }
        return animal_has_Caso;
    }

    public void setAnimal_has_Caso(List<Animal_has_Caso> animal_has_Caso) {
        this.animal_has_Caso = animal_has_Caso;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

}
