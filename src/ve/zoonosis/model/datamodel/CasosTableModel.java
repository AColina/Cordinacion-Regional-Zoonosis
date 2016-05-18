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
package ve.zoonosis.model.datamodel;

import com.megagroup.model.builder.AbstractLazyDataModel;
import java.util.List;
import ve.zoonosis.model.entidades.proceso.Caso;

/**
 *
 * @author angel.colina
 */
public class CasosTableModel extends AbstractLazyDataModel<Caso> {

    @Override
    public String columnValue(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "{fechaElaboracion}";
            case 1:
                return "{parroquia.nombre}";
            case 2:
                return "{parroquia.municipio.nombre}";
            default:
                throw new UnsupportedOperationException("El índice: " + columnIndex + " aún no se ha programado.");
        }
    }

    @Override
    public void nombreColumnas(List<String> list) {
        list.add("Fecha de elaboracioón");
        list.add("Parroquia");
        list.add("Municipio");
        list.add("Opciones");
    }

    @Override
    public void crearSubLista(int posicionInical) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
