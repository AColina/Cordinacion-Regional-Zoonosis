/*
 * Copyright 2016 Gustavo.
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
package ve.zoonosis.controller.modulos.informe.caso.municipio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio.CasoAnimalSemanalPorMunicipioController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.vistas.modulos.informes.caso.municipio.InformeCasoMunicipioSemanal;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class InformeCasoMunicipioSemanalController extends InformeCasoMunicipioSemanal {

    private static final Logger LOG = Logger.getLogger(InformeCasoMunicipioSemanalController.class.getName());
    private RequestBuilder rb;

    public InformeCasoMunicipioSemanalController() {
        inicializar();
    }

    @Override
    public void inicializar() {
        iniForm();
       // dia.setDate(new Date());
        // dia.setMaxSelectableDate(new Date());

        //  municipios.adda
        try {
            semanas.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    Date fechaActual = new Date();
                    if (Integer.valueOf((String) years.getSelectedItem()) == 1900 + fechaActual.getYear()) {

                        if (Integer.valueOf(((Semana) semanas.getSelectedItem()).getNombre().replaceAll("[a-zA-Z\\ ]+", "")) > Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) {
                            for (int i = 0; i < semanas.getItemCount(); i++) {
                                if (Integer.valueOf(((Semana) semanas.getItemAt(i)).getNombre().replaceAll("[a-zA-Z\\ ]+", "")) == Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) {
                                    semanas.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }

                    }
                }
            });

            rb = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> listaMunicipios = rb.ejecutarJson(List.class, Municipio.class);
            if (listaMunicipios == null || listaMunicipios.isEmpty()) {
                mButton1.setEnabled(false);
            } else if (municipios != null) {
                municipios.setModel(new DefaultComboBoxModel(listaMunicipios.toArray()));
                municipios.setSelectedIndex(0);
            }
            rb = new RequestBuilder("services/calendario/YearList.php");
            List<HashMap> listaYears = rb.ejecutarJson(List.class, HashMap.class);
            if (listaYears == null || listaYears.isEmpty()) {
                mButton1.setEnabled(false);
            } else if (years != null) {
                for (HashMap year : listaYears) {
                    years.addItem(year.get("year"));
                }
                years.setSelectedIndex(0);
            }

            years.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    buscarSemanas();
                }
            });
            buscarSemanas();

            mButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //obtenerEstadistica();
                    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void buscarSemanas() {
        try {
            RequestBuilder rb = new RequestBuilder("services/calendario/WeeksForYear.php",
                    new HashMap<String, Object>() {
                        {
                            put("year", years.getSelectedItem());
                        }
                    });
            List<Semana> listaSemanas = rb.ejecutarJson(List.class, Semana.class);
            if (listaSemanas == null || listaSemanas.isEmpty()) {
                mButton1.setEnabled(false);
            } else if (years != null) {
                semanas.setModel(new DefaultComboBoxModel(listaSemanas.toArray()));
                semanas.setSelectedIndex(0);
            }
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(CasoAnimalSemanalPorMunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public JButton getAceptar() {
        return null; //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JButton getGuardar() {
        return null; //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JButton getCancelar() {
        return null; //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
