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
package ve.zoonosis.model.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ve.zoonosis.controller.casos.BandejaCasosController;
import ve.zoonosis.controller.jornadasvacunacion.BandejaJornadaVacunacionController;
import ve.zoonosis.model.components.AbstractInternalListener;

/**
 *
 * @author clases
 */
public class TemplateListeners {

    public static Bandeja getJornadaVacunacionBandeja() {
        return new Bandeja("Jornadas de Vacunación", BandejaJornadaVacunacionController.class);
    }

    public static Bandeja getCasosBandeja() {
        return new Bandeja("Casos", BandejaCasosController.class);
    }

    public static class Bandeja extends AbstractInternalListener implements ActionListener {

        private final String titulo;
        private final Class clase;

        public Bandeja(String titulo, Class clase) {
            this.titulo = titulo;
            this.clase = clase;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            crearInternalFrame(titulo, clase);

        }

    }

}
