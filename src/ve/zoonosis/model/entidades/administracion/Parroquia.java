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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.entidades.proceso.Vacunacion;

/**
 *
 * @author clases
 */
public class Parroquia extends Entidad {

    private String nombre;
    @JsonBackReference("municipio-parroquia")
    private Municipio municipio;
    @JsonIgnore
    private List<Cliente> clientes;
    @JsonIgnore
    private List<Vacunacion> vacunaciones;
    @JsonIgnore
    private List<Caso> casos;

    public Parroquia() {
    }

    public Parroquia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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
