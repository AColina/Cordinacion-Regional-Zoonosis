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
package ve.zoonosis.model.bean;

import com.megagroup.bean.FormController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author angel.colina
 * @param <Entidad>
 */
public abstract class AbstractForm< Entidad extends Object> extends JPanel implements FormController {

    protected void iniForm() {
        if (getAceptar() != null) {
            getAceptar().addActionListener(new AceptarActionListener());
        }
        if (getGuardar() != null) {
            getGuardar().addActionListener(new GuardarActionListener());
        }
        if (getCancelar() != null) {
            getCancelar().addActionListener(new CancelarActionListener());
        }
    }

    public class ValidarFormularioActionListener extends MouseAdapter implements KeyListener, ActionListener, 
                                                                                CaretListener, ChangeListener {

        public ValidarFormularioActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean enable = validar();
            if (getAceptar() != null) {
                getAceptar().setEnabled(enable);
            }
            if (getGuardar() != null) {
                getGuardar().setEnabled(enable);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            boolean enable = validar();
            if (getAceptar() != null) {
                getAceptar().setEnabled(enable);
            }
            if (getGuardar() != null) {
                getGuardar().setEnabled(enable);
            }
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            boolean enable = validar();
            if (getAceptar() != null) {
                getAceptar().setEnabled(enable);
            }
            if (getGuardar() != null) {
                getGuardar().setEnabled(enable);
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            boolean enable = validar();
            if (getAceptar() != null) {
                getAceptar().setEnabled(enable);
            }
            if (getGuardar() != null) {
                getGuardar().setEnabled(enable);
            }
        }

    }

    protected class AceptarActionListener implements ActionListener {

        public AceptarActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            aceptar();
        }

    }

    protected class GuardarActionListener implements ActionListener {

        public GuardarActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            guardar();
        }

    }

    protected class CancelarActionListener implements ActionListener {

        public CancelarActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cancelar();
        }

    }

    public abstract JButton getAceptar();

    public abstract JButton getGuardar();

    public abstract JButton getCancelar();
}
