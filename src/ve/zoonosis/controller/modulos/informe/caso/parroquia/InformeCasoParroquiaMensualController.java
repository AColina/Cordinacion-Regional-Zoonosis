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
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.listener.MunicipioListener;
import ve.zoonosis.vistas.modulos.informes.caso.parroquia.InformeCasoParroquiaMensual;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class InformeCasoParroquiaMensualController extends InformeCasoParroquiaMensual {

    private static final Logger LOG = Logger.getLogger(InformeCasoParroquiaMensualController.class.getName());
    private RequestBuilder rb;

    public InformeCasoParroquiaMensualController() {
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
                    //obtenerEstadistica();
                    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } catch (URISyntaxException | RuntimeException ex) {
            LOG.log(Level.SEVERE, null, ex);
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
