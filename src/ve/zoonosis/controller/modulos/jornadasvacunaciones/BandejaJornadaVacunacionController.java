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
package ve.zoonosis.controller.modulos.jornadasvacunaciones;

import com.megagroup.componentes.MDataTable;
import com.megagroup.model.builder.LazyColumnListenerModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import org.joda.time.DateTime;
import ve.zoonosis.controller.modulos.casos.BandejaCasosController;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.datamodel.JornadaTableModel;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.entidades.proceso.Vacunacion;
import ve.zoonosis.model.listener.FechaListener;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.jornadasvacunaciones.BandejaJornadaVacunacion;
import windows.RequestBuilder;

/**
 *
 * @author angel.colina
 */
public class BandejaJornadaVacunacionController extends BandejaJornadaVacunacion<Vacunacion> {

    private NuevaJornadaController jornadaController;

    public BandejaJornadaVacunacionController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniciarBandeja(false);
        buscar.addActionListener(new BuscarLstener());
        nuevo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                abrir(-1);
            }
        });
        FechaListener.createListener(desde, hasta, buscar);
        nuevo.setVisible(LoginController.getUsuario() != null);
        botonVer = 4;
        bandeja.setColumnListenerModel(LazyColumnListenerModel.class);

        try {
            List<Semana> s = new RequestBuilder("services/calendario/WeeksForYear.php",
                    new HashMap<String, Object>() {
                        {
                            put("year", new DateTime().getYear());
                        }

                    }).ejecutarJson(List.class, Semana.class);
            if (s != null) {
                s.add(0, null);
                semana.setModel(new DefaultComboBoxModel(s.toArray()));
                semana.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            Logger.getLogger(BandejaCasosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            List<Municipio> municipios = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php")
                    .ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipios.add(0, null);
                 municipio.setModel(new ListComboBoxModel<>(municipios));
                municipio.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            Logger.getLogger(BandejaCasosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        municipio.addActionListener(new MunicipioListener(parroquia));
        hasta.setMaxSelectableDate(new Date());
        bandeja.setModel(new JornadaTableModel());
    }

    @Override
    protected void addItemPopUp(int index) {
        JMenuItem nuevo = new JMenuItem("Nuevo");

        popupMenu.add(nuevo);
        if (index > -1) {
            JMenuItem ver = new JMenuItem("Ver");

            popupMenu.add(ver);
        }
    }

    @Override
    public MDataTable getBandeja() {
        return bandeja;
    }

    @Override
    public void abrir(int index) {
        Vacunacion entidad = null;
        if (index > -1) {
            entidad = bandeja.getModel().getValueAt(index);
        }
        jornadaController = new NuevaJornadaController(this, entidad);
    }

    @Override
    public void buscar() {

        JornadaTableModel model = new JornadaTableModel();
        model.setDesde(desde.getDate());
        model.setHasta(hasta.getDate());
        model.setSemana((Semana) semana.getSelectedItem());
        model.setParroquia((Parroquia) parroquia.getSelectedItem());
        model.setMunicipio((Municipio) municipio.getSelectedItem());
        bandeja.setModel(model);
    }

    @Override
    public Object getSelectItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
