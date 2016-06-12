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
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.ComponentUtils;
import com.megagroup.utilidades.Logger;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.apache.commons.lang3.StringUtils;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
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

    private final CrearNovedadController novedadController;
    private Persona persona;
    private MDialog dialog;

    public NuevoClienteController(CrearNovedadController novedadController) {
        this.novedadController = novedadController;
        inicializar();
    }

    @Override
    public final void inicializar() {
        activeInput(false);
        cedula.setEnabled(true);

        if (entity == null) {
            entity = new Cliente();
        }
        persona = new Persona();
        aceptar.setEnabled(false);
        
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
                limpiar();
            }
        });
        cedula.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarPersona();
                }
            }
        });

        BindObject bindObject2 = new BindObject(entity);
        Bindings.bind(correo, bindObject2.getBind("correo"));
        Bindings.bind(direccion, bindObject2.getBind("direccion"));
        Bindings.bind(telefono, bindObject2.getBind("telefono"));
        Bindings.bind(parroquia, bindObject2.getBind("parroquia"), true);

        try {
            rb = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> municipios = rb.ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipios.add(0, null);
                municipio.setModel(new ListComboBoxModel<>(municipios));
                municipio.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        municipio.addActionListener(new MunicipioListener(parroquia));
        parroquia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entity.setParroquia((Parroquia) parroquia.getSelectedItem());
                aceptar.setEnabled(validar());
            }
        });
        autoCreateValidateForm(Persona.class, Cliente.class);
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

    private void activeInput(boolean b) {
        for (Component component : this.getComponents()) {
            if ((component instanceof JTextField) || (component instanceof JComboBox)) {
                component.setEnabled(b);
                if (component instanceof JTextField) {
                    ((JTextField) component).setText(null);
                } else {
                    ((JComboBox) component).setSelectedIndex(-1);
                }
            }
        }
    }

    public void limpiar() {
        persona = new Persona();
        activeInput(false);
        cedula.setEnabled(true);
    }

    private void buscarPersona() {
        final String c = cedula.getText();
        if (StringUtils.isEmpty(c)) {
            cedula.requestFocus();
            return;
        }
        activeInput(true);
        try {
            persona = null;
            rb = new RequestBuilder("services/administracion/PersonaWs/ObtenerPersonaPorCedula.php",
                    new HashMap<String, Object>() {
                        {
                            put("cedula", c);
                        }
                    });
            persona = rb.ejecutarJson(Persona.class);
            if (persona != null && persona.getCliente() != null) {
                MGrowl.showGrowl(MGrowlState.ERROR,
                        String.format("El cliente con la cedula \"%s\" ya ha sido regisrtado", c));
                limpiar();
                return;
            }
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
            JOptionPane.showMessageDialog(this, "La persona con la cedula especificada "
                    + "no fue encontrada", "Información", JOptionPane.INFORMATION_MESSAGE);
            persona = new Persona();
            persona.setCedula(c);
        }
        BindObject bindObject2 = new BindObject(persona);
        Bindings.bind(cedula, bindObject2.getBind("cedula"));
        Bindings.bind(nombre, bindObject2.getBind("nombre"));
        Bindings.bind(apellido, bindObject2.getBind("apellido"));

        entity.setPersona(persona);
        validar();
        if (apellido.isEnabled()) {
            nombre.requestFocus();
        } else {
            correo.requestFocus();
        }
    }

    @Override
    public boolean validar() {
        boolean v = new ValidateEntity(persona).validate(this, "cedula");

        if (v) {
            v = new ValidateEntity(entity).validate(this, new String[]{"correo", "direccion", "parroquia"}, null);
        }
        dialog.revalidate();
        if (dialog.getDialogScroll().getHorizontalScrollBar().isVisible()) {
            dialog.pack();
        }
        return v;
    }

    @Override
    public void aceptar() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) novedadController.getCliente().getModel();
        model.addElement(entity);
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
