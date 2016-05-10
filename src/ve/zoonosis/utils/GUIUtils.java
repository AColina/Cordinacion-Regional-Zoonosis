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
package ve.zoonosis.utils;

import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

/**
 * Common GUI utilities accessed via static methods.
 *
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public class GUIUtils {

    public static void moveToFront(final JInternalFrame fr) {
        if (fr != null) {
            processOnSwingEventThread(new Runnable() {
                public void run() {
                    fr.moveToFront();
                    fr.setVisible(true);
                    try {
                        fr.setSelected(true);
                        if (fr.isIcon()) {
                            fr.setIcon(false);
                        }
                        fr.setSelected(true);
                    } catch (PropertyVetoException ex) {

                    }
                    fr.requestFocus();
                }
            });
        }

    }

    public static void processOnSwingEventThread(Runnable todo) {
        processOnSwingEventThread(todo, false);
    }

    public static void processOnSwingEventThread(Runnable todo, boolean wait) {
        if (todo == null) {
            throw new IllegalArgumentException("Runnable == null");
        }

        if (wait) {
            if (SwingUtilities.isEventDispatchThread()) {
                todo.run();
            } else {
                try {
                    SwingUtilities.invokeAndWait(todo);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            if (SwingUtilities.isEventDispatchThread()) {
                todo.run();
            } else {
                SwingUtilities.invokeLater(todo);
            }
        }
    }
}
