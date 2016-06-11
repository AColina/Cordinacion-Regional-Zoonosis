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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;

/**
 *
 * @author clases
 */
public class Municipio extends Entidad {

    private String nombre;
    @JsonManagedReference("municipio-parroquia")
    @JsonProperty("parroquias")
    private List<Parroquia> parroquiasAsociadas;

    public Municipio() {
    }

    public Municipio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Parroquia> getParroquiasAsociadas() {
        if (parroquiasAsociadas == null) {
            parroquiasAsociadas = new ArrayList<>();
        }
        return parroquiasAsociadas;
    }

    public void setParroquiasAsociadas(List<Parroquia> parroquiasAsociadas) {
        this.parroquiasAsociadas = parroquiasAsociadas;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
