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
package ve.zoonosis.controller.modulos.novdedades;

import com.megagroup.componentes.MDataTable;
import com.megagroup.model.builder.LazyColumnListenerModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import ve.zoonosis.controller.modulos.casos.BandejaCasosController;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.datamodel.NovedadesTableModel;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.model.listener.FechaListener;
import ve.zoonosis.vistas.modulos.novedades.BandejaNovedades;
import windows.RequestBuilder;

/**
 *
 * @author angel.colina
 */
public class BandejaNovedadesController extends BandejaNovedades<Novedades> {

    private CrearNovedadController novedadController;

    public BandejaNovedadesController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniciarBandeja(false);
        FechaListener.createListener(desde, hasta, buscar);
        buscar.addActionListener(new BuscarLstener());
        nuevo.setVisible(LoginController.getUsuario() != null);
        bandeja.setColumnListenerModel(LazyColumnListenerModel.class);
        botonVer = 4;
        nuevo.addActionListener(new CrearNovedad());
        desde.setMaxSelectableDate(new Date());
        hasta.setMaxSelectableDate(new Date());
        try {
            String municipios = new RequestBuilder("services/proceso/NovedadesWs/LastDate.php")
                    .ejecutarJsonToString();
            if (StringUtils.isNotEmpty(municipios)) {
                Date min = new SimpleDateFormat("dd/MM/yyyy").parse(municipios);
                hasta.setMinSelectableDate(min);
                desde.setMinSelectableDate(min);
            }
        } catch (URISyntaxException | RuntimeException | ParseException ex) {
            Logger.getLogger(BandejaCasosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bandeja.setModel(new NovedadesTableModel());
    }

    @Override
    protected void addItemPopUp(int index) {

    }

    @Override
    public MDataTable getBandeja() {
        return bandeja;
    }

    @Override
    public void abrir(int index) {
        if (LoginController.getUsuario() != null) {
            novedadController = new CrearNovedadController(BandejaNovedadesController.this,
                    bandeja.getModel().getValueAt(index));
        }
    }

    @Override
    public void buscar() {
        NovedadesTableModel model = new NovedadesTableModel();
        model.setDesde(desde.getDate());
        model.setHasta(hasta.getDate());
        model.setNombre(nombre.getText());
        bandeja.setModel(model);
    }

    @Override
    public Object getSelectItem() {
        return null;
    }

    // ActionListener de jmenupopup
    private class CrearNovedad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            novedadController = new CrearNovedadController(BandejaNovedadesController.this);
        }

    }
}
