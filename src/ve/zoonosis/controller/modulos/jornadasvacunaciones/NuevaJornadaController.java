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
package ve.zoonosis.controller.modulos.jornadasvacunaciones;

import com.megagroup.Application;
import com.megagroup.bean.Controller;
import com.megagroup.componentes.MDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import ve.zoonosis.vistas.modulos.jornadasvacunaciones.NuevaJornada;

/**
 *
 * @author angel.colina
 */
public class NuevaJornadaController extends NuevaJornada implements Controller {

    private MDialog dialog;
    private final BandejaJornadaVacunacionController bandejaController;
    private Object entidad;

    public NuevaJornadaController(BandejaJornadaVacunacionController bandejaController) {
        this(bandejaController, null);
    }

    public NuevaJornadaController(BandejaJornadaVacunacionController bandejaController, Object entidad) {
        this.bandejaController = bandejaController;
        this.entidad = entidad;
        inicializar();
    }

    @Override
    public final void inicializar() {
        dialog = new MDialog(Application.getAPLICATION_FRAME());
        

        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                bandejaController.buscar();
            }
        });
        dialog.setTitle("Nueva Jornada de Vacunación");
        //CREANDO LSITENERS
        cancelar.addActionListener(new CancelarActionListener());

        //CARGANDO DATA Y BUSCANDO DE INFORMACION
        if (entidad == null) {
            entidad = new Object();
        } else {

        }
        
        dialog.showPanel(this);
        dialog.setResizable(true);
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {
        guardar();
        cancelar();
    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {
        bandejaController.buscar();
        dialog.dispose();
    }

}
