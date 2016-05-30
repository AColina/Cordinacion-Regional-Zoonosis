/*
 * Copyright 2016 Kelvin López.
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
package ve.zoonosis.controller.modulos.estadistica;

import javax.swing.JButton;
import ve.zoonosis.vistas.estadistica.jornada.animales.JornadaAnimalDiarioPorMunicipio;

/**
 *
 * @author Kelvin López
 */
public class JornadaAnimalDiarioPorMunicipioController extends JornadaAnimalDiarioPorMunicipio {

    public JornadaAnimalDiarioPorMunicipioController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        iniForm();
    }

    @Override
    public JButton getAceptar() {
        return generar;
    }

    @Override
    public JButton getGuardar() {
        return null;
    }

    @Override
    public JButton getCancelar() {
        return null;
    }

    @Override
    public boolean validar() {
        return false;
    }

    @Override
    public void aceptar() {

    }

    @Override
    public void guardar() {

    }

    @Override
    public void cancelar() {

    }

}
