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
import javax.swing.JMenuItem;
import ve.zoonosis.vistas.modulos.casos.BandejaCasos;

/**
 *
 * @author angel.colina
 */
public class BandejaCasosController extends BandejaCasos {

    public BandejaCasosController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniciarBandeja(true);
        buscar.addActionListener(new BuscarLstener());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() {
   
    }

    @Override
    public Object getSelectItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
