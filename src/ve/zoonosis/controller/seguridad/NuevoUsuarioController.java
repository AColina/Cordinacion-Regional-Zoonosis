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
package ve.zoonosis.controller.seguridad;

import com.megagroup.binding.BindObject;
import com.megagroup.binding.components.Bindings;
import com.megagroup.binding.model.BindingEvent;
import com.megagroup.componentes.MDialog;
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.ComponentUtils;
import com.megagroup.utilidades.Logger;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.entidades.administracion.Permiso;
import ve.zoonosis.model.entidades.administracion.Persona;
import ve.zoonosis.model.entidades.administracion.Usuario;
import ve.zoonosis.vistas.seguridad.NuevoUsuario;
import windows.RequestBuilder;
import windows.ValidateEntity;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class NuevoUsuarioController extends NuevoUsuario<Usuario> {

    private static final Logger LOG = Logger.getLogger(NuevoUsuarioController.class);
    private RequestBuilder rb;
    private Persona persona;
    private MDialog dialog;

    public NuevoUsuarioController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        activeInput(false);
        cedula.setEnabled(true);
        if (entity == null) {
            entity = new Usuario();
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
                persona = new Persona();
                activeInput(false);
                cedula.setEnabled(true);
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

        try {
            rb = new RequestBuilder("services/administracion/PermisoWs/BuscarPermiso.php");
            List<Permiso> permisos = rb.ejecutarJson(List.class, Permiso.class);
            if (permisos != null) {
                permisos.add(0, null);
                permiso.setModel(new ListComboBoxModel<>(permisos));
                permiso.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        BindObject bindObject2 = new BindObject(entity);
        Bindings.bind(usr, bindObject2.getBind("nombre"));
        Bindings.bind(contrasena, bindObject2.getBind("contrasena"));
        Bindings.bind(fechaNacimiento, bindObject2.getBind("fechaNacimiento"), new SimpleDateFormat("dd/MM/yyyy"));
        Bindings.bind(permiso, bindObject2.getBind("permiso"), true);
        autoCreateValidateForm(Persona.class,Usuario.class);
        iniciarDialogo();
    }

    private void iniciarDialogo() {
        dialog = new MDialog();
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
            if ((component instanceof JTextField) || (component instanceof JComboBox) || (component instanceof JDateChooser)) {
                component.setEnabled(b);
                if (component instanceof JTextField) {
                    ((JTextField) component).setText(null);
                } else if (component instanceof JDateChooser) {
                    ((JDateChooser) component).setDate(null);
                } else {
                    ((JComboBox) component).setSelectedIndex(-1);
                }
            }
        }
    }

    private void buscarPersona() {
        final String c = cedula.getText();
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

        if (apellido.isEnabled()) {
            nombre.requestFocus();
        } else {
            usr.requestFocus();
        }
    }

    @Override
    public boolean validar() {
        boolean v = new ValidateEntity(persona).validate();

        if (v) {
            v = new ValidateEntity(entity).validate();
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

            Usuario u = new RequestBuilder("services/administracion/PersonaWs/CrearUsuario.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(entity)
                    .ejecutarJson(Usuario.class);
            if (u != null) {
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Usuario creado con exito");
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
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
