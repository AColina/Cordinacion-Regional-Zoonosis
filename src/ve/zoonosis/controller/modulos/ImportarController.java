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
package ve.zoonosis.controller.modulos;

import com.megagroup.componentes.MDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import ve.zoonosis.model.combomodel.EnumComboBoxModel;
import ve.zoonosis.model.enums.Modulos;
import ve.zoonosis.vistas.modulos.Importar;

/**
 *
 * @author angel.colina
 */
public class ImportarController extends Importar {

    private final JFileChooser chooser;
    private MDialog dialog;

    public ImportarController() {
        chooser = new JFileChooser();
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniForm();
//        chooser.set
        modulo.setModel(new EnumComboBoxModel(Modulos.class));
        buscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.showDialog(ImportarController.this, JFileChooser.APPROVE_SELECTION);
            }
        });
        inicializarDialog();
    }

    private void inicializarDialog() {
        dialog = new MDialog();
        dialog.setTitle("Importación");
        dialog.showPanel(this);
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {

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
