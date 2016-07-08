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
package ve.zoonosis.controller.modulos.casos;

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
import javax.swing.JButton;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.funcionales.Animal;
import ve.zoonosis.model.entidades.proceso.Animal_has_Caso;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.casos.NuevoCaso;
import windows.Recursos;
import windows.RequestBuilder;
import windows.ValidateEntity;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class NuevoCasoController extends NuevoCaso<Animal_has_Caso> {

    private static final Logger LOG = Logger.getLogger(NuevoCasoController.class);

    private final BandejaCasosController controller;
    private MDialog dialog;
    private RequestBuilder rb;
    private Caso caso;

    public NuevoCasoController(BandejaCasosController controller, Animal_has_Caso caso) {
        super(caso);
        this.controller = controller;
        inicializar();
    }

    public NuevoCasoController(BandejaCasosController controller) {
        this(controller, null);
    }

    @Override
    public final void inicializar() {
        parroquia.setModel(new ListComboBoxModel<Parroquia>());

        if (entity == null) {
            entity = new Animal_has_Caso();
            caso = new Caso();
            parroquia.addItemListener(new CambioParroquia());
        } else {
            caso = entity.getCaso();
            caso.getAnimal_has_Caso().add(entity);
        }

        aceptar.setEnabled(false);
        iniForm();

        try {
            System.out.println("asdasdasdasdasdasda");
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

        if (caso.getParroquia() != null) {
            municipio.setSelectedItem(caso.getParroquia().getMunicipio());
            parroquia.setSelectedItem(caso.getParroquia());
            municipio.setEnabled(false);
            parroquia.setEnabled(false);
        }
        BindObject<Caso> bindObject = new BindObject(caso);

        Bindings.bind(parroquia, bindObject.getBind("parroquia"), ((ListComboBoxModel) parroquia.getModel()).getItems(), true);
        try {
            rb = new RequestBuilder("services/funcionales/AnimalWs/ListaAnimales.php");
            List<Animal> animales = rb.ejecutarJson(List.class, Animal.class);
            if (animales != null) {
                animal.setModel(new ListComboBoxModel(animales));
                animal.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        BindObject bindObject2 = new BindObject(entity);
        Bindings.bind(animal, bindObject2.getBind("animal"), ((ListComboBoxModel) animal.getModel()).getItems(), true);
        Bindings.bind(cantidadIngresado, bindObject2.getBind("cantidadIngresado"));
        Bindings.bind(cantidadPositivos, bindObject2.getBind("cantidadPositivos"));
        cantidadIngresado.setMaxLength(3);
        cantidadPositivos.setMaxLength(3);
        autoCreateValidateForm(Caso.class, Animal_has_Caso.class);
        if (entity.getAnimal() != null) {
            animal.setEnabled(false);
        }
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
        boolean v = new ValidateEntity(caso).validate(this);

        if (v) {
            if (v = new ValidateEntity(entity).validate(this)) {
                if (entity.getCantidadIngresado() < entity.getCantidadPositivos()) {
                    v = false;
                    positivosError.setText("<html><p>La cantidad de casos positivos <br>"
                            + " no debe ser mayor a la cantidad <br>"
                            + " de animales ingresados</p></html>");
                    positivosError.setVisible(true);
                } else {
                    positivosError.setText(null);
                    positivosError.setVisible(false);
                }
            }
        }
        dialog.revalidate();
        if (dialog.getDialogScroll().getHorizontalScrollBar().isVisible()) {
            dialog.pack();
        }
        return v;
    }

    @Override
    public void aceptar() {
        try {
            if (entity.getId() == null) {
                caso.getAnimal_has_Caso().add(entity);
            }

            rb = new RequestBuilder("services/proceso/CasoWs/CrearCaso.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(caso);
            caso = rb.ejecutarJson(Caso.class);
            if (caso != null) {
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Registro guardado con exito");
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        cancelar();

    }
    //  METODOS PRIVADOS

    private void buscarCaso() {
        try {
            if (parroquia.getSelectedIndex() == -1) {
                return;
            }
            rb = new RequestBuilder("services/proceso/CasoWs/ObtenerCasoPorDia.php",
                    new HashMap<String, Object>() {
                        {
                            put("dia", Recursos.FORMATO_FECHA.format(new Date()));
                            put("parroquia", ((Parroquia) parroquia.getSelectedItem()).getId());
                        }
                    });
            caso = rb.ejecutarJson(Caso.class);
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        if (caso == null) {
            caso = new Caso();
            caso.setFechaElaboracion(new Date());
            caso.setParroquia((Parroquia) parroquia.getSelectedItem());
            caso.setSemana(Recursos.SEMANA_ACTUAL);
        }
        entity.setCaso(caso);
    }

    //CLASES INTERNAS
    private class CambioParroquia implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            buscarCaso();
        }

    }

    //GETTER AND SETTER
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

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelar() {
        dialog.close();
    }

}
