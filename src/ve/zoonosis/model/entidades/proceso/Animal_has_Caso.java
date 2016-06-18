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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.funcionales.Animal;

/**
 *
 * @author angel.colina
 */
public class Animal_has_Caso extends Entidad{

    @JsonBackReference("caso_has_animal")
    private Caso caso;
    @NotNull(message = "Debe seleccionar un animal")
    private Animal animal;
    @NotNull(message = "Debe ingresar una cantidad")
    @Min(value = 0, message = "El valor minimo requrido es 1")
    @Max(value=100,message = "El valor maximo requerido es 100")
    @DefaultValue("0")
    private Integer cantidadIngresado;
    @NotNull(message = "Debe ingresar una cantidad")
    @Min(value = 0, message = "El valor minimo requrido es 0")
    @Max(value=100,message = "El valor maximo requerido es 100")
    @DefaultValue("0")
    private Integer cantidadPositivos;

    public Animal_has_Caso() {
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Integer getCantidadIngresado() {
        return cantidadIngresado;
    }

    public void setCantidadIngresado(Integer cantidadIngresado) {
        this.cantidadIngresado = cantidadIngresado;
    }

    public Integer getCantidadPositivos() {
        return cantidadPositivos;
    }

    public void setCantidadPositivos(Integer cantidadPositivos) {
        this.cantidadPositivos = cantidadPositivos;
    }

}
