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

import java.util.Date;
import java.util.List;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.model.entidades.administracion.Parroquia;

/**
 *
 * @author clases
 */
public class Caso extends Entidad {

    private Date fechaElaboracion;
    private Parroquia parroquia;
    private List<Animal_has_Caso> animal_has_Caso;

    public Caso() {
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
    }

    public List<Animal_has_Caso> getAnimal_has_Caso() {
        return animal_has_Caso;
    }

    public void setAnimal_has_Caso(List<Animal_has_Caso> animal_has_Caso) {
        this.animal_has_Caso = animal_has_Caso;
    }

}
