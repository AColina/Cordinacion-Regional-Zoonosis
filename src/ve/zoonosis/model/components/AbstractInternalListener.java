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
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.event.InternalFrameAdapter;
import ve.zoonosis.vistas.Template;

/**
 *
 * @author clases
 */
public abstract class AbstractInternalListener {

    public InternalFrame crearInternalFrame(String titulo,
            Class<? extends JComponent> clase, Object... param) {

        for (Component component : Template.INSTANCE.getContentPane().getComponents()) {
            if (component instanceof InternalFrame) {
                if (component.getName().equalsIgnoreCase(titulo)) {
                    component.requestFocus();
                    ((InternalFrame) component).toFront();
                    return (InternalFrame) component;
                }
            }
        }

        JComponent component = (JComponent) ReflectionUtils.newInstance(clase, param);
        InternalFrame i = new InternalFrame(titulo);
        i.setName(titulo);
        i.add(component);
        i.show();
        i.addInternalFrameListener(new InternalFrameEvent());
        Template.INSTANCE.add(i);
        i.requestFocus();
        i.toFront();
        return i;
    }

    private class InternalFrameEvent extends InternalFrameAdapter {

        @Override
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
            Template.INSTANCE.remove(e.getInternalFrame());
            Template.INSTANCE.repaint();
        }

    }
}
