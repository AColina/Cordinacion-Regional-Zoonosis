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
package ve.zoonosis.controller.funcionales;

import com.megagroup.componentes.MDialog;
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.Logger;
import com.megagroup.utilidades.RestrictedTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ve.zoonosis.model.entidades.funcionales.Animal;
import ve.zoonosis.model.entidades.funcionales.Especie;
import ve.zoonosis.vistas.funcionales.CrearEspecie;
import windows.RequestBuilder;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class CrearEspecieController extends CrearEspecie<Especie> {

    private static final Logger LOG = Logger.getLogger(CrearEspecieController.class);

    private MDialog dialog;
    private RequestBuilder rb;
    private int ct;

    public CrearEspecieController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniForm();
        ct = -1;
        registrarEspecie.setEnabled(false);
        registrarAnimal.setEnabled(false);
        try {
            rb = new RequestBuilder("services/funcionales/EspecieWs/ListaEspecies.php");
            List<Especie> especiess = rb.ejecutarJson(List.class, Especie.class);
            especies.setModel(new DefaultComboBoxModel(especiess.toArray()));
            especies.setSelectedIndex(-1);
            AutoCompleteDecorator.decorate(especies);

        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
        RestrictedTextField r = new RestrictedTextField(nombreEspecie);
        r.setLimit(45);
        r.setOnlyLetter(true);
        RestrictedTextField r3 = new RestrictedTextField(nombreAnimal);
        r3.setLimit(45);
        r3.setOnlyLetter(true);
        nombreEspecie.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                registrarEspecie.setEnabled(validarEspecie());
            }
        });
        nombreAnimal.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                registrarAnimal.setEnabled(validarAnimal());
            }
        });
        especies.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAnimal.setEnabled(validarAnimal());
            }
        });
        registrarEspecie.addActionListener(new ResgistrarEspecie());
        registrarAnimal.addActionListener(new ResgistrarAnimal());
        iniciarDialogo();
    }

    private void iniciarDialogo() {
        dialog = new MDialog();
        dialog.setTitle("Nuevo");
        dialog.setResizable(false);
        dialog.showPanel(this);
    }

    private boolean validarEspecie() {
        return StringUtils.isNotEmpty(nombreEspecie.getText())
                && nombreEspecie.getText().length() > 3;
    }

    private boolean validarAnimal() {
        return StringUtils.isNotEmpty(nombreAnimal.getText())
                && nombreAnimal.getText().length() > 3 && especies.getSelectedItem() != null;
    }

    @Override
    public boolean validar() {
        return true;
    }

    @Override
    public void aceptar() {
        guardar();
        cancelar();
    }

    @Override
    public void guardar() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) especies.getModel();
            Object[] o = new Object[model.getSize()];
            for (int i = 0; i < o.length; i++) {
                o[i] = model.getElementAt(i);
            }

            rb = new RequestBuilder("services/funcionales/EspecieWs/CrearEspecies.php")
                    .setMetodo(MetodosDeEnvio.POST).crearJson(o);
            List<Especie> especiess = rb.ejecutarJson(List.class, Especie.class);

            if (especiess != null) {
                especies.setModel(new DefaultComboBoxModel(especiess.toArray()));
                especies.setSelectedIndex(-1);
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Datos guardados con exito");
            } else {
                MGrowl.showGrowl(MGrowlState.ERROR, "Los Datos no han sido guardados");
            }
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private class ResgistrarEspecie implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultComboBoxModel model = (DefaultComboBoxModel) especies.getModel();
            model.addElement(new Especie(nombreEspecie.getText(), (long) ct));
            nombreEspecie.setText("");
            especies.setSelectedIndex(-1);
            ct--;

        }

    }

    private class ResgistrarAnimal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Especie es = (Especie) especies.getSelectedItem();
            es.getAnimalesAsociados().add(new Animal(nombreAnimal.getText()));
            nombreAnimal.setText("");
        }

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
        return guardar;
    }

    @Override
    public JButton getCancelar() {
        return cancelar;
    }

}
