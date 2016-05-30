/*
 * Copyright 2016 clases.
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
package ve.zoonosis.model.components;

import com.megagroup.reflection.ReflectionUtils;
import com.megagroup.utilidades.Logger;
import java.awt.Component;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.event.InternalFrameAdapter;
import ve.zoonosis.vistas.Template;

/**
 *
 * @author clases
 */
public abstract class AbstractInternalListener {

    private static final Logger LOG = Logger.getLogger(AbstractInternalListener.class);

    private final Template TEMPLATE;

    public AbstractInternalListener(Template TEMPLATE) {
        this.TEMPLATE = TEMPLATE;
    }

    public InternalFrame crearInternalFrame(String titulo,
            Class<? extends JComponent> clase, Object... param) {

        for (Component component : TEMPLATE.getFondo().getAllFrames()) {
            if (component instanceof InternalFrame) {
                if (component.getName().equalsIgnoreCase(titulo)) {
                    InternalFrame i = (InternalFrame) component;
                    if (i.isIcon()) {
                        try {
                            i.setIcon(false);
                        } catch (PropertyVetoException ex) {
                            LOG.LOGGER.log(Level.SEVERE, null, ex);
                        }
                    }
                    component.requestFocus();

                    i.toFront();
                    return i;

                }
            }
        }

        JComponent component = (JComponent) ReflectionUtils.newInstance(clase, param);
        InternalFrame i = new InternalFrame(titulo);
        i.setName(titulo);
        i.add(component);
        i.show();
        i.addInternalFrameListener(new InternalFrameEvent());
        TEMPLATE.addInternalFrame(i);
        i.requestFocus();
        i.toFront();
        return i;
    }

    private class InternalFrameEvent extends InternalFrameAdapter {

        @Override
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
            if (e.getInternalFrame().isIcon()) {
                TEMPLATE.getFondo().remove(e.getInternalFrame().getDesktopIcon());
            } else {
                TEMPLATE.getFondo().remove(e.getInternalFrame());

            }
            TEMPLATE.repaint();
        }

    }
}
