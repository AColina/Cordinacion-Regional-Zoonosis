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
package ve.zoonosis.controller.modulos.informe.caso.parroquia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import ve.zoonosis.controller.DialogMessageController;
import ve.zoonosis.controller.modulos.estadistica.caso.animales.parroquia.CasoAnimalSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.novdedades.VerNovedadController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.utils.PDFCreator;
import ve.zoonosis.utils.RandomColor;
import ve.zoonosis.vistas.modulos.informes.caso.parroquia.InformeCasoParroquiaMensual;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class InformeCasoParroquiaMensualController extends InformeCasoParroquiaMensual {

    private static final Logger LOG = Logger.getLogger(InformeCasoParroquiaMensualController.class.getName());
    private RequestBuilder rb;
    private JFileChooser archivo;
    private PDFCreator pdfc = new PDFCreator();
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public InformeCasoParroquiaMensualController() {
        inicializar();
    }

    @Override
    public final void inicializar() {
        jLabel1.setText("");
        btnImprimir.setEnabled(false);
        btnGuardar.setEnabled(false);
        jLabel1.setIcon(null);
        jLabel1.repaint();
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
    //    dia.setDate(new Date());

        //  municipios.adda
        try {
            //rb = new RequestBuilder("services/administracion/ParroquiaWs/ListaParroquias.php");
            //List<Parroquia> listaParroquias = rb.ejecutarJson(List.class, Parroquia.class);
            mButton1.setEnabled(false);
            rb = new RequestBuilder("services/administracion/MunicipioWs/ListaMunicipios.php");
            List<Municipio> listaMunicipio = rb.ejecutarJson(List.class, Municipio.class);
            if (listaMunicipio != null) {
                municipio.setModel(new DefaultComboBoxModel(listaMunicipio.toArray()));
                municipio.setSelectedIndex(-1);
            }
            municipio.addActionListener(new MunicipioListener(parroquias));
            parroquias.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    mButton1.setEnabled(true);
                    if (parroquias.getSelectedItem() == null) {
                        mButton1.setEnabled(false);
                    }
                }

            });
            //if (listaParroquias == null || listaParroquias.isEmpty()) {
            //  mButton1.setEnabled(false);
            //} else if (parroquias != null) {
            //parroquias.setModel(new DefaultComboBoxModel(listaParroquias.toArray()));
            //parroquias.setSelectedIndex(0);
            //}

            mButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        obtenerInforme();
                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (IOException ex) {
                        Logger.getLogger(InformeCasoParroquiaMensualController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerInforme() throws IOException {
        RandomColor rc = new RandomColor();
        pdfc.clearPage();
        pdfc.addCenterText(20, "Informe");
        jLabel1.setIcon(null);
        jLabel1.repaint();
        btnImprimir.setEnabled(false);
        btnGuardar.setEnabled(false);

        try {
            final String nombreParroquia = ((Parroquia) parroquias.getSelectedItem()).getNombre();
            //    final Date fecha = dia.getDate();
            int numeroMes = mes.getMonth();
            int numeroAño = año.getYear();
            GregorianCalendar gc = new GregorianCalendar(numeroAño, numeroMes, 1);
            System.out.println(gc.getTime());
            final Date fecha = gc.getTime();
            //    final Date fecha = dia.getDate();
            rb = new RequestBuilder("services/funcionales/AnimalWs/ObtenerListaPorMesDeCasoPorParroquia.php",
                    new HashMap<String, Object>() {
                        {
                            put("nombreParroquia", nombreParroquia);
                            put("dia", fecha);
                        }
                    });
            List<HashMap> valores = rb.ejecutarJson(List.class, HashMap.class);
            HashMap v = new HashMap();

            if (valores == null || valores.isEmpty()) {
                new DialogMessageController("No se encontraron registros", "Aviso");
                //JOptionPane.showMessageDialog(null, "No se encontraron registros", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                btnImprimir.setEnabled(true);
                btnGuardar.setEnabled(true);
                for (HashMap valore : valores) {
                    Iterator i = valore.entrySet().iterator();
                    while (i.hasNext()) {
                        Map.Entry e = (Map.Entry) i.next();
                        if (v.get(e.getKey()) != null && isNumeric((String) e.getValue())) {
                            v.put(e.getKey(), (int) v.get(e.getKey()) + Integer.valueOf((String) e.getValue()));
                        } else if (isNumeric((String) e.getValue())) {
                            v.put(e.getKey(), Integer.valueOf((String) e.getValue()));
                        }
                    }
                }
                pdfc.addNewLine();
                int positivos = ((int) v.get("cantidadPositivos"));
                int total = ((int) v.get("cantidadIngresado"));
                int negativos = total - positivos;
                pdfc.addLeftText("Informe sobre los casos en el mes de " + obtenerNombreMes(numeroMes) + " en la parroquia " + parroquias.getSelectedItem() + " del Municipio " + municipio.getSelectedItem()
                        + ", durante este periodo de tiempo se pudo observar y llevar un registro de la cantidad de casos"
                        + "recibidos por nuestra jurisdiccion dando los siguientes resultados: Casos positivos " + positivos + " (" + decimalFormat.format(((float) positivos / (float) total) * 100) + "%), "
                        + "casos negativos " + negativos + " (" + decimalFormat.format(((float) negativos / (float) total) * 100) + "%), total de casos " + (total) + ".");
                ImageIcon ii = new ImageIcon(pdfc.getImagePage(0));
                jLabel1.setIcon(ii);
                jLabel1.repaint();
//                List<HashMap> ordValores = sumarRepetidos(valores);
//                List<ChartObject> lista = new ArrayList<>();
//                int maxval = 0;
//                for (HashMap valor : ordValores) {
//                    ChartObject chart = new ChartObject(valor.get("nombre").toString(), 
//                            Double.parseDouble(valor.get("cantidadIngresado").toString()), rc.obtenerColorAleatorio());
//                    lista.add(chart);
//                    maxval = maxval + Integer.parseInt(valor.get("cantidadIngresado").toString());
//                    System.out.println(valor.get("nombre"));
//                    System.out.println(valor.get("cantidadIngresado"));
//
//                }
//                pieChartPanel2.cargarPie(maxval, lista);
//                pieChartPanel2.repaint();
            }
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(CasoAnimalSemanalPorParroquiaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InformeCasoParroquiaSemanalController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public boolean isNumeric(String value) {
        try {
            Integer.valueOf(value);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public JButton getAceptar() {
        return btnImprimir;
    }

    @Override
    public JButton getGuardar() {
        return btnGuardar;
    }

    @Override
    public JButton getCancelar() {
        return null;
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {
        try {
            try {
                pdfc.saveDocument("temp.pdf");
            } catch (IOException ex) {
                Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
            }
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("temp.pdf");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);

            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void guardar() {
        if (archivo.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                pdfc.saveDocument(archivo.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void cancelar() {

    }

}
