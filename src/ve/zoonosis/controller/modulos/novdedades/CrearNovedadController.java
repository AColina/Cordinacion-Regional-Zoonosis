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

import com.megagroup.Application;
import com.megagroup.binding.BindObject;
import com.megagroup.binding.components.Bindings;
import com.megagroup.componentes.MDialog;
import com.megagroup.utilidades.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.vistas.modulos.novedades.CrearNovedad;

/**
 *
 * @author angel.colina
 */
public class CrearNovedadController extends CrearNovedad<Novedades> {

    private BandejaNovedadesController controller;
    private Novedades novedad;
    private MDialog dialog;
private NuevoClienteController clienteController;
    public CrearNovedadController(BandejaNovedadesController pantallaController, Novedades novedad) {
        super();
        this.controller = pantallaController;
        this.novedad = novedad;
        inicializar();
    }

    public CrearNovedadController(BandejaNovedadesController novedadesController) {
        this(novedadesController, null);
    }

    @Override
    public final void inicializar() {
        if (novedad == null) {
            novedad = new Novedades();
        }
        aceptar.setEnabled(false);
        iniForm();

        BindObject<Novedades> bindObject = new BindObject(novedad);
        Bindings.bind(nombre, bindObject.getBind("nombre"));
        Bindings.bind(descripcion, bindObject.getBind("descripcion"));
        Bindings.bind(cliente, bindObject.getBind("cliente"), true);

        descripcion.addKeyListener(new ValidarFormularioActionListener());
        nombre.addKeyListener(new ValidarFormularioActionListener());
        cliente.addActionListener(new ValidarFormularioActionListener());
        iniciarDialogo();
    }

    private void iniciarDialogo() {
         nuevoCliente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
          clienteController=      new NuevoClienteController(CrearNovedadController.this);
            }
        });
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
        boolean enable = true;
        if (StringUtils.isEmpty(novedad.getNombre())) {
            enable = false;
        } else if (StringUtils.isEmpty(novedad.getDescripcion())
                || novedad.getDescripcion().replace(" ", "").length() < 5) {
            enable = false;
        } else if (novedad.getCliente() == null) {
            enable = false;
        }
        return enable;
    }

    @Override
    public void aceptar() {

    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {
        controller.buscar();
        dialog.dispose();
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

    public JComboBox getCliente() {
        return cliente;
    }

}
