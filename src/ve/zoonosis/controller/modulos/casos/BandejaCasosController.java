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

import com.megagroup.componentes.MDataTable;
import com.megagroup.model.builder.LazyColumnListenerModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.datamodel.CasosTableModel;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.vistas.modulos.casos.BandejaCasos;

/**
 *
 * @author angel.colina
 */
public class BandejaCasosController extends BandejaCasos<Caso> {

    private NuevoCasoController casoController;

    public BandejaCasosController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniciarBandeja(false);
        botonVer = 3;
        nuevo.setVisible(LoginController.getUsuario()!=null);
        buscar.addActionListener(new BuscarLstener());
        bandeja.setColumnListenerModel(LazyColumnListenerModel.class);
        bandeja.setModel(new CasosTableModel());
        nuevo.addActionListener(new CrearCasoListener());
    }

    @Override
    protected void addItemPopUp(int index) {

        JMenuItem nuevo = new JMenuItem("Nuevo");

        popupMenu.add(nuevo);
        if (index > -1) {
            JMenuItem ver = new JMenuItem("Ver");

            popupMenu.add(ver);
        }
    }

    @Override
    public MDataTable getBandeja() {
        return bandeja;
    }

    @Override
    public void abrir(int index) {
        casoController = new NuevoCasoController(BandejaCasosController.this,
                bandeja.getModel().getValueAt(index));
    }

    @Override
    public void buscar() {
        bandeja.setModel(new CasosTableModel());
    }

    @Override
    public Object getSelectItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // ActionListener de jmenupopup
    private class CrearCasoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            casoController = new NuevoCasoController(BandejaCasosController.this);

        }

    }
}
