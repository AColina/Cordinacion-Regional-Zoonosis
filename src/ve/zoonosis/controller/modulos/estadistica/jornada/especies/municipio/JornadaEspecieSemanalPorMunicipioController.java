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
package ve.zoonosis.controller.modulos.estadistica.jornada.especies.municipio;

import ve.zoonosis.controller.modulos.estadistica.jornada.animales.municipio.*;
import com.megagroup.utilidades.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import ve.zoonosis.controller.modulos.casos.NuevoCasoController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.utils.RandomColor;
import ve.zoonosis.vistas.componente.piechart.ChartObject;
import ve.zoonosis.vistas.modulos.estadistica.jornada.animales.municipio.JornadaAnimalSemanaPorMunicipio;
import ve.zoonosis.vistas.modulos.estadistica.jornada.especies.municipio.JornadaEspecieSemanaPorMunicipio;
import windows.RequestBuilder;

/**
 *
 * @author Kelvin López
 */
public class JornadaEspecieSemanalPorMunicipioController extends JornadaEspecieSemanaPorMunicipio {

    private static final Logger LOG = Logger.getLogger(NuevoCasoController.class);
    private RequestBuilder rb;

    public JornadaEspecieSemanalPorMunicipioController() {
        inicializar();

    }

    @Override
    public final void inicializar() {
        iniForm();
       // dia.setDate(new Date());
        // dia.setMaxSelectableDate(new Date());

        //  municipios.adda
        try {
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
                    obtenerEstadistica();
                    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(JornadaEspecieSemanalPorMunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerEstadistica() {
        RandomColor rc = new RandomColor();
        System.out.println("el boton sirve");
        try {
            final String nombreMunicipio = ((Municipio) municipios.getSelectedItem()).getNombre();
            //    final Date fecha = dia.getDate();
            rb = new RequestBuilder("services/funcionales/EspecieWs/ObtenerListaEspeciesPorSemanaDeJornadaPorMunicipio.php",
                    new HashMap<String, Object>() {
                        {
                            put("nombreMunicipio", nombreMunicipio);
                            put("semana", ((Semana)semanas.getSelectedItem()).getNombre());
                            put("year",years.getSelectedItem());
                            //           put("dia", fecha);
                        }
                    });
            List<HashMap> valores = rb.ejecutarJson(List.class, HashMap.class);

            if (valores == null || valores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron registros", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                List<HashMap> ordValores = sumarRepetidos(valores);
                List<ChartObject> lista = new ArrayList<>();
                int maxval = 0;
                for (HashMap valor : ordValores) {
                    ChartObject chart = new ChartObject(valor.get("nombre").toString(), Double.parseDouble(valor.get("cantidad").toString()), rc.obtenerColorAleatorio());
                    lista.add(chart);
                    maxval = maxval + Integer.parseInt(valor.get("cantidad").toString());
                    System.out.println(valor.get("nombre"));
                    System.out.println(valor.get("cantidad"));

                }
                pieChartPanel2.cargarPie(maxval, lista);
                pieChartPanel2.repaint();
            }
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(JornadaEspecieSemanalPorMunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private List<HashMap> sumarRepetidos(final List<HashMap> valores) {
        boolean esRepetido;
        List<HashMap> clon = new ArrayList<>();
        List<HashMap> sumados = new ArrayList<>();
        clon.addAll(valores);

        for (int i = 0; i < valores.size(); i++) {
            esRepetido = false;
            final int index = i;
            final int sumf;
            int sumt = 0;
            for (HashMap sumado : sumados) {
                if (sumado.get("nombre").toString().equalsIgnoreCase(valores.get(i).get("nombre").toString())) {
                    esRepetido = true;
                }
            }
            if (esRepetido) {
                continue;
            }
            for (int j = i; j < clon.size(); j++) {

                if (clon.get(j).get("nombre").toString().equalsIgnoreCase(valores.get(i).get("nombre").toString())) {
                    sumt = sumt + Integer.parseInt(clon.get(j).get("cantidad").toString());
                }

            }
            sumf = sumt;
            sumados.add(new HashMap<String, Object>() {
                {
                    put("nombre", valores.get(index).get("nombre"));
                    put("cantidad", sumf);
                }
            });

        }

        return sumados;
    }

    @Override
    public JButton getAceptar() {
        return null;
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
