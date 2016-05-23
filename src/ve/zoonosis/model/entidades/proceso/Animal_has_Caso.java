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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import ve.zoonosis.model.entidades.EntidadGlobal;
import ve.zoonosis.model.entidades.funcionales.Animal;

/**
 *
 * @author clases
 */
public class Animal_has_Caso implements EntidadGlobal {

    private Caso caso;
    @NotNull
    private Animal animal;
    @NotNull
    @Min(0)
    @Max(100)
    @DefaultValue("0")
    private Integer cantidadIngresado;
    @NotNull
    @Min(0)
    @Max(100)
    @DefaultValue("0")
    private Integer cantidadPositivo;

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

    public Integer getCantidadPositivo() {
        return cantidadPositivo;
    }

    public void setCantidadPositivo(Integer cantidadPositivo) {
        this.cantidadPositivo = cantidadPositivo;
    }

}
