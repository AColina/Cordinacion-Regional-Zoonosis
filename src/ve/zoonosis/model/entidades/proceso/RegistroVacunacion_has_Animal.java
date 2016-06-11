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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import ve.zoonosis.model.entidades.EntidadGlobal;
import ve.zoonosis.model.entidades.funcionales.Animal;

/**
 *
 * @author clases
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RegistroVacunacion_has_Animal implements EntidadGlobal {

    @JsonBackReference("RegistroVacunacion_has_Animal")
    private RegistroVacunacion registroVacunacion;
    @NotNull(message = "Debe seleccionar un animal")
    private Animal animal;
    @NotNull(message = "Debe ingresar una cantidad")
    @Min(value = 0, message = "El valor minimo requrido es 0")
    @Max(value = 100, message = "El valor maximo requerido es 100")
    private Integer cantidad;

    public RegistroVacunacion_has_Animal() {
    }

    public RegistroVacunacion getRegistroVacunacion() {
        return registroVacunacion;
    }

    public void setRegistroVacunacion(RegistroVacunacion registroVacunacion) {
        this.registroVacunacion = registroVacunacion;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
