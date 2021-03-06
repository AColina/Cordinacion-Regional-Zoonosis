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
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.entidades.proceso.Animal_has_Caso;
import ve.zoonosis.model.pojos.BusquedasCasosPojo;
import windows.RequestBuilder;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class CasosTableModel extends AbstractLazyDataModel<Animal_has_Caso> {

    private static final Logger LOG = Logger.getLogger(CasosTableModel.class.getName());
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private HashMap<Long, Parroquia> parroquias;
    private RequestBuilder builder;
    private Semana semana;
    private Parroquia parroquia;
    private Municipio municipio;
    private Date desde;
    private Date hasta;

    public CasosTableModel() {
    }

    public CasosTableModel(Semana semana) {
        this.semana = semana;
    }

    @Override
    public String columnValue(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "{caso.semana.nombre}";
            case 1:
                return "{caso.fechaElaboracion}";
            case 2:
                return "{caso.parroquia.nombre}";
            case 3:
                return "{caso.parroquia.municipio.nombre}";
            case 4:
                return "{animal.nombre}";
            case 5:
                return "{cantidadIngresado}";
            case 6:
                return "{cantidadPositivos}";
            case 7:
                return "Ver";
            default:
                throw new UnsupportedOperationException("El índice: " + columnIndex + " aún no se ha programado.");
        }
    }

    @Override
    public void nombreColumnas(List<String> list) {
        list.add("Semana");
        list.add("Dia");
        list.add("Parroquia");
        list.add("Municipio");
        list.add("Animal");
        list.add("Cantidad Ingresado");
        list.add("Cantidad Positivos");
        list.add("Opciones");
    }

    @Override
    public IRegistrar crearSubLista(int posicionInical) {
        try {
            parroquias = new HashMap<>();
            HashMap map = new HashMap();
            if (semana != null) {
                map.put("idSemana", semana.getId());
            }
            if (municipio != null) {
                map.put("idMunicipio", municipio.getId());
            }
            if (parroquia != null) {
                map.put("idParroquia", parroquia.getId());
            }
            if (desde != null) {
                map.put("desde", format.format(desde));
            }
            if (hasta != null) {
                map.put("hasta", format.format(hasta));
            }
            map.put("cantidad", (int) paginacion);
            map.put("inicial", posicionInical);

            builder = new RequestBuilder("services/proceso/CasoWs/BandejaCasos.php", map);
            final BusquedasCasosPojo pojo = builder.ejecutarJson(BusquedasCasosPojo.class);
            if (pojo != null) {
                for (Animal_has_Caso resultado : pojo.getResultados()) {
                    final Parroquia p = resultado.getCaso().getParroquia();
                    try {
                        Parroquia pp = parroquias.get(p.getId());
                        if (pp == null) {
                            builder = new RequestBuilder("services/administracion/MunicipioWs/BuscarMunicipioPorParroquia.php",
                                    new HashMap<String, Object>() {
                                        {
                                            put("idParroquia", p.getId());
                                        }
                                    }, MetodosDeEnvio.GET);

                            Municipio municipios = builder.ejecutarJson(Municipio.class);
                            p.setMunicipio(municipios);
                            parroquias.put(p.getId(), p);
                        } else {
                            p.setMunicipio(pp.getMunicipio());
                        }
                    } catch (URISyntaxException | RuntimeException ex) {
                        LOG.LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                return new IRegistrar() {

                    @Override
                    public List resultados() {
                        return pojo.getResultados();
                    }

                    @Override
                    public Long numeroRegistros() {
                        return pojo.getCantidad();
                    }
                };
            }

        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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
