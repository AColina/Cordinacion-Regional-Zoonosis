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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
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
public class NuevaJornadaController extends NuevaJornada<RegistroVacunacion_has_Animal> {

    private static final Logger LOG = Logger.getLogger(NuevaJornadaController.class);
    private final BandejaJornadaVacunacionController controller;
    private MDialog dialog;
    private RequestBuilder rb;
    private Vacunacion vacunacion;
    private RegistroVacunacion registroVacunacion;

    public NuevaJornadaController(BandejaJornadaVacunacionController controller, RegistroVacunacion_has_Animal entidad) {
        super(entidad);
        this.controller = controller;
        inicializar();
    }

    public NuevaJornadaController(BandejaJornadaVacunacionController controller) {
        this(controller, null);
    }

    @Override
    public final void inicializar() {
        parroquia.setModel(new ListComboBoxModel<Parroquia>());
        if (entity == null) {
            entity = new RegistroVacunacion_has_Animal();
            vacunacion = new Vacunacion();
            registroVacunacion = new RegistroVacunacion();
            parroquia.addItemListener(new CambioParroquia());
        } else {
            registroVacunacion = entity.getRegistroVacunacion();
            registroVacunacion.getVacunacion_has_Animal().add(entity);
            vacunacion = registroVacunacion.getVacunacion();
            vacunacion.getRegistroVacunacion().add(registroVacunacion);
        }

        aceptar.setEnabled(false);
        iniForm();

        try {
            rb = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> municipios = rb.ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipio.setModel(new ListComboBoxModel<>(municipios));
                municipio.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        municipio.addActionListener(new MunicipioListener(parroquia));

        if (vacunacion.getParroquia() != null) {
            municipio.setSelectedItem(vacunacion.getParroquia().getMunicipio());
            parroquia.setSelectedItem(vacunacion.getParroquia());
            municipio.setEnabled(false);
            parroquia.setEnabled(false);
        }
        BindObject bindObject = new BindObject(vacunacion);

        Bindings.bind(parroquia, bindObject.getBind("parroquia"), ((ListComboBoxModel) parroquia.getModel()).getItems(), true);
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

        BindObject bindObject2 = new BindObject(entity);
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
        try {
            if (parroquia.getSelectedIndex() == -1) {
                return;
            }
            rb = new RequestBuilder("services/proceso/VacunacionWs/ObtenerVacunacionPorDia.php",
                    new HashMap<String, Object>() {
                        {
                            put("dia", Recursos.FORMATO_FECHA.format(new Date()));
                            put("parroquia", ((Parroquia) parroquia.getSelectedItem()).getId());
                        }
                    });
            vacunacion = rb.ejecutarJson(Vacunacion.class);
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        if (vacunacion == null) {
            vacunacion = new Vacunacion();
            vacunacion.setFechaElaboracion(new Date());
            vacunacion.setParroquia((Parroquia) parroquia.getSelectedItem());
            vacunacion.setSemana(Recursos.SEMANA_ACTUAL);
        }
        registroVacunacion = null;
        for (RegistroVacunacion reg : vacunacion.getRegistroVacunacion()) {
            if (reg.getUsuario().getId().equals(LoginController.getUsuario().getId())) {
                registroVacunacion = reg;
            }
        }
        if (registroVacunacion == null) {
            registroVacunacion = new RegistroVacunacion();
            registroVacunacion.setUsuario(LoginController.getUsuario());
            registroVacunacion.setVacunacion(vacunacion);
            vacunacion.getRegistroVacunacion().add(registroVacunacion);
        }

        entity.setRegistroVacunacion(registroVacunacion);
    }

    @Override
    public boolean validar() {
        boolean v = new ValidateEntity(vacunacion).validate(this);

        if (v) {
            v = new ValidateEntity(entity).validate(this);
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
            if (entity.getId() == null) {
                registroVacunacion.getVacunacion_has_Animal().add(entity);
            }
            rb = new RequestBuilder("services/proceso/VacunacionWs/CrearVacunacion.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(vacunacion);
            vacunacion = rb.ejecutarJson(Vacunacion.class);
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
