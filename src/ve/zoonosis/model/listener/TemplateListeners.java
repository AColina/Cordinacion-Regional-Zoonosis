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
package ve.zoonosis.model.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ve.zoonosis.controller.modulos.casos.BandejaCasosController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.municipio.CasoEspecieMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.municipio.CasoEspecieDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.parroquia.CasoEspecieDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.parroquia.CasoEspecieMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.JornadaAnimalDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.JornadaAnimalMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio.JornadaEspecieMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio.JornadaEspeciesDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.parroquia.JornadaEspecieDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.parroquia.JornadaEspecieMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.jornadasvacunaciones.BandejaJornadaVacunacionController;
import ve.zoonosis.controller.modulos.novdedades.BandejaNovedadesController;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.components.AbstractInternalListener;

/**
 *
 * @author clases
 */
public class TemplateListeners {

    public static Bandeja getJornadaVacunacionBandeja() {
        return new Bandeja("Jornadas de Vacunación", BandejaJornadaVacunacionController.class);
    }

    public static CerrarSesion getCerrarSesion() {
        return new CerrarSesion();
    }

    public static Bandeja getCasosBandeja() {
        return new Bandeja("Casos", BandejaCasosController.class);
    }

    public static Bandeja getNovedadesBandeja() {
        return new Bandeja("Novedades", BandejaNovedadesController.class);
    }

    public static Bandeja getJornadaAnimalDiarioPorMunicipio() {
        return new Bandeja("Estadistica animal diaria por municipio", JornadaAnimalDiarioPorMunicipioController.class);
    }

//    public static Bandeja getJornadaAnimalSemanalPorMunicipio() {
//        return new Bandeja("Estadistica animal semanal por municipio", JornadaAnimalSemanaPorMunicipioController.class);
//    }

    public static Bandeja getJornadaAnimalMensualPorMunicipio() {
        return new Bandeja("Estadistica animal mensual por municipio", JornadaAnimalMensualPorMunicipioController.class);
    }

    public static Bandeja getJornadaAnimalDiarioPorParroquia() {
        return new Bandeja("Estadistica animal diaria por parroquia", JornadaAnimalDiarioPorParroquiaController.class);
    }

    public static Bandeja getJornadaEspecieDiarioPorMunicipio() {
        return new Bandeja("Estadistica de especies diaria por municipio", JornadaEspeciesDiarioPorMunicipioController.class);
    }

    public static Bandeja getJornadaEspecieDiarioPorParroquia() {
        return new Bandeja("Estadistica de especies diaria por parroquia", JornadaEspecieDiarioPorParroquiaController.class);
    }

    public static Bandeja getCasoAnimalDiarioPorMunicipio() {
        return new Bandeja("Estadistica de animal diaria por municipio", CasoAnimalDiarioPorMunicipioController.class);
    }

    public static Bandeja getCasoAnimalDiarioPorParroquia() {
        return new Bandeja("Estadistica animal diaria por parroquia", CasoAnimalDiarioPorParroquiaController.class);
    }

    public static Bandeja getCasoEspecieDiarioPorMunicipio() {
        return new Bandeja("Estadistica de especie diaria por municipio", CasoEspecieDiarioPorMunicipioController.class);
    }

    public static Bandeja getCasoEspecieDiarioPorParroquia() {
        return new Bandeja("Estadistica de especie diaria por parroquia", CasoEspecieDiarioPorParroquiaController.class);
    }

    public static Bandeja getJornadaAnimalMensualPorParroquia() {
        return new Bandeja("Estadistica animal mensual por parroquia", JornadaAnimalMensualPorParroquiaController.class);
    }

    public static Bandeja getCasoAnimalMensualPorMunicipio() {
        return new Bandeja("Estadistica de animal mensual por municipio", CasoAnimalMensualPorMunicipioController.class);
    }

    public static Bandeja getCasoAnimalMensualPorParroquia() {
        return new Bandeja("Estadistica animal mensual por parroquia", CasoAnimalMensualPorParroquiaController.class);
    }

    public static Bandeja getJornadaEspecieMensualPorMunicipio() {
        return new Bandeja("Estadistica especie mensual por municipio", JornadaEspecieMensualPorMunicipioController.class);
    }

    public static Bandeja getJornadaEspecieMensualPorParroquia() {
        return new Bandeja("Estadistica especie mensual por parroquia", JornadaEspecieMensualPorParroquiaController.class);
    }

    public static Bandeja getCasoEspecieMensualPorMunicipio() {
        return new Bandeja("Estadistica especie mensual por municipio", CasoEspecieMensualPorMunicipioController.class);
    }

    public static Bandeja getCasoEspecieMensualPorParroquia() {
        return new Bandeja("Estadistica especie mensual por parroquia", CasoEspecieMensualPorParroquiaController.class);
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

    public static class CerrarSesion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginController.cerrarSesion();
        }

    }
}
