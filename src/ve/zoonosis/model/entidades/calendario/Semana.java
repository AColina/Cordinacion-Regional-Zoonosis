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
package ve.zoonosis.model.entidades.calendario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.entidades.proceso.Vacunacion;

/**
 *
 * @author angel.colina
 */
public class Semana extends Entidad {

    private String nombre;
    private Integer year;
    @JsonIgnore
    private List<Vacunacion> vacunaciones;
    @JsonIgnore
    private List<Caso> casos;

    public Semana() {
    }

    public Semana(String nombre, Integer year) {
        this.nombre = nombre;
        this.year = year;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Vacunacion> getVacunaciones() {
        if (vacunaciones == null) {
            vacunaciones = new ArrayList<>();
        }
        return vacunaciones;
    }

    public void setVacunaciones(List<Vacunacion> vacunaciones) {
        this.vacunaciones = vacunaciones;
    }

    public List<Caso> getCasos() {
        if (casos == null) {
            casos = new ArrayList<>();
        }
        return casos;
    }

    public void setCasos(List<Caso> casos) {
        this.casos = casos;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
