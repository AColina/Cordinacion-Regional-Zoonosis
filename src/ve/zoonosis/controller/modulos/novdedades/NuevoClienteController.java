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

import com.megagroup.binding.BindObject;
import com.megagroup.binding.components.Bindings;
import com.megagroup.binding.model.BindingEvent;
import com.megagroup.componentes.MDialog;
import com.megagroup.utilidades.ComponentUtils;
import com.megagroup.utilidades.Logger;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Persona;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.novedades.NuevoCliente;
import windows.RequestBuilder;
import windows.ValidateEntity;

/**
 *
 * @author angel.colina
 */
public class NuevoClienteController extends NuevoCliente<Cliente> {

    private static final Logger LOG = Logger.getLogger(NuevoClienteController.class);
    private RequestBuilder rb;
    private final Cliente cliente;
    private final CrearNovedadController novedadController;
    private Persona persona;
    private MDialog dialog;

    public NuevoClienteController(CrearNovedadController novedadController) {
        this.novedadController = novedadController;
        cliente = new Cliente();
        inicializar();
    }

    @Override
    public final void inicializar() {
        aceptar.setEnabled(false);
        persona = new Persona();
        iniForm();
        buscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPersona();
            }
        });
        limpiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                persona = new Persona();
                cedula.setText(null);
                cedula.setEnabled(true);
                nombre.setText(null);
                nombre.setEnabled(true);
                apellido.setText(null);
                apellido.setEnabled(true);

            }
        });
        autoCreateValidateForm(Persona.class, Cliente.class);

        BindObject bindObject2 = new BindObject(cliente);
        Bindings.bind(correo, bindObject2.getBind("correo"));
        Bindings.bind(direccion, bindObject2.getBind("direccion"));
        Bindings.bind(telefono, bindObject2.getBind("telefono"));

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
        Bindings.bind(parroquia, bindObject2.getBind("parroquia"), true);
        iniciarDialogo();
    }

    private void iniciarDialogo() {
        dialog = new MDialog((Dialog) SwingUtilities.getWindowAncestor(novedadController));
        dialog.setTitle("Nuevo");
        dialog.setResizable(false);
        dialog.showPanel(this);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                cancelar();
            }
        });

    }

    private void buscarPersona() {
        final String c = cedula.getText();
        try {
            persona = null;
            rb = new RequestBuilder("services/administracion/PersonaWs/ObtenerPersonaPorCedula.php",
                    new HashMap<String, Object>() {
                        {
                            put("cedula", c);
                        }
                    });
            persona = rb.ejecutarJson(Persona.class);
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        ComponentUtils.removeListener(cedula, BindingEvent.class);
        ComponentUtils.removeListener(nombre, BindingEvent.class);
        ComponentUtils.removeListener(apellido, BindingEvent.class);

        cedula.setEnabled(persona == null);
        nombre.setEnabled(persona == null);
        apellido.setEnabled(persona == null);

        if (persona == null) {
            persona = new Persona();
            persona.setCedula(c);
        }
        BindObject bindObject = new BindObject(persona);
        Bindings.bind(nombre, bindObject.getBind("nombre"));
        Bindings.bind(apellido, bindObject.getBind("apellido"));
        Bindings.bind(cedula, bindObject.getBind("cedula"));
        cliente.setPersona(persona);

    }

    @Override
    public boolean validar() {
        boolean valid;
        ValidateEntity validateEntity = new ValidateEntity(persona);
        valid = validateEntity.validate();
        if (!valid) {
            return valid;
        }
        return new ValidateEntity(cliente).validate();

    }

    @Override
    public void aceptar() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) novedadController.getCliente().getModel();
        model.addElement(cliente);
        novedadController.getCliente().setSelectedIndex(-1);
        cancelar();

    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelar() {
        dialog.close();
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
    public JButton getAceptar() {
        return aceptar;
    }

}
