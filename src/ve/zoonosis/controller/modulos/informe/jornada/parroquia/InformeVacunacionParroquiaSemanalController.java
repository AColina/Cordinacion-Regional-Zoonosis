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
package ve.zoonosis.controller.modulos.informe.jornada.parroquia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import ve.zoonosis.controller.modulos.estadistica.jornada.animales.parroquia.JornadaAnimalSemanalPorParroquiaController;
import ve.zoonosis.controller.modulos.novdedades.VerNovedadController;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.utils.PDFCreator;
import ve.zoonosis.vistas.modulos.informes.jornada.parroquia.InformeVacunacionParroquiaSemanal;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class InformeVacunacionParroquiaSemanalController extends InformeVacunacionParroquiaSemanal {

    private static final Logger LOG = Logger.getLogger(InformeVacunacionParroquiaSemanalController.class.getName());
    private RequestBuilder rb;
    private JFileChooser archivo;
    private PDFCreator pdfc = new PDFCreator();

    public InformeVacunacionParroquiaSemanalController() {
        inicializar();

    }

    @Override
    public final void inicializar() {
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
                    // obtenerEstadistica();
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
            java.util.logging.Logger.getLogger(JornadaAnimalSemanalPorParroquiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return btnCancelar;
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
        setVisible(false);
    }

}
