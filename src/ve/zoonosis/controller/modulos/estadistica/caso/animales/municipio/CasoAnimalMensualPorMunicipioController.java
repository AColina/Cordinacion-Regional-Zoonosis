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
package ve.zoonosis.controller.modulos.estadistica.caso.animales.municipio;

import com.megagroup.utilidades.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import ve.zoonosis.controller.DialogMessageController;
import ve.zoonosis.controller.modulos.casos.NuevoCasoController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.utils.RandomColor;
import ve.zoonosis.model.components.piechart.ChartObject;
import ve.zoonosis.vistas.modulos.estadistica.caso.animales.municipio.CasoAnimalMensualPorMunicipio;
import windows.RequestBuilder;

/**
 *
 * @author Kelvin López
 */
public class CasoAnimalMensualPorMunicipioController extends CasoAnimalMensualPorMunicipio {

    private static final Logger LOG = Logger.getLogger(NuevoCasoController.class);
    private RequestBuilder rb;

    public CasoAnimalMensualPorMunicipioController() {
        inicializar();

    }

    @Override
    public final void inicializar() {
        iniForm();
        JSpinner s = (JSpinner) año.getSpinner();
        JTextField t = (JTextField) s.getEditor();
        t.setEditable(false);
        año.setMaximum(1900 + new Date().getYear());
        PropertyChangeListener validator = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fechaActual = new Date();
                if (evt.getPropertyName().equals("year") || evt.getPropertyName().equals("month")) {
                    System.out.println("entra");
                    // System.out.println(año.getYear());
                    if (año.getYear() == 1900 + fechaActual.getYear()) {
                        if (mes.getMonth() > fechaActual.getMonth()) {
                            mes.setMonth(fechaActual.getMonth());

                        }

                    }
                }
            }
        };

        año.addPropertyChangeListener(validator);
        mes.addPropertyChangeListener(validator);
//        dia.setDate(new Date());

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

    private void obtenerEstadistica() {
        RandomColor rc = new RandomColor();
        System.out.println("el boton sirve");
        try {
            final String nombreMunicipio = ((Municipio) municipios.getSelectedItem()).getNombre();
            int numeroMes = mes.getMonth();

            int numeroAño = año.getYear();
            GregorianCalendar gc = new GregorianCalendar(numeroAño, numeroMes, 1);
            System.out.println(gc.getTime());
            final Date fecha = gc.getTime();
            // final String fecha = numeroMes+"-"+numeroAño;
            System.out.println(numeroMes);
            System.out.println(numeroAño);
            rb = new RequestBuilder("services/funcionales/AnimalWs/ObtenerListaAnimalesPorMesDeCasoPorMunicipio.php",
                    new HashMap<String, Object>() {
                        {
                            put("nombreMunicipio", nombreMunicipio);
                            put("dia", fecha);
                        }
                    });
            List<HashMap> valores = rb.ejecutarJson(List.class, HashMap.class);

            if (valores == null || valores.isEmpty()) {
                new DialogMessageController("No se encontraron registros", "Aviso");
                //JOptionPane.showMessageDialog(null, "No se encontraron registros", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                List<HashMap> ordValores = sumarRepetidos(valores);
                List<ChartObject> lista = new ArrayList<>();
                int maxval = 0;
                for (HashMap valor : ordValores) {
                    ChartObject chart = new ChartObject(valor.get("nombre").toString(), Double.parseDouble(valor.get("cantidadIngresado").toString()), rc.obtenerColorAleatorio());
                    lista.add(chart);
                    maxval = maxval + Integer.parseInt(valor.get("cantidadIngresado").toString());
                    System.out.println(valor.get("nombre"));
                    System.out.println(valor.get("cantidadIngresado"));

                }
                pieChartPanel2.cargarPie(maxval, lista);
                pieChartPanel2.repaint();
            }
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(CasoAnimalMensualPorMunicipioController.class.getName()).log(Level.SEVERE, null, ex);
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
                    sumt = sumt + Integer.parseInt(clon.get(j).get("cantidadIngresado").toString());
                }

            }
            sumf = sumt;
            sumados.add(new HashMap<String, Object>() {
                {
                    put("nombre", valores.get(index).get("nombre"));
                    put("cantidadIngresado", sumf);
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
