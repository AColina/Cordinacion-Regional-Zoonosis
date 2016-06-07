/*
 * Copyright 2016 angel.colina.
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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.administracion.Usuario;

/**
 *
 * @author angel.colina
 */
public class RegistroVacunacion extends Entidad {

    @JsonBackReference("registro_Vacunacion")
    private Vacunacion vacunacion;
    private Usuario usuario;
    @JsonManagedReference("RegistroVacunacion_has_Animal")
    private List<RegistroVacunacion_has_Animal> vacunacion_has_Animal;

    public RegistroVacunacion() {
    }

    public Vacunacion getVacunacion() {
        return vacunacion;
    }

    public void setVacunacion(Vacunacion vacunacion) {
        this.vacunacion = vacunacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<RegistroVacunacion_has_Animal> getVacunacion_has_Animal() {
        if (vacunacion_has_Animal == null) {
            vacunacion_has_Animal = new ArrayList<>();
        }
        return vacunacion_has_Animal;
    }

    public void setVacunacion_has_Animal(List<RegistroVacunacion_has_Animal> vacunacion_has_Animal) {
        this.vacunacion_has_Animal = vacunacion_has_Animal;
    }

}
