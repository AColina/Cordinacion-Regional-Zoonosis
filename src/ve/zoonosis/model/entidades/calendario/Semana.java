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

import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.administracion.Parroquia;

/**
 *
 * @author clases
 */
public class Semana extends Entidad {

    private String semana;
    private int year;
    private List<Parroquia> parroquiaAsociada;

    public Semana() {
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Parroquia> getParroquiaAsociada() {
        return parroquiaAsociada;
    }

    public void setParroquiaAsociada(List<Parroquia> parroquiaAsociada) {
        this.parroquiaAsociada = parroquiaAsociada;
    }

}
