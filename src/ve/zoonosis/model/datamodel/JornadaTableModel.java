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
import java.util.Date;
import java.util.List;
import ve.zoonosis.model.entidades.proceso.Vacunacion;

/**
 *
 * @author angel.colina
 */
public class JornadaTableModel extends AbstractLazyDataModel<Vacunacion> {

    private Long semana;
    private Date desde;
    private Date hasta;
    private Long idMunicipio;
    private Long idParroquia;

    public JornadaTableModel() {
    }

    public JornadaTableModel(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public JornadaTableModel(Long semana, Date desde, Date hasta, Long idMunicipio, Long idParroquia) {
        this.semana = semana;
        this.desde = desde;
        this.hasta = hasta;
        this.idMunicipio = idMunicipio;
        this.idParroquia = idParroquia;
    }

    @Override
    public void nombreColumnas(List<String> nombres) {
        nombres.add("Semana");
        nombres.add("Dia");
        nombres.add("Municipio");
        nombres.add("Parroquia");
        nombres.add("Usuario");
        nombres.add("Opciones");

    }

    @Override
    public String columnValue(int columnIndex) {
        return " ";
    }

    @Override
    public void crearSubLista(int posicionInical) {

    }

}
