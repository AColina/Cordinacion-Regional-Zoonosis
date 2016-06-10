/*
 * Copyright 2016 Kelvin López.
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
package ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio;

import com.megagroup.utilidades.Logger;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import ve.zoonosis.controller.modulos.casos.NuevoCasoController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.vistas.modulos.estadistica.jornada.animales.municipio.JornadaAnimalSemanaPorMunicipio;
import windows.RequestBuilder;

/**
 *
 * @author Kelvin López
 */
public class JornadaAnimalSemanaPorMunicipioController extends JornadaAnimalSemanaPorMunicipio {

    private static final Logger LOG = Logger.getLogger(NuevoCasoController.class);
    private RequestBuilder rb;

    public JornadaAnimalSemanaPorMunicipioController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniForm();
        try {
            rb = new RequestBuilder("administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> listaMunicipios = rb.ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipios.setModel(new DefaultComboBoxModel(listaMunicipios.toArray()));
                municipios.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public JButton getAceptar() {
        return null;
    }

    @Override
    public JButton getGuardar() {
        return null;
    }

    @Override
    public JButton getCancelar() {
        return null;
    }

    @Override
    public boolean validar() {
        return false;
    }

    @Override
    public void aceptar() {

    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {

    }

}
