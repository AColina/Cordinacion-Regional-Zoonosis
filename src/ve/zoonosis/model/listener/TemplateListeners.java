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

import com.megagroup.reflection.ReflectionUtils;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import ve.zoonosis.controller.funcionales.CrearEspecieController;
import ve.zoonosis.controller.modulos.ImportarController;
import ve.zoonosis.controller.modulos.casos.BandejaCasosController;
import ve.zoonosis.controller.modulos.estadistica.ComparativaAnualController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalSemanalPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.municipio.CasoEspecieMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.municipio.CasoEspecieDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.municipio.CasoEspecieSemanalPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.parroquia.CasoEspecieDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.parroquia.CasoEspecieMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.caso.especies.parroquia.CasoEspecieSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.JornadaAnimalDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.JornadaAnimalMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.JornadaAnimalSemanalPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio.JornadaEspecieMensualPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio.JornadaEspecieSemanalPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio.JornadaEspeciesDiarioPorMunicipioController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.parroquia.JornadaEspecieDiarioPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.parroquia.JornadaEspecieMensualPorParroquiaController;
import ve.zoonosis.controller.modulos.estadistica.jornada.especies.parroquia.JornadaEspecieSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.informe.caso.municipio.InformeCasoMunicipioMensualController;
import ve.zoonosis.controller.modulos.informe.caso.municipio.InformeCasoMunicipioSemanalController;
import ve.zoonosis.controller.modulos.informe.caso.parroquia.InformeCasoParroquiaMensualController;
import ve.zoonosis.controller.modulos.informe.caso.parroquia.InformeCasoParroquiaSemanalController;
import ve.zoonosis.controller.modulos.informe.jornada.municipio.InformeVacunacionMunicipioMensualController;
import ve.zoonosis.controller.modulos.informe.jornada.municipio.InformeVacunacionMunicipioSemanalController;
import ve.zoonosis.controller.modulos.informe.jornada.parroquia.InformeVacunacionParroquiaMensualController;
import ve.zoonosis.controller.modulos.informe.jornada.parroquia.InformeVacunacionParroquiaSemanalController;
import ve.zoonosis.controller.modulos.jornadasvacunaciones.BandejaJornadaVacunacionController;
import ve.zoonosis.controller.modulos.novdedades.BandejaNovedadesController;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.controller.seguridad.NuevoUsuarioController;
import ve.zoonosis.model.components.AbstractInternalListener;
import ve.zoonosis.vistas.otros.AcercaDe;

/**
 *
 * @author angel.colina
 */
@SuppressWarnings("ResultOfObjectAllocationIgnored")
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
    
    public static Bandeja getNovedadesBandejaVer() {
        return new Bandeja("Novedades", BandejaNovedadesController.class,true);
    }

    public static Bandeja getInformeJornadaSemanalMunicipio(){
        return new Bandeja("Informe semanal por municipio",InformeVacunacionMunicipioSemanalController.class);
    }
    
    public static Bandeja getInformeJornadaMensualMunicipio(){
        return new Bandeja("Informe mensual por municipio",InformeVacunacionMunicipioMensualController.class);
    }
    
    /*public static Bandeja getInformeJornadaAnualMunicipio(){
        return new Bandeja("Informe semanal por municipio",InformeVacunacionMunicipioAnualController.class);
    }*/
    
     public static Bandeja getInformeJornadaSemanalParroquia(){
        return new Bandeja("Informe semanal por parroquia",InformeVacunacionParroquiaSemanalController.class);
    }
    
    public static Bandeja getInformeJornadaMensualParroquia(){
        return new Bandeja("Informe mensual por parroquia",InformeVacunacionParroquiaMensualController.class);
    }
    
   /* public static Bandeja getInformeJornadaAnualParroquia(){
        return new Bandeja("Informe semanal por municipio",InformeVacunacionParroquiaAnualController.class);
    }*/
    
    public static Bandeja getInformeCasoSemanalMunicipio(){
        return new Bandeja("Informe semanal por municipio",InformeCasoMunicipioSemanalController.class);
    }
    
    public static Bandeja getInformeCasoMensualMunicipio(){
        return new Bandeja("Informe mensual por municipio",InformeCasoMunicipioMensualController.class);
    }
    
    /*public static Bandeja getInformeJornadaAnualMunicipio(){
        return new Bandeja("Informe semanal por municipio",InformeVacunacionMunicipioAnualController.class);
    }*/
    
     public static Bandeja getInformeCasoSemanalParroquia(){
        return new Bandeja("Informe semanal por parroquia",InformeCasoParroquiaSemanalController.class);
    }
    
    public static Bandeja getInformeCasoMensualParroquia(){
        return new Bandeja("Informe mensual por parroquia",InformeCasoParroquiaMensualController.class);
    }
    
   /* public static Bandeja getInformeJornadaAnualParroquia(){
        return new Bandeja("Informe semanal por municipio",InformeVacunacionParroquiaAnualController.class);
    }*/
    
    public static Bandeja getJornadaAnimalDiarioPorMunicipio() {
        return new Bandeja("Estadistica animal diaria por municipio", JornadaAnimalDiarioPorMunicipioController.class);
    }

    public static Bandeja getJornadaAnimalSemanalPorMunicipio() {
        return new Bandeja("Estadistica animal semanal por municipio", JornadaAnimalSemanalPorMunicipioController.class);
    }

    public static Bandeja getJornadaAnimalMensualPorMunicipio() {
        return new Bandeja("Estadistica animal mensual por municipio", JornadaAnimalMensualPorMunicipioController.class);
    }

    public static Bandeja getJornadaAnimalDiarioPorParroquia() {
        return new Bandeja("Estadistica animal diaria por parroquia", JornadaAnimalDiarioPorParroquiaController.class);
    }

    public static Bandeja getJornadaEspecieDiarioPorMunicipio() {
        return new Bandeja("Estadistica de especie diaria por municipio", JornadaEspeciesDiarioPorMunicipioController.class);
    }

    public static Bandeja getJornadaEspecieDiarioPorParroquia() {
        return new Bandeja("Estadistica de especie diaria por parroquia", JornadaEspecieDiarioPorParroquiaController.class);
    }

    public static Bandeja getCasoAnimalDiarioPorMunicipio() {
        return new Bandeja("Estadistica animal diaria por municipio", CasoAnimalDiarioPorMunicipioController.class);
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
        return new Bandeja("Estadistica animal mensual por municipio", CasoAnimalMensualPorMunicipioController.class);
    }

    public static Bandeja getCasoAnimalMensualPorParroquia() {
        return new Bandeja("Estadistica animal mensual por parroquia", CasoAnimalMensualPorParroquiaController.class);
    }

    public static Bandeja getJornadaEspecieMensualPorMunicipio() {
        return new Bandeja("Estadistica por especie mensual por municipio", JornadaEspecieMensualPorMunicipioController.class);
    }

    public static Bandeja getJornadaEspecieMensualPorParroquia() {
        return new Bandeja("Estadistica por especie mensual por parroquia", JornadaEspecieMensualPorParroquiaController.class);
    }

    public static Bandeja getCasoEspecieMensualPorMunicipio() {
        return new Bandeja("Estadistica por especie mensual por municipio", CasoEspecieMensualPorMunicipioController.class);
    }

    public static Bandeja getCasoEspecieMensualPorParroquia() {
        return new Bandeja("Estadistica por especie mensual por parroquia", CasoEspecieMensualPorParroquiaController.class);
    }

    public static Bandeja getJornadaAnimalSemanalPorParroquia() {
        return new Bandeja("Estadistica animal semanal por parroquia", JornadaAnimalSemanalPorParroquiaController.class);
    }

    public static Bandeja getCasoAnimalSemanalPorMunicipio() {
        return new Bandeja("Estadistica animal semanal por municipio", CasoAnimalSemanalPorMunicipioController.class);
    }

    public static Bandeja getCasoAnimalSemanalPorParroquia() {
        return new Bandeja("Estadistica animal semanal por parroquia", CasoAnimalSemanalPorParroquiaController.class);
    }

    public static Bandeja getJornadaEspecieSemanalPorMunicipio() {
        return new Bandeja("Estadistica por especie semanal por municipio", JornadaEspecieSemanalPorMunicipioController.class);
    }

    public static Bandeja getJornadaEspecieSemanalPorParroquia() {
        return new Bandeja("Estadistica por especie semanal por parroquia", JornadaEspecieSemanalPorParroquiaController.class);
    }

    public static Bandeja getCasoEspecieSemanalPorMunicipio() {
        return new Bandeja("Estadistica por especie semanal por municipio", CasoEspecieSemanalPorMunicipioController.class);
    }

    public static Bandeja getCasoEspecieSemanalPorParroquia() {
        return new Bandeja("Estadistica por especie semanal por parroquia", CasoEspecieSemanalPorParroquiaController.class);
    }

    public CrearDialogo getNuevoUsuario() {
        return new CrearDialogo(NuevoUsuarioController.class);
    }

    public CrearDialogo getDatosDeUsuario() {
        return new CrearDialogo(NuevoUsuarioController.class, LoginController.getUsuario());
    }

    public CrearDialogo getImportar() {
        return new CrearDialogo(ImportarController.class);
    }

    public CrearDialogo getNuevaEspecie() {
        return new CrearDialogo(CrearEspecieController.class);
    }

    public SingleInstance getAcercaDe() {
        return new SingleInstance(AcercaDe.class);
    }

    public static class Bandeja extends AbstractInternalListener implements ActionListener {

        private final String titulo;
        private final Class clase;
        private final Object[] objects;

        public Bandeja(String titulo, Class clase,Object... objects) {
            this.titulo = titulo;
            this.clase = clase;
            this.objects = objects;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            crearInternalFrame(titulo, clase,objects);
        }
    }

    public class CrearDialogo implements ActionListener {

        private final Class<? extends Container> container;
        private final Object[] values;

        public CrearDialogo(Class<? extends Container> container, Object... values) {
            this.container = container;
            this.values = values;
        }

        @Override

        public void actionPerformed(ActionEvent e) {
            ReflectionUtils.newInstanceForVector(container, values);
        }

    }

    public class SingleInstance implements ActionListener {

        private final Class<? extends Container> container;

        public SingleInstance(Class<? extends Container> container) {
            this.container = container;
        }

        @Override

        public void actionPerformed(ActionEvent e) {
            try {
                JComponent c = ReflectionUtils.runMethod(container, container.getDeclaredMethod("getInstance"));
                c.show();
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(TemplateListeners.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static class CerrarSesion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginController.cerrarSesion();
        }

    }

    public static Bandeja getComparativaAnual() {
        return new Bandeja("Estadistica Comparativa Anual", ComparativaAnualController.class);
    }
}
