/*
 * Copyright 2016 angel.colina.
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

import com.megagroup.model.adapter.ListDataAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;

/**
 *
 * @author angel.colina
 */
public class MunicipioListener implements ActionListener {

    private final JComboBox parroquia;

    public MunicipioListener(JComboBox<Parroquia> parroquia) {
        this.parroquia = parroquia;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox c = (JComboBox) e.getSource();
        Municipio m = (Municipio) c.getSelectedItem();
        if (m != null) {
            DefaultComboBoxModel oldModel = (DefaultComboBoxModel) parroquia.getModel();
            DefaultComboBoxModel model = new DefaultComboBoxModel<>(m.getParroquiasAsociadas().toArray());
            for (ListDataListener col : oldModel.getListDataListeners()) {
                if (col instanceof ListDataAdapter) {
                    model.addListDataListener((ListDataListener) col);
                }
            }
            parroquia.setModel(model);
        } else {
            parroquia.setModel(new DefaultComboBoxModel<>());
        }
        parroquia.setSelectedIndex(-1);
    }

}
