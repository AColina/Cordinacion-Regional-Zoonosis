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

import com.megagroup.Application;
import com.megagroup.componentes.MDialog;
import com.megagroup.utilidades.Logger;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.jornadasvacunaciones.NuevaJornada;
import windows.RequestBuilder;

/**
 *
 * @author angel.colina
 */
public class NuevaJornadaController extends NuevaJornada<RegistroVacunacion> {

    private static final Logger LOG = Logger.getLogger(NuevaJornadaController.class);
    private final BandejaJornadaVacunacionController controller;
    private MDialog dialog;
    private RequestBuilder rb;

    public NuevaJornadaController(BandejaJornadaVacunacionController controller, RegistroVacunacion entidad) {
        super(entidad);
        this.controller = controller;
        inicializar();
    }

    public NuevaJornadaController(BandejaJornadaVacunacionController controller) {
        this(controller, null);
    }

    @Override
    public final void inicializar() {
        if (entity == null) {
            entity = new RegistroVacunacion();
        }
        aceptar.setEnabled(false);
        iniForm();

        try {
            rb = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> municipios = rb.ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipio.setModel(new DefaultComboBoxModel(municipios.toArray()));
                municipio.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        municipio.addActionListener(new MunicipioListener(parroquia));
        iniciarDialogo();
    }

    private void iniciarDialogo() {

        dialog = new MDialog(Application.getAPLICATION_FRAME());
        dialog.setTitle("Nuevo");
        dialog.setResizable(false);
        dialog.showPanel(this);
        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                controller.buscar();
            }
        });

    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {
        guardar();
        cancelar();
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {
        controller.buscar();
        dialog.close();
    }

    @Override
    public JButton getAceptar() {
        return aceptar;
    }

    @Override
    public JButton getGuardar() {
        return null;
    }

    @Override
    public JButton getCancelar() {
        return cancelar;
    }

}
