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
import com.megagroup.componentes.MDialog;
import com.megagroup.utilidades.StringUtils;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.administracion.Persona;
import ve.zoonosis.vistas.modulos.novedades.NuevoCliente;

/**
 *
 * @author angel.colina
 */
public class NuevoClienteController extends NuevoCliente<Cliente> {

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
        BindObject bindObject = new BindObject(persona);
        Bindings.bind(nombre, bindObject.getBind("nombre"));
        Bindings.bind(apellido, bindObject.getBind("apellido"));
        Bindings.bind(cedula, bindObject.getBind("cedula"));
        cliente.setPersona(persona);

        nombre.addKeyListener(new ValidarFormularioActionListener());
        apellido.addKeyListener(new ValidarFormularioActionListener());
        cedula.addKeyListener(new ValidarFormularioActionListener());

        BindObject bindObject2 = new BindObject(cliente);
        Bindings.bind(correo, bindObject2.getBind("correo"));
        Bindings.bind(direccion, bindObject2.getBind("direccion"));
        Bindings.bind(telefono, bindObject2.getBind("telefono"));
        Bindings.bind(parroquia, bindObject2.getBind("parroquia"), true);
        iniciarDialogo();
    }

    private void iniciarDialogo() {
        dialog = new MDialog((Dialog) SwingUtilities.getWindowAncestor(novedadController));
        dialog.setTitle("Nuevo");
        dialog.setResizable(false);
        dialog.showPanel(this);
        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                cancelar();
            }
        });

    }

    private void buscarPersona() {

    }

    @Override
    public boolean validar() {
        boolean enable = true;
        if (cliente.getPersona() == null) {
            enable = false;

//        } else if (cliente.getParroquia() == null) {
//            enable = false;
        } else if (StringUtils.isEmpty(cliente.getPersona().getNombre())) {
            enable = false;
        } else if (StringUtils.isEmpty(cliente.getPersona().getApellido())) {
            enable = false;
        } else if (cliente.getPersona().getCedula() == null) {
            enable = false;
        }
        return enable;
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
        dialog.dispose();
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
