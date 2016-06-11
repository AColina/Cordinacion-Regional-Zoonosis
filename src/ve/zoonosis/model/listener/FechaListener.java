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

import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.JButton;

/**
 *
 * @author angel.colina
 */
public class FechaListener implements PropertyChangeListener {

    private final JDateChooser desde;
    private final JDateChooser hasta;
    private final JButton button;

    private FechaListener(JDateChooser desde, JDateChooser hasta, JButton button) {
        this.desde = desde;
        this.hasta = hasta;
        this.button = button;
        this.desde.addPropertyChangeListener(FechaListener.this);
        this.hasta.addPropertyChangeListener(FechaListener.this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("date")) {
            if (desde.equals(evt.getSource())) {
                button.setEnabled((evt.getNewValue() != null && hasta.getDate() != null)
                        ? (((Date) evt.getNewValue()).compareTo(hasta.getDate()) < 0)
                        : true);
            } else {
                button.setEnabled((evt.getNewValue() != null && desde.getDate() != null)
                        ? (((Date) evt.getNewValue()).compareTo(desde.getDate()) > 0)
                        : true);
            }
        }
    }

    public static FechaListener createListener(JDateChooser desde, JDateChooser hasta, JButton button) {
        return new FechaListener(desde, hasta, button);
    }
}
