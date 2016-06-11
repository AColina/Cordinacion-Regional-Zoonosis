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
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JButton;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.entidades.administracion.Cliente;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.vistas.modulos.novedades.CrearNovedad;
import windows.RequestBuilder;
import windows.ValidateEntity;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class CrearNovedadController extends CrearNovedad<Novedades> {

    private static final Logger LOG = Logger.getLogger(CrearNovedadController.class);

    private BandejaNovedadesController controller;
    private MDialog dialog;
    private RequestBuilder rb;
    private NuevoClienteController clienteController;

    public CrearNovedadController(BandejaNovedadesController pantallaController, Novedades novedad) {
        super(novedad);
        this.controller = pantallaController;
        inicializar();
    }

    public CrearNovedadController(BandejaNovedadesController novedadesController) {
        this(novedadesController, null);
    }

    @Override
    public final void inicializar() {
        if (entity == null) {
            entity = new Novedades();
        }
        aceptar.setEnabled(false);
        iniForm();

        BindObject<Novedades> bindObject = new BindObject(entity);
        Bindings.bind(nombre, bindObject.getBind("nombre"));
        Bindings.bind(descripcion, bindObject.getBind("descripcion"));
        List<Cliente> clientes = null;
        try {
            rb = new RequestBuilder("services/administracion/PersonaWs/ListaClientes.php");
            clientes = rb.ejecutarJson(List.class, Cliente.class);
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        if (clientes == null) {
            clientes = new ArrayList();
        }
        Bindings.bind(cliente, bindObject.getBind("cliente"), clientes, true);

        autoCreateValidateForm(Novedades.class);
        iniciarDialogo();
    }

    private void iniciarDialogo() {
        nuevoCliente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clienteController = new NuevoClienteController(CrearNovedadController.this);
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
        ValidateEntity validateEntity = new ValidateEntity(entity);
        return validateEntity.validate();
    }

    @Override
    public void aceptar() {
        try{
            
        entity.setFechaElaboracion(new Date());
        entity.setUsuario(LoginController.getUsuario());
                    rb = new RequestBuilder("services/proceso/NovedadesWs/CrearNovedad.php")
                    .setMetodo(MetodosDeEnvio.POST)
                    .crearJson(entity);
            entity = rb.ejecutarJson(Novedades.class);
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

}
