/*
 * Copyright 2016 clases.
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
package ve.zoonosis.controller.jornadasvacunacion;

import com.megagroup.model.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ve.zoonosis.vistas.modulos.jornadasvacunaciones.BandejaJornadaVacunacion;

/**
 *
 * @author clases
 */
public class BandejaJornadaVacunacionController extends BandejaJornadaVacunacion implements Controller {

    public BandejaJornadaVacunacionController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        nuevo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

}
