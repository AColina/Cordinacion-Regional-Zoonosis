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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
public class NuevoCasoController extends NuevoCaso<Caso> {

    private static final Logger LOG = Logger.getLogger(NuevoCasoController.class);

    private final BandejaCasosController controller;
    private MDialog dialog;
    private RequestBuilder rb;
    private Animal_has_Caso caso;

    public NuevoCasoController(BandejaCasosController controller, Caso caso) {
        super(caso);
        this.controller = controller;
        inicializar();
    }

    public NuevoCasoController(BandejaCasosController controller) {
        this(controller, null);
    }

    @Override
    public final void inicializar() {
        System.out.println("inicializando");
        if (entity == null) {
            entity = new Caso();
        }
        caso = new Animal_has_Caso();
        aceptar.setEnabled(false);
        iniForm();
        BindObject<Caso> bindObject = new BindObject(entity);
        Bindings.bind(parroquia, bindObject.getBind("parroquia"), true);

        try {
            System.out.println("asdasdasdasdasdasda");
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
        BindObject bindObject2 = new BindObject(caso);
        Bindings.bind(animal, bindObject2.getBind("animal"), true);
        Bindings.bind(cantidadIngresado, bindObject2.getBind("cantidadIngresado"));
        Bindings.bind(cantidadPositivos, bindObject2.getBind("cantidadPositivos"));
        autoCreateValidateForm(Caso.class, Animal_has_Caso.class);
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
        boolean v = new ValidateEntity(entity).validate(this);

        if (v) {
            v = new ValidateEntity(caso).validate(this);
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
            entity.setSemana(Recursos.SEMANA_ACTUAL);
            entity.getParroquia().setCasos(null);
            entity.getSemana().setCasos(null);
            rb = new RequestBuilder("services/proceso/CasoWs/CrearCaso.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(entity);
            entity = rb.ejecutarJson(Caso.class);
            if (entity != null) {
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
            entity = null;
            entity = rb.ejecutarJson(Caso.class);
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        if (entity == null) {
            entity = new Caso();
            entity.setFechaElaboracion(new Date());
            entity.setParroquia((Parroquia) parroquia.getSelectedItem());
            entity.getAnimal_has_Caso().add(caso);
        }
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
