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
import com.megagroup.utilidades.Logger;
import com.megagroup.utilidades.StringUtils;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.model.pojos.BusquedasNovedadesPojo;
import windows.RequestBuilder;

/**
 *
 * @author angel.colina
 */
public class NovedadesTableModel extends AbstractLazyDataModel<Novedades> {

    private static final Logger LOG = Logger.getLogger(NovedadesTableModel.class);

    private RequestBuilder builder;
    private String nombre;
    private Date desde;
    private Date hasta;

    public NovedadesTableModel() {
    }

    public NovedadesTableModel(String nombre, Date desde, Date hasta) {
        this.nombre = nombre;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public String columnValue(int i) {
        switch (i) {
            case 0:
                return "{fechaElaboracion}";
            case 1:
                return "{nombre}";
            case 2:
                return "{descripcion}";
            case 3:
                return "{cliente.persona.nombre}";
            case 4:
                return "Opciones";
            default:
                throw new UnsupportedOperationException("El índice: " + i + " aún no se ha programado.");
        }
    }

    @Override
    public void nombreColumnas(List<String> list) {
        list.add("Fecha Elaboración");
        list.add("Nombre");
        list.add("Descripción");
        list.add("Cliente");
        list.add("Opciones");
    }

    @Override
    public void crearSubLista(int posicionInical) {
        try {

            HashMap map = new HashMap();
            if (!StringUtils.isEmpty(nombre)) {
                map.put("nombre", nombre);
            }
            if (desde != null) {
                map.put("desde", desde);
            }
            if (hasta != null) {
                map.put("hasta", hasta);
            }
            map.put("cantidad", (int) paginacion);
            map.put("inicial", posicionInical);

            builder = new RequestBuilder("services/proceso/NovedadesWs/BandejaNovedades.php", map);
            BusquedasNovedadesPojo pojo = builder.ejecutarJson(BusquedasNovedadesPojo.class);
            if (pojo != null) {
                setResultados(pojo.getResultados());
                numeroPaginas = Math.ceil(numeroRegistros / paginacion);
            }

        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
