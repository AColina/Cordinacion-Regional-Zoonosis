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
package ve.zoonosis.model.entidades.funcionales;

import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Vacunacion_has_Animal;

/**
 *
 * @author clases
 */
public class Animal extends Entidad {

    private String nombre;
    private Especie especie;
    private List<Vacunacion_has_Animal> vacunacion_has_Animal;

    public Animal(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public List<Vacunacion_has_Animal> getVacunacion_has_Animal() {
        return vacunacion_has_Animal;
    }

    public void setVacunacion_has_Animal(List<Vacunacion_has_Animal> vacunacion_has_Animal) {
        this.vacunacion_has_Animal = vacunacion_has_Animal;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
