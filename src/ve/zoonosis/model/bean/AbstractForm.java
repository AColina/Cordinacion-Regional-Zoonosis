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

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.megagroup.bean.FormController;
import com.megagroup.reflection.ReflectionUtils;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import ve.zoonosis.model.entidades.EntidadGlobal;

/**
 *
 * @author angel.colina
 * @param <Entity>
 */
public abstract class AbstractForm< Entity extends EntidadGlobal> extends JPanel implements FormController {

    protected ValidarFormularioActionListener formularioActionListener;
    protected Entity entity;

    {
        formularioActionListener = new ValidarFormularioActionListener();
    }

    public AbstractForm() {
    }

    public AbstractForm(Entity entity) {
        this.entity = entity;

    }

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

    protected final void autoCreateValidateForm(Class<? extends EntidadGlobal>... entitys) {
        for (Class<? extends EntidadGlobal> e : entitys) {
            Field[] fields = ReflectionUtils.getAllFields(e);
            for (Field field : fields) {
                Annotation[] annotation = field.getAnnotations();
                if (annotation.length > 0) {
                    boolean add = false;
                    for (Annotation annotation1 : annotation) {
                        if (annotation1.annotationType().getAnnotation(JacksonAnnotation.class) == null) {
                            add = true;
                        }
                    }
                    if (add) {
                        Field f = ReflectionUtils.getField(this, field.getName());
                        if (f != null && Component.class.isAssignableFrom(f.getType())) {
                            Component c = ReflectionUtils.runGetter(f, this);
                            agregarValidEvent(c, formularioActionListener);
                        }
                    }
                }
            }
        }
    }

    protected void agregarValidEvent(Component o, ValidarFormularioActionListener evt) {
        if (o instanceof JTextComponent) {
            o.addKeyListener(evt);
        } else if (o instanceof JComboBox) {
            ((JComboBox) o).addActionListener(evt);
        } else if (o instanceof JDateChooser) {
            ((JTextField) ((JDateChooser) o).getDateEditor().getUiComponent()).addKeyListener(evt);
            ((JDateChooser) o).getDateEditor().addPropertyChangeListener(evt);
        }
    }

    public class ValidarFormularioActionListener implements KeyListener, ActionListener,
            CaretListener, ChangeListener,
            PropertyChangeListener {

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

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {
                boolean enable = validar();
                if (getAceptar() != null) {
                    getAceptar().setEnabled(enable);
                }
                if (getGuardar() != null) {
                    getGuardar().setEnabled(enable);
                }
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

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}
