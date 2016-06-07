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
import com.megagroup.binding.BindObject;
import com.megagroup.binding.components.Bindings;
import com.megagroup.componentes.MDialog;
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.Logger;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.funcionales.Animal;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion_has_Animal;
import ve.zoonosis.model.entidades.proceso.Vacunacion;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.jornadasvacunaciones.NuevaJornada;
import windows.Recursos;
import windows.RequestBuilder;
import windows.ValidateEntity;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class NuevaJornadaController extends NuevaJornada<Vacunacion> {

    private static final Logger LOG = Logger.getLogger(NuevaJornadaController.class);
    private final BandejaJornadaVacunacionController controller;
    private MDialog dialog;
    private RequestBuilder rb;
    private RegistroVacunacion vacunacion;
    private RegistroVacunacion_has_Animal vacunacion_has_Animal;

    public NuevaJornadaController(BandejaJornadaVacunacionController controller, Vacunacion entidad) {
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
            entity = new Vacunacion();
        }
        vacunacion = new RegistroVacunacion();
        vacunacion_has_Animal = new RegistroVacunacion_has_Animal();
        aceptar.setEnabled(false);
        iniForm();
        BindObject bindObject = new BindObject(entity);
        Bindings.bind(parroquia, bindObject.getBind("parroquia"), true);
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
        parroquia.addItemListener(new CambioParroquia());
        try {
            rb = new RequestBuilder("services/funcionales/AnimalWs/ListaAnimales.php");
            List<Animal> animales = rb.ejecutarJson(List.class, Animal.class);
            if (animales != null) {
                animal.setModel(new DefaultComboBoxModel(animales.toArray()));
                animal.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }

        BindObject bindObject2 = new BindObject(vacunacion_has_Animal);
        Bindings.bind(animal, bindObject2.getBind("animal"), true);
        Bindings.bind(cantidad, bindObject2.getBind("cantidad"));
        autoCreateValidateForm(Vacunacion.class, RegistroVacunacion_has_Animal.class);
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

    private void buscarJornada() {

    }

    @Override
    public boolean validar() {
        boolean v = new ValidateEntity(entity).validate(this);

        if (v) {
            v = new ValidateEntity(vacunacion_has_Animal).validate(this);
        }
        dialog.revalidate();
        if (dialog.getDialogScroll().getHorizontalScrollBar().isVisible()) {
            dialog.pack();
        }
        return v;
    }

    //CLASES INTERNAS
    private class CambioParroquia implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            buscarJornada();
        }

    }

    @Override
    public void aceptar() {
        try {
            vacunacion.setUsuario(LoginController.getUsuario());
            vacunacion.getVacunacion_has_Animal().add(vacunacion_has_Animal);
            vacunacion_has_Animal.setRegistroVacunacion(vacunacion);
            entity.getRegistroVacunacion().add(vacunacion);
            entity.setSemana(Recursos.SEMANA_ACTUAL);
            rb = new RequestBuilder("services/proceso/VacunacionWs/CrearVacunacion.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(entity);
            entity = rb.ejecutarJson(Vacunacion.class);
            if (entity != null) {
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Registro guardado con exito");
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        cancelar();
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {
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
