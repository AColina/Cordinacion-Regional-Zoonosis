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

import ve.zoonosis.model.entidades.funcionales.Animal;

/**
 *
 * @author clases
 */
public class Animal_has_Caso {

    private Caso caso;
    private Animal animal;
    private int cantidadIngresado;
    private int cantidadPositivo;

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

    public int getCantidadIngresado() {
        return cantidadIngresado;
    }

    public void setCantidadIngresado(int cantidadIngresado) {
        this.cantidadIngresado = cantidadIngresado;
    }

    public int getCantidadPositivo() {
        return cantidadPositivo;
    }

    public void setCantidadPositivo(int cantidadPositivo) {
        this.cantidadPositivo = cantidadPositivo;
    }

}
