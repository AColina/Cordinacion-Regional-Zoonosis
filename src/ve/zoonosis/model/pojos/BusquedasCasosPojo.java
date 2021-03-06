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
package ve.zoonosis.model.pojos;

import java.util.ArrayList;
import java.util.List;
import ve.zoonosis.model.entidades.proceso.Animal_has_Caso;

/**
 *
 * @author angel.colina
 */
public class BusquedasCasosPojo {

    private Long cantidad;
    private List<Animal_has_Caso> resultados;

    public BusquedasCasosPojo() {
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public List<Animal_has_Caso> getResultados() {
        if (resultados == null) {
            resultados = new ArrayList<>();
        }
        return resultados;
    }

}
