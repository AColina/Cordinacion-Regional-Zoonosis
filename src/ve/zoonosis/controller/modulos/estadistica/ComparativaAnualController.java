/*
 * Copyright 2016 FP03.
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

import com.megagroup.utilidades.Logger;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import ve.zoonosis.vistas.modulos.estadistica.ComparativaAnual;
import windows.RequestBuilder;

/**
 *
 * @author FP03
 */
public class ComparativaAnualController extends ComparativaAnual {

    private static final Logger LOG = Logger.getLogger(ComparativaAnualController.class);
    private RequestBuilder rb;
    JFreeChart grafica;
    DefaultCategoryDataset Datos;

    public ComparativaAnualController() {
        inicializar();
    }

    @Override
    public JButton getAceptar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JButton getGuardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JButton getCancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public final void inicializar() {
        mButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Datos = new DefaultCategoryDataset();
                generarEstadistica();
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        try {
            rb = new RequestBuilder("services/calendario/YearList.php");
            List<HashMap> listaYears = rb.ejecutarJson(List.class, HashMap.class);
            if (listaYears == null || listaYears.isEmpty()) {
                mButton1.setEnabled(false);
            } else {
                if (listaYears.size() >= 2) {
                    years1.addItem(listaYears.get(0).get("year"));
                    years2.addItem(listaYears.get(1).get("year"));

                    for (int i = 2; i < listaYears.size(); i++) {
                        years1.addItem(listaYears.get(i).get("year"));
                        years2.addItem(listaYears.get(i).get("year"));

                    }
                } else {
                    mButton1.setEnabled(false);
                }
            }
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(ComparativaAnualController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generarEstadistica() {
        panel.removeAll();
        // Primer año
        String year;
        for (int j = 0; j < 2; j++) {
            if (j == 0) {
                year = years1.getSelectedItem().toString();
            } else {
                year = years2.getSelectedItem().toString();
            }

            for (Integer i = 1; i < 13; i++) {
                int mes;

                if (i < 10) {

                    mes = Integer.parseInt(0 + i.toString());
                } else {
                    mes = i;
                }
                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(year), mes-1, 1);

                final Date fecha = gc.getTime();
                RequestBuilder rb = new RequestBuilder();
                if (porCasoJornada.getSelectedItem().toString().equalsIgnoreCase("Jornadas")) {
                    try {
                        rb = new RequestBuilder("services/funcionales/AnimalWs/ObtenerListaAnimalesDeJornada.php",
                                new HashMap<String, Object>() {
                                    {
                                        put("dia", fecha);
                                    }
                                });

                        // System.out.println("Cantidad animales = " + cantidadAnimales);
                    } catch (URISyntaxException ex) {
                        java.util.logging.Logger.getLogger(ComparativaAnualController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        rb = new RequestBuilder("services/funcionales/AnimalWs/ObtenerListaAnimalesDeCaso.php",
                                new HashMap<String, Object>() {
                                    {
                                        put("dia", fecha);
                                    }
                                });

                        // System.out.println("Cantidad animales = " + cantidadAnimales);
                    } catch (URISyntaxException ex) {
                        java.util.logging.Logger.getLogger(ComparativaAnualController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                Integer cantidadAnimales = rb.ejecutarJson(Integer.class);
                if (cantidadAnimales == null) {
                    cantidadAnimales = 0;
                }
                System.out.println(mes);
                System.out.println(cantidadAnimales+" - "+obtenerNombreMes(mes));
                Datos.addValue(cantidadAnimales, year, obtenerNombreMes(mes));

            }
        }
        grafica = ChartFactory.createBarChart("Animales Por año",
                "Meses", "Animales", Datos,
                PlotOrientation.VERTICAL, true, true, false);
        grafica.setBackgroundPaint(Color.white);
        final CategoryPlot plot = grafica.getCategoryPlot();
        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
        ChartPanel Panel = new ChartPanel(grafica);
        JFrame a = new JFrame();
       // frame b = new frame();
        a.getContentPane().add(Panel);
        a.pack();
        panel.add(a.getContentPane());
        panel.repaint();


    }

    private String obtenerNombreMes(int numeroMes) {
        switch (numeroMes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";

        }
        return null;
    }

}
