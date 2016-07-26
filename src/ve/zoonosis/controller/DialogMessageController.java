/*
 * Copyright 2016 gustavog.
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
package ve.zoonosis.controller;

import com.megagroup.Application;
import com.megagroup.componentes.MDialog;
import javax.swing.JButton;
import ve.zoonosis.model.entidades.Entidad;
import ve.zoonosis.vistas.DialogMessage;

/**
 *
 * @author gustavog
 */
public class DialogMessageController extends DialogMessage<Entidad>{

    private MDialog dialog;

    public DialogMessageController(String msg, String title) {
        inicializar();
        jLabel2.setText(msg);
        dialog.setTitle(title);
    }
    
    
    
    @Override
    public JButton getAceptar() {
        return btnImprimir;
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
        return true;
    }

    @Override
    public void aceptar() {
        dialog.close();
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
    public void inicializar() {
        iniciarDialogo();
    }
    
    private void iniciarDialogo() {
        dialog = new MDialog(Application.getAPLICATION_FRAME());
        dialog.setTitle("Error");
        dialog.setResizable(false);
        dialog.showPanel(this);
    }
}
