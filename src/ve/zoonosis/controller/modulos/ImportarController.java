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
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.joda.time.DateTime;
import ve.zoonosis.controller.modulos.casos.BandejaCasosController;
import ve.zoonosis.model.combomodel.EnumComboBoxModel;
import ve.zoonosis.model.combomodel.ListComboBoxModel;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.enums.Modulos;
import ve.zoonosis.model.imports.VacunadosDiarioPorMunicipio;
import ve.zoonosis.vistas.modulos.Importar;
import windows.RequestBuilder;

/**
 *
 * @author angel.colina
 */
public class ImportarController extends Importar {

    private final JFileChooser chooser;
    private final FileFilter filtro = new FileNameExtensionFilter("Hoja de Excel", "xls");
    private final int acualYear = new DateTime().getYear();
    private MDialog dialog;
    private File proccesFile;

    public ImportarController() {
        chooser = new JFileChooser();
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniForm();
        chooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
        modulo.setModel(new EnumComboBoxModel(Modulos.class));

        modulo.addActionListener(formularioActionListener);
        buscar.addActionListener(formularioActionListener);
        year.addPropertyChangeListener(formularioActionListener);
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.addChoosableFileFilter(filtro);

        buscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = chooser.showOpenDialog(ImportarController.this);

                if ((opc == JFileChooser.APPROVE_OPTION) && (chooser.getSelectedFile().exists())) {
                    proccesFile = chooser.getSelectedFile();
                    formato.setText(proccesFile.getName());
                }
            }
        });
        try {
            List<Municipio> municipios = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php")
                    .ejecutarJson(List.class, Municipio.class);
            if (municipios != null) {
                municipios.add(0, null);
                municipio.setModel(new ListComboBoxModel<>(municipios));
                municipio.setSelectedIndex(-1);
            }
        } catch (URISyntaxException | RuntimeException ex) {
            Logger.getLogger(BandejaCasosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        municipio.addActionListener(formularioActionListener);
        aceptar.setEnabled(false);
        inicializarDialog();
    }

    private void inicializarDialog() {
        dialog = new MDialog();
        dialog.setTitle("Importación");
        dialog.showPanel(this);
    }

    @Override
    public boolean validar() {

        boolean valid = true;
        if (year.getValue() > acualYear || year.getYear() < acualYear - 5) {
            valid = false;
        } else if (modulo.getSelectedItem() == null || municipio.getSelectedItem() == null) {
            valid = false;
        } else if (proccesFile == null || !proccesFile.exists()) {
            valid = false;
        }
        return valid;
    }

    @Override
    public void aceptar() {
        try {
            new VacunadosDiarioPorMunicipio(proccesFile, (Municipio) municipio.getSelectedItem(), year.getValue()).iniciarLectura();
            MGrowl.showGrowl(MGrowlState.WAIT, "Se ha iniciado la importación");
            cancelar();
        } catch (IOException ex) {
            Logger.getLogger(ImportarController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
